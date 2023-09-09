package org.devsu.api.converters;

import org.devsu.api.models.dtos.requests.CuentaRequest;
import org.devsu.api.models.dtos.responses.CuentaResponse;
import org.devsu.api.models.entities.Cuenta;
import org.devsu.api.models.enums.TipoCuenta;

import java.util.Arrays;

public class CuentaConverter {

    private CuentaConverter() {}

    public static Cuenta convertRequestToEntity(CuentaRequest cuentaRequest) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(cuentaRequest.getNumeroCuenta());

        TipoCuenta tipoCuenta = Arrays.stream(TipoCuenta.values())
                .filter(type -> type.name().equalsIgnoreCase(cuentaRequest.getTipoCuenta()))
                .findFirst()
                .orElse(null);

        cuenta.setTipoCuenta(tipoCuenta);
        cuenta.setSaldoInicial(cuentaRequest.getSaldoInicial());

        return cuenta;
    }

    public static CuentaResponse convertEntityToResponse(Cuenta cuenta) {
        CuentaResponse cuentaResponse = new CuentaResponse();
        cuentaResponse.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaResponse.setTipoCuenta(cuenta.getTipoCuenta().getValue());
        cuentaResponse.setSaldoInicial(cuenta.getSaldoInicial());
        cuentaResponse.setClienteNombre(cuenta.getCliente().getNombre());
        cuentaResponse.setEstado(cuenta.isEstado());

        return cuentaResponse;
    }
}
