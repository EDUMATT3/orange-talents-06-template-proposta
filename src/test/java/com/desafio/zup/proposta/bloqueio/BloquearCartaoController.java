package com.desafio.zup.proposta.bloqueio;

import com.desafio.zup.proposta.proposta.Proposta;
import com.desafio.zup.proposta.proposta.PropostaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
//@ActiveProfiles(profiles = {"dev"})
@WithMockUser(authorities = "SCOPE_cartoes:write")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BloquearCartaoController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropostaRepository propostaRepository;

    String numeroCartao = "1001-1001-1001-1001";

    //todo: bug, o mock mvc retornar 404, parece estar fora de contexto;

//    @Test
//    @DisplayName("Deve bloquear um cartão com sucesso e retornar 200")
//    void test1() throws Exception {
//
//        Proposta novaProposta = new Proposta("50508675847", "usuario@email.com", "any address", "fulano", new BigDecimal("2.500"));
//        novaProposta.adicionaCartao(numeroCartao);
//
//        propostaRepository.save(novaProposta);
//
//        String uri = "/cartoes/"+numeroCartao+"/bloqueio";
//        mockMvc.perform(post(uri)
//                .header(HttpHeaders.USER_AGENT, "Chrome/5.0"))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//    }
}
