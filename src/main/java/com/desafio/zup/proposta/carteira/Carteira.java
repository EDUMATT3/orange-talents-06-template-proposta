package com.desafio.zup.proposta.carteira;

import com.desafio.zup.proposta.proposta.Proposta;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CarteiraDigital carteiraDigital;

    @ManyToOne
    private Proposta proposta;

    @Deprecated
    public Carteira(){}

    public Carteira(String email, CarteiraDigital carteiraDigital, Proposta proposta) {
        this.email = email;
        this.carteiraDigital = carteiraDigital;
        this.proposta = proposta;
    }

    public Long getId() {
        return id;
    }
}
