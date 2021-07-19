package com.desafio.zup.proposta.proposta;

import com.desafio.zup.proposta.compartilhado.Documento;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Map;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Proposta(@NotBlank String documento,
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

    public Long getId() {
        return id;
    }
}
