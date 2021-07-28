package com.desafio.zup.proposta.avisoviagem;

import com.desafio.zup.proposta.proposta.Proposta;
import com.desafio.zup.proposta.proposta.PropostaRepository;
import com.desafio.zup.proposta.sistemacartoes.SistemaCartoesFeignClient;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("cartoes")
public class NovoAvisoViagemController {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    SistemaCartoesFeignClient client;

    //todo: refator...
    @PostMapping("{id}/viagens")
    public ResponseEntity<?> novoAviso(@PathVariable String id, @Valid @RequestBody NovoAvisoViagemRequest body, HttpServletRequest request){

        Optional<Proposta> possivelProposta = propostaRepository.findByNumeroCartao(id);

        if (possivelProposta.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        if (!notificarLegado(id, body)){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("message", "Não foi possivel concluir a ação, tente mais tarde"));
        }

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

        Proposta proposta = possivelProposta.get();

        proposta.adicionaAvisoViagem(body, ip, userAgent);
        propostaRepository.save(proposta);

        return ResponseEntity.ok().build();
    }

    private boolean notificarLegado(String id, NovoAvisoViagemRequest body) {
        Map<String, Object> request = Map.of("destino", body.getDestino(), "validoAte", body.getTermino());
        Response response = client.nofiticarViagem(id, request);
        return HttpStatus.OK.equals(HttpStatus.resolve(response.status()));
    }

}
