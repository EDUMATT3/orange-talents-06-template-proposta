package com.desafio.zup.proposta.proposta;

import com.desafio.zup.proposta.compartilhado.CustomMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ConsultaPropostaControllerTest {

    @Autowired
    private CustomMockMvc mvc;

    private String uri = "/propostas";

    private Map<String, Object> requestContent = new HashMap<>();

    //aparentemente não pode chamar o método put de um map instanciado de map.of, precisei criar a partir de HashMap


    @BeforeEach
    void init(){
        requestContent.putAll(Map.of(
                "documento", "50508675847",
                "email", "usuario@email.com",
                "endereco", "any address",
                "nome", "fulano de tal",
                "salario", 2.500));
    }

    @Test
    @DisplayName("Deveria retornar status 200 e a dados da proposta com estado ELEGIVEL")
    void teste1() throws Exception {
        mvc.post(uri, requestContent);

        mvc.get(uri+"/"+1L)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("estado").value(EstadoProposta.ELEGIVEL.name()));
    }

    @Test
    @DisplayName("Deveria retornar status 200 e a dados da proposta com estado NAO_ELEGIVEL")
    void teste2() throws Exception {
        requestContent.put("documento", "39200455808");
        mvc.post(uri, requestContent);

        mvc.get(uri+"/"+1L)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("estado").value(EstadoProposta.NAO_ELEGIVEL.name()));
    }

    @Test
    @DisplayName("Deveria salvar nova proposta")
    void teste3() throws Exception {
        mvc.get(uri+"/"+2L)
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}