package com.decrypto.challenge.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author dbenitez
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(path = "/ping")
    public ResponseEntity<?> test() {
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception error) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
