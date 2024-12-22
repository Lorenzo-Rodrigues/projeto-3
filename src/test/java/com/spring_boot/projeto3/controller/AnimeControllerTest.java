package com.spring_boot.projeto3.controller;

import com.spring_boot.projeto3.model.Anime;
import com.spring_boot.projeto3.requests.AnimePostRequestBody;
import com.spring_boot.projeto3.requests.AnimePutRequestBody;
import com.spring_boot.projeto3.service.AnimeService;
import com.spring_boot.projeto3.util.AnimeCreator;
import com.spring_boot.projeto3.util.AnimePostRequestBodyCreator;
import com.spring_boot.projeto3.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {
    @InjectMocks
    private AnimeController animeController;

    @Mock
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeServiceMock.listAllPageable(ArgumentMatchers.any()))
                .thenReturn(animePage);
        BDDMockito.when(animeServiceMock.findByIdOrThrowException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());
        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());
        BDDMockito.doNothing().when(animeServiceMock).update(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("listAllPageable returns list of anime inside page object when successful")
    void listAllPageable_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeController.listAllPageable(null).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.toList().getFirst().getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful(){
        Long expectedId = AnimeCreator.createValidAnime().getId();
        Anime anime = animeController.findById(1).getBody();

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }
    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){
        Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();
        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }
    @Test
    @DisplayName("update updates anime when successful")
    void update_UpdatesAnime_WhenSuccessful(){
        Assertions.assertThatCode(()->animeController.update(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = animeController.update(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){
        Assertions.assertThatCode(()->animeController.delete(1L))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = animeController.delete(1L);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}