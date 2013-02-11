import com.airqnetworks.snet.*;

import gnu.io.*;

public class Main {
    public static void main(String[] args) {
        SerialDevice device = new SerialDevice();
        device.listPorts();
    }

}
