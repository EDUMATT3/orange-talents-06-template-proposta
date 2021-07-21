package com.desafio.zup.proposta.proposta;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolicitacaoAnaliseRequest {

    @JsonProperty
    private String documento, nome;

    @JsonProperty
    private Long idProposta;

    public SolicitacaoAnaliseRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId();
    }
}
