package com.pauta.aplicacao.model;

import jakarta.persistence.*;

@Entity
public class Voto {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne
    public Usuario usuario;

    @ManyToOne
    public Pauta pauta;

    public boolean voto;

}
