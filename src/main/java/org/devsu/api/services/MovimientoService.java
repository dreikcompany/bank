package org.devsu.api.services;

import org.devsu.api.models.dtos.requests.MovimientoRequest;
import org.devsu.api.models.dtos.responses.MovimientoResponse;

import java.util.List;

public interface MovimientoService {

    List<MovimientoResponse> findAll();
    MovimientoResponse findById(Long id);
    MovimientoResponse save(MovimientoRequest movimientoRequest);
    MovimientoResponse delete(Long id);
}
