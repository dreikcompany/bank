package org.devsu.api.repositories;

import org.devsu.api.models.entities.Cuenta;
import org.springframework.data.repository.CrudRepository;

public interface CuentaRepository extends CrudRepository<Cuenta, Long> {

    Cuenta findCuentaByNumeroCuentaAndEstado(String numeroCuenta, boolean estado);
}
