package com.spring_boot.projeto3.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CustomizedExceptionResponse {
    protected String message;
}
