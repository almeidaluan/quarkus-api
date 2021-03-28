package com.ifood.model;


import javax.persistence.*;

@Entity
@Table(name = "LOCALIZACAO")
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Double longitude;

    public Double latitude;
}
