package com.tronxi.automaticgun.infrastructure.serial;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FingerprintSerialPort {

    private final SerialPort fingerPrintSerialPort;

    public FingerprintSerialPort(SerialPort fingerPrintSerialPort) {
        this.fingerPrintSerialPort = fingerPrintSerialPort;
    }

    public void enroll() {
        try {
            fingerPrintSerialPort.getOutputStream().write("e".getBytes());
            fingerPrintSerialPort.getOutputStream().flush();
            fingerPrintSerialPort.getOutputStream().write("1".getBytes());
            fingerPrintSerialPort.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void check() {
        try {
            fingerPrintSerialPort.getOutputStream().write("c".getBytes());
            fingerPrintSerialPort.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
