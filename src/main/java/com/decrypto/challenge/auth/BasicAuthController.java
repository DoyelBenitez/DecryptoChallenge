package com.decrypto.challenge.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author dbenitez
 */
@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
