package org.devsu.api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.devsu.api.commons.LocalDateTimeUtil;
import org.devsu.api.converters.MovimientoConverter;
import org.devsu.api.models.dtos.requests.MovimientoRequest;
import org.devsu.api.models.dtos.responses.MovimientoResponse;
import org.devsu.api.models.entities.Cuenta;
import org.devsu.api.models.entities.Movimiento;
import org.devsu.api.models.enums.TipoMovimiento;
import org.devsu.api.repositories.CuentaRepository;
import org.devsu.api.repositories.MovimientoRepository;
import org.devsu.api.services.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoResponse> findAll() {
        List<MovimientoResponse> movimientoResponses = new ArrayList<>();
        List<Movimiento> movimientos = movimientoRepository.findAll();
        movimientos.stream().forEach(movimiento -> movimientoResponses.add(MovimientoConverter.convertEntityToResponse(movimiento, movimiento.getCuenta().getSaldoInicial())));

        return movimientoResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientoResponse findById(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id).orElse(null);
        return Objects.nonNull(movimiento) ? MovimientoConverter.convertEntityToResponse(movimiento, movimiento.getCuenta().getSaldoInicial()) : null;
    }

    @Override
    @Transactional
    public MovimientoResponse save(MovimientoRequest movimientoRequest) {
        Cuenta cuenta = cuentaRepository.findById(movimientoRequest.getCuentaId()).orElse(null);

        if (Objects.nonNull(cuenta)) {
            Movimiento movimiento = MovimientoConverter.convertRequestToEntity(movimientoRequest);
            validateDailyLimitUsed(movimiento, cuenta, movimientoRepository);
            validateBalance(movimiento, cuenta, movimientoRepository);
            movimiento.setCuenta(cuenta);
            movimiento = movimientoRepository.save(movimiento);
            return MovimientoConverter.convertEntityToResponse(movimiento, cuenta.getSaldoInicial());
        } else {
            throw new EntityNotFoundException("La cuenta con ID " + movimientoRequest.getCuentaId() + " no existe.");
        }
    }

    @Override
    @Transactional
    public MovimientoResponse delete(Long id) {
        MovimientoResponse movimientoResponse = null;

        Movimiento movimiento = movimientoRepository.findById(id).orElse(null);
        if (Objects.nonNull(movimiento)) {
            movimientoResponse = MovimientoConverter.convertEntityToResponse(movimiento, movimiento.getCuenta().getSaldoInicial());
            movimientoRepository.delete(movimiento);
            if (!movimientoRepository.existsById(id))
                return movimientoResponse;
        }

        return null;
    }

    private static void validateDailyLimitUsed(Movimiento movimiento, Cuenta cuenta, MovimientoRepository movimientoRepository) {
        if (movimiento.getTipoMovimiento().equals(TipoMovimiento.DEBITO)) {
            List<Movimiento> movimientos = movimientoRepository.findByFechaBetweenAndTipoMovimientoAndEstado(LocalDateTimeUtil.ignoreTime(movimiento.getFecha()), LocalDateTimeUtil.toLastSecond(movimiento.getFecha()), movimiento.getTipoMovimiento(), Boolean.TRUE);
            BigDecimal limiteDiarioUsado = movimientos.stream().map(Movimiento::getValor).reduce(BigDecimal.ZERO, BigDecimal::add).add(movimiento.getValor()).abs();
            if (limiteDiarioUsado.compareTo(cuenta.getLimiteRetiro()) > 0)
                throw new IllegalArgumentException("Cupo diario Excedido.");
        }
    }

    private static void validateBalance(Movimiento movimiento, Cuenta cuenta, MovimientoRepository movimientoRepository) {
        Movimiento ultimoMovimiento = movimientoRepository.findFirstByCuentaAndEstadoOrderByIdDesc(cuenta, Boolean.TRUE);
        if (Objects.nonNull(ultimoMovimiento))
            movimiento.setSaldo(ultimoMovimiento.getSaldo().add(movimiento.getValor()));
        else
            movimiento.setSaldo(cuenta.getSaldoInicial().add(movimiento.getValor()));

        if (movimiento.getSaldo().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Saldo no disponible.");
    }
}
