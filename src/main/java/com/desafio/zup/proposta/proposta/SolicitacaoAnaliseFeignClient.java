package com.desafio.zup.proposta.proposta;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "solicitacao", url = "${api.solicitacao-analise.url}")
public interface SolicitacaoAnaliseFeignClient {

    @PostMapping
    SolicitacaoAnaliseResponse consulta(SolicitacaoAnaliseRequest request);

}
