package com.spring_boot.projeto3.util;

import com.spring_boot.projeto3.model.Anime;

public class AnimeCreator {
    public static Anime createValidAnime(){
        return Anime.builder()
                .name("Baki 2")
                .id(1L)
                .build();
    }
}
