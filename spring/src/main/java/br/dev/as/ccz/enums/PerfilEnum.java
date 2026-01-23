package br.dev.as.ccz.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum PerfilEnum {

    ADMIN("ADMIN"),

    AGENTE_CCZ("AGENTE_CCZ"),

    CIDADAO("CIDADAO");

    private String value;

    PerfilEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static PerfilEnum fromValue(String text) {
        for (PerfilEnum b : PerfilEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
