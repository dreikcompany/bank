package org.devsu.api.services.impl;

import org.devsu.api.models.dtos.responses.ReporteResponse;
import org.devsu.api.repositories.ReporteRepository;
import org.devsu.api.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Override
    public List<ReporteResponse> generatedReportByClientId(Long clienteId, boolean estado, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return reporteRepository.generatedReportByClientId(clienteId, estado, fechaInicio, fechaFin);
    }

    @Override
    public List<ReporteResponse> generatedReportByIdentificacion(Long identificacion, boolean estado, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return reporteRepository.generatedReportByIdentificacion(identificacion, estado, fechaInicio, fechaFin);
    }
}
