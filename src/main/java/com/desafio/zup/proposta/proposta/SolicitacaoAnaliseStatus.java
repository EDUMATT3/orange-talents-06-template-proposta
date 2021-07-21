package com.desafio.zup.proposta.proposta;

public enum SolicitacaoAnaliseStatus {
    COM_RESTRICAO(EstadoProposta.NAO_ELEGIVEL),
    SEM_RESTRICAO(EstadoProposta.ELEGIVEL);

    private final EstadoProposta estado;

    SolicitacaoAnaliseStatus(EstadoProposta estado) {
        this.estado = estado;
    }

    public EstadoProposta getEstado() {
        return estado;
    }
}
