package com.uai.app.ui;

import com.uai.app.dominio.Libro;
import com.uai.app.dominio.enums.Titles;
import com.uai.app.logic.DataManager;
import com.uai.app.logic.SearchManager;
import com.uai.app.ui.utils.UAIJFrame;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

public class EditarLibroUI extends UAIJFrame {
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton buscarButton;
    private JButton guardarCambiosButton;
    private JPanel mainTabla;
    Libro edit = new Libro();

    public EditarLibroUI(String title) {
        super(title);
        this.setMainPanel(mainPanel);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText().equals("")){
                    JOptionPane.showMessageDialog(mainPanel, "Debe ingresar el título del libro", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if (SearchManager.getInstance().findLibroByAttribute(Titles.TITULO, textField1.getText()).isEmpty()) {
                        JOptionPane.showMessageDialog(mainPanel, "No se ha encontrado el título del libro", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        String[] titles = {"Título", "Autor", "Año", "N° de estante", "Sección de estante", "Piso", "Edificio", "Sede"};
                        HashSet<Libro> data = SearchManager.getInstance().findLibroByAttribute(Titles.TITULO, textField1.getText());
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
                        tableModel.addTableModelListener(new TableModelListener() {
                            @Override
                            public void tableChanged(TableModelEvent e) {
                                try {
                                    edit.setTitulo(table.getValueAt(0, 0).toString());
                                    edit.setAutor(table.getValueAt(0, 1).toString());
                                    edit.setAnio(Integer.parseInt(table.getValueAt(0, 2).toString()));
                                    edit.setEstante_numero(Integer.parseInt(table.getValueAt(0, 3).toString()));
                                    edit.setEstante_seccion(table.getValueAt(0, 4).toString());
                                    edit.setPiso(Integer.parseInt(table.getValueAt(0, 5).toString()));
                                    edit.setEdificio(table.getValueAt(0, 6).toString());
                                    edit.setSede(table.getValueAt(0, 7).toString());
                                    if (edit.getTitulo().equals("") || edit.getAutor().equals("") || edit.getEstante_seccion().equals("") ||
                                            edit.getEdificio().equals("") || edit.getSede().equals("")){
                                        JOptionPane.showMessageDialog(mainPanel, "Por favor, editar los campos de forma correcta", "Error", JOptionPane.ERROR_MESSAGE);
                                        dispose();
                                    }
                                }catch(NumberFormatException exception){
                                    JOptionPane.showMessageDialog(mainPanel, "Por favor, editar los campos de forma correcta", "Error", JOptionPane.ERROR_MESSAGE);
                                    dispose();
                                }
                            }
                        });
                    }
                }
            }
        });

        guardarCambiosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashSet<Libro> data = SearchManager.getInstance().findLibroByAttribute(Titles.TITULO, textField1.getText());
                for (Libro p : data) {
                    DataManager.getInstance().removerLibro(p);
                }
                DataManager.getInstance().agregarLibro(edit);
                dispose();
            }
        });
    }
}
