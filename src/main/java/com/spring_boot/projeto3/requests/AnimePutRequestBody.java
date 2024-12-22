package com.spring_boot.projeto3.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AnimePutRequestBody(Long id, @NotBlank(message = "cannot be empty") String name) {

}
