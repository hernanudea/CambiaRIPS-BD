package rips.cam.cod.rips.imp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import rips.cam.cod.codigos.Codigos;
import rips.cam.cod.log.Log;
import rips.cam.cod.proc.Operaciones;
import rips.cam.cod.proc.VentanaPrincipal;
import rips.cam.cod.rips.JuegoRips;
import rips.cam.cod.rips.Rips;
import rips.cam.cod.validaciones.Validaciones;

public class ArchivosAC extends Rips {
    String finalidadConsulta;
    String causaExterna;
    String codigoDiagnosticoRelacionado2;
    String codigoDiagnosticoRelacionado3;
    int tipoDiagnosticoPrincipal;
    int valorConsulta;
    int valorCuotaModeradora;
    int valorNetoPagar;
    static Date fechaConsulta;
    public static ArrayList<ArchivosAC> arrayAC = new ArrayList<ArchivosAC>();
    public static boolean error = false;
    public static boolean advertencia = false;
    public static String ruta;
    public static int valorModeradoras = 0;
    public static int valorNeto = 0;

    public ArchivosAC(int numeroFactura, String codigoPrestador, String tipoIdentificacionUsuario, String numeroIdentificacionUsuario, Date fechaProcedimientoConsulta, String numeroAutorizacion, String codigoConsultaProcedimiento, String finalidadConsulta, String causaExterna, String codigoDiagnosticoPrincipal,
                      String codigoDiagnosticoRelacionado1, String codigoDiagnosticoRelacionado2, String codigoDiagnosticoRelacionado3, int tipoDiagnosticoPrincipal, int valorConsulta, int valorCuotaModeradora, int valorNetoPagar) {
        super(numeroFactura, codigoPrestador, tipoIdentificacionUsuario, numeroIdentificacionUsuario, fechaProcedimientoConsulta, numeroAutorizacion, codigoConsultaProcedimiento, codigoDiagnosticoPrincipal, codigoDiagnosticoRelacionado1);
        this.finalidadConsulta = finalidadConsulta;
        this.causaExterna = causaExterna;
        this.codigoDiagnosticoRelacionado2 = codigoDiagnosticoRelacionado2;
        this.codigoDiagnosticoRelacionado3 = codigoDiagnosticoRelacionado3;
        this.tipoDiagnosticoPrincipal = tipoDiagnosticoPrincipal;
        this.valorConsulta = valorConsulta;
        this.valorCuotaModeradora = valorCuotaModeradora;
        this.valorNetoPagar = valorNetoPagar;
    }

    public static void limpiarArrayList() {
        arrayAC.clear();
    }

    public String getFinalidadConsulta() {
        return finalidadConsulta;
    }

    public void setFinalidadConsulta(String finalidadConsulta) {
        this.finalidadConsulta = finalidadConsulta;
    }

    public String getCausaExterna() {
        return causaExterna;
    }

    public void setCausaExterna(String causaExterna) {
        this.causaExterna = causaExterna;
    }

    public String getCodigoDiagnosticoRelacionado2() {
        return codigoDiagnosticoRelacionado2;
    }

    public void setCodigoDiagnosticoRelacionado2(String codigoDiagnosticoRelacionado2) {
        this.codigoDiagnosticoRelacionado2 = codigoDiagnosticoRelacionado2;
    }

    public String getCodigoDiagnosticoRelacionado3() {
        return codigoDiagnosticoRelacionado3;
    }

    public void setCodigoDiagnosticoRelacionado3(String codigoDiagnosticoRelacionado3) {
        this.codigoDiagnosticoRelacionado3 = codigoDiagnosticoRelacionado3;
    }

    public int getTipoDiagnosticoPrincipal() {
        return tipoDiagnosticoPrincipal;
    }

    public void setTipoDiagnosticoPrincipal(int tipoDiagnosticoPrincipal) {
        this.tipoDiagnosticoPrincipal = tipoDiagnosticoPrincipal;
    }

    public int getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(int valorConsulta) {
        this.valorConsulta = valorConsulta;
    }

    public int getValorCuotaModeradora() {
        return valorCuotaModeradora;
    }

    public void setValorCuotaModeradora(int valorCuotaModeradora) {
        this.valorCuotaModeradora = valorCuotaModeradora;
    }

