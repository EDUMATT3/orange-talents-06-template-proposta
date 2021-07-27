package com.desafio.zup.proposta.proposta;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;

public class ConsultaPropostaResponse {

    private String documento, email, endereco, nome, numeroCartao;

    private BigDecimal salario;

    private EstadoProposta estado;

    public ConsultaPropostaResponse(Proposta proposta){
        Assert.state(Objects.nonNull(proposta), "proposta não deveria ser nula");

        this.documento = proposta.getDocumento();
        this.email = proposta.getEmail();
        this.endereco = proposta.getEndereco();
        this.nome = proposta.getEmail();
        this.numeroCartao = proposta.getNumeroCartao();
        this.salario = proposta.getSalario();
        this.estado = proposta.getEstado();
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getNome() {
        return nome;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public EstadoProposta getEstado() {
        return estado;
    }
}
