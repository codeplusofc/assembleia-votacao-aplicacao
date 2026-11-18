package com.pauta.aplicacao.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class SessaoVotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne
    public Pauta pauta;


    public LocalDateTime inicio;

    public LocalDateTime fim;


}
