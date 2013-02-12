/**
 * (C)Copyright 2013, AirQ Networks s.r.l. (http://www.airqnetworks.com)
 *
 * This software is part of the sNET adapter for Java programming language. For
 * more information about sNET protocol, see
 * http://wiki.airqnetworks.com/index.php/SNET_Protocol
 *
 *
 * This software is released under the terms of the GNU LGPL license. See
 * http://www.gnu.org/licenses/lgpl.html for more information.
 */

package com.airqnetworks.snet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cnoviello
 */
public class Driver implements Runnable {

    private Thread innerThread = null;
    private Boolean wantClose = false;
    private Device device;
    
    public Driver(Device dev) {
        this.device = dev;
    }
    
    public void start() {
        this.innerThread = new Thread(this, "Driver thread");
        this.innerThread.start();
        this.wantClose = false;
        try {
            this.device.connect();
        } catch (Exception ex) {
            
        }
    }

    public void stop() {
        this.wantClose = true;
        try {
            this.innerThread.join();
        } catch (InterruptedException ex) {
        
        }
        this.device.close();
    }

    /**
     * 
     * @param dev
     * @return 
     */
    protected static int readSNETMessage(Device dev, byte[] message) throws Exception {
        int i = 0;
        int len = 0;
        Boolean checkEnd = false;
        byte[] serData = new byte[1];
        String preamble;
        
        while(true) {
            len = dev.read(serData);
            if(len > 0) {
                message[i] = serData[0];
                if(serData[0] == '\n') {
                    if(i > 0 && message[i-1] == '$') {
                        /* 
                         * EOL Found. Let's check if at least preamble is correct 
                         */
                        if(i >= 18) { /* Data messages in sNET v4 can't be less then 19 bytes */
                            preamble = new String(message, 0, 4);
                            if(preamble.equals("AIRQ"))
                                return ++i;
                        }
                        i = -1; /* Invalid message: discarding */
                    }
                }
                i++;
            }
        }
    }
    
    @Override
    public void run() {
        byte data[] = new byte[30];
        int len = 0;
        DataMessage message;
        String deviceid;
        while(!this.wantClose) {
            try {
                len = Driver.readSNETMessage(this.device, data);
                if(len > 0) {
                    message = MessageHandler.message(data, len, this);
                    System.out.println("Len: " + message.length);
                    System.out.println("RSSI: " + message.getRSSI());                    
                    System.out.println("Packet Number: " + message.getPacketNumber());
                    System.out.println("Device ID: " + new String(message.getDeviceID(), 0, 4));                    
                    System.out.println("Device ID: " + message.getDeviceID()[0]);
                }
            } catch (IOException ex) {
                
            } catch (Exception ex) {
              ex.printStackTrace();
            }
            
        }
        System.out.println("Addio");
        
    }
}

