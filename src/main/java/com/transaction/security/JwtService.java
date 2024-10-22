package com.transaction.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.transaction.configurations.AppConfigurationProperties;
import com.transaction.configurations.CachingConfig;
import com.transaction.repository.UserRepository;
import com.transaction.entity.Users;
import com.transaction.enums.GenericStatus;
import com.transaction.utils.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;


/**
 * @author Iheanyi Eleke
 * email:iheanyi.eleke@gmail.com
 * May 2024
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final AppConfigurationProperties appConfigurationProperties;
    private final UserRepository userRepository;
    private final CachingConfig cachingConfig;
    public Users user;
    public String generateJwtToken(Long id){
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(appConfigurationProperties.getJwtSecret());
            token = JWT.create()
                    .withClaim("userId", id)
                    .withExpiresAt((new Date((new Date()).getTime() + appConfigurationProperties.getJwtExpiration() * 1000L)))
                    .withIssuer("")
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            exception.printStackTrace();
        }
        return token;
    }

    public String detokenizeJwtToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(appConfigurationProperties.getJwtSecret());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("")
                .build();
        DecodedJWT jwt = verifier.verify(token);

        requestPrincipal(jwt.getClaim("userId").asLong());

        return userRepository.findByIdAndStatus(jwt.getClaim("userId").asLong(), GenericStatus.ACTIVE).map(Users::getUsername).orElseThrow(()->
                new ErrorResponse(HttpStatus.BAD_REQUEST, "Unable to validate user's details"));
    }

    public void invalidateToken(Long id){
        Objects.requireNonNull(cachingConfig.cacheManager().getCache("tokens")).evictIfPresent(id);
    }

    private void requestPrincipal(Long id){
        this.user = userRepository.findByIdAndStatus(id, GenericStatus.ACTIVE).orElse(null);
    }

    public boolean validateJwtToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(appConfigurationProperties.getJwtSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Claim userId = jwt.getClaim("userId");
            if (cachingConfig.cacheManager().getCache("tokens").get(userId.asLong(), (String::new)).equals("")){
                return false;
            }
            log.info("USER ID: " + userId);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature -> Message: {} ", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token -> Message: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            Objects.requireNonNull(cachingConfig.cacheManager().getCache("tokens")).evictIfPresent(user.getId());
            log.error("Expired JWT token -> Message: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token -> Message: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty -> Message: {}", e.getMessage());
        }
        return false;
    }


}
