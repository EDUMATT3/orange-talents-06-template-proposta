package com.desafio.zup.proposta.bloqueio;

import com.desafio.zup.proposta.proposta.Proposta;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Profile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    @NotNull
    @ManyToOne
    private Proposta proposta;

    public Bloqueio() {
    }

    @Deprecated


    public Bloqueio(@NotBlank String ip,
                    @NotBlank String userAgent,
                    Proposta proposta) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.proposta = proposta;
    }
}
