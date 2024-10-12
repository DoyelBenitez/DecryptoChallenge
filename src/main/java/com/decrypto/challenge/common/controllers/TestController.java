package com.decrypto.challenge.common.controllers;

import com.decrypto.challenge.common._core.utils.JacksonConverterUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author dbenitez
 */
@Tag(name = "Test", description = "Solo dev")
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(path = "/ping", produces = "application/vnd.api+json")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("pong");
    }
}
