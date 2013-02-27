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
public class MessageHandler {
    public static final int DEV_TYPE_AIRQ101 = 101;
    public static final int DEV_TYPE_AIRQ191 = 191;
    public static final int DEV_TYPE_AIRQ300 = 300;
            
    public static DataMessage message(byte[] rawmessage, int len, SNETDriver driver)
    {
        DataMessage message = new DataMessage(rawmessage, len, driver);
        switch(message.getDeviceType())
        {
            case DEV_TYPE_AIRQ300:
                return new AIRQ300Message(rawmessage, len, driver);
                
            case DEV_TYPE_AIRQ101:
            case DEV_TYPE_AIRQ191:
                return new AIRQ101Message(rawmessage, len, driver);
        }
        
        return message;        
    }
}
