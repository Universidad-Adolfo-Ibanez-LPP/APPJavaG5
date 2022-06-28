package com.uai.app.dominio.enums;

public enum Titles {

    TITULO("titulo"),AUTOR("autor"),ANIO("anio"),ESTANTE_NUMERO("estante_numero"),
    ESTANTE_SECCION("estante_seccion"),PISO("piso"),EDIFICIO("edificio"),SEDE("sede");

    private String val;

    public String getVal() {
        return val;
    }

    Titles(String val) {
        this.val = val;
    }
}
