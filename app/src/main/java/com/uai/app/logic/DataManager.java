package com.uai.app.logic;

import com.uai.app.dominio.Libro;
import com.uai.app.dominio.enums.Titles;
import com.uai.app.exceptions.DataNotLoadedException;
import com.uai.app.ui.utils.*;
import org.apache.commons.text.CaseUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/*
*
* Dado que solo necesitare una instancia de esta clase
* la convierto en SIngleton https://refactoring.guru/design-patterns/singleton
*
 */
public class DataManager {

    private HashSet<Libro> data;

    private static DataManager instance;

    //todos los singletons
    // tienen constructores privados
    private DataManager(){
    }

    public static DataManager getInstance(){
        if (instance == null){
            instance = new DataManager();
            SearchManager.getInstance();
        }
        return instance;
    }

    public HashSet<Libro> getData() {return data;}

    public Map<String, Set<Libro>> getLibrosByColum(Titles columName){
        //ahora instancio un mapa con esas claves
        Map<String, Set<Libro>> resultados = new HashMap<>();
        for (Libro p : this.data){
            //primero debo saber que atributo
            // es para saber a que get llamare
            // esto se denomina llamar
            // a metodos por reflexion
            Class<?> classObj = p.getClass();
            Method printMessage = null;
            try {
                String camelCase = CaseUtils.toCamelCase(columName.getVal(), true);
                printMessage = classObj.getDeclaredMethod("get"+camelCase);
                String filterName = String.valueOf(printMessage.invoke(p));
                Set<Libro> biblioteca = resultados.get(filterName);
                //Significa que debo crear si es true
                if (AppUtils.isNull(biblioteca)){
                    //uso un set para evitar repetidos
                    biblioteca = new HashSet<Libro>();
                }
                biblioteca.add(p);
                resultados.put(filterName, biblioteca);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return resultados;
    }

    public void setData(HashSet<Libro> data) {this.data = data;}

    public String getDataAsString() throws DataNotLoadedException {
        //verifico que la data exista y sino devuelvo excepcion
        if (AppUtils.isNull(data)){
          throw new DataNotLoadedException();
        }
        //Creo un string para ir sumando ahi la data
        StringBuilder sb = new StringBuilder(data.size()*50);
        for (Libro p : data){
               sb.append(p);
               sb.append("\n");
        }
        return sb.toString();
    }

    public void agregarLibro(Libro p){
        this.data.add(p);
    }

    public void removerLibro(Libro p){
        this.data.remove(p);
    }

    public void removerLibros(Collection<Libro> libros){
        this.data.removeAll(libros);
    }
}
