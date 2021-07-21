package com.desafio.zup.proposta.proposta;

import com.desafio.zup.proposta.compartilhado.ExecutorTransacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("propostas")
public class NovaPropostaController {

    private final Logger logger = LoggerFactory.getLogger(NovaPropostaController.class);
    private EntityManager em;
    private ExecutorTransacao executorTransacao;
    private SolicitacaoAnaliseService solicitacaoAnaliseService;

    public NovaPropostaController(EntityManager em, ExecutorTransacao executorTransacao, SolicitacaoAnaliseService solicitacaoAnaliseService) {
        this.em = em;
        this.executorTransacao = executorTransacao;
        this.solicitacaoAnaliseService = solicitacaoAnaliseService;
    }

    @PostMapping
    public ResponseEntity<?> nova(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder builder){

        if(request.solicitanteTemProposta(em)){
            logger.info("Solicitante com documento: {} já fez proposta antes", request.getDocumento());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        Proposta novaProposta = request.toModel();
        executorTransacao.salvaEComita(novaProposta);

        SolicitacaoAnaliseStatus analiseStatus = solicitacaoAnaliseService.analisa(novaProposta);
        novaProposta.setEstado(analiseStatus.getEstado());

        executorTransacao.atualizaEComita(novaProposta);

        logger.info("Proposta criada com id: {}", novaProposta.getId());

        URI location = builder.path("/propostas/{id}").build(novaProposta.getId());
        return ResponseEntity.created(location).build();
    }
}
