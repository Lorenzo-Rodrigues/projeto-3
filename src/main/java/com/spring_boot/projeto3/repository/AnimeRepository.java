package com.spring_boot.projeto3.repository;

import com.spring_boot.projeto3.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimeRepository extends JpaRepository<Anime,Long> {
    Optional<Anime> findByName(String name);
}
