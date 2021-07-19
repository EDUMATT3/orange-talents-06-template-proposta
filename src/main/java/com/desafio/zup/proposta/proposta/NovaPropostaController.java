package com.desafio.zup.proposta.proposta;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("propostas")
public class NovaPropostaController {

    @PostMapping
    public void nova(){
    }
}
