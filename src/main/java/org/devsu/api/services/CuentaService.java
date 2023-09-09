package org.devsu.api.services;

import org.devsu.api.models.dtos.requests.CuentaRequest;
import org.devsu.api.models.dtos.responses.CuentaResponse;
import org.devsu.api.models.entities.Cuenta;

import java.util.List;

public interface CuentaService {

    List<CuentaResponse> findAll();
    CuentaResponse findById(Long id);
    Cuenta findCuentaById(Long id);
    CuentaResponse findCuentaByNumeroCuentaAndEstado(String numeroCuenta, boolean estado);
    CuentaResponse save(CuentaRequest cuentaRequest);
    CuentaResponse update(Cuenta cuenta);
}
