package com.uai.app.ui;

import com.uai.app.dominio.Libro;
import com.uai.app.logic.DataManager;
import com.uai.app.ui.utils.UAIJFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.HashSet;

public class MostrarDatosUI extends UAIJFrame  {

    private JPanel mainPanel;
    private JPanel mainTableContainer;

    public MostrarDatosUI(String title) {
        super(title);
        this.setMainPanel(mainPanel);
        String[] titles = {"titulo","autor","anio","estante_numero","estante_seccion","piso","edificio","sede"};
        //obtengo las libros en una matriz
        HashSet<Libro> data = DataManager.getInstance().getData();
        String[][] dataTabla = new String[data.size()][4];
        int cont = 0;
        for(Libro p : data) {
            dataTabla[cont] = p.getDataToCsv();
            cont++;
        }
        TableModel tableModel = new DefaultTableModel(dataTabla, titles);
        JTable table = new JTable(tableModel);
        mainTableContainer.setLayout(new BorderLayout());
        mainTableContainer.add(new JScrollPane(table), BorderLayout.CENTER);
        mainTableContainer.add(table.getTableHeader(), BorderLayout.NORTH);
        mainTableContainer.setVisible(true);
        mainTableContainer.setSize(400,400);
    }

}
