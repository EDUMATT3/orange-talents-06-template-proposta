package com.desafio.zup.proposta.compartilhado;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.function.Supplier;

@Component
public class ExecutorTransacao {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public <T> T salvaEComita(T objeto) {
        Assert.state(Objects.nonNull(objeto), "Objeto não deveria ser nulo");
        manager.persist(objeto);
        return objeto;
    }

    @Transactional
    public <T> T atualizaEComita(T objeto) {
        Assert.state(Objects.nonNull(objeto), "Objeto não deveria ser nulo");
        return manager.merge(objeto);
    }

    @Transactional
    public <T> T executa(Supplier<T> algumCodigoComRetorno){
        return algumCodigoComRetorno.get();
    }
}
