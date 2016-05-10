package rips.cam.cod.validaciones;

import rips.cam.cod.log.Log;
import rips.cam.cod.proc.VentanaPrincipal;
import rips.cam.cod.rips.imp.ArchivosAC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * Clase para realizar diferentes validaciones
 *
 * @author hernan
 */
public class Validaciones {
    boolean error = false;


    public boolean getError() {
        return error;
    }

    ArchivosAC archivoAC;
    public static final int LONGITUD_CODIGO_PRESTADOR = 12;
    public static final int LONGITUD_TIPO_IDENTIFICACION = 2;
    public static final int LONGITUD_DIAGNOSTICO = 4;
    public static final int LIMITE_INFERIOR_TIPO_DIAGNOSTICO = 1;
    public static final int LIMITE_SUPERIOR_TIPO_DIAGNOSTICO = 3;


    public void setError(boolean error) {
        this.error = error;
    }


    public ArchivosAC getArchivoAC() {
        return archivoAC;
    }

    public void setArchivoAC(ArchivosAC archivoAC) {
        this.archivoAC = archivoAC;
    }


    public Validaciones(ArchivosAC archivoAC) {
        this.archivoAC = archivoAC;
    }

    /**
     * Comprueba la longitud de una cadena de texto
     *
     * @param cadena   que debe ser verificada
     * @param longitud longitud esperada
     * @return true si la cadena cumple con la longitud esperada o false si no lo hace.
     */
    public static boolean verificarLongitud(String cadena, int longitud) {
        if (cadena.length() == longitud) {
            return true;
        } else {
            return false;
        }
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

    public ArchivosAC validarTodosLosCamposAC(Validaciones validacionesAC) {
        ArchivosAC AC = validacionesAC.getArchivoAC();

        if (!validacionesAC.isNumero(String.valueOf(AC.getNumeroFactura()))) {
            AC.setError(true);
            Log.guardarIncidencia(0, "AC", AC.getNumeroLinea(), String.valueOf(AC.getNumeroFactura()), "Numero de factura debe ser un valor numerico. Este valor no lo es");
        }
        if (!validacionesAC.verificarLongitud(AC.getCodigoPrestador(), LONGITUD_CODIGO_PRESTADOR)) {
            AC.setError(true);
            Log.guardarIncidencia(0, "AC", AC.getNumeroLinea(), AC.getCodigoPrestador(), "Código del prestador debe tener 12 caracteres. Este valor no los tiene");
        }
        if (!validacionesAC.verificarLongitud(AC.getTipoIdentificacionUsuario(), LONGITUD_TIPO_IDENTIFICACION)) {
            AC.setError(true);
            Log.guardarIncidencia(0, "AC", AC.getNumeroLinea(), AC.getTipoIdentificacionUsuario(), "Tipo de identificación debe tener dos caracteres. Este valor no los tiene");
        }
        if (!validacionesAC.isNumero(AC.getNumeroIdentificacionUsuario())) {
            AC.setError(true);
            Log.guardarIncidencia(0, "AC", AC.getNumeroLinea(), AC.getNumeroIdentificacionUsuario(), "Numero de Identificacion debe ser un valor numerico. Este valor no lo es");
        }
        if (!validacionesAC.isDate(AC.getFechaProcedimientoConsultaIngreso())) {
            AC.setError(true);
            Log.guardarIncidencia(0, "AC", AC.getNumeroLinea(), AC.getFechaProcedimientoConsultaIngreso(), "Fecha de consulta debe ser una fecha valida. Este valor no lo es");
        }
        if (!validacionesAC.isObligatorio(AC.getCodigoConsultaProcedimiento())) {
            AC.setError(true);
            Log.guardarIncidencia(0, "AC", AC.getNumeroLinea(), AC.getCodigoConsultaProcedimiento(), "Código de consulta es requerido. Este campo está vacio");
        }
        if (!validacionesAC.isObligatorio(AC.getFinalidadConsulta())) {
            AC.setError(true);
            Log.guardarIncidencia(0, "AC", AC.getNumeroLinea(), AC.getFinalidadConsulta(), "Finalidad de la Consulta es requerido. Este campo está vacio");
        }
        if (!validacionesAC.isObligatorio(AC.getCausaExterna())) {
            AC.setError(true);
            Log.guardarIncidencia(0, "AC", AC.getNumeroLinea(), AC.getCausaExterna(), "Causa Externa es requerido. Este campo está vacio");
        }
        if (!validacionesAC.isObligatorio(AC.getCodigoDiagnosticoPrincipal())) {
            AC.setError(true);
            Log.guardarIncidencia(0, "AC", AC.getNumeroLinea(), AC.getCodigoDiagnosticoPrincipal(), "Código Diagnostico Principal es requerido. Este campo está vacio");
        }
        if (!validacionesAC.verificarLongitud(AC.getCodigoDiagnosticoPrincipal(), LONGITUD_DIAGNOSTICO)) {
            AC.setError(true);
            Log.guardarIncidencia(0, "AC", AC.getNumeroLinea(), AC.getCodigoDiagnosticoPrincipal(), "Código Diagnostico Principal debe tener cuatro caracteres. Este valor no los tiene");
        }
        if (!(AC.getCodigoDiagnosticoRelacionado2().length() != 0) && (validacionesAC.verificarLongitud(AC.getCodigoDiagnosticoRelacionado2(), LONGITUD_DIAGNOSTICO))) {
            AC.setError(true);
            Log.guardarIncidencia(0, "AC", AC.getNumeroLinea(), AC.getCodigoDiagnosticoRelacionado2(), "Código Diagnostico Relacionado 2 debe tener cuatro caracteres. Este valor no los tiene");
        }
        if (!(AC.getCodigoDiagnosticoRelacionado3().length() != 0) && validacionesAC.verificarLongitud(AC.getCodigoDiagnosticoRelacionado3(), LONGITUD_DIAGNOSTICO)) {
            AC.setError(true);
            Log.guardarIncidencia(0, "AC", AC.getNumeroLinea(), AC.getCodigoDiagnosticoRelacionado3(), "Código Diagnostico Relacionado 3 debe tener cuatro caracteres. Este valor no los tiene");
        }
        if (!validacionesAC.verificarRango(String.valueOf(AC.getTipoDiagnosticoPrincipal()), LIMITE_INFERIOR_TIPO_DIAGNOSTICO, LIMITE_SUPERIOR_TIPO_DIAGNOSTICO)) {
            AC.setError(true);
            Log.guardarIncidencia(0, "AC", AC.getNumeroLinea(), String.valueOf(AC.getTipoDiagnosticoPrincipal()), "Código Diagnostico Principal es requerido. Este campo está vacio o fuera de rango");
        }
        return AC;
    }
}
