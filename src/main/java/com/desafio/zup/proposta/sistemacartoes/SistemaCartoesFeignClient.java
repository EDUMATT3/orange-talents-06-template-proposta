package com.desafio.zup.proposta.sistemacartoes;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "sistema-cartoes", url = "${api.cartoes.url}")
public interface SistemaCartoesFeignClient {

    @GetMapping
    SistemaCartoesResponse getCartaoByIdProposta(@RequestParam Long idProposta);

    @PostMapping("{id}/bloqueios")
    Response nofiticarBloqueio(@PathVariable String id, Map<String, String> body);

}
