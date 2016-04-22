package rips.cam.cod.rips.imp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import rips.cam.cod.codigos.Codigos;
import rips.cam.cod.log.Log;
import rips.cam.cod.proc.Operaciones;
import rips.cam.cod.proc.VentanaPrincipal;
import rips.cam.cod.rips.JuegoRips;
import rips.cam.cod.rips.Rips;
import rips.cam.cod.validaciones.Validaciones;

public class ArchivosAT extends Rips {
    int tipoServicio;
    String nombreServicio;
    int cantidad;
    int valorUnitarioMaterial;
    int valorTotalmaterial;
    public static ArrayList<ArchivosAT> vectorAC = new ArrayList<ArchivosAT>();
    public static String ruta;
    public static boolean error = false;
    public static boolean advertencia = false;
    public static int valorTotalmateriales = 0;

    public ArchivosAT(int numeroFactura, String codigoPrestador, String tipoIdentificacionUsuario, String numeroIdentificacionUsuario, String numeroAutorizacion, String codigoConsultaProcedimiento, String nombreServicio, int cantidad, int valorUnitarioMaterial, int valorTotalmaterial) {
        super(numeroFactura, codigoPrestador, tipoIdentificacionUsuario, numeroIdentificacionUsuario, numeroAutorizacion);
        this.tipoServicio = tipoServicio;
        this.nombreServicio = nombreServicio;
        this.cantidad = cantidad;
        this.valorUnitarioMaterial = valorUnitarioMaterial;
        this.valorTotalmaterial = valorTotalmaterial;
    }

    public static void calularRutaArchivoAT(String cadena) {
        ruta = cadena;
        if (cadena.contains("CT")) { // hacemos que ruta tenga el nombre del archivo de la misma forma que el CT
            ruta = cadena.replace("CT", "AT");
        } else {
            if (cadena.contains("ct")) {
                ruta = cadena.replace("ct", "at");
            } else {
                if (cadena.contains("Ct")) {
                    ruta = cadena.replace("Ct", "At");
                }
            }
        }
    }

    public static void recorrerArchivo() {
        try {
            BufferedWriter archivoATGuardar = new BufferedWriter(new FileWriter(ruta + ".txt"));
            BufferedReader archivoLeer = new BufferedReader(new FileReader(ruta));
            int lineaArchivo = 0;

            while (archivoLeer.ready()) {
                lineaArchivo++;
                String linea = archivoLeer.readLine();
                String[] arreglo = linea.split(",");

                //ToDo: mejorar esto.
                arreglo[10] = Operaciones.reemplazarCerosFinales(arreglo[10]);
                if (Validaciones.isNumero(arreglo[10])) {
                    valorTotalmateriales = valorTotalmateriales + Integer.parseInt(arreglo[10]);
                } else {
                    error = true;
                    Log.guardarIncidencia(1, "AT", lineaArchivo, arreglo[10], "El valor Total Materiales no tiene un valor numerico");
                }


                String codigoNuevo = Codigos.cambiarCodigo(arreglo[6]);
                if (codigoNuevo == null) {
                    Log.guardarIncidencia(1, "AT", lineaArchivo, arreglo[6], "Este valor no esta en el archivo de códigos");
                    archivoATGuardar.write(linea + "\r\n");
                    continue;
                } else {
                    if (codigoNuevo.equals("-1")) {
                        advertencia = true;
                        archivoATGuardar.write(arreglo[0] + "," + arreglo[1] + "," + arreglo[2] + "," + arreglo[3] + "," + arreglo[4] + "," + arreglo[5] + "," + arreglo[6] + "," + arreglo[7] + "," + arreglo[8] + "," + arreglo[9] + "," + (arreglo[10] + ".00") + "\r\n");
                        Log.guardarIncidencia(1, "AT", lineaArchivo, arreglo[6], "No se debe procesar según la configuración del archivo de códigos");
                    } else {
                        advertencia = true;
                        archivoATGuardar.write(arreglo[0] + "," + arreglo[1] + "," + arreglo[2] + "," + arreglo[3] + "," + arreglo[4] + "," + arreglo[5] + "," + codigoNuevo + "," + arreglo[7] + "," + arreglo[8] + "," + arreglo[9] + "," + (arreglo[10] + ".00") + "\r\n");
                    }
                }
            }
            archivoLeer.close();
            archivoATGuardar.close();
            JuegoRips.borrarRenombrar(ruta, ruta + ".txt");
        } catch (FileNotFoundException e) {
            VentanaPrincipal.agregarATxaRespuesta("No se encontro el Archivo " + ruta);
        } catch (IOException e) {
            VentanaPrincipal.agregarATxaRespuesta("Se produjo un error de IO al recorrer el Archivo AC" + ruta);
        } finally {
            if (error) {
                VentanaPrincipal.mostrarMensaje("El archivo AT presenta errores.\nPor favor corrijalos manualmente.\nLea es archivo log para mas información");
                VentanaPrincipal.agregarATxaRespuesta("\r\nEl archivo AT presenta errores");
            }
            if (advertencia) {
                VentanaPrincipal.agregarATxaRespuesta("\r\nEl archivo AT presenta advertencias");
            }
        }
    }

    public static int sumarValorModeradoraNeto() {
        return valorTotalmateriales;
    }

    public static void limpiarRegistros() {
        valorTotalmateriales = 0;
    }
}