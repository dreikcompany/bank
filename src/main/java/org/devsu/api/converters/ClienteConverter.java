package org.devsu.api.converters;

import org.devsu.api.models.dtos.requests.ClienteRequest;
import org.devsu.api.models.dtos.responses.ClienteResponse;
import org.devsu.api.models.entities.Cliente;

public class ClienteConverter {

    private ClienteConverter() {}

    public static Cliente convertRequestToEntity(ClienteRequest clienteRequest) {
        Cliente cliente = new Cliente();
        cliente.setIdentificacion(clienteRequest.getIdentificacion());
        cliente.setNombre(clienteRequest.getNombre());
        cliente.setGenero(clienteRequest.getGenero());
        cliente.setEdad(clienteRequest.getEdad());
        cliente.setDireccion(clienteRequest.getDireccion());
        cliente.setTelefono(clienteRequest.getTelefono());
        cliente.setContrasena(clienteRequest.getContrasena());

        return cliente;
    }

    public static ClienteResponse convertEntityToResponse(Cliente cliente) {
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setId(cliente.getId());
        clienteResponse.setIdentificacion(cliente.getIdentificacion());
        clienteResponse.setNombre(cliente.getNombre());
        clienteResponse.setDireccion(cliente.getDireccion());
        clienteResponse.setTelefono(cliente.getTelefono());
        clienteResponse.setContrasena(cliente.getContrasena());
        clienteResponse.setEstado(cliente.isEstado());

        return clienteResponse;
    }
}
