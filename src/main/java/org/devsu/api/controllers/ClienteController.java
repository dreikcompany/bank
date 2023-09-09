package org.devsu.api.controllers;

import jakarta.validation.Valid;
import org.devsu.api.commons.RequestValidator;
import org.devsu.api.models.dtos.requests.ClienteRequest;
import org.devsu.api.models.dtos.responses.ClienteResponse;
import org.devsu.api.models.entities.Cliente;
import org.devsu.api.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<ClienteResponse> clienteResponses = service.findAll();
        return clienteResponses.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(clienteResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(name = "id") Long id) {
        ClienteResponse clienteResponse = service.findById(id);
        return Objects.isNull(clienteResponse) ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(clienteResponse);
    }

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody ClienteRequest clienteRequest, BindingResult bindingResult) {
        return bindingResult.hasErrors() ? RequestValidator.validate(bindingResult) : ResponseEntity.status(HttpStatus.CREATED).body(service.save(clienteRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody ClienteRequest clienteRequest, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors())
            return RequestValidator.validate(bindingResult);

        Cliente clienteBD = service.findByClientId(id);

        if (Objects.nonNull(clienteBD)) {
            clienteBD.setIdentificacion(clienteRequest.getIdentificacion());
            clienteBD.setNombre(clienteRequest.getNombre());
            clienteBD.setDireccion(clienteRequest.getDireccion());
            clienteBD.setTelefono(clienteRequest.getTelefono());
            clienteBD.setContrasena(clienteRequest.getContrasena());
            return ResponseEntity.status(HttpStatus.OK).body(service.update(clienteBD));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        //validar si la necesidad del ejemplo sugiere un delete fisico o si esta bien hacerlo inhabilitando al cliente
        Cliente clienteBD = service.findByClientId(id);
        if (Objects.nonNull(clienteBD)) {
            clienteBD.setEstado(Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.OK).body(service.update(clienteBD));
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patch(@RequestBody ClienteRequest clienteRequest, @PathVariable Long id) {

        Cliente clienteBD = service.findByClientId(id);

        if (Objects.nonNull(clienteBD)) {
            clienteBD.setDireccion(clienteRequest.getDireccion());
            return ResponseEntity.status(HttpStatus.OK).body(service.update(clienteBD));
        }

        return ResponseEntity.notFound().build();
    }
}
