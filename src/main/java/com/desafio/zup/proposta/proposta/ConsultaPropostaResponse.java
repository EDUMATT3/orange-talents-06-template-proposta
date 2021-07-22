package com.desafio.zup.proposta.proposta;

import com.desafio.zup.proposta.compartilhado.Documento;
import com.desafio.zup.proposta.proposta.EstadoProposta;
import com.desafio.zup.proposta.proposta.Proposta;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
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
