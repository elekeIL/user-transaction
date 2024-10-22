package com.transaction.controller;

import com.transaction.pojo.TokenPojo;
import com.transaction.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Response;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @GetMapping("/token")
    public ResponseEntity<TokenPojo> accessToken(){
        Long value = Long.valueOf(RandomStringUtils.randomNumeric(6));
        TokenPojo pojo = new TokenPojo();
        pojo.setToken(jwtService.generateJwtToken(value));
        return ResponseEntity.ok().body(pojo);
    }
}
