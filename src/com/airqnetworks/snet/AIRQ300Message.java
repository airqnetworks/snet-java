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

/**
 *
 * @author cnoviello
 */
public class AIRQ300Message extends DataMessage {
    protected static final byte IN1_MASK    = 0x1;
    protected static final byte IN2_MASK    = 0x2;
    protected static final byte IN3_MASK    = 0x4;
    protected static final byte IN4_MASK    = 0x8;
    protected static final byte RELAY1_MASK = 0x10;
    protected static final byte RELAY2_MASK = 0x20;
    protected static final byte POWER_MASK  = 0x40;
    private   static final int MSG_DATA_LEN = 1; /* Use getDataLen() instead of this */
            
    public AIRQ300Message(byte[] message, int len, SNETDriver driver) {
        super(message, len, driver);
    }
    
    public int getDataLen() {
        return AIRQ300Message.MSG_DATA_LEN;
    }
    
    public Boolean getIOStatus(byte mask) {
        Object data = getData(0);
        return (((Byte)data) & mask) == mask;
    }

    public Boolean getIN1() {
        return getIOStatus(IN1_MASK);
    }

    public Boolean getIN2() {
        return getIOStatus(IN2_MASK);
    }
    
    public Boolean getIN3() {
        return getIOStatus(IN3_MASK);
    }
    
    public Boolean getIN4() {
        return getIOStatus(IN1_MASK);
    }
    
    public Boolean getPOWER() {
        return getIOStatus(POWER_MASK);
    }
    
    public Boolean getRELAY1() {
        return getIOStatus(RELAY1_MASK);
    }
    
    public Boolean getRELAY2() {
        return getIOStatus(RELAY2_MASK);
    }

   
}
