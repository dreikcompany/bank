package org.devsu.api.converters;

import org.devsu.api.models.dtos.requests.MovimientoRequest;
import org.devsu.api.models.dtos.responses.MovimientoResponse;
import org.devsu.api.models.entities.Movimiento;
import org.devsu.api.models.enums.TipoMovimiento;

import java.math.BigDecimal;

public class MovimientoConverter {

    private MovimientoConverter() {}

    public static Movimiento convertRequestToEntity(MovimientoRequest movimientoRequest) {
        validateValue(movimientoRequest.getValor());
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(movimientoRequest.getFecha());
        movimiento.setValor(movimientoRequest.getValor());
        movimiento.setTipoMovimiento(movimientoRequest.getValor().compareTo(BigDecimal.ZERO) > 0 ? TipoMovimiento.CREDITO : TipoMovimiento.DEBITO);
        return movimiento;
    }

    public static MovimientoResponse convertEntityToResponse(Movimiento movimiento, BigDecimal saldoInicial) {
        MovimientoResponse movimientoResponse = new MovimientoResponse();
        movimientoResponse.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
        movimientoResponse.setTipoCuenta(movimiento.getCuenta().getTipoCuenta().getValue());
        movimientoResponse.setSaldoInicial(saldoInicial);
        movimientoResponse.setEstado(movimiento.isEstado());
        movimientoResponse.setMovimiento(buildMessage(movimiento));

        return movimientoResponse;
    }

    private static String buildMessage(Movimiento movimiento) {
        StringBuilder message = new StringBuilder();

        if (movimiento.getTipoMovimiento().equals(TipoMovimiento.DEBITO))
            message.append("Retiro de ").append(movimiento.getValor().abs().doubleValue());
        else
            message.append("Deposito de ").append(movimiento.getValor().abs().doubleValue());

        return message.toString();
    }

    private static void validateValue(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) == 0)
            throw new IllegalArgumentException("El valor no puede ser cero.");
    }
}
