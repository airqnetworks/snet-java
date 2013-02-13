/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airqnetworks.snet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cnoviello
 */
public class SerialDeviceTest {
    
    public SerialDeviceTest() {
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.junit.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of SerialDevice method, of class SerialDevice.
     */
    @org.junit.Test
    public void testSerialDevice() {
        System.out.println("SerialDevice");
        SerialDevice instance = new SerialDevice();
        instance.SerialDevice();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listPorts method, of class SerialDevice.
     */
    @org.junit.Test
    public void testListPorts() {
        System.out.println("listPorts");
        SerialDevice.listPorts();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPortTypeName method, of class SerialDevice.
     */
    @org.junit.Test
    public void testGetPortTypeName() {
        System.out.println("getPortTypeName");
        int portType = 0;
        String expResult = "";
        String result = SerialDevice.getPortTypeName(portType);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of open method, of class SerialDevice.
     */
    @org.junit.Test
    public void testOpen() {
        System.out.println("open");
        SerialDevice instance = new SerialDevice();
        instance.open();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class SerialDevice.
     */
    @org.junit.Test
    public void testClose() {
        System.out.println("close");
        SerialDevice instance = new SerialDevice();
        instance.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of read method, of class SerialDevice.
     */
    @org.junit.Test
    public void testRead() {
        System.out.println("read");
        SerialDevice instance = new SerialDevice();
        instance.read();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of write method, of class SerialDevice.
     */
    @org.junit.Test
    public void testWrite() {
        System.out.println("write");
        SerialDevice instance = new SerialDevice();
        instance.write();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
