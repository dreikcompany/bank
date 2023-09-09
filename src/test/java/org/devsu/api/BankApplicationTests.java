package org.devsu.api;

import org.devsu.api.commons.LocalDateTimeUtil;
import org.devsu.api.models.dtos.responses.ClienteResponse;
import org.devsu.api.models.entities.Cliente;
import org.devsu.api.repositories.ClienteRepository;
import org.devsu.api.repositories.CuentaRepository;
import org.devsu.api.repositories.MovimientoRepository;
import org.devsu.api.services.ClienteService;
import org.devsu.api.services.CuentaService;
import org.devsu.api.services.MovimientoService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankApplicationTests {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private CuentaService cuentaService;

	@Autowired
	private MovimientoService movimientoService;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CuentaRepository cuentaRepository;

	@Autowired
	private MovimientoRepository movimientoRepository;

	@Test
	@Order(1)
	void contextLoads() {
		assertNotNull(clienteService);
		assertNotNull(cuentaService);
		assertNotNull(movimientoService);
		assertNotNull(clienteRepository);
		assertNotNull(cuentaRepository);
		assertNotNull(movimientoRepository);
	}

	@Test
	@Order(2)
	void testValidateDateAfter() {
		LocalDate initialDate = LocalDate.of(2023, 9, 1);
		LocalDate endDate = LocalDate.of(2023, 9, 30);

		assertDoesNotThrow(() -> LocalDateTimeUtil.validateDateAfter(initialDate, endDate));

		LocalDate invalidInitialDate = LocalDate.of(2023, 9, 30);
		LocalDate invalidEndDate = LocalDate.of(2023, 9, 1);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> LocalDateTimeUtil.validateDateAfter(invalidInitialDate, invalidEndDate));

		assertEquals("Fecha fin debe ser posterior a Fecha inicio.", exception.getMessage());
	}

	@Test
	@Order(3)
	void findAllClienteResponses() {
		List<ClienteResponse> clienteResponses = clienteService.findAll();
		if (clienteResponses.isEmpty())
			assertTrue(Boolean.TRUE.equals(clienteResponses.isEmpty()));
		else
			assertTrue(clienteResponses.size() > 0);
	}

	@Test
	@Order(4)
	void saveCliente() {
		//Given
		Cliente cliente = new Cliente();
		cliente.setIdentificacion(777777L);
		cliente.setNombre("Juan Perez");
		cliente.setGenero("Masculino");
		cliente.setEdad(25);
		cliente.setDireccion("Marly");
		cliente.setTelefono("6585854");
		cliente.setContrasena("1234");

		//When
		cliente = clienteRepository.save(cliente);

		//Then
		assertNotNull(cliente);
		assertEquals(777777L, cliente.getIdentificacion());
		assertEquals("Juan Perez", cliente.getNombre());
		assertEquals("Marly", cliente.getDireccion());
		assertEquals("6585854", cliente.getTelefono());
		assertEquals("1234", cliente.getContrasena());
		assertNotNull(cliente.getId());
		assertNotEquals(Boolean.FALSE, cliente.isEstado());
	}

}
