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
        
        SerialDevice port = new SerialDevice("/dev/tty.usbserial");
        Driver drv = new Driver(port);
        drv.start();
        while(counter < 5) {
            counter++;
            Thread.sleep(1000);
        }
        drv.stop();
    }

}
