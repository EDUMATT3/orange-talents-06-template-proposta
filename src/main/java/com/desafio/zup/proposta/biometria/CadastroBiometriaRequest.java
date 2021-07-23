package com.desafio.zup.proposta.biometria;

import com.desafio.zup.proposta.proposta.Proposta;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Optional;

public class CadastroBiometriaRequest {

    @NotBlank
    private String numeroCartao, fingerPrint;

    public CadastroBiometriaRequest(String numeroCartao, String fingerPrint) {
        this.numeroCartao = numeroCartao;
        this.fingerPrint = fingerPrint;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public boolean cartaoInvalido(EntityManager em) {
        return (boolean) em.createQuery("SELECT CASE WHEN COUNT(p) > 0 THEN false ELSE true END FROM Proposta p WHERE p.numeroCartao=?1")
                .setParameter(1, numeroCartao)
                .getSingleResult();
    }

    public Biometria toModel(EntityManager em) {
        Proposta proposta = em.createQuery("SELECT p FROM Proposta p WHERE p.numeroCartao=?1", Proposta.class)
                .setParameter(1, numeroCartao)
                .getSingleResult();
        Assert.state(Objects.nonNull(proposta), "Proposta não encontrada com o numero de cartão: "+ numeroCartao);

        return new Biometria(fingerPrint, proposta);
    }
}
