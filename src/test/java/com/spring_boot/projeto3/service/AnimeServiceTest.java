package com.spring_boot.projeto3.service;

import com.spring_boot.projeto3.exception.BadRequestException;
import com.spring_boot.projeto3.exception.CustomizedException;
import com.spring_boot.projeto3.mapper.AnimeMapper;
import com.spring_boot.projeto3.model.Anime;
import com.spring_boot.projeto3.repository.AnimeRepository;
import com.spring_boot.projeto3.requests.AnimePostRequestBody;
import com.spring_boot.projeto3.requests.AnimePutRequestBody;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

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

    }

    @Test
    @DisplayName("listAllPageable returns list of anime inside page object when successful")
    void listAllPageable_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){
        var validAnime = new Anime(1L,"Baki");
        PageImpl<Anime> animePage = new PageImpl<>(List.of(validAnime));
        when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);
        String expectedName = validAnime.getName();

        Page<Anime> animePageable = animeService.listAllPageable(PageRequest.of(1,1));

        Assertions.assertThat(animePageable).isNotNull();
        Assertions.assertThat(animePageable.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePageable.toList().getFirst().getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful(){
        var validAnime = new Anime(1L,"Baki");
        when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(validAnime));
        Long expectedId = validAnime.getId();
        Anime anime = animeService.findById(1);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }
    @Test
    @DisplayName("findById throws BadRequestException when anime is not found")
    void findById_ThrowsBadRequestException_WhenAnimeIsNotFound(){
        when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(()->animeService.findById(1));
    }
    @Test
    @DisplayName("findByName returns anime when successful")
    void findByName_ReturnsAnime_WhenSuccessful(){
        var validAnime = new Anime(1L,"Baki");
        when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(validAnime));
        String expectedName = validAnime.getName();

        Anime anime = animeService.findByName("abc");

        Assertions.assertThat(anime.getId()).isNotNull();
        Assertions.assertThat(anime.getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findByName throws BadRequestException when anime is not found")
    void findByName_ThrowsBadRequestException_WhenAnimeIsNotFound(){
        when(animeRepositoryMock.findByName(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(()->animeService.findByName("abc"));
    }
    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){
        var animePostRequestBody= new AnimePostRequestBody("Baki 2");
        var entity = new Anime(1L, "Baki 2");
        when(animeMapperMock.ToAnime(animePostRequestBody)).thenReturn(entity);
        when(animeRepositoryMock.save(ArgumentMatchers.any())).thenReturn(entity);

        Anime anime = animeService.save(animePostRequestBody);

        Assertions.assertThat(anime.getName()).isEqualTo(animePostRequestBody.name());
        Assertions.assertThat(anime.getId()).isNotNull();
    }
    @Test
    @DisplayName("save throws CustomizedException when try to save anime that starts with Z")
    void save_ThrowsCustomizedException_WhenTryToSaveAnimeThatStartsWithZ() {
        var animePostRequestBody = new AnimePostRequestBody("Zelda");
        var invalidAnime = new Anime(1L,"Zelda");

        when(animeMapperMock.ToAnime(animePostRequestBody)).thenReturn(invalidAnime);

        Assertions.assertThatExceptionOfType(CustomizedException.class)
                .isThrownBy(()->animeService.save(animePostRequestBody));
    }
    @Test
    @DisplayName("update updates anime when successful")
    void update_UpdatesAnime_WhenSuccessful(){
        var savedAnime = new Anime(1L,"Baki");
        var animeUpdateRequest = new AnimePutRequestBody(1L,"Bleach");
        var expectedAnime = new Anime(1L,"Bleach");

        when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(savedAnime));

        when(animeMapperMock.ToAnime(animeUpdateRequest)).thenReturn(expectedAnime);

        expectedAnime.setId(savedAnime.getId());
        when(animeRepositoryMock.save(expectedAnime)).thenReturn(expectedAnime);

        Anime finalResult = animeService.update(animeUpdateRequest);

        Assertions.assertThat(finalResult.getId()).isEqualTo(savedAnime.getId());
        Assertions.assertThat(finalResult.getName()).isEqualTo(animeUpdateRequest.name());
    }
}