package com.uai.app.logic;

import com.uai.app.dominio.Libro;
import com.uai.app.dominio.enums.Titles;
import com.uai.app.ui.utils.AppUtils;
import org.apache.commons.text.CaseUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/*
 * Esta clase me sirve para filtrar la data que tenemos en DataManager
 * Acorde a ciertos criterios o formas de busqueda
 * Para valores que no sean numericos utiliza distancia de Levenshtein
 */
public class SearchManager {
    //nos dice la precision en la busqueda el numero
    // cuanto mas cercano a 0 es mas exacta la busqueda
    // cuanto mas lejano menos exacta
    // Chila difiere en 1 de Chile por ejemplo
    public static final int FILTER_MAX_DISTANCE = 2;

    //lo que me servira para medir las distancias entre dos strings
    private static LevenshteinDistance lv = new LevenshteinDistance();

    private static SearchManager instance;

    //todos los singletons
    // tienen constructores privados
    private SearchManager(){

    }
    //  para acceder la unica instancia
    public static SearchManager getInstance(){
        if (instance == null){
            instance = new SearchManager();
        }
        return instance;
    }

    /*
     * Esto usa la distancia de Levenshtein
     * para buscar, para llamarlo tenemos que
     * pasarle un valor de una enumeracion que se corresponde
     * al campo donde vamos a buscar, supongamos country,
     * queremos que nos devuelvan todas las Libros
     * que viven en Chile, por lo que lo llamamos
     *
     * findPersonByAttribute(Tittles.COUNTRY, "Chile")
     *
     * si quisieramos todas las Libros de nombre David
     * Entonces deberiamos llamarlo de la siguiente forma
     *
     * findPersonByAttribute(Tittles.NAME, "David")
     */

    /*
     * Esto usa la distancia de Levenshtein
     * para buscar, para llamarlo tenemos que
     * pasarle un valor de una enumeracion que se corresponde
     * al campo donde vamos a buscar, supongamos country,
     * queremos que nos devuelvan todas las Libros
     * que viven en Chile, por lo que lo llamamos
     *
     * findPersonByAttribute(Tittles.COUNTRY, "Chile")
     *
     * si quisieramos todas las Libros de nombre David
     * Entonces deberiamos llamarlo de la siguiente forma
     *
     * findPersonByAttribute(Tittles.NAME, "David")
     *
     * SI queremos especificar nosotros la precision
     *  y que no sea siempre 4 llamamos al metodo de abajo
     */
    public HashSet<Libro> findLibroByAttribute(Titles title, String theSearch){
        return findLibroByAttribute(title, theSearch, FILTER_MAX_DISTANCE);
    }

    //mismo metodo que el de arriba solo que pedimos la precision
    public HashSet<Libro> findLibroByAttribute(Titles title, String theSearch, int precision){
        //ahora instancio un mapa con esas claves
        HashSet<Libro> data = DataManager.getInstance().getData();;
        HashSet<Libro> libros = new HashSet<Libro>();
        for (Libro p : data){
            //Uso lo mismo que en el data manager
            Class<?> classObj = p.getClass();
            Method printMessage = null;
            try {
                String camelCase = CaseUtils.toCamelCase(title.getVal(), true);
                printMessage = classObj.getDeclaredMethod("get"+camelCase);
                String filterName = String.valueOf(printMessage.invoke(p));
                // si es un numero entonces no uso distancia de leventeihns
                if (printMessage.getReturnType().isPrimitive() ||
                        printMessage.getReturnType().isAssignableFrom(Integer.class)){
                    if (theSearch.trim().equalsIgnoreCase(filterName)){
                        libros.add(p);
                    }
                } else {
                    //Con una distancia de 3 estamos bien cubiertos
                    if (lv.apply(theSearch, filterName) < precision){
                        libros.add(p);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return libros;
    }
    /*
     * Este metodo devuelve conjuntos de Libros
     *  agrupados por grupos acorde a una columna//atributo
     * de la Libro. Si lo llamamos asi
     *
     * getPeopleByColum(Tittles.COUNTRY)
     *
     * va a devolver un mapa donde cada clave es el pais y asociado
     * como valor tendran las Libros que viven en ese pais
     * esto es util cuando me piden por ejemplo todos los libros de una seccion etc
     */
    public Map<String, Set<Libro>> getLibroByColum(Titles columName){
        //ahora instancio un mapa con esas claves
        Map<String, Set<Libro>> resultados = new HashMap<>();
        HashSet<Libro> data = DataManager.getInstance().getData();;
        for (Libro p : data){
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
                Set<Libro> libros = resultados.get(filterName);
                //Significa que debo crear si es true
                if (AppUtils.isNull(libros)){
                    //uso un set para evitar repetidos
                    libros = new HashSet<Libro>();
                }
                libros.add(p);
                resultados.put(filterName, libros);

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
}
