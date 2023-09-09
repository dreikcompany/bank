package org.devsu.api.repositories;

import org.devsu.api.models.entities.Cuenta;
import org.devsu.api.models.entities.Movimiento;
import org.devsu.api.models.enums.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    Movimiento findFirstByCuentaAndEstadoOrderByIdDesc(Cuenta cuenta, Boolean estado);
    List<Movimiento> findByFechaBetweenAndTipoMovimientoAndEstado(LocalDateTime fechaInicio, LocalDateTime fechaFin, TipoMovimiento tipoMovimiento, Boolean estado);
}
