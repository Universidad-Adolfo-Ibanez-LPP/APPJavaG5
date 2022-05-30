package com.uai.app.files;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.uai.app.dominio.Libro;
import com.uai.app.logic.DataManager;

import com.uai.app.exceptions.CSVNotFoundException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;

public class FileManager {

    private File theFile;

    private String[] titles = {"titulo","autor","anio","estante_numero","estante_seccion","piso","edificio","sede"};
    /*
    Reviso si existe el archivo que me van a hacer ocupar
    y sino tiro una excepcion para arriba
     */
    public FileManager(String fileName) throws CSVNotFoundException {
        this.theFile = new File(fileName);
        if (!theFile.exists()){
            throw new CSVNotFoundException();
        }
    }

    public HashSet<Libro> getData() {
        List<Libro> beans = null;
        try {
            FileReader ff = new FileReader(theFile, Charset.forName("UTF-8"));
            beans = new CsvToBeanBuilder(ff)
                    .withType(Libro.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            System.err.println("No existe el archivo");
        } catch (IOException e) {
            System.err.println("No se puede leer el archivo");
        }
        HashSet<Libro> librosFinales = new HashSet();
        librosFinales.addAll(beans);
        return librosFinales;
    }

    public void saveData(){
        try {
            FileWriter t = new FileWriter(theFile.getName());
            CSVWriter writer = new CSVWriter(t);
            // Aca convierto al csv que necesito
            writer.writeNext(titles, false);
            HashSet<Libro> data = DataManager.getInstance().getData();

            for(Libro p : data){
                //significa que lo quiero mantener
                writer.writeNext(p.getDataToCsv(),false);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
