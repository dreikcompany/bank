package org.devsu.api.repositories;

import org.devsu.api.models.entities.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    Cliente findClienteByIdentificacion(Long identificacion);
}
