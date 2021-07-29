package com.desafio.zup.proposta.proposta;

import com.desafio.zup.proposta.compartilhado.ExecutorTransacao;
import com.desafio.zup.proposta.compartilhado.Metricas;
import com.desafio.zup.proposta.proposta.solicitacaoanalise.SolicitacaoAnaliseService;
import com.desafio.zup.proposta.proposta.solicitacaoanalise.SolicitacaoAnaliseStatus;
import io.micrometer.core.instrument.Tags;
import io.opentracing.Span;
import io.opentracing.Tracer;
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
    private Metricas metricas;
    private Tracer tracer;

    public NovaPropostaController(EntityManager em, ExecutorTransacao executorTransacao, SolicitacaoAnaliseService solicitacaoAnaliseService, Metricas metricas, Tracer tracer) {
        this.em = em;
        this.executorTransacao = executorTransacao;
        this.solicitacaoAnaliseService = solicitacaoAnaliseService;
        this.metricas = metricas;
        this.tracer = tracer;
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

        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("proposta.documento", request.getDocumento());

        logger.info("Proposta criada com id: {}", novaProposta.getId());

        metricas.contador("proposta-criada", Tags.of("banco", "itáu"));

        URI location = builder.path("/propostas/{id}").build(novaProposta.getId());
        return ResponseEntity.created(location).build();
    }
}
