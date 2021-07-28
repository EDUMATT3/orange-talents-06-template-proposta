package com.desafio.zup.proposta.sistemacartoes;

import com.desafio.zup.proposta.compartilhado.ExecutorTransacao;
import com.desafio.zup.proposta.proposta.Proposta;
import com.desafio.zup.proposta.proposta.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.desafio.zup.proposta.proposta.EstadoProposta.ELEGIVEL;

@Component
public class SistemaCartoesScheduled {

    private PropostaRepository propostaRepository;
    private SistemaCartoesFeignClient client;
    private ExecutorTransacao executorTransacao;

    private final Logger logger = LoggerFactory.getLogger(SistemaCartoesScheduled.class);

    public SistemaCartoesScheduled(PropostaRepository propostaRepository, SistemaCartoesFeignClient client, ExecutorTransacao executorTransacao) {
        this.propostaRepository = propostaRepository;
        this.client = client;
        this.executorTransacao = executorTransacao;
    }

    @Scheduled(fixedDelayString = "${cartoes.associacao.delay}")
    public void doAlgo() throws Exception{
        List<Proposta> resultList = propostaRepository.getPropostasElegiveisSemCartao();

        logger.info("Propostas elegiveis e sem cartão associado - tamanho: {}", resultList.size());

        resultList.forEach(proposta -> {
            try {
                SistemaCartoesResponse response = client.getCartaoByIdProposta(proposta.getId());
                proposta.adicionaCartao(response.getId());
                executorTransacao.atualizaEComita(proposta);
            } catch (FeignException e) {
                logger.error("Não foi possivel encontrar o cartão para a proposta: {}. Motiveo: {}. Resposta: {}", proposta.getId(), e.getMessage(), e.contentUTF8());
            }
        });
    }
}
