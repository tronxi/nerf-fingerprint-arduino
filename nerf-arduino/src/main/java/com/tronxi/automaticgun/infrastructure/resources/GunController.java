package com.tronxi.automaticgun.infrastructure.resources;

import com.tronxi.automaticgun.infrastructure.serial.NerfSerialPortAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gun")
@CrossOrigin(maxAge = 3600)
public class GunController {

    private final NerfSerialPortAdapter nerfSerialPortAdapter;

    @Autowired
    public GunController(NerfSerialPortAdapter nerfSerialPortAdapter) {
        this.nerfSerialPortAdapter = nerfSerialPortAdapter;
    }

    @GetMapping("/shoot")
    public ResponseEntity<Void> shoot() {
        nerfSerialPortAdapter.shoot();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/start")
    public ResponseEntity<Void> start() {
        nerfSerialPortAdapter.startRotor();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stop")
    public ResponseEntity<Void> stop() {
        nerfSerialPortAdapter.endRotor();
        return ResponseEntity.ok().build();
    }

}
