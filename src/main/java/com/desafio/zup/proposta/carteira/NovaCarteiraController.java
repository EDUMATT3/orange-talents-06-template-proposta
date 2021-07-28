package com.desafio.zup.proposta.carteira;

import com.desafio.zup.proposta.proposta.NovaPropostaController;
import com.desafio.zup.proposta.proposta.Proposta;
import com.desafio.zup.proposta.proposta.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static com.desafio.zup.proposta.carteira.CarteiraDigital.PAYPAL;

@RestController
@RequestMapping("cartoes")
public class NovaCarteiraController {

    private final Logger logger = LoggerFactory.getLogger(NovaCarteiraController.class);

    @Autowired
    private PropostaRepository propostaRepository;

    @PersistenceContext
    private EntityManager em;

    @PostMapping("{id}/carteiras/paypal")
    @Transactional
    public ResponseEntity<?> novaCateira(@PathVariable String id, @Valid @RequestBody NovaCateiraRequest request, UriComponentsBuilder builder){

        Optional<Proposta> possivelProposta = propostaRepository.findByNumeroCartao(id);

        if (possivelProposta.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Proposta proposta = possivelProposta.get();

        Carteira novaCarteira = request.toModel(proposta);

        if(proposta.temCarteiraAssociada(novaCarteira)){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        em.persist(novaCarteira);
        logger.info("Adionada carteira com id: {}", novaCarteira.getId());

        URI location = builder.path("/cartoes/{idCartao}/carteiras/{idCarteira}").buildAndExpand(id, novaCarteira.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
