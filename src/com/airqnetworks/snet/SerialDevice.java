/**
 * (C)Copyright 2013, AirQ Networks s.r.l. (http://www.airqnetworks.com)
 *
 * This software is part of the sNET adapter for Java programming language. For
 * more information about sNET protocol, see
 * http://wiki.airqnetworks.com/index.php/SNET_Protocol
 *
 *
 * This software is released under the terms of the GNU LGPL license. See
 * http://www.gnu.org/licenses/lgpl.html for more information.
 */
package com.airqnetworks.snet;

import gnu.io.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SerialDevices class abstracts the access to a serial interface. SerialDevice
 * implements {@link Device} interface and it's based on the RxTx library
 * (http://rxtx.qbang.org)
 */
public class SerialDevice implements Device {

    /**
     * This is a convinient method to list all available serial ports. This
     * method is expecially useful during development process or to provide a
     * configuration interface for the user.
     *
     * @return an enumeration containing all port detected. For more information
     * about {@link CommPortIndetifier} class, refer to RxTX library
     */
    public static Enumeration<CommPortIdentifier> listPorts() {
        return CommPortIdentifier.getPortIdentifiers();
    }

    /**
     * Return a description of the give port idenditifer (name + port type).
     * 
     * @param port - Serial port
     * @return
     */
    public static String getPortDescription(CommPortIdentifier port) {
        String portType;

        switch (port.getPortType()) {
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

    private String portName;
    private CommPortIdentifier portIdentifer;
    protected SerialPort port;
    private InputStream in;
    private OutputStream out;    
    private int baudrate;
    private int timeout;
    
    public SerialDevice(String port) throws NoSuchPortException {
        this.portName = port;
        this.baudrate = 19200;
        this.timeout = 2000;
        this.portIdentifer = CommPortIdentifier.getPortIdentifier(this.portName);
    }

    public SerialDevice(String port, int baudrate) throws NoSuchPortException {
        this(port);
        this.baudrate = 19200;
    }
    
    public SerialDevice(String port, int baudrate, int timeout) throws NoSuchPortException {
        this(port, baudrate);
        this.timeout = timeout;
    }

    public void connect() throws PortInUseException, IOException {
        if(!this.portIdentifer.isCurrentlyOwned())
        {
           port = (SerialPort)this.portIdentifer.open(this.getClass().getName(), this.timeout);
            try {
                port.setSerialPortParams(this.baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                in = port.getInputStream();
                out = port.getOutputStream();
                
            } catch (UnsupportedCommOperationException ex) {
            }
        } else
        {
            
        }
    }

    public void close() {
        port.close();
    }

    public int read(byte[] b) throws IOException{
        try {
            return in.read(b);
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public int write(byte[] b) {
        return 0;
    }
}