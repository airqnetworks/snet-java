package com.airqnetworks.snet;

interface Device {
    public void open();
    public void close();
    public void read();
    public void write();
}