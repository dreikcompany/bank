package org.devsu.api.models.dtos.requests;

import jakarta.validation.constraints.NotNull;

public class ClienteRequest extends PersonaRequest{

    @NotNull
    private String contrasena;

    private boolean estado;

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
