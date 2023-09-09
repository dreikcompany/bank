package org.devsu.api.models.enums;

public enum TipoCuenta {
    AHORROS("Ahorros"),
    CORRIENTE("Corriente");

    private String value;

    TipoCuenta(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
