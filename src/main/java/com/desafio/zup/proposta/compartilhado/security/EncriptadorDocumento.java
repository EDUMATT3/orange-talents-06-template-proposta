package com.desafio.zup.proposta.compartilhado.security;

import org.springframework.security.crypto.encrypt.Encryptors;

public class EncriptadorDocumento {

    private static final String SALT = "70726f706f73746173";

    public static String encriptar(String text){
        return Encryptors.queryableText("documento", SALT).encrypt(text);
    }

    public static String descriptar(String text){
        return Encryptors.queryableText("documento", SALT).decrypt(text);
    }
}