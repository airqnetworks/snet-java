/**
 * (C)Copyright 2013, AirQ Networks s.r.l. (http://www.airqnetworks.com)
 * 
 * This software is part of the sNET adapter for Java programming language.
 * For more information about sNET protocol, see 
 * http://wiki.airqnetworks.com/index.php/SNET_Protocol
 * 
 * 
 * This software is released under the terms of the
 * GNU LGPL license. See http://www.gnu.org/licenses/lgpl.html
 * for more information.
 */

package com.airqnetworks.snet;

import gnu.io.*;
import java.util.*;
import java.lang.*;

/**
 * SerialDevices class abstracts the access to a serial interface. SerialDevice
 * implements {@link Device} interface and it's based on the RxTx library (http://rxtx.qbang.org)
  */
public class SerialDevice implements Device
{
    public void SerialDevice() {
        
    }
    /**
     * This is a convinient method to list all available serial ports. This 
     * method is expecially useful during development process or to provide
     * a configuration interface for the user.
     * 
     * @return an enumeration containing all port detected. For more information
     * about {@link CommPortIndetifier} class, refer to RxTX library
     */
    public static Enumeration<CommPortIdentifier> listPorts()
    {
        return CommPortIdentifier.getPortIdentifiers();
    }
    
    /**
     * 
     * @param port - sdasd
     * @return 
     */
    public static String getPortDescription ( CommPortIdentifier port )
    {
        String portType;
        
        switch ( port.getPortType() )
        {
            case CommPortIdentifier.PORT_I2C:
                portType = "I2C";
                break;
            case CommPortIdentifier.PORT_PARALLEL:
                portType = "Parallel";
                break;
            case CommPortIdentifier.PORT_RAW:
                portType = "Raw";
                break;
            case CommPortIdentifier.PORT_RS485:
                portType = "RS485";
                break;
            case CommPortIdentifier.PORT_SERIAL:
                portType = "Serial";
                break;
            default:
                portType = "unknown type";
        }
        
        return port.getName() + " - " + portType;
    }

    public void open()
    {
    }
    public void close()
    {
    }
    public void read()
    {
    }
    public void write()
    {
    }
    
}