package com.desafio.zup.proposta.proposta;

import com.desafio.zup.proposta.compartilhado.CustomMockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.Serializable;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NovaPropostaControllerTest {

    @Autowired
    private CustomMockMvc mvc;

    @Test
    @DisplayName("Deveria salvar nova proposta")
    void teste1() throws Exception {
        Map<String, Object> content = Map.of(
                "documento", "50508675847",
                "email", "usuario@email.com",
                "endereco", "any address",
                "nome", "fulano de tal",
                "salario", 2.500);

        mvc.post("/propostas", content)
        .andExpect(status().is2xxSuccessful())
        .andExpect(MockMvcResultMatchers.header().exists("location"));
    }
}
