package com.desafio.zup.proposta.proposta;

import com.desafio.zup.proposta.compartilhado.CustomMockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static com.desafio.zup.proposta.compartilhado.CustomMockMvc.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class NovaPropostaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String uri = "/propostas";

    private Map<String, Object> requestContent = Map.of(
            "documento", "50508675847",
            "email", "usuario@email.com",
            "endereco", "any address",
            "nome", "fulano de tal",
            "salario", 2.500);

    @Test
    @DisplayName("Deveria salvar nova proposta")
    void teste1() throws Exception {
        post(uri, requestContent, mockMvc)
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.header().string("location", "http://localhost/propostas/1"));
    }

    @Test
    @DisplayName("Deve retornar 422(UNPROCESSABLE_ENTITY) caso solicitante ja tenha proposta")
    void teste2() throws Exception {
        post(uri, requestContent, mockMvc);
        post(uri, requestContent, mockMvc)
                .andDo(print())
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }

    //todo: testar os estado da proposta
//    @Test
//    @DisplayName("Deve retornar salvar uma proposta com estado ELEGIVEL quando documento não inicia com 3")

}
