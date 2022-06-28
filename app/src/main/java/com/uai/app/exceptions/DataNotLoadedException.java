package com.uai.app.exceptions;

public class DataNotLoadedException extends Exception {

    public DataNotLoadedException() {
        super("La Data aun no se cargo antes de llamar a este metodo, " +
                "debes llamar a setData antes");
    }
}
