package com.desafio.zup.proposta.proposta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("propostas")
public class NovaPropostaController {

    @PersistenceContext
    private EntityManager em;

    private final Logger logger = LoggerFactory.getLogger(NovaPropostaController.class);

    @PostMapping
    @Transactional
    public ResponseEntity<?> nova(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder builder){

        Proposta novaProposta = request.toModel();
        em.persist(novaProposta);

        logger.info("Proposta criada com {}", novaProposta.getId());

        URI location = builder.path("/propostas/{id}").build(novaProposta.getId());
        return ResponseEntity.created(location).build();
    }
}
