package com.example.client.clientservice.Handler;

import com.example.client.clientservice.Exception.ClientException;
import com.example.client.clientservice.Exception.InvalidFieldException;
import com.example.client.clientservice.Exception.JwtException;
import com.example.client.clientservice.Exception.UnauthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ClientExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        Map<String,Map> group = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        group.put("error",errors);
        return new ResponseEntity<>(
                group,new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {ClientException.class})
    public ResponseEntity<?> handleTransfersException(ClientException ex , WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return new ResponseEntity<>(
                errors,new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {SQLException.class})
    public ResponseEntity<?> handleSQLException(SQLException ex , WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        String message=ex.getMessage();
        if(ex.getMessage().contains("unique_email")){
            message = "Email already exist !";
        }

        if(ex.getMessage().contains("unique_idCard")){
            message = "CIN already exist !";
        }

        if(ex.getMessage().contains("unique_phoneNumber")){
            message = "Phone number already exist !";
        }
        errors.put("error", message);
        return new ResponseEntity<>(
                errors,new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex , WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        String message=ex.getMessage();
        errors.put("error", message);
        return new ResponseEntity<>(
                errors,new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJWTException(JwtException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Unauthorized.");
        return new ResponseEntity<>(
                errors,new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Unauthenticated.");
        return new ResponseEntity<>(
                errors,new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<?> handleInvalidFieldException(InvalidFieldException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "invalid data !");
        return new ResponseEntity<>(
                errors,new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


}
