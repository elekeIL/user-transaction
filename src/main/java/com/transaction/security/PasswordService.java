package com.transaction.security;

import com.transaction.configurations.AppConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordService {
    private final AppConfigurationProperties appConfigurationProperties;

    public String hashPassword(String password){
       return BCrypt.hashpw(password, appConfigurationProperties.getSecretSalt());
    }

    public Boolean comparePassword(String userPassword, String providedPassword){
       String encryptedPassword = hashPassword(providedPassword);
       return userPassword.equals(encryptedPassword);
    }
}
