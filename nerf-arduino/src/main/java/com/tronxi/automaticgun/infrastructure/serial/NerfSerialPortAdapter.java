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

    public void shoot() {
        try {
            nerfSerialPort.openPort();
            while(!nerfSerialPort.isOpen()){}
            nerfSerialPort.getOutputStream().write("s".getBytes());
            nerfSerialPort.getOutputStream().flush();
            //nerfSerialPort.closePort();
        } catch (IOException e) {
            //nerfSerialPort.closePort();
            e.printStackTrace();
        }
    }
}
