package com.spring_boot.projeto3.util;

import com.spring_boot.projeto3.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {
    public static AnimePutRequestBody createAnimePutRequestBody(){
        return AnimePutRequestBody.builder()
                .id(AnimeCreator.createValidAnime().getId())
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .build();
    }
}
