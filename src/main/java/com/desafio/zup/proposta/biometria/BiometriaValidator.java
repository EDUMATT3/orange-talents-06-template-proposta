package com.desafio.zup.proposta.biometria;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BiometriaValidator implements Validator {

    private final Logger logger = LoggerFactory.getLogger(BiometriaValidator.class);

    @Override
    public boolean supports(Class<?> aClass) {
        return CadastroBiometriaRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(errors.hasErrors()) return;

        CadastroBiometriaRequest request = (CadastroBiometriaRequest) o;

        try {
            java.util.Base64.getDecoder().decode(request.getFingerPrint());
        } catch(IllegalArgumentException e) {
            logger.warn("Fingerprint em formato inválido.");
            errors.rejectValue("fingerPrint", null, "Deve estar em base64");
        }
    }
}
