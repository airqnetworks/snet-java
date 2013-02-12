/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airqnetworks.snet;

/**
 *
 * @author cnoviello
 */
public class DataMessage {
    protected static int MSG_PREAMBLE       = 0; /* AIRQ preamble */
    protected static int MSG_PREAMBLE_LEN   = 4;
    protected static int MSG_DID            = 9; /* Device ID */
    protected static int MSG_DID_LEN        = 4;
    protected static int MSG_PKT            = 14;/* Packet number */
    protected static int MSG_PKT_LEN        = 1;
    protected static int MSG_RSSI           = 15; /* RSSI */
    protected static int MSG_RSSI_LEN       = 1;
    protected static int MSG_LQI            = 16; /* LQI*/
    protected static int MSG_LQI_LEN        = 1;
    
    protected Driver driver = null;
    protected byte[] rawmessage = null;
    public int length = 0;
    
    public DataMessage(byte[] message, int len, Driver driver) {
        this.driver = driver;
        this.rawmessage = message;
        this.length = len;
    }
    
    public static Boolean checkMessage(byte[] message, int len) {
        String preamble;
        
        if(len >= 19) /* Data messages are at least 19 byte long */
        {
            preamble = new String(message, 0, 4);
            if(!preamble.equals("AIRQ"))
                return false;
            else
                return true;
        }
        return false;
    }
    
    public byte[] getDeviceID() {
        byte[] devaddr = new byte[4];
        for(int i = DataMessage.MSG_DID; i < DataMessage.MSG_DID + DataMessage.MSG_DID_LEN; i++)
            devaddr[i - DataMessage.MSG_DID] = this.rawmessage[i];
        return devaddr;
    }
    
    public int getPacketNumber() {
        return this.rawmessage[DataMessage.MSG_PKT];
    }
    
    public int getRSSI() {
        return this.rawmessage[DataMessage.MSG_RSSI];
    }
    
    
}  

