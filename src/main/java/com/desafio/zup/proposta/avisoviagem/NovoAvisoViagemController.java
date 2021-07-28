package com.desafio.zup.proposta.avisoviagem;

import com.desafio.zup.proposta.proposta.Proposta;
import com.desafio.zup.proposta.proposta.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("cartoes")
public class NovoAvisoViagemController {

    @Autowired
    private PropostaRepository propostaRepository;

    @PostMapping("{id}/viagens")
    public ResponseEntity<?> novoAviso(@PathVariable String id, @Valid @RequestBody NovoAvisoViagemRequest body, HttpServletRequest request){

        Optional<Proposta> possivelProposta = propostaRepository.findByNumeroCartao(id);

        if (possivelProposta.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

        Proposta proposta = possivelProposta.get();

        proposta.adicionaAvisoViagem(body, ip, userAgent);
        propostaRepository.save(proposta);

        return ResponseEntity.ok().build();
    }

}
