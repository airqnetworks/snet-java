/**
 * Software License Agreement
 *
 * (C)Copyright 2013, AirQ Networks s.r.l. (http://www.airqnetworks.com)
 * All rights reserved.
 *
 * AirQ Networks licenses to you the right to use, modify, copy, and
 * distribute this software/library when used in conjuction with an 
 * AirQ Networks trasceiver to interface AirQ Networks wireless devices
 * (sensors, control boards and other devices produced by AirQ Networks).
 *
 * THE SOFTWARE AND DOCUMENTATION ARE PROVIDED "AS IS" WITHOUT
 * WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT
 * LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO EVENT SHALL
 * AIRQ NETWORKS BE LIABLE FOR ANY INCIDENTAL, SPECIAL, INDIRECT OR
 * CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, COST OF
 * PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR SERVICES, ANY CLAIMS
 * BY THIRD PARTIES (INCLUDING BUT NOT LIMITED TO ANY DEFENSE
 * THEREOF), ANY CLAIMS FOR INDEMNITY OR CONTRIBUTION, OR OTHER
 * SIMILAR COSTS, WHETHER ASSERTED ON THE BASIS OF CONTRACT, TORT
 * (INCLUDING NEGLIGENCE), BREACH OF WARRANTY, OR OTHERWISE.
 *
 *
 * Author               Date    Comment
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Carmine Noviello    13/2/13	Original
 */

package com.airqnetworks.snet;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author cnoviello
 */
public class SNETDriver implements Runnable {

    class CallListener implements Runnable {
        private SNETDriverEventListener listener;
        private DataMessage message;
        private SNETDriver driver;
        
        public CallListener(SNETDriverEventListener listener, SNETDriver driver, DataMessage message)
        {
            this.listener = listener;
            this.message = message;
            this.driver = driver;
        }
        
        public void run() {
            listener.driverEvent(new SNETDriverEvent(driver, message));
        }
        
    }    

    private Thread innerThread = null;
    private Boolean wantClose = false;
    private Device device;
    public LinkedBlockingQueue<DataMessage> messages;
    private List<SNETDriverEventListener> listeners;
    
    public SNETDriver(Device dev) {
        device = dev;
        messages = new LinkedBlockingQueue<DataMessage>();
        listeners = new ArrayList<SNETDriverEventListener>();
    }
    
    public void addEventListener(SNETDriverEventListener listener)
    {
        listeners.add(listener);
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

    public void removeEventListener(SNETDriverEventListener listener)
    {
        listeners.remove(listener);
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
                    for(int i=0; i<listeners.size(); i++)
                    {
                        Thread listenerThread = new Thread(new CallListener(listeners.get(i), this, message));
                        listenerThread.start();
                    }
                }
            } catch (IOException ex) {
                
            } catch (Exception ex) {
              ex.printStackTrace();
            }
            
        }
        System.out.println("Addio");
        
    }
}

