package org.devsu.api.services.impl;

import org.devsu.api.converters.ClienteConverter;
import org.devsu.api.models.dtos.requests.ClienteRequest;
import org.devsu.api.models.dtos.responses.ClienteResponse;
import org.devsu.api.models.entities.Cliente;
import org.devsu.api.repositories.ClienteRepository;
import org.devsu.api.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> findAll() {
        List<ClienteResponse> clienteResponses = new ArrayList<>();
        List<Cliente> clientes = (List<Cliente>) repository.findAll();

        if (!clientes.isEmpty())
            clientes.stream().forEach(cliente -> clienteResponses.add(ClienteConverter.convertEntityToResponse(cliente)));

        return clienteResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse findById(Long id) {
        Cliente cliente = repository.findById(id).orElse(null);

        return Objects.nonNull(cliente) ? ClienteConverter.convertEntityToResponse(cliente) : null;
    }

    @Override
    public Cliente findByClientId(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public ClienteResponse save(ClienteRequest clienteRequest) {
        Cliente cliente = ClienteConverter.convertRequestToEntity(clienteRequest);
        cliente = repository.save(cliente);

        return ClienteConverter.convertEntityToResponse(cliente);
    }

    @Override
    @Transactional
    public ClienteResponse update(Cliente cliente) {
        cliente = repository.save(cliente);
        return ClienteConverter.convertEntityToResponse(cliente);
    }
}
