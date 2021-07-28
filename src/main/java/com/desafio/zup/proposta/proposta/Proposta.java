package com.desafio.zup.proposta.proposta;

import com.desafio.zup.proposta.avisoviagem.AvisoViagem;
import com.desafio.zup.proposta.avisoviagem.NovoAvisoViagemRequest;
import com.desafio.zup.proposta.bloqueio.Bloqueio;
import com.desafio.zup.proposta.bloqueio.EstadoCartao;
import com.desafio.zup.proposta.compartilhado.Documento;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @Enumerated(EnumType.STRING)
    private EstadoProposta estado;

    private String numeroCartao;

    @Enumerated(EnumType.STRING)
    private EstadoCartao estadoCartao;

    @OneToMany(mappedBy = "proposta", cascade = CascadeType.MERGE)
    private List<Bloqueio> bloqueios = Collections.emptyList();

    @OneToMany(mappedBy = "proposta", cascade = CascadeType.MERGE)
    private List<AvisoViagem> avisoViagens = Collections.emptyList();;

    @Deprecated
    public Proposta() {
    }

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

    public String getDocumento() {
        return this.documento;
    }

    public String getNome() {
        return nome;
    }

    public void setEstado(EstadoProposta estado) {
        this.estado = estado;
    }

    public void adicionaCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
        this.estadoCartao = EstadoCartao.DESBLOQUEADO;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public EstadoProposta getEstado() {
        return estado;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public boolean estaBloqueado() {
        Assert.notNull(this.numeroCartao, "Este metodo deveria ser chamado após adionar um cartão");
        return this.estadoCartao.equals(EstadoCartao.BLOQUEADO);
    }

    public void bloquear(String ip, String userAgent) {
        Assert.isTrue(!estaBloqueado(), "Não deveria bloquear um cartão já bloqueado");
        Assert.hasText(ip, "Ip deveria estar presente");
        Assert.hasText(userAgent, "UserAgent deveria estar presente");

        this.estadoCartao = EstadoCartao.BLOQUEADO;
        this.bloqueios.add(new Bloqueio(ip, userAgent, this));
    }

    public void adicionaAvisoViagem(NovoAvisoViagemRequest body, String ip, String userAgent) {
        Assert.notNull(this.numeroCartao, "Este metodo deveria ser chamado após adionar um cartão");
        Assert.hasText(ip, "Ip deveria estar presente");
        Assert.hasText(userAgent, "UserAgent deveria estar presente");

        this.avisoViagens.add(new AvisoViagem(ip, userAgent, body.getDestino(), body.getTermino(), this));
    }
}

