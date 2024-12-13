package com.spring_boot.projeto3.service;

import com.spring_boot.projeto3.model.Anime;
import com.spring_boot.projeto3.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnimeService {
    @Autowired
    private AnimeRepository animeRepository;

    public Anime save(Anime anime){
       return animeRepository.save(anime);
    }
    public List<Anime> listAll(){
        return animeRepository.findAll();
    }
    public Anime findById(long id){
        return animeRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Anime not found"));
    }
    public void update(){


    }

    public void delete(long id){
        animeRepository.delete(findById(id));
    }
}
