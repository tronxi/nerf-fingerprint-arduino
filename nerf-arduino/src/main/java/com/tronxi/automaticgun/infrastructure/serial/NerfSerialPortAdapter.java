package com.tronxi.automaticgun.infrastructure.serial;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NerfSerialPortAdapter {

    private final SerialPort nerfSerialPort;

    @Autowired
    public NerfSerialPortAdapter(SerialPort nerfSerialPort) {
        this.nerfSerialPort = nerfSerialPort;
    }

    public void startRotor() {
        try {
            nerfSerialPort.getOutputStream().write("a".getBytes());
            nerfSerialPort.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void endRotor() {
        try {
            nerfSerialPort.getOutputStream().write("q".getBytes());
            nerfSerialPort.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shoot() {
        try {
            nerfSerialPort.getOutputStream().write("s".getBytes());
            nerfSerialPort.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
