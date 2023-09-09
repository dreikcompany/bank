package org.devsu.api.repositories;

import org.devsu.api.models.dtos.responses.ReporteResponse;
import org.devsu.api.models.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReporteRepository extends JpaRepository<Movimiento, Long> {

    @Query("SELECT new org.devsu.api.models.dtos.responses.ReporteResponse(mov.fecha, c.nombre, cta.numeroCuenta, cta.tipoCuenta, cta.saldoInicial, mov.estado, mov.valor, mov.saldo) " +
            "FROM Movimiento mov " +
            "INNER JOIN mov.cuenta cta " +
            "INNER JOIN cta.cliente c " +
            "WHERE c.id = :clienteId " +
            "AND mov.estado = :estado " +
            "AND mov.fecha >= :fechaInicio AND mov.fecha <= :fechaFin " +
            "ORDER BY mov.fecha ASC ")
    List<ReporteResponse> generatedReportByClientId(@Param("clienteId") Long clienteId, @Param("estado") boolean estado, @Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT new org.devsu.api.models.dtos.responses.ReporteResponse(mov.fecha, c.nombre, cta.numeroCuenta, cta.tipoCuenta, cta.saldoInicial, mov.estado, mov.valor, mov.saldo) " +
            "FROM Movimiento mov " +
            "INNER JOIN mov.cuenta cta " +
            "INNER JOIN cta.cliente c " +
            "WHERE c.identificacion = :identificacion " +
            "AND mov.estado = :estado " +
            "AND mov.fecha >= :fechaInicio AND mov.fecha <= :fechaFin " +
            "ORDER BY mov.fecha ASC ")
    List<ReporteResponse> generatedReportByIdentificacion(@Param("identificacion") Long identificacion, @Param("estado") boolean estado, @Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
}
