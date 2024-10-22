package com.transaction.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = true)
@Getter
@Setter
public class AppConfigurationProperties {
    private String jwtSecret;
    private int jwtExpiration;
    private String secretSalt;
    private String tinValidationUrl;
    private String googleBaseUrl;
    private String reCaptchaSecretKey;
}
