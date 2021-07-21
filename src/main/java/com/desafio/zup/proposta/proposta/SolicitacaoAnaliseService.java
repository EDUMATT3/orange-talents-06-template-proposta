package com.desafio.zup.proposta.proposta;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;

@Service
public class SolicitacaoAnaliseService {

    private final SolicitacaoAnaliseFeignClient client;

    public SolicitacaoAnaliseService(SolicitacaoAnaliseFeignClient client) {
        this.client = client;
    }

    public SolicitacaoAnaliseStatus analisa(Proposta proposta) {
        Assert.state(Objects.nonNull(proposta), "Proposta não deveria ser nula");

        try {

            SolicitacaoAnaliseResponse solicitacaoStatus = client.consulta(new SolicitacaoAnaliseRequest(proposta));
            return solicitacaoStatus.getResultadoSolicitacao();

        } catch (FeignException e) {

            if (HttpStatus.resolve(e.status()).equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
                return SolicitacaoAnaliseStatus.COM_RESTRICAO;
            }

            //todo: Tratar outras possíveis respostas
            return SolicitacaoAnaliseStatus.COM_RESTRICAO;
        }
    }
}
