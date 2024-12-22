package com.spring_boot.projeto3.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AnimePostRequestBody(@NotBlank(message = "cannot be empty") String name){

}

