package com.desafio.zup.proposta.compartilhado;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

public class CustomMockMvc {

//    private MockMvc mockMvc;
//
//    public CustomMockMvc(MockMvc mockMvc) {
//        this.mockMvc = mockMvc;
//    }

    public static ResultActions post(String url, Map<String, Object> params, MockMvc mockMvc) {
        try {
            String payload = new ObjectMapper()
                    .writeValueAsString(params);


            MockHttpServletRequestBuilder content = MockMvcRequestBuilders
                    .post(url)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(payload);

            return mockMvc.perform(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultActions get(String url, MockMvc mockMvc) {
        try {

            MockHttpServletRequestBuilder content = MockMvcRequestBuilders
                    .get(url)
                    .accept(MediaType.APPLICATION_JSON_VALUE);

            return mockMvc.perform(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}