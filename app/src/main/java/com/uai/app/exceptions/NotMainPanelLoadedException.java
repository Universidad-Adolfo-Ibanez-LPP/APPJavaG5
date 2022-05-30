package com.uai.app.exceptions;

public class NotMainPanelLoadedException extends Exception {

    public NotMainPanelLoadedException() {
        super("No se especifico el panel principal del form");
    }
}
