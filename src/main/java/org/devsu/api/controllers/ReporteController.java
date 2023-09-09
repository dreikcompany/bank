package org.devsu.api.controllers;

import org.devsu.api.commons.LocalDateTimeUtil;
import org.devsu.api.models.dtos.responses.ReporteResponse;
import org.devsu.api.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService service;

    @GetMapping
    public ResponseEntity<Object> generateAccountStatementByClienteId(@RequestParam Long clienteId,
                                                                      @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fechaInicio,
                                                                      @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fechaFin) {
        LocalDateTimeUtil.validateDateAfter(fechaInicio, fechaFin);
        List<ReporteResponse> reporteResponse = service.generatedReportByClientId(clienteId, Boolean.TRUE, LocalDateTimeUtil.ignoreTime(fechaInicio), LocalDateTimeUtil.toLastSecond(fechaFin));
        return reporteResponse.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(reporteResponse);
    }

    @GetMapping("/identificacion")
    public ResponseEntity<Object> generateAccountStatementByIdentificacion(@RequestParam Long identificacion,
                                                                      @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fechaInicio,
                                                                      @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fechaFin) {
        LocalDateTimeUtil.validateDateAfter(fechaInicio, fechaFin);
        List<ReporteResponse> reporteResponse = service.generatedReportByIdentificacion(identificacion, Boolean.TRUE, LocalDateTimeUtil.ignoreTime(fechaInicio), LocalDateTimeUtil.toLastSecond(fechaFin));
        return reporteResponse.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(reporteResponse);
    }
}