    public int getValorNetoPagar() {
        return valorNetoPagar;
    }

    public void setValorNetoPagar(int valorNetoPagar) {
        this.valorNetoPagar = valorNetoPagar;
    }

    public void agregarAlArray(ArchivosAC archivoAC) {
        arrayAC.add(archivoAC);
    }

    public static void recorrerArchivo() {
        try {
            BufferedWriter archivoACGuardar = new BufferedWriter(new FileWriter(ruta + ".txt"));
            BufferedReader archivoLeer = new BufferedReader(new FileReader(ruta));
            int lineaArchivoAC = 0;

            while (archivoLeer.ready()) {
                lineaArchivoAC++;
                String linea = archivoLeer.readLine();
                String[] arreglo = linea.split(",");
                validarTodoslosCampos(arreglo, lineaArchivoAC);
                //ToDo: mejorar esto.
                arreglo[15] = Operaciones.reemplazarCerosFinales(arreglo[15]);
                arreglo[16] = Operaciones.reemplazarCerosFinales(arreglo[16]);
                if (Validaciones.isNumero(arreglo[15])) { //validamos que moderadora sea numerico
                    valorModeradoras = valorModeradoras + Integer.parseInt(arreglo[15]);
                } else {
                    error = true;
                    Log.guardarIncidencia(1, "AC", lineaArchivoAC, arreglo[15], "la cuota moderadora no tiene un valor numerico");
                }

                if (Validaciones.isNumero(arreglo[16])) { //validamos que neto sea numerico
                    valorNeto = valorNeto + Integer.parseInt(arreglo[16]);
                } else {
                    error = true;
                    Log.guardarIncidencia(1, "AC", lineaArchivoAC, arreglo[16], "la cuota moderadora no tiene un valor numerico");
                }

                String codigoNuevo = Codigos.cambiarCodigo(arreglo[6]);

                if (codigoNuevo == null) {
                    Log.guardarIncidencia(1, "AC", lineaArchivoAC, arreglo[6], "Este valor no esta en el archivo de códigos");
                    archivoACGuardar.write(linea + "\r\n");
                    error = true;
                    continue;
                } else {
                    if (codigoNuevo.equals("-1")) {
                        advertencia = true;
                        archivoACGuardar.write(arreglo[0] + "," + arreglo[1] + "," + arreglo[2] + "," + arreglo[3] + "," + arreglo[4] + "," + arreglo[5] + "," + arreglo[6] + "," + arreglo[7] + "," + arreglo[8] + "," + arreglo[9] + "," + arreglo[10] + "," + arreglo[11] + "," + arreglo[12] + "," + arreglo[13] + "," + arreglo[14] + "," + (arreglo[15] + ".00")
                                + "," + (arreglo[16] + ".00") + "\r\n");
                        //Log.guardarIncidencia(1, "AC", lineaArchivoAC, arreglo[6], "No se debe procesar según la configuración del archivo de códigos");
                    } else {
                        advertencia = true;
                        archivoACGuardar.write(arreglo[0] + "," + arreglo[1] + "," + arreglo[2] + "," + arreglo[3] + "," + arreglo[4] + "," + arreglo[5] + "," + codigoNuevo + "," + arreglo[7] + "," + arreglo[8] + "," + arreglo[9] + "," + arreglo[10] + "," + arreglo[11] + "," + arreglo[12] + "," + arreglo[13] + "," + arreglo[14] + "," + (arreglo[15] + ".00")
                                + "," + (arreglo[16] + ".00") + "\r\n");
                    }
                }
            }
            archivoLeer.close();
            archivoACGuardar.close();
            JuegoRips.borrarRenombrar(ruta, ruta + ".txt");
        } catch (FileNotFoundException e) {
            VentanaPrincipal.agregarATxaRespuesta("NO se encontro el Archivo " + ruta);
        } catch (IOException e) {
            VentanaPrincipal.agregarATxaRespuesta("Se produjo un error de IO al recorrer el Archivo AC" + ruta);
        } finally {
            if (error) {
                VentanaPrincipal.mostrarMensaje("El archivo AC presenta errores.\nPor favor corrijalos manualmente.\nLea es archivo log para mas información");
                VentanaPrincipal.agregarATxaRespuesta("\r\nEl archivo AC presenta errores");
            }
            if (advertencia) {
                VentanaPrincipal.agregarATxaRespuesta("\r\nEl archivo AC presenta advertencias");
            }
        }
    }

    private static void validarTodoslosCampos(String[] arreglo, int lineaArchivoAC) {
        Validaciones validar = new Validaciones();
        if (!Validaciones.isNumero(arreglo[0])) {
            error = true;
            Log.guardarIncidencia(0, "AC", lineaArchivoAC, arreglo[0], "Numero de factura debe ser un valor numerico. Este valor no lo es");
        }
        if (!Validaciones.verificarLongitud(arreglo[1], 12)) {
            error = true;
            Log.guardarIncidencia(0, "AC", lineaArchivoAC, arreglo[1], "Código del prestador debe tener 12 caracteres. Este valor no los tiene");
        }
        if (!Validaciones.verificarLongitud(arreglo[2], 2)) {
            error = true;
            Log.guardarIncidencia(0, "AC", lineaArchivoAC, arreglo[2], "Tipo de identificación debe tener dos caracteres. Este valor no los tiene");
        }
        if (!Validaciones.isNumero(arreglo[3])) {
            error = true;
            Log.guardarIncidencia(0, "AC", lineaArchivoAC, arreglo[3], "Numero de Identificacion debe ser un valor numerico. Este valor no lo es");
        }
        if (!Validaciones.isDate(arreglo[4])) {
            error = true;
            Log.guardarIncidencia(0, "AC", lineaArchivoAC, arreglo[4], "Fecha de consulta debe ser una fecha valida. Este valor no lo es");
        }
        if (!Validaciones.isObligatorio(arreglo[6])) {
            error = true;
            Log.guardarIncidencia(0, "AC", lineaArchivoAC, arreglo[6], "Código de consulta es requerido. Este campo está vacio");
        }
        if (!Validaciones.isObligatorio(arreglo[7])) {
            error = true;
            Log.guardarIncidencia(0, "AC", lineaArchivoAC, arreglo[7], "Finalidad de la Consulta es requerido. Este campo está vacio");
        }
        if (!Validaciones.isObligatorio(arreglo[8])) {
            error = true;
            Log.guardarIncidencia(0, "AC", lineaArchivoAC, arreglo[8], "Causa Externa es requerido. Este campo está vacio");
        }
        if (!Validaciones.isObligatorio(arreglo[9])) {
            error = true;
            Log.guardarIncidencia(0, "AC", lineaArchivoAC, arreglo[9], "Código Diagnostico Principal es requerido. Este campo está vacio");
        }
        if (!Validaciones.verificarLongitud(arreglo[9], 4)) {
            error = true;
            Log.guardarIncidencia(0, "AC", lineaArchivoAC, arreglo[9], "Código Diagnostico Principal debe tener cuatro caracteres. Este valor no los tiene");
        }
        if (!Validaciones.isObligatorio(arreglo[13])) {
            error = true;
            Log.guardarIncidencia(0, "AC", lineaArchivoAC, arreglo[13], "Código Diagnostico Principal es requerido. Este campo está vacio");
        }
        if (!validar.verificarRango(arreglo[13], 1, 3)) {
            error = true;
            Log.guardarIncidencia(0, "AC", lineaArchivoAC, arreglo[13], "Tipo de Diagnostico Principal debe estar en el rango. Este valor no cumple la condición");
        }
        if (!Validaciones.isNumero(arreglo[14].replace(".00", ""))) {
            error = true;
            Log.guardarIncidencia(0, "AC", lineaArchivoAC, arreglo[14], "Valor de la consulta debe ser un valor numerico");
        }
    }

    public static void calularRutaArchivoAC(String cadena) {
        ruta = cadena;
        if (cadena.contains("CT")) { // hacemos que ruta tenga el nombre del archivo de la misma forma que el CT
            ruta = cadena.replace("CT", "AC");
        } else {
            if (cadena.contains("ct")) {
                ruta = cadena.replace("ct", "ac");
            } else {
                if (cadena.contains("Ct")) {
                    ruta = cadena.replace("Ct", "Ac");
                }
            }
        }
    }

    public static int sumarValorModeradoraNeto() {
        return valorModeradoras + valorNeto;
    }

    public static void limpiarRegistros() {
        valorNeto = 0;
        valorModeradoras = 0;
    }
}
