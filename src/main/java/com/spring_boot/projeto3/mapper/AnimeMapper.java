package com.spring_boot.projeto3.mapper;

import com.spring_boot.projeto3.model.Anime;
import com.spring_boot.projeto3.requests.AnimePostRequestBody;
import com.spring_boot.projeto3.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimeMapper {
    Anime ToAnime(AnimePostRequestBody animePostRequestBody);

    Anime ToAnime (AnimePutRequestBody animePutRequestBody);
}
