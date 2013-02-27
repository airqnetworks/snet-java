/**
 * Software License Agreement
 *
 * (C)Copyright 2013, AirQ Networks s.r.l. (http://www.airqnetworks.com)
 * All rights reserved.
 *
 * AirQ Networks licenses to you the right to use, modify, copy, and
 * distribute this software/library when used in conjuction with an 
 * AirQ Networks trasceiver to interface AirQ Networks wireless devices
 * (transceivers, sensors, control boards and other devices produced 
 * by AirQ Networks). Other uses, either express or implied, are prohibited.
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
 */
	 
package com.airqnetworks.snet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cnoviello
 */
public class DataMessage {
    protected static final int MSG_PREAMBLE       = 0; /* AIRQ preamble */
    protected static final int MSG_PREAMBLE_LEN   = 4;
    protected static final int MSG_RCVID          = 4; /* Receiver ID */
    protected static final int MSG_RCVID_LEN      = 4;
    protected static final int MSG_TYPE           = 8; /* Message type */
    protected static final int MSG_TYPE_LEN       = 1;
    protected static final int MSG_DID            = 9; /* Device ID */
    protected static final int MSG_DID_LEN        = 4;
    protected static final int MSG_PKT            = 14; /* Packet number */
    protected static final int MSG_PKT_LEN        = 1;
    protected static final int MSG_FWD            = 15; /* Forward info */
    protected static final int MSG_FWD_LEN        = 1;
    protected static final int MSG_RSSI           = 16; /* RSSI */
    protected static final int MSG_RSSI_LEN       = 1;
    protected static final int MSG_LQI            = 17; /* LQI */
    protected static final int MSG_LQI_LEN        = 1;
    protected static final int MSG_DATA           = 18; /* DATA */
    private   static final int MSG_DATA_LEN       = 0; /* Use getDataLen() instead of this */
    
    protected static final int DATA_MESSAGE_LEN   = 20;
        
    protected SNETDriver driver = null;
    protected byte[] rawmessage = null;
    public int length = 0;
    
    public DataMessage(byte[] message, int len, SNETDriver driver) {
        this.driver = driver;
        this.rawmessage = message;
        this.length = len;
    }
    
    public static Boolean checkMessage(byte[] message, int len) {
        String preamble;
        
        if(len >= DATA_MESSAGE_LEN) /* Data messages are at least 19 byte long */
        {
            preamble = new String(message, 0, 4);
            if(!preamble.equals("AIRQ"))
                return false;
            else
                return true;
        }
        return false;
    }
    
    public int[] getDeviceID() {
        int[] devaddr = new int[4];
        for(int i = DataMessage.MSG_DID; i < DataMessage.MSG_DID + DataMessage.MSG_DID_LEN; i++)
            devaddr[i - DataMessage.MSG_DID] = 0x00FF & rawmessage[i];
        return devaddr;
    }

    public byte[] getData()
    {
        byte[] data = new byte[getDataLen()];
        for(int i = this.MSG_DATA; i < DataMessage.MSG_DATA + getDataLen(); i++)
            data[i-this.MSG_DATA] = rawmessage[i];        
        
        return data;
    }
    
    public byte getData(int i) throws java.lang.ArrayIndexOutOfBoundsException {
        if(i < getDataLen())
            return rawmessage[DataMessage.MSG_DATA + i];
        throw new java.lang.ArrayIndexOutOfBoundsException(i);
    }
    
    public int getDataLen()
    {
        return this.length - DATA_MESSAGE_LEN;
    }
    
    public int getDeviceType()
    {
        return ID2BORAD_TYPE(getDeviceID()[0]);
    }
    
    public int getPacketNumber() {
        /* Packet Number in sNET is a single byte ranging from 1..255. So, it's
         * an unsigned char in C language. We have to address the fact that
         * Java lacks for unsigned types.
         */
        int pkt = 0x00FF; /* This trick is requires since java lacks for unsigned types */
        pkt &= this.rawmessage[DataMessage.MSG_PKT];
        return pkt;
    }

    public Boolean getPOWER()
    {
        return false;
    }
    
    public int[] getReceiverID() {
        int[] rcvaddr = new int[4];
        for(int i = DataMessage.MSG_RCVID; i < DataMessage.MSG_RCVID + DataMessage.MSG_RCVID_LEN; i++)
            rcvaddr[i - DataMessage.MSG_RCVID] = 0x00FF & rawmessage[i];
        
        return rcvaddr;
    }

    public int getRSSI() {
        return rawmessage[DataMessage.MSG_RSSI];
    }
    
    private static int ID2BORAD_TYPE(int id) {
        switch(id) {
            case 3:
                return 300;
            case 4:
                return 310;
            default:
                return id;
        }
    }
    
    public int getFWD()
    {
        return rawmessage[DataMessage.MSG_FWD];
    }
    
    public String toString() {
        String output = "";
        output += "AIRQ" + Integer.toString(getDeviceType()) + " - ";
        for (int i = 0; i < 4; i++)
            output += Integer.toString(getReceiverID()[i]) + (i < 3 ? "." : "");
        
        output += ",";
        for (int i = 0; i < 4; i++)
            output += Integer.toString(getDeviceID()[i]) + (i < 3 ? "." : "");
        
        output += "," + Integer.toString(getPacketNumber());
        output += "," + Integer.toString(getRSSI()) + ",";
        for(int i = 0; i < getDataLen(); i++) {
            output += Integer.toHexString(getData(i)) + ",";
        }
        output += getPOWER() ? "POWER" : "BATTERY";
        output += "," + (getFWD() > 0 ? "FORWARDED" : "DIRECT");
        
        Date d = new Date();
        output += "," + d.toString();

        return output;
    }
}  

