package com.tronxi.automaticgun.configuration;

import com.fazecast.jSerialComm.SerialPort;
import com.tronxi.automaticgun.infrastructure.serial.SerialPortListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class SerialPortConfiguration {

    @Value("${serial.port.nerf}")
    private String portNerf;

    @Value("${serial.port.fingerprint}")
    private String portFingerprint;

    @Bean
    public SerialPortListener fingerprintSerialPortListener() {
        return new SerialPortListener();
    }

    @Bean
    public SerialPort nerfSerialPort() {
        SerialPort sp = SerialPort.getCommPort(portNerf);
        sp.setComPortParameters(9600, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
        sp.openPort();
        return sp;
    }

    @Bean
    @DependsOn({"nerfSerialPort"})
    public SerialPort fingerPrintSerialPort() {
        SerialPort sp = SerialPort.getCommPort(portFingerprint);
        sp.setComPortParameters(9600, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
        sp.addDataListener(fingerprintSerialPortListener());
        sp.openPort();
        return sp;
    }
}
