package org.devsu.api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.devsu.api.converters.CuentaConverter;
import org.devsu.api.models.dtos.requests.CuentaRequest;
import org.devsu.api.models.dtos.responses.CuentaResponse;
import org.devsu.api.models.entities.Cliente;
import org.devsu.api.models.entities.Cuenta;
import org.devsu.api.repositories.ClienteRepository;
import org.devsu.api.repositories.CuentaRepository;
import org.devsu.api.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CuentaResponse> findAll() {
        List<CuentaResponse> cuentaResponses = new ArrayList<>();
        List<Cuenta> cuentas = (List<Cuenta>) cuentaRepository.findAll();

        if (!cuentas.isEmpty())
            cuentas.stream().forEach(cuenta -> cuentaResponses.add(CuentaConverter.convertEntityToResponse(cuenta)));

        return cuentaResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaResponse findById(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id).orElse(null);
        return Objects.nonNull(cuenta) ? CuentaConverter.convertEntityToResponse(cuenta) : null;
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findCuentaById(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaResponse findCuentaByNumeroCuentaAndEstado(String numeroCuenta, boolean estado) {
        Cuenta cuenta = cuentaRepository.findCuentaByNumeroCuentaAndEstado(numeroCuenta, estado);
        return Objects.nonNull(cuenta) ? CuentaConverter.convertEntityToResponse(cuenta) : null;
    }

    @Override
    @Transactional
    public CuentaResponse save(CuentaRequest cuentaRequest) {
        Cliente cliente = clienteRepository.findById(cuentaRequest.getClienteId()).orElse(null);

        if (Objects.nonNull(cliente)) {
            Cuenta cuenta = CuentaConverter.convertRequestToEntity(cuentaRequest);
            cuenta.setCliente(cliente);
            cuenta = cuentaRepository.save(cuenta);
            return CuentaConverter.convertEntityToResponse(cuenta);
        } else {
            throw new EntityNotFoundException("El cliente con ID " + cuentaRequest.getClienteId() + " no existe.");
        }
    }

    @Override
    @Transactional
    public CuentaResponse update(Cuenta cuenta) {
        cuenta = cuentaRepository.save(cuenta);
        return CuentaConverter.convertEntityToResponse(cuenta);
    }
}
