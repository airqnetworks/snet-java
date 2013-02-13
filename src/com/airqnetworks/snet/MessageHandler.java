/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airqnetworks.snet;

/**
 *
 * @author cnoviello
 */
public class MessageHandler {
    public static final int DEV_TYPE_AIRQ101 = 101;
    public static final int DEV_TYPE_AIRQ300 = 3;
            
    public static DataMessage message(byte[] rawmessage, int len, Driver driver)
    {
        DataMessage message = new DataMessage(rawmessage, len, driver);
        switch(message.getDeviceType())
        {
            case DEV_TYPE_AIRQ300:
                return new AIRQ300Message(rawmessage, len, driver);
                
            case DEV_TYPE_AIRQ101:
                return new AIRQ101Message(rawmessage, len, driver);
        }
        
        return message;        
    }
}
