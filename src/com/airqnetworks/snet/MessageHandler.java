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
    public static DataMessage message(byte[] rawmessage, int len, Driver driver)
    {
        return new DataMessage(rawmessage, len, driver);        
    }
}
