package com.spring_boot.projeto3.service;

import com.spring_boot.projeto3.exception.BadRequestException;
import com.spring_boot.projeto3.exception.CustomizedException;
import com.spring_boot.projeto3.mapper.AnimeMapper;
import com.spring_boot.projeto3.model.Anime;
import com.spring_boot.projeto3.repository.AnimeRepository;
import com.spring_boot.projeto3.requests.AnimePostRequestBody;
import com.spring_boot.projeto3.requests.AnimePutRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnimeService  {
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private AnimeMapper animeMapper;

    public Anime save(AnimePostRequestBody animePostRequestBody){
        return animeRepository.save(animeMapper.ToAnime(animePostRequestBody));
    }
    public Page<Anime> listAllPageable(Pageable pageable){
        return animeRepository.findAll(pageable);
    }

    @Cacheable("animes")
    public Anime findById(long id){
        return animeRepository.findById(id)
                .orElseThrow(BadRequestException::new);
    }
    public Anime findByName(String name){
        return animeRepository.findByName(name)
                .orElseThrow(BadRequestException::new);
    }
    public Anime update(AnimePutRequestBody animePutRequestBody){
        Anime savedAnime = findById(animePutRequestBody.id());
        Anime anime = animeMapper.ToAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());
        return animeRepository.save(anime);
    }
    public void delete(long id){
        animeRepository.delete(findById(id));
    }
}
