package com.desafio.zup.proposta.proposta;

import com.desafio.zup.proposta.compartilhado.Documento;
import com.desafio.zup.proposta.compartilhado.security.EncriptadorDocumento;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;
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

    public Proposta toModel() {
        return new Proposta(EncriptadorDocumento.encriptar(documento), email, endereco, nome, salario);
    }

    public boolean solicitanteTemProposta(EntityManager em) {
        return (boolean) em.createQuery("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Proposta p WHERE p.documento=?1")
                .setParameter(1, EncriptadorDocumento.encriptar(documento))
                .getSingleResult();
    }

    public String getDocumento() {
        return this.documento;
    }
}
