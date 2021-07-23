package com.desafio.zup.proposta.biometria;

import com.desafio.zup.proposta.proposta.Proposta;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Biometria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fingerprint;

    @ManyToOne
    private Proposta proposta;

    public Biometria( @NotBlank String fingerprint, Proposta proposta) {
        this.fingerprint = fingerprint;
        this.proposta = proposta;
    }

    public Long getId() {
        return this.id;
    }
}
