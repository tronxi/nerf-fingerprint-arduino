package com.tronxi.automaticgun.infrastructure.serial;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class SerialPortListener implements SerialPortMessageListener {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Override
    public byte[] getMessageDelimiter() {
        return "\n".getBytes();
    }

    @Override
    public boolean delimiterIndicatesEndOfMessage() {
        return true;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        String message = new String(serialPortEvent.getReceivedData());
        System.out.println(message);
        simpMessagingTemplate.convertAndSend("/topic/message", message);
    }
}
