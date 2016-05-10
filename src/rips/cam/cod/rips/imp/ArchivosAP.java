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

public class ArchivosAP extends Rips {

    int ambitoRealizacionProcedimiento; // 1 ambulatorio, 2 hospitalario, 3 urgencias
    int finalidadProcedimiento;
    int personalAtiende;
    String complicacion;
    int formaRealizacionActoQuirurgico;
    int valorProcedimiento;
    public static ArrayList<ArchivosAP> arrayAP = new ArrayList<ArchivosAP>();
    public static String ruta;
    public static boolean error = false;
    public static boolean advertencia = false;
    public static int valorTotalProcedimiento = 0;

    public ArchivosAP(int numeroFactura, String codigoPrestador, String tipoIdentificacionUsuario, String numeroIdentificacionUsuario, String fechaProcedimientoConsulta, String numeroAutorizacion, String codigoConsultaProcedimiento, String codigoDiagnosticoPrincipal, String codigoDiagnosticoRelacionado1, int ambitoRealizacionProcedimiento,
                      int finalidadProcedimiento, int personalAtiende, String complicacion, int formaRealizacionActoQuirurgico, int valorProcedimiento) {
        super(numeroFactura, codigoPrestador, tipoIdentificacionUsuario, numeroIdentificacionUsuario, fechaProcedimientoConsulta, numeroAutorizacion, codigoConsultaProcedimiento, codigoDiagnosticoPrincipal, codigoDiagnosticoRelacionado1);
        this.ambitoRealizacionProcedimiento = ambitoRealizacionProcedimiento;
        this.finalidadProcedimiento = finalidadProcedimiento;
        this.personalAtiende = personalAtiende;
        this.complicacion = complicacion;
        this.formaRealizacionActoQuirurgico = formaRealizacionActoQuirurgico;
        this.valorProcedimiento = valorProcedimiento;
    }

    public static void calularRutaArchivoAP(String cadena) {
        ruta = cadena;
        if (cadena.contains("CT")) { // hacemos que ruta tenga el nombre del archivo de la misma forma que el CT
            ruta = cadena.replace("CT", "AP");
        } else {
            if (cadena.contains("ct")) {
                ruta = cadena.replace("ct", "ap");
            } else {
                if (cadena.contains("Ct")) {
                    ruta = cadena.replace("Ct", "Ap");
                }
            }
        }
    }

    public static void recorrerArchivo() {
        try {
            BufferedWriter archivoAPGuardar = new BufferedWriter(new FileWriter(ruta + ".txt"));
            BufferedReader archivoLeer = new BufferedReader(new FileReader(ruta));
            int lineaArchivo = 0;

            while (archivoLeer.ready()) {
                lineaArchivo++;
                String linea = archivoLeer.readLine();
                String[] arreglo = linea.split(",");

                //ToDo: mejorar esto.
                arreglo[14] = Operaciones.reemplazarCerosFinales(arreglo[14]);
                if (Validaciones.isNumero(arreglo[14])) {
                    valorTotalProcedimiento = valorTotalProcedimiento + Integer.parseInt(arreglo[14]);
                } else {
                    error = true;
                    Log.guardarIncidencia(1, "AP", lineaArchivo, arreglo[14], "El valor total de procedimientos no tiene un valor numerico");
                }

                String codigoNuevo = Codigos.cambiarCodigo(arreglo[6]);
                if (codigoNuevo == null) {
                    Log.guardarIncidencia(1, "AP", lineaArchivo, arreglo[6], "Este valor no esta en el archivo de códigos");
                    archivoAPGuardar.write(linea + "\r\n");
                    continue;
                } else {
                    if (codigoNuevo.equals("-1")) {
                        advertencia = true;
                        archivoAPGuardar.write(arreglo[0] + "," + arreglo[1] + "," + arreglo[2] + "," + arreglo[3] + "," + arreglo[4] + "," + arreglo[5] + "," + arreglo[6] + "," + arreglo[7] + "," + arreglo[8] + "," + arreglo[9] + "," + arreglo[10] + "," + arreglo[11] + "," + arreglo[12] + "," + arreglo[13] + "," + (arreglo[14] + ".00") + "\r\n");
                        Log.guardarIncidencia(1, "AP", lineaArchivo, arreglo[6], "No se debe procesar según la configuración del archivo de códigos");
                    } else {
                        advertencia = true;
                        archivoAPGuardar.write(arreglo[0] + "," + arreglo[1] + "," + arreglo[2] + "," + arreglo[3] + "," + arreglo[4] + "," + arreglo[5] + "," + codigoNuevo + "," + arreglo[7] + "," + arreglo[8] + "," + arreglo[9] + "," + arreglo[10] + "," + arreglo[11] + "," + arreglo[12] + "," + arreglo[13] + "," + (arreglo[14] + ".00") + "\r\n");
                    }
                }
            }
            archivoLeer.close();
            archivoAPGuardar.close();
            JuegoRips.borrarRenombrar(ruta, ruta + ".txt");
        } catch (FileNotFoundException e) {
            VentanaPrincipal.agregarATxaRespuesta("No se encontro el Archivo " + ruta);
        } catch (IOException e) {
            VentanaPrincipal.agregarATxaRespuesta("Se produjo un error de IO al recorrer el Archivo AC" + ruta);
        } finally {
            if (error) {
                VentanaPrincipal.mostrarMensaje("El archivo AP presenta errores.\nPor favor corrijalos manualmente.\nLea es archivo log para mas información");
                VentanaPrincipal.agregarATxaRespuesta("\r\nEl archivo AP presenta errores");
            }
            if (advertencia) {
                VentanaPrincipal.agregarATxaRespuesta("\r\nEl archivo AP presenta advertencias");
            }
        }
    }

    public static int sumarValorModeradoraNeto() {
        return valorTotalProcedimiento;
    }

    public static void limpiarRegistros() {
        valorTotalProcedimiento = 0;
    }
}
