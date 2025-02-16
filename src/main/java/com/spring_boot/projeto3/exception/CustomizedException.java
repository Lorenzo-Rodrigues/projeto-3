package com.spring_boot.projeto3.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomizedException extends RuntimeException{
    public CustomizedException(){

    }

}
