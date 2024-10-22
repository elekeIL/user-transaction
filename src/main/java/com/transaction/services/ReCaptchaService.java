package com.transaction.services;

public interface ReCaptchaService {
    Boolean verifyCaptchaToken(String token);
}
