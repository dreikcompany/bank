package org.devsu.api.models.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Persona {

    private String contrasena;
    private boolean estado;

    @OneToMany(mappedBy = "cliente")
    private List<Cuenta> cuentas;


    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @PrePersist
    public void prePersist() {
        this.estado = true;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }
}
