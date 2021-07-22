package com.desafio.zup.proposta.sistemacartoes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sistema-cartoes", url = "${api.cartoes.url}")
public interface SistemaCartoesFeignClient {

    @GetMapping
    SistemaCartoesResponse getCartaoByIdProposta(@RequestParam Long idProposta);
}
