/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airqnetworks.snet;

/**
 *
 * @author cnoviello
 */
public class AIRQ101Message extends DataMessage {
    public AIRQ101Message(byte[] message, int len, Driver driver) {
        super(message, len, driver);
    }
}
