package com.tronxi.automaticgun.infrastructure.resources;

import com.tronxi.automaticgun.infrastructure.serial.FingerprintSerialPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fingerprint")
@CrossOrigin(maxAge = 3600)
public class FingerprintController {

    private final FingerprintSerialPort fingerprintSerialPort;

    public FingerprintController(FingerprintSerialPort fingerprintSerialPort) {
        this.fingerprintSerialPort = fingerprintSerialPort;
    }

    @GetMapping("/enroll")
    public ResponseEntity<Void> enroll() {
        fingerprintSerialPort.enroll();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity<Void> check() {
        fingerprintSerialPort.check();
        return ResponseEntity.ok().build();
    }
}
