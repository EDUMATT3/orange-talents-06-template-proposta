package com.desafio.zup.proposta.carteira;

import com.desafio.zup.proposta.proposta.Proposta;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.util.Assert;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class NovaCateiraRequest {

    @NotBlank
    @Email
    private String email;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovaCateiraRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Carteira toModel(Proposta proposta) {
        Assert.state(Objects.nonNull(proposta), "Proposta não deveria ser nula");

        return new Carteira(email, CarteiraDigital.PAYPAL ,proposta);
    }
}
