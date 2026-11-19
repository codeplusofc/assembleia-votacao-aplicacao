package com.pauta.aplicacao.model;

import jakarta.persistence.*;

@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @ManyToOne
    public Usuario usuario;

    @ManyToOne
    public Pauta pauta;

    public boolean voto;

    public boolean isVoto() {
        return voto;
    }

    public void setVoto(boolean voto) {
        this.voto = voto;
    }

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
