package com.desafio.zup.proposta.proposta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    Optional<Proposta> findByNumeroCartao(String numeroCartao);

    @Query("SELECT p FROM Proposta p where p.estado = 'ELEGIVEL' and p.numeroCartao = null")
    List<Proposta> getPropostasElegiveisSemCartao();
}
