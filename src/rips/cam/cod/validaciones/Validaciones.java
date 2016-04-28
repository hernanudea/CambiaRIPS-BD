package rips.cam.cod.validaciones;

import rips.cam.cod.proc.VentanaPrincipal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * Clase para realizar diferentes validaciones
 *
 * @author hernan
 */
public class Validaciones {
    /**
     * Comprueba la longitud de una cadena de texto
     *
     * @param cadena   que debe ser verificada
     * @param longitud longitus esperada
     * @return true si la cadena cumple con la longitus esperada o false si no lo hace.
     */
    public static boolean verificarLongitud(String cadena, int longitud) {
        return cadena.length() == longitud;
    }

    /**
     * verifica si es una fecha
     *
     * @param fecha
     * @return false si no es una fecha, true si si lo es
     */
    public static boolean isDate(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * verifica si la cadena recibida es un numero
     *
     * @param cadena a verificar
     * @return true si es un numero, false en caso contrario
     */
    public static boolean isNumero(String cadena) {
        try {
            Long.parseLong(cadena);
            return true;
        } catch (NumberFormatException nfe) {

            return false;
        }
    }

    /**
     * Verifica si la cadena ogligatoria esta vacia o fue llenada.
     *
     * @param cadena
     * @return true si la cadena es diferente a null o a vacia, false si el campo no fue lleno
     */
    public static boolean isObligatorio(String cadena) {
        if (cadena.equals("")) {
            return false;
        }
        return !cadena.equals(null);
    }

    public boolean verificarRango(String valor, int limiteInferior, int limiteSuperior) {
        try {
            int valorInt = Integer.parseInt(valor);
            return valorInt >= limiteInferior && valorInt <= limiteSuperior;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

}
