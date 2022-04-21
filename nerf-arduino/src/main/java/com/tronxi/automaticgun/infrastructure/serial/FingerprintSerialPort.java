package com.tronxi.automaticgun.infrastructure.serial;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FingerprintSerialPort {

    private final SerialPort fingerPrintSerialPort;

    public FingerprintSerialPort(SerialPort fingerPrintSerialPort) {
        this.fingerPrintSerialPort = fingerPrintSerialPort;
    }

    public void enroll()  {
        try {
            fingerPrintSerialPort.openPort();
            //while(!fingerPrintSerialPort.isOpen()){}
            fingerPrintSerialPort.getOutputStream().write("e".getBytes());
            fingerPrintSerialPort.getOutputStream().flush();
            String nextPosition = getNextPosition();
            System.out.println(nextPosition);
            fingerPrintSerialPort.getOutputStream().write(nextPosition.getBytes());
            fingerPrintSerialPort.getOutputStream().flush();
            Integer updatedNextPosition = Integer.parseInt(nextPosition) + 1;
            setNextPosition(updatedNextPosition);
            //fingerPrintSerialPort.closePort();
        } catch (IOException e) {
            //fingerPrintSerialPort.closePort();
            throw new RuntimeException();
        }
    }

    public void check() {
        try {
            fingerPrintSerialPort.openPort();
            //while(!fingerPrintSerialPort.isOpen()){}
            fingerPrintSerialPort.getOutputStream().write("c".getBytes());
            fingerPrintSerialPort.getOutputStream().flush();
            //fingerPrintSerialPort.closePort();
        } catch (IOException e) {
            //fingerPrintSerialPort.closePort();
            e.printStackTrace();
        }
    }

    private String getNextPosition() {
        try {
            return Files.readString(Path.of("position.txt")).replaceAll("\\n", "").replaceAll("\"", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setNextPosition(Integer position) {
        String ps = position.toString();
        try {
            Files.write(Path.of("position.txt"), ps.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
