import com.airqnetworks.snet.*;

import gnu.io.*;
import java.util.Enumeration;

public class Main {
    public static void main(String[] args) throws Exception{
        byte buffer[] = new byte[1024];
        int len = 0;
        int counter = 0;
        
        Enumeration<CommPortIdentifier> ports = SerialDevice.listPorts();
        while(ports.hasMoreElements()) {
            System.out.println(SerialDevice.getPortDescription(ports.nextElement()));
        }
        
        SerialDevice port = new SerialDevice("/dev/tty.usbserial-FTC8JJO1");
        Driver drv = new Driver(port);
        drv.start();
        while(counter < 5) {
            try {
                while (true) {
                    DataMessage message = drv.messages.remove();
                    System.out.println("Len: " + message.length);
                    System.out.println("RSSI: " + message.getRSSI());                    
                    System.out.println("Packet Number: " + message.getPacketNumber());
                    System.out.println("Device ID: " + new String(message.getDeviceID(), 0, 4));                    
                    System.out.println("Device Type: " + message.getDeviceType());
                }
            } catch (java.util.NoSuchElementException e) {
                
            }
            counter++;
            Thread.sleep(1000);
        }
        drv.stop();
    }

}
