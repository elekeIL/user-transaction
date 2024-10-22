package com.transaction.controlleradvice;

import com.transaction.utils.response.ApiResponse;
import com.transaction.utils.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eleke Iheanyi
 * email: iheanyi.eleke@gmail.com
 * May, 2024
 **/
@ControllerAdvice
public class ErrorControllerAdvice {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MessageSource messageSource;
    private ObjectError allError;

    public ErrorControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
    }



    @ExceptionHandler(ErrorResponse.class)
    public ResponseEntity<?> handle(ErrorResponse e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(e.getApiResponse().getCode()).body(e.getApiResponse());
    }



    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex,
            WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + " : " + violation.getMessage());
        }
        ApiResponse<List<String>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(400);
        apiResponse.setMessage(errors.size() > 0 ? errors.get(0) : "Invalid request");
        apiResponse.setData(errors);
        return new ResponseEntity<Object>(apiResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError allError : ex.getBindingResult().getFieldErrors()) {
            errors.add(allError.getField() + ": " + allError.getDefaultMessage());
        }
        ApiResponse<List<String>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(400);
        apiResponse.setMessage(errors.size() > 0 ? errors.get(0) : "Invalid request");
        apiResponse.setData(errors);
        return new ResponseEntity<Object>(apiResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handle(Exception e) {
        e.printStackTrace();
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Unknown error");
        apiResponse.setCode(500);
        return  ResponseEntity.status(500).body(apiResponse);
    }
}
