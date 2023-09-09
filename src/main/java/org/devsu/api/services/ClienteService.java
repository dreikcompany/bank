package org.devsu.api.services;

import org.devsu.api.models.dtos.requests.ClienteRequest;
import org.devsu.api.models.dtos.responses.ClienteResponse;
import org.devsu.api.models.entities.Cliente;

import java.util.List;

public interface ClienteService {

    List<ClienteResponse> findAll();
    ClienteResponse findById(Long id);
    Cliente findByClientId(Long id);
    ClienteResponse save(ClienteRequest clienteRequest);
    ClienteResponse update(Cliente cliente);
}
