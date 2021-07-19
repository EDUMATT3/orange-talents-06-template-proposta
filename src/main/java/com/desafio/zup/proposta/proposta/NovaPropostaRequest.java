package com.desafio.zup.proposta.proposta;

import com.desafio.zup.proposta.compartilhado.Documento;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @NotBlank
    @Documento
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String endereco, nome;

    @NotNull
    @PositiveOrZero //salario pode ser zero?
    private BigDecimal salario;

    public NovaPropostaRequest(@NotBlank String documento,
                               @NotBlank @Email String email,
                               @NotBlank String endereco,
                               @NotBlank String nome,
                               @NotNull @PositiveOrZero BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.endereco = endereco;
        this.nome = nome;
        this.salario = salario;
    }
}
