package com.desafio.zup.proposta.compartilhado;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorsResponse {
    private List<String> globalErrorMessages = new ArrayList<>();
    private List<FieldErrorResponse> fieldErrors = new ArrayList<>();

    public void addError(String Message){
        globalErrorMessages.add(Message);
    }

    public void addFieldError(String field, String message){
        fieldErrors.add(new FieldErrorResponse(field, message));
    }

    public List<String> getGlobalErrorMessages() {
        return globalErrorMessages;
    }

    public List<FieldErrorResponse> getFieldErrors() {
        return fieldErrors;
    }
}