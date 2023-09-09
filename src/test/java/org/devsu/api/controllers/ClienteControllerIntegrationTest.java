package org.devsu.api.controllers;

import org.devsu.api.models.dtos.requests.ClienteRequest;
import org.devsu.api.models.dtos.responses.ClienteResponse;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;


    @Test
    @Order(1)
    void save() {
        // Given
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setIdentificacion(123456789L);
        clienteRequest.setNombre("Maria Perez");
        clienteRequest.setGenero("Femenino");
        clienteRequest.setEdad(25);
        clienteRequest.setDireccion("Kenedy");
        clienteRequest.setTelefono("99996644");
        clienteRequest.setContrasena("1234");

        // When
        webTestClient.post()
                .uri("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(clienteRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClienteResponse.class)
                .value(clienteResponse -> {
                    // Then
                    assertNotNull(clienteResponse);
                    assertEquals(123456789L, clienteResponse.getIdentificacion());
                    assertEquals("Maria Perez", clienteResponse.getNombre());
                    assertEquals("Kenedy", clienteResponse.getDireccion());
                    assertEquals("99996644", clienteResponse.getTelefono());
                    assertEquals("1234", clienteResponse.getContrasena());
                    assertNotNull(clienteResponse.getId());
                    assertNotEquals(Boolean.FALSE, clienteResponse.isEstado());
                });

    }

}