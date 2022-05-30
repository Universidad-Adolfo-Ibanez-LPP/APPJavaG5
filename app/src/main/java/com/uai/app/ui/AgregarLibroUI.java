package com.uai.app.ui;

import com.uai.app.dominio.Libro;
import com.uai.app.logic.DataManager;
import com.uai.app.ui.utils.UAIJFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgregarLibroUI extends UAIJFrame {

    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JButton agregarButton;

    public AgregarLibroUI(String title) {
        super(title);
        this.setMainPanel(mainPanel);

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Libro a = new Libro();
                    a.setTitulo(textField1.getText());
                    a.setAutor(textField2.getText());
                    a.setAnio(Integer.parseInt(textField3.getText()));
                    a.setEstante_numero(Integer.parseInt(textField4.getText()));
                    a.setEstante_seccion(textField5.getText());
                    a.setPiso(Integer.parseInt(textField6.getText()));
                    a.setEdificio(textField7.getText());
                    a.setSede(textField8.getText());
                    DataManager.getInstance().agregarLibro(a);
                    dispose();
                }catch(NumberFormatException exception){
                    JOptionPane.showMessageDialog(mainPanel, "Por favor, llenar todos los campos de forma correcta", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}

