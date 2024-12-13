package com.spring_boot.projeto3.model;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "ANIMES")
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
