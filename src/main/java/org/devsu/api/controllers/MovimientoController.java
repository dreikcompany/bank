package org.devsu.api.controllers;

import jakarta.validation.Valid;
import org.devsu.api.commons.RequestValidator;
import org.devsu.api.models.dtos.requests.MovimientoRequest;
import org.devsu.api.models.dtos.responses.MovimientoResponse;
import org.devsu.api.services.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService service;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<MovimientoResponse> movimientoResponses = service.findAll();
        return movimientoResponses.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(movimientoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(name = "id") Long id) {
        MovimientoResponse movimientoResponse = service.findById(id);
        return Objects.isNull(movimientoResponse) ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(movimientoResponse);
    }

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody MovimientoRequest movimientoRequest, BindingResult bindingResult) {
        return bindingResult.hasErrors() ? RequestValidator.validate(bindingResult) : ResponseEntity.status(HttpStatus.CREATED).body(service.save(movimientoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        MovimientoResponse movimientoResponse = service.delete(id);
        return Objects.isNull(movimientoResponse) ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(movimientoResponse);
    }
}
