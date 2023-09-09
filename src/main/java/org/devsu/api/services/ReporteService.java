package org.devsu.api.services;

import org.devsu.api.models.dtos.responses.ReporteResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ReporteService {

    List<ReporteResponse> generatedReportByClientId(Long clienteId, boolean estado, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    List<ReporteResponse> generatedReportByIdentificacion(Long identificacion, boolean estado, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
