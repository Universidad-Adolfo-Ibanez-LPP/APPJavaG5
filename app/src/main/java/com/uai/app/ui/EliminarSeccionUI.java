package com.uai.app.ui;

import com.uai.app.dominio.Libro;
import com.uai.app.dominio.enums.Titles;
import com.uai.app.logic.DataManager;
import com.uai.app.logic.SearchManager;
import com.uai.app.ui.utils.UAIJFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class EliminarSeccionUI extends UAIJFrame {
    private JTextField textField1;
    private JButton buscarButton;
    private JButton eliminarButton;
    private JPanel mainPanel;
    private JPanel mainTabla;

    public EliminarSeccionUI(String title){
        super(title);
        this.setMainPanel(mainPanel);
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText().equals("")){
                    JOptionPane.showMessageDialog(mainPanel, "Debe ingresar la sección de estante a eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if (SearchManager.getInstance().findLibroByAttribute(Titles.ESTANTE_SECCION, textField1.getText()).isEmpty()) {
                        JOptionPane.showMessageDialog(mainPanel, "No se ha encontrado la sección de estante", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String[] titles = {"Título", "Autor", "Año", "N° de estante", "Sección de estante", "Piso", "Edificio", "Sede"};
                        HashSet<Libro> data = SearchManager.getInstance().findLibroByAttribute(Titles.ESTANTE_SECCION, textField1.getText());
                        int cont = 0;
                        String[][] dataTabla = new String[data.size()][4];
                        for (Libro p : data) {
                            dataTabla[cont] = p.getDataToCsv();
                            cont++;
                        }
                        TableModel tableModel = new DefaultTableModel(dataTabla, titles);
                        JTable table = new JTable(tableModel);
                        mainTabla.setLayout(new BorderLayout());
                        mainTabla.add(new JScrollPane(table), BorderLayout.CENTER);
                        mainTabla.add(table.getTableHeader(), BorderLayout.NORTH);
                        mainTabla.setVisible(true);
                        mainTabla.setSize(400, 400);
                    }
                }
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashSet<Libro> data = SearchManager.getInstance().findLibroByAttribute(Titles.ESTANTE_SECCION, textField1.getText());
                for (Libro p : data) {
                    DataManager.getInstance().removerLibro(p);
                }
                dispose();
            }
        });
    }
}
