package com.desafio.zup.proposta.proposta;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SolicitacaoAnaliseResponse {

    private SolicitacaoAnaliseStatus resultadoSolicitacao;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SolicitacaoAnaliseResponse(SolicitacaoAnaliseStatus resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public SolicitacaoAnaliseStatus getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
