package com.spring_boot.projeto3.handler;

import com.spring_boot.projeto3.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponse> handlerMethodArgumentNotValidException
            (MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Invalid Fields")
                        .details("Check the field(s) error")
                        .developerMessage(exception.getClass().getName())
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionResponse> handlerBadRequestException
            (BadRequestException exception) {

        return new ResponseEntity<>(
                BadRequestExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception")
                        .details("Anime not found")
                        .developerMessage("The name in url has to be exact the same even capslock, double check to see if you missed any letter")
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomizedException.class)
    public ResponseEntity<CustomizedExceptionResponse> handlerCustomizedException
            (CustomizedException exception) {
        return new ResponseEntity<>(CustomizedExceptionResponse
                .builder()
                .message("Anime cannot start with Z")
                .build(),HttpStatus.BAD_REQUEST);
    }
}


