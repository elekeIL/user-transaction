package com.transaction.controlleradvice;

import com.transaction.utils.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Optional;

/**
 * @author Eleke Iheanyi
 * email: iheanyi.eleke@gmail.com
 * May, 2024
 **/
@ControllerAdvice
@Slf4j
public class OptionalResponseControllerAdvice implements ResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getParameterType().equals(Optional.class);
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
//        logger.info("checking response body for {}", request.getURI().toString());
        if (body instanceof Optional) {
            return ((Optional<?>) body).orElseThrow(() -> new ErrorResponse(HttpStatus.BAD_REQUEST, "An error occurred"));
        }
        return body;
    }
}
