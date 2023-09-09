package org.devsu.api.controllers;

import jakarta.validation.Valid;
import org.devsu.api.commons.RequestValidator;
import org.devsu.api.models.dtos.requests.CuentaRequest;
import org.devsu.api.models.dtos.requests.CuentaUpdateRequest;
import org.devsu.api.models.dtos.responses.CuentaResponse;
import org.devsu.api.models.entities.Cuenta;
import org.devsu.api.models.enums.TipoCuenta;
import org.devsu.api.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService service;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<CuentaResponse> cuentaResponses = service.findAll();
        return cuentaResponses.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(cuentaResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(name = "id") Long id) {
        CuentaResponse cuentaResponse = service.findById(id);
        return Objects.isNull(cuentaResponse) ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(cuentaResponse);
    }

    @GetMapping("/numero-cuenta/{numeroCuenta}")
    public ResponseEntity<Object> findByNumeroCuenta(@PathVariable(name = "numeroCuenta") String numeroCuenta) {
        CuentaResponse cuentaResponse = service.findCuentaByNumeroCuentaAndEstado(numeroCuenta, Boolean.TRUE);
        return Objects.isNull(cuentaResponse) ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(cuentaResponse);
    }

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody CuentaRequest cuentaRequest, BindingResult bindingResult) {
        return bindingResult.hasErrors() ? RequestValidator.validate(bindingResult) : ResponseEntity.status(HttpStatus.CREATED).body(service.save(cuentaRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody CuentaUpdateRequest cuentaUpdateRequest, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors())
            return RequestValidator.validate(bindingResult);

        Cuenta cuentaBd = service.findCuentaById(id);

        if (Objects.nonNull(cuentaBd)) {
            cuentaBd.setNumeroCuenta(cuentaUpdateRequest.getNumeroCuenta());
            TipoCuenta tipoCuenta = Arrays.stream(TipoCuenta.values())
                    .filter(type -> type.name().equalsIgnoreCase(cuentaUpdateRequest.getTipoCuenta()))
                    .findFirst()
                    .orElse(null);

            cuentaBd.setTipoCuenta(tipoCuenta);
            return ResponseEntity.status(HttpStatus.OK).body(service.update(cuentaBd));
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        //validar si la necesidad del ejemplo sugiere un delete fisico o si esta bien hacerlo inhabilitando al cliente
        Cuenta cuentaBD = service.findCuentaById(id);
        if (Objects.nonNull(cuentaBD)) {
            cuentaBD.setEstado(Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.OK).body(service.update(cuentaBD));
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patch(@RequestBody CuentaRequest cuentaRequest, @PathVariable Long id) {

        Cuenta cuentaBD = service.findCuentaById(id);

        if (Objects.nonNull(cuentaBD)) {
            TipoCuenta tipoCuenta = Arrays.stream(TipoCuenta.values())
                    .filter(type -> type.name().equalsIgnoreCase(cuentaRequest.getTipoCuenta()))
                    .findFirst()
                    .orElse(null);


            cuentaBD.setTipoCuenta(tipoCuenta);
            return ResponseEntity.status(HttpStatus.OK).body(service.update(cuentaBD));
        }

        return ResponseEntity.notFound().build();
    }
}
