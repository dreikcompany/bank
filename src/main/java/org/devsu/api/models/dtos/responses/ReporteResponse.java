package org.devsu.api.models.dtos.responses;

import org.devsu.api.models.enums.TipoCuenta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReporteResponse {

    private LocalDateTime fecha;
    private String nombreCliente;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private boolean estado;
    private BigDecimal movimiento;
    private BigDecimal saldoDisponible;

    //Se agrega mas de 7 parametros al constructor para mostrar el reporte solicitado
    @SuppressWarnings("all")
    public ReporteResponse(LocalDateTime fecha, String nombreCliente, String numeroCuenta, TipoCuenta tipoCuenta, BigDecimal saldoInicial, boolean estado, BigDecimal movimiento, BigDecimal saldoDisponible) {
        this.fecha = fecha;
        this.nombreCliente = nombreCliente;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta.getValue();
        this.saldoInicial = saldoInicial;
        this.estado = estado;
        this.movimiento = movimiento;
        this.saldoDisponible = saldoDisponible;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public BigDecimal getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(BigDecimal movimiento) {
        this.movimiento = movimiento;
    }

    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }
}
