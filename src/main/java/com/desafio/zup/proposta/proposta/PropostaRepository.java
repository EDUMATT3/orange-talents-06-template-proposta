package com.desafio.zup.proposta.proposta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    Optional<Proposta> findByNumeroCartao(String numeroCartao);
}