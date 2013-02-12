package com.airqnetworks.snet;
/**
 * Prova di documentazione
 */


interface Device {
    public void connect() throws Exception;
    public void close();
    public int read(byte[] b) throws Exception;;
    public int write(byte[] b) throws Exception;;
}