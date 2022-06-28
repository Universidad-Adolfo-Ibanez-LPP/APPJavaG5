package com.uai.app.ui;

import com.uai.app.ui.utils.UAIJFrame;
import com.uai.app.ui.utils.UIBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminarLibroUI extends UAIJFrame{
    private JPanel mainPanel;
    private JButton tituloButton;
    private JButton seccionButton;
    private JButton sedeButton;
    private JButton pisoButton;

    public EliminarLibroUI(String title) {
        super(title);
        this.setMainPanel(mainPanel);

        tituloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {UIBuilder.buildUI(EliminarTituloUI.class);
            }
        });
        sedeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {UIBuilder.buildUI(EliminarSedeUI.class);
            }
        });
        seccionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {UIBuilder.buildUI(EliminarSeccionUI.class);
            }
        });
        pisoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {UIBuilder.buildUI(EliminarPisoUI.class);
            }
        });
    }
}
