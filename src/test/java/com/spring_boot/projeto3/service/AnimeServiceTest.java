package com.spring_boot.projeto3.service;

import com.spring_boot.projeto3.mapper.AnimeMapper;
import com.spring_boot.projeto3.model.Anime;
import com.spring_boot.projeto3.repository.AnimeRepository;
import com.spring_boot.projeto3.util.AnimeCreator;
import com.spring_boot.projeto3.util.AnimePostRequestBodyCreator;
import com.spring_boot.projeto3.util.AnimePutRequestBodyCreator;
import org.apache.coyote.BadRequestException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepositoryMock;

    @Mock
    private AnimeMapper animeMapperMock;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }

    @Test
    @DisplayName("listAllPageable returns list of anime inside page object when successful")
    void listAllPageable_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeService.listAllPageable(PageRequest.of(1,1));

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.toList().getFirst().getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findByIdOrThrowException returns anime when successful")
    void findByIdOrThrowException_ReturnsAnime_WhenSuccessful(){
        Long expectedId = AnimeCreator.createValidAnime().getId();
        Anime anime = animeService.findByIdOrThrowException(1);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }
    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){
        Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());
        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }
    @Test
    @DisplayName("update updates anime when successful")
    void update_UpdatesAnime_WhenSuccessful(){
        Assertions.assertThatCode(()->animeService.update(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();
    }
    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){
        Assertions.assertThatCode(()->animeService.delete(1L))
                .doesNotThrowAnyException();
    }
    @Test
    @DisplayName("findByIdOrThrowException throws BadRequestException when anime is not found")
    void findByIdOrThrowException_ThrowsBadRequestException_WhenAnimeIsNotFound(){
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                        .isThrownBy(()->animeService.findByIdOrThrowException(1));
    }
}