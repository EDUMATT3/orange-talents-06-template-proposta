package com.desafio.zup.proposta.sistemacartoes;

import com.desafio.zup.proposta.compartilhado.ExecutorTransacao;
import com.desafio.zup.proposta.proposta.Proposta;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class SistemaCartoesScheduled {

    private EntityManager em;
    private SistemaCartoesFeignClient client;
    private ExecutorTransacao executorTransacao;

    private final Logger logger = LoggerFactory.getLogger(SistemaCartoesScheduled.class);

    public SistemaCartoesScheduled(EntityManager em, SistemaCartoesFeignClient client, ExecutorTransacao executorTransacao) {
        this.em = em;
        this.client = client;
        this.executorTransacao = executorTransacao;
    }

    @Scheduled(fixedDelayString = "${cartoes.associacao.delay}")
    public void doAlgo() throws Exception{
        List<Proposta> resultList = em.createQuery("SELECT p FROM Proposta p where p.estado = 'ELEGIVEL' and p.numeroCartao = null", Proposta.class)
                .getResultList();

        logger.info("Propostas elegiveis e sem cartão associado - tamanho: {}", resultList.size());

        resultList.forEach(proposta -> {
            try {
                SistemaCartoesResponse response = client.getCartaoByIdProposta(proposta.getId());
                proposta.setNumeroCartao(response.getId());
                executorTransacao.atualizaEComita(proposta);
            } catch (FeignException e) {
                logger.error("Não foi possivel encontrar o cartão para a proposta: {}. Motiveo: {}. Resposta: {}", proposta.getId(), e.getMessage(), e.contentUTF8());
            }
        });
    }
}
