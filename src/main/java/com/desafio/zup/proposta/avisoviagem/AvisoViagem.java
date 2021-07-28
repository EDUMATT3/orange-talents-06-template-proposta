package com.desafio.zup.proposta.avisoviagem;

import com.desafio.zup.proposta.proposta.Proposta;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate termino;

    @NotNull
    @ManyToOne
    private Proposta proposta;

    @Deprecated
    public AvisoViagem(){ }

    public AvisoViagem(@NotBlank String ip,
                       @NotBlank String userAgent,
                       @NotBlank String destino,
                       @NotNull @Future LocalDate termino,
                       @NotNull Proposta proposta) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.destino = destino;
        this.termino = termino;
        this.proposta = proposta;
    }
}
