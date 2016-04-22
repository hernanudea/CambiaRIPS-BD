package rips.cam.cod.rips.imp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import rips.cam.cod.log.Log;
import rips.cam.cod.proc.Operaciones;
import rips.cam.cod.proc.VentanaPrincipal;
import rips.cam.cod.rips.JuegoRips;
import rips.cam.cod.rips.Rips;
import rips.cam.cod.validaciones.Validaciones;

public class ArchivosAF extends Rips {

    String codigoPrestador;
    String razonSocialIPS;
    String tipoIdentificacionIPS;
    String numeroIdentificacionIPS;
    int numeroFactura;
    Date fechaExpedicion;
    Date fechaInicioPeriodoFacturacion;
    Date fechaFinPeriodoFacturacion;
    String codigoAdministradora;
    String nombreAdministradora;
    String numeroContrato;
    String planBeneficios;
    String numeroPoliza;
    int valorCopago;
    int valorComision;
    int valorDescuento;
    int valorNeto;
    public static int valorTotalCopago = 0;
    public static int valorTotalNeto = 0;


    public static ArrayList<ArchivosAF> arrayAF = new ArrayList<ArchivosAF>();
    public static String ruta;
    public static boolean error = false;
    public static boolean advertencia = false;

    public ArchivosAF(int numeroFactura, String codigoPrestador, String tipoIdentificacionUsuario, String numeroIdentificacionUsuario, Date fechaProcedimientoConsultaIngreso, String numeroAutorizacion, String codigoDiagnosticoRelacionado1, String codigoPrestador2, String razonSocialIPS, String tipoIdentificacionIPS, String numeroIdentificacionIPS,
                      int numeroFactura2, Date fechaExpedicion, Date fechaInicioPeriodoFacturacion, Date fechaFinPeriodoFacturacion, String codigoAdministradora, String nombreAdministradora, String numeroContrato, String planBeneficios, String numeroPoliza, int valorCopago, int valorComision, int valorDescuento, int valorNeto) {
        super(numeroFactura, codigoPrestador, tipoIdentificacionUsuario, numeroIdentificacionUsuario, fechaProcedimientoConsultaIngreso, numeroAutorizacion, codigoDiagnosticoRelacionado1);
        codigoPrestador = codigoPrestador2;
        this.razonSocialIPS = razonSocialIPS;
        this.tipoIdentificacionIPS = tipoIdentificacionIPS;
        this.numeroIdentificacionIPS = numeroIdentificacionIPS;
        numeroFactura = numeroFactura2;
        this.fechaExpedicion = fechaExpedicion;
        this.fechaInicioPeriodoFacturacion = fechaInicioPeriodoFacturacion;
        this.fechaFinPeriodoFacturacion = fechaFinPeriodoFacturacion;
        this.codigoAdministradora = codigoAdministradora;
        this.nombreAdministradora = nombreAdministradora;
        this.numeroContrato = numeroContrato;
        this.planBeneficios = planBeneficios;
        this.numeroPoliza = numeroPoliza;
        this.valorCopago = valorCopago;
        this.valorComision = valorComision;
        this.valorDescuento = valorDescuento;
        this.valorNeto = valorNeto;
    }

    public static void calularRutaArchivoAF(String cadena) {
        ruta = cadena;
        if (cadena.contains("CT")) { // hacemos que ruta tenga el nombre del archivo de la misma forma que el CT
            ruta = cadena.replace("CT", "AF");
        } else {
            if (cadena.contains("ct")) {
                ruta = cadena.replace("ct", "af");
            } else {
                if (cadena.contains("Ct")) {
                    ruta = cadena.replace("Ct", "Af");
                }
            }
        }
    }

    public static void llenarCamposVaciosAF() {
        int lineaArchivo = 0;
        try {
            BufferedWriter archivoAFGuardar = new BufferedWriter(new FileWriter(ruta + ".txt"));
            BufferedReader archivoLeer = new BufferedReader(new FileReader(ruta));

            while (archivoLeer.ready()) {
                String linea = archivoLeer.readLine();
                String[] arreglo = linea.split(",");


                //ToDo: mejorar esto.
                arreglo[13] = Operaciones.reemplazarCerosFinales(arreglo[13]);
                arreglo[16]= Operaciones.reemplazarCerosFinales(arreglo[16]);
                if (Validaciones.isNumero(arreglo[13])) {
                    valorTotalCopago = valorTotalCopago + Integer.parseInt(arreglo[13]);
                } else {
                    error = true;
                    Log.guardarIncidencia(1, "AF", lineaArchivo, arreglo[13], "El valor Total Copagos no tiene un valor numerico");
                }
                if (Validaciones.isNumero(arreglo[16])) {
                    valorTotalNeto = valorTotalNeto + Integer.parseInt(arreglo[16]);
                } else {
                    error = true;
                    Log.guardarIncidencia(1, "AF", lineaArchivo, arreglo[16], "El valor Total Neto no tiene un valor numerico");
                }

                arreglo[13] = cambiarVacioPorCero(arreglo[13]);
                arreglo[14] = cambiarVacioPorCero(arreglo[14]);
                arreglo[15] = cambiarVacioPorCero(arreglo[15]);
                archivoAFGuardar.write(arreglo[0] + "," + arreglo[1] + "," + arreglo[2] + "," + arreglo[3] + "," + arreglo[4] + "," + arreglo[5] + "," + arreglo[6] + "," + arreglo[7] + "," + arreglo[8] + "," + arreglo[9] + "," + arreglo[10] + "," + arreglo[11] + "," + arreglo[12] + "," + (arreglo[13]+".00") + "," + arreglo[14] + "," + (arreglo[15]+".00") + ","
                        + (arreglo[16]+".00") + "\r\n");
                lineaArchivo++;
            }
            archivoLeer.close();
            archivoAFGuardar.close();
            JuegoRips.borrarRenombrar(ruta, ruta + ".txt");
        } catch (FileNotFoundException e) {
            VentanaPrincipal.agregarATxaRespuesta("No se encontro el Archivo " + ruta);
        } catch (IOException e) {
            VentanaPrincipal.agregarATxaRespuesta("Se produjo un error de IO al recorrer el Archivo AC" + ruta);
        } finally {
            if (error) {
                VentanaPrincipal.mostrarMensaje("El archivo AF presenta errores.\nPor favor corrijalos manualmente.\nLea es archivo log para mas información");
                VentanaPrincipal.agregarATxaRespuesta("\r\nEl archivo AF presenta errores");
            }
            if (advertencia) {
                VentanaPrincipal.agregarATxaRespuesta("\r\nEl archivo AF presenta advertencias");
            }
        }
    }

    private static String cambiarVacioPorCero(String cadena) {
        if (cadena.equals("")) {
            return "0";
        }
        return cadena;
    }

    public static int sumarValorModeradoraNeto() {
        return valorTotalCopago + valorTotalNeto;
    }

    public static void limpiarRegistros() {
        valorTotalCopago = 0;
        valorTotalNeto = 0;
    }
}
