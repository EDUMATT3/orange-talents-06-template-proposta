package com.desafio.zup.proposta.biometria;

import com.desafio.zup.proposta.proposta.NovaPropostaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("biometrias")
public class CadastroBiometriaController {

    private final Logger logger = LoggerFactory.getLogger(CadastroBiometriaController.class);

    @PersistenceContext
    private EntityManager em;

    @InitBinder
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(new BiometriaValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cria(@RequestBody @Valid CadastroBiometriaRequest request, UriComponentsBuilder builder){

        if(request.cartaoInvalido(em)){
            return ResponseEntity.notFound().build();
        }

        Biometria biometria = request.toModel(em);

        em.persist(biometria);

        logger.info("Biometria cadastrada com sucesso - id: {}", biometria.getId());

        URI location = builder.path("/biometrias/{id}").build(biometria.getId());
        return ResponseEntity.created(location).build();
    }

}
