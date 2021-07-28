package com.desafio.zup.proposta.proposta;

import com.desafio.zup.proposta.compartilhado.Metricas;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@RestController
@RequestMapping("propostas")
public class ConsultaPropostaController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("{id}")
    @Timed(value = "consulta-propostas", extraTags = {"banco", "itau"})
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<Proposta> possivelProposta = Optional.ofNullable(entityManager.find(Proposta.class, id));

        if (possivelProposta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ConsultaPropostaResponse response = new ConsultaPropostaResponse(possivelProposta.get());

        return ResponseEntity.ok(response);
    }
}
