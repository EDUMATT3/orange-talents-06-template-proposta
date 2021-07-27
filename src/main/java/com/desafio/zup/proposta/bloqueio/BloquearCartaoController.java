package com.desafio.zup.proposta.bloqueio;

import com.desafio.zup.proposta.proposta.Proposta;
import com.desafio.zup.proposta.proposta.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("cartoes")
public class BloquearCartaoController {

    private final Logger logger = LoggerFactory.getLogger(BloquearCartaoController.class);

    @Autowired
    private PropostaRepository propostaRepository;

    @PostMapping("{id}/bloqueio")
    public ResponseEntity<?> bloquear(HttpServletRequest request, @PathVariable String id){

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

        Optional<Proposta> possivelProposta = propostaRepository.findByNumeroCartao(id);

        if(possivelProposta.isEmpty()){
            logger.info("Tentativa de bloqueio, cartao: {} não encontrado", id);
            return ResponseEntity.notFound().build();
        }

        Proposta proposta = possivelProposta.get();

        if(proposta.estaBloqueado()){
            logger.info("Tentativa de bloqueio, cartao: {} já bloqueado", id);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        proposta.bloquear(ip, userAgent);
        propostaRepository.save(proposta);
        logger.info("Cartão: {} bloqueado com sucesso", id);

        return ResponseEntity.ok().build();
    }
}
