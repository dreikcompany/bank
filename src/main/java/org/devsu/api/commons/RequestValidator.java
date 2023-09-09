package org.devsu.api.commons;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class RequestValidator {

    private RequestValidator() {}

    public static ResponseEntity<Object> validate(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().stream().forEach(fieldError -> errors.put(fieldError.getField(), "El Campo " + fieldError.getField() + " " + fieldError.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
