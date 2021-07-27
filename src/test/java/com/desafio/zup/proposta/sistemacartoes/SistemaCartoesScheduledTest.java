package com.desafio.zup.proposta.sistemacartoes;

import com.desafio.zup.proposta.proposta.EstadoProposta;
import com.desafio.zup.proposta.proposta.Proposta;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("dev")
@SpringBootTest//to resolve: o teste falha sem essa anotação por causa da CustomMockMvc
public class SistemaCartoesScheduledTest {

    @MockBean
    private EntityManager em;

    @MockBean
    private  SistemaCartoesFeignClient client;

    @Value("${cartoes.associacao.delay}")
    private String delay;

    @Test
    @DisplayName("Deve chamar a api externa e adicionar um numero de cartao")
    void test1() throws InterruptedException {
        Proposta proposta = new Proposta("50508675847", "fulano@email.com", "Rua das quantas", "fulano de tal", new BigDecimal("2.500"));
        ReflectionTestUtils.setField(proposta, "id", 1L);
        proposta.setEstado(EstadoProposta.ELEGIVEL);

        SistemaCartoesResponse response = new SistemaCartoesResponse();
        String idCartao = "1001-1001-1001-1001";
        ReflectionTestUtils.setField(response, "id", idCartao);

        TypedQuery<Proposta> query = (TypedQuery<Proposta>) Mockito.mock(TypedQuery.class);


        doReturn(query).when(em).createQuery(anyString(), anyObject());
        doReturn(Lists.newArrayList(proposta)).when(query).getResultList();
        doReturn(response).when(client).getCartaoByIdProposta(1L);

        //aguardar o delay do agendamento, para ambiente de teste setei 1 segundo
        Thread.sleep(Long.parseLong(delay));

        //uso da reflection para pegar o atributo e não criar um método get apenas para o tes
        String numeroCartao = (String) ReflectionTestUtils.getField(proposta, "numeroCartao");

        Assertions.assertEquals(idCartao, numeroCartao);
        Mockito.verify(client, times(1)).getCartaoByIdProposta(1L);
    }

    @Test
    @DisplayName("Quando a lista de propostas estiver vazia, não deve consultar a api de cartões")
    void test2() throws InterruptedException {
        TypedQuery<Proposta> query = (TypedQuery<Proposta>) Mockito.mock(TypedQuery.class);

        doReturn(query).when(em).createQuery(anyString(), anyObject());
        doReturn(Lists.emptyList()).when(query).getResultList();

        Thread.sleep(Long.parseLong(delay));

        Mockito.verify(client, never()).getCartaoByIdProposta(anyLong());
    }
}
