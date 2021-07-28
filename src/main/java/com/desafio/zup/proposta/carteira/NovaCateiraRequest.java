package com.desafio.zup.proposta.carteira;

import com.desafio.zup.proposta.proposta.Proposta;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.util.Assert;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class NovaCateiraRequest {

    @NotBlank
    @Email
    private String email;

    @NotNull
    private CarteiraDigital carteiraDigital;

    public NovaCateiraRequest(@NotBlank @Email String email,
                              @NotNull CarteiraDigital carteiraDigital) {
        this.email = email;
        this.carteiraDigital = carteiraDigital;
    }

    public Carteira toModel(Proposta proposta) {
        Assert.state(Objects.nonNull(proposta), "Proposta não deveria ser nula");

        return new Carteira(email, carteiraDigital ,proposta);
    }
}
