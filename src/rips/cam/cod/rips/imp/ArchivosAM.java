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

public class ArchivosAM extends Rips {
    int numeroFactura;
    String codigoPrestador;
    String tipoIdentificacionUsuario;
    String numeroIdentificacionUsuario;
    String numeroAutorizacion;
    String codigoMedicamento;
    int tipoMedicamento;// 1 Pos, 2 no pos
    String nombreGenericoMedicamento;
    String formaFarmaceutica;
    String concentracionMedicamento;
    String unidadMedica;
    int numeroUnidad;
    int valorUnitarioMedicamento;
    int valorTotalmedicamento;
    public static ArrayList<ArchivosAM> arrayAM = new ArrayList<ArchivosAM>();
    public static String ruta;
    public static boolean error = false;
    public static boolean advertencia = false;
    public static int valorTotalMedicamentos = 0;

    public ArchivosAM(int numeroFactura, String codigoPrestador, String tipoIdentificacionUsuario, String numeroIdentificacionUsuario, String fechaProcedimientoConsulta, String numeroAutorizacion, String codigoConsultaProcedimiento, String codigoDiagnosticoPrincipal, String codigoDiagnosticoRelacionado1, int numeroFactura2, String codigoPrestador2,
                      String tipoIdentificacionUsuario2, String numeroIdentificacionUsuario2, String numeroAutorizacion2, String codigoMedicamento, int tipoMedicamento, String nombreGenericoMedicamento, String formaFarmaceutica, String concentracionMedicamento, String unidadMedica, int numeroUnidad, int valorUnitarioMedicamento, int valorTotalmedicamento) {
        super(numeroFactura, codigoPrestador, tipoIdentificacionUsuario, numeroIdentificacionUsuario, fechaProcedimientoConsulta, numeroAutorizacion, codigoConsultaProcedimiento, codigoDiagnosticoPrincipal, codigoDiagnosticoRelacionado1);
        numeroFactura = numeroFactura2;
        codigoPrestador = codigoPrestador2;
        tipoIdentificacionUsuario = tipoIdentificacionUsuario2;
        numeroIdentificacionUsuario = numeroIdentificacionUsuario2;
        numeroAutorizacion = numeroAutorizacion2;
        this.codigoMedicamento = codigoMedicamento;
        this.tipoMedicamento = tipoMedicamento;
        this.nombreGenericoMedicamento = nombreGenericoMedicamento;
        this.formaFarmaceutica = formaFarmaceutica;
        this.concentracionMedicamento = concentracionMedicamento;
        this.unidadMedica = unidadMedica;
        this.numeroUnidad = numeroUnidad;
        this.valorUnitarioMedicamento = valorUnitarioMedicamento;
        this.valorTotalmedicamento = valorTotalmedicamento;
    }

    public static void calularRutaArchivoAM(String cadena) {
        ruta = cadena;
        if (cadena.contains("CT")) { // hacemos que ruta tenga el nombre del archivo de la misma forma que el CT
            ruta = cadena.replace("CT", "AM");
        } else {
            if (cadena.contains("ct")) {
                ruta = cadena.replace("ct", "am");
            } else {
                if (cadena.contains("Ct")) {
                    ruta = cadena.replace("Ct", "Am");
                }
            }
        }
    }

    public static void recorrerArchivo() {
        try {
            BufferedWriter archivoAMGuardar = new BufferedWriter(new FileWriter(ruta + ".txt"));
            BufferedReader archivoLeer = new BufferedReader(new FileReader(ruta));
            int lineaArchivo = 0;

            while (archivoLeer.ready()) {
                lineaArchivo++;
                String linea = archivoLeer.readLine();
                String[] arreglo = linea.split(",");

                //ToDo: mejorar esto.
                arreglo[13]= Operaciones.reemplazarCerosFinales(arreglo[13]);
                if (Validaciones.isNumero(arreglo[13])) {
                    valorTotalMedicamentos = valorTotalMedicamentos + Integer.parseInt(arreglo[13]);
                } else {
                    error = true;
                    Log.guardarIncidencia(1, "AM", lineaArchivo, arreglo[13], "El valor total medicamentos no tiene un valor numerico");
                }

                String codigoNuevo = Codigos.cambiarCodigo(arreglo[5]);
                // verificamos que el campo ogligatorio este incluido
                if (!Validaciones.isObligatorio(arreglo[5])) {
                    Log.guardarIncidencia(1, "AM", lineaArchivo, arreglo[5], "Este campo es ogligatorio y no se encuentra diligenciado");
                    error = true;
                }
                if (codigoNuevo == null) {
                    Log.guardarIncidencia(1, "AM", lineaArchivo, arreglo[5], "Este valor no esta en el archivo de códigos");
                    archivoAMGuardar.write(linea + "\r\n");
                    error = true;
                    continue;
                } else {
                    if (codigoNuevo.equals("-1")) {
                        advertencia = true;
                        archivoAMGuardar.write(arreglo[0] + "," + arreglo[1] + "," + arreglo[2] + "," + arreglo[3] + "," + arreglo[4] + "," + arreglo[5] + "," + arreglo[6] + "," + arreglo[7] + "," + arreglo[8] + "," + arreglo[9] + "," + arreglo[10] + "," + arreglo[11] + "," + arreglo[12] + "," + (arreglo[13]+".00") + "\r\n");
                        Log.guardarIncidencia(1, "AM", lineaArchivo, arreglo[6], "No se debe procesar según la configuración del archivo de códigos");
                    } else {
                        advertencia = true;
                        archivoAMGuardar.write(arreglo[0] + "," + arreglo[1] + "," + arreglo[2] + "," + arreglo[3] + "," + arreglo[4] + "," + codigoNuevo + "," + arreglo[6] + "," + arreglo[7] + "," + arreglo[8] + "," + arreglo[9] + "," + arreglo[10] + "," + arreglo[11] + "," + arreglo[12] + "," + (arreglo[13]+".00") + "\r\n");
                    }
                }
            }
            archivoLeer.close();
            archivoAMGuardar.close();
            JuegoRips.borrarRenombrar(ruta, ruta + ".txt");
        } catch (FileNotFoundException e) {
            VentanaPrincipal.agregarATxaRespuesta("No se encontro el Archivo " + ruta);
        } catch (IOException e) {
            VentanaPrincipal.agregarATxaRespuesta("Se produjo un error de IO al recorrer el Archivo AC" + ruta);
        } finally {
            if (error) {
                VentanaPrincipal.mostrarMensaje("El archivo AM presenta errores.\nPor favor corrijalos manualmente.\nLea es archivo log para mas información");
                VentanaPrincipal.agregarATxaRespuesta("\r\nEl archivo AM presenta errores");
            }
            if (advertencia) {
                VentanaPrincipal.agregarATxaRespuesta("\r\nEl archivo AM presenta advertencias");
            }
        }
    }

    public static int sumarValorModeradoraNeto() {
        return valorTotalMedicamentos;
    }

    public static void limpiarRegistros() {
        valorTotalMedicamentos=0;
    }
}
