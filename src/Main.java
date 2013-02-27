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

import com.airqnetworks.snet.*;

import gnu.io.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class Main implements SNETDriverEventListener {
    public static void main(String[] args) throws Exception{
        byte buffer[] = new byte[1024];
        int len = 0;
        int counter = 0;
        
        Enumeration<CommPortIdentifier> ports = SerialDevice.listPorts();
        while(ports.hasMoreElements()) {
            System.out.println(SerialDevice.getPortDescription(ports.nextElement()));
        }
        
        SerialDevice port = new SerialDevice("/dev/tty.usbserial");//-FTC8JJO1");
        SNETDriver drv = new SNETDriver(port);
        drv.addEventListener(new Main());
        drv.start();
        while(counter < 10) {
            counter++;
            Thread.sleep(1000);
        }
        drv.stop();
    }
    
    public void driverEvent(SNETDriverEvent evt) {
        DataMessage message = evt.getMessage();
        System.out.println(((AIRQ101Message)message).getTEMP());
        System.out.println(((AIRQ101Message)message).getBATT());        
    }

}
