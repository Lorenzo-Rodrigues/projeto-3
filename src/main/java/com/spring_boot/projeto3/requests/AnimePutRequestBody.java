package com.spring_boot.projeto3.requests;

import jakarta.validation.constraints.NotBlank;

public record AnimePutRequestBody(Long id, @NotBlank(message = "cannot be empty") String name) {

}
