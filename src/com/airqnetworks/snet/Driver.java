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
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author cnoviello
 */
public class Driver implements Runnable {

    private Thread innerThread = null;
    private Boolean wantClose = false;
    private Device device;
    public LinkedBlockingQueue<DataMessage> messages;
    
    public Driver(Device dev) {
        device = dev;
        messages = new LinkedBlockingQueue<DataMessage>();
    }
    
    public void start() {
        innerThread = new Thread(this, "Driver thread");
        innerThread.start();
        wantClose = false;
        try {
            device.connect();
        } catch (Exception ex) {
            
        }
    }

    public void stop() {
        wantClose = true;
        try {
            innerThread.join();
        } catch (InterruptedException ex) {
        
        }
        device.close();
    }

    /**
     * 
     * @param dev
     * @return 
     */
    protected int readSNETMessage(Device dev, byte[] message) throws Exception {
        int i = 0;
        int len = 0;
        Boolean checkEnd = false;
        byte[] serData = new byte[1];
        String preamble;
        
        while(!wantClose) {
            len = dev.read(serData);
            if(i >= message.length) /* Avoid buffer overrun */
                return -1;
            
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
        return -1;
    }
    
    public void onData() {

    }
    
    @Override
    public void run() {
        byte data[] = new byte[30];
        int len = 0;
        DataMessage message;
        String deviceid;
        while(!wantClose) {
            try {
                len = readSNETMessage(device, data);
                if(len > 0) {
                    message = MessageHandler.message(data, len, this);
                    messages.add(message);
                    onData();
                }
            } catch (IOException ex) {
                
            } catch (Exception ex) {
              ex.printStackTrace();
            }
            
        }
        System.out.println("Addio");
        
    }
}

