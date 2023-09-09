package org.devsu.api.models.enums;

public enum TipoMovimiento {
    DEBITO("Debito"),
    CREDITO("Credito");

    private String value;

    TipoMovimiento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
