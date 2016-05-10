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
    //public static ArrayList<ArchivosAC> arrayAC = new ArrayList<ArchivosAC>();
    public static boolean errorArchivo = false;
    public static boolean advertencia = false;//ToDo: Quitar esta linea
    public static String ruta;
    public static int valorModeradoras = 0;
    public static int valorNeto = 0;
    public String descripcionError = "";

   /* public ArchivosAC(int numeroFactura, String codigoPrestador, String tipoIdentificacionUsuario, String numeroIdentificacionUsuario, Date fechaProcedimientoConsulta, String numeroAutorizacion, String codigoConsultaProcedimiento, String finalidadConsulta, String causaExterna, String codigoDiagnosticoPrincipal,
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
    }*/

   /* public static void limpiarArrayList() {
        arrayAC.clear();
    }*/


  /*  public void agregarAlArray(ArchivosAC archivoAC) {
        arrayAC.add(archivoAC);
    }*/

    public static void recorrerArchivo() {
        try {
            BufferedWriter archivoACGuardar = new BufferedWriter(new FileWriter(ruta + ".txt"));
            BufferedReader archivoLeer = new BufferedReader(new FileReader(ruta));
            int lineaArchivoAC = 0;

            while (archivoLeer.ready()) {
                lineaArchivoAC++;
                String linea = archivoLeer.readLine();
                String[] arreglo = linea.split(",");

                //creamos objeto ArchivosAC
                ArchivosAC registroAC = new ArchivosAC();

                registroAC.setNumeroFactura(Integer.parseInt(Operaciones.reemplazarCerosFinales(arreglo[0])));
                registroAC.setCodigoPrestador(arreglo[1]);
                registroAC.setTipoIdentificacionUsuario(arreglo[2]);
                registroAC.setNumeroIdentificacionUsuario(arreglo[3]);
                registroAC.setFechaProcedimientoConsultaIngreso(arreglo[4]);
                registroAC.setNumeroAutorizacion(arreglo[5]);
                registroAC.setCodigoConsultaProcedimiento(arreglo[6]);
                registroAC.setFinalidadConsulta(arreglo[7]);
                registroAC.setCausaExterna(arreglo[8]);
                registroAC.setCodigoDiagnosticoPrincipal(arreglo[9]);
                registroAC.setCodigoDiagnosticoRelacionado1(arreglo[10]);
                registroAC.setCodigoDiagnosticoRelacionado2(arreglo[11]);
                registroAC.setCodigoDiagnosticoRelacionado3(arreglo[12]);
                registroAC.setTipoDiagnosticoPrincipal(Integer.parseInt(arreglo[13]));
                //ToDO: Validar los siguientes tres atributos
                registroAC.setValorConsulta(Integer.parseInt(Operaciones.reemplazarCerosFinales(arreglo[14])));
                registroAC.setValorCuotaModeradora(Integer.parseInt(Operaciones.reemplazarCerosFinales(arreglo[15])));
                registroAC.setValorNetoPagar(Integer.parseInt(Operaciones.reemplazarCerosFinales(arreglo[16])));
                registroAC.setNumeroLinea(lineaArchivoAC);

                Validaciones validacionesAC = new Validaciones(registroAC);
                ArchivosAC registroACValidado = validacionesAC.validarTodosLosCamposAC(validacionesAC);

                if (registroACValidado.getError()) {
                    archivoACGuardar.write(linea);
                    errorArchivo = true;
                } else {
                    String codigoNuevo = Codigos.cambiarCodigo(registroACValidado.getCodigoConsultaProcedimiento());
                    if (codigoNuevo == null) {
                        Log.guardarIncidencia(1, "AC", lineaArchivoAC, registroACValidado.getCodigoConsultaProcedimiento(), "Este valor no esta en el archivo de códigos");
                        archivoACGuardar.write(linea + "\r\n");
                        errorArchivo = true;
                        continue;
                    } else {
                        if (codigoNuevo.equals("-1")) {
                            archivoACGuardar.write(linea + "\r\n");
                        } else {
                            advertencia = true;
                            archivoACGuardar.write(arreglo[0] + "," + arreglo[1] + "," + arreglo[2] + "," + arreglo[3] + "," + arreglo[4] + "," + arreglo[5] + "," + codigoNuevo + "," + arreglo[7] + "," + arreglo[8] + "," + arreglo[9] + "," + arreglo[10] + "," + arreglo[11] + "," + arreglo[12] + "," + arreglo[13] + "," + arreglo[14] + "," + (arreglo[15] + ".00")
                                    + "," + (arreglo[16] + ".00") + "\r\n");
                        }
                    }
                }
            }
            //ToDo: mover las sigueintes dos lineas al bloque finally
            archivoLeer.close();
            archivoACGuardar.close();
            JuegoRips.borrarRenombrar(ruta, ruta + ".txt");
        } catch (FileNotFoundException e) {
            VentanaPrincipal.agregarATxaRespuesta("NO se encontro el Archivo " + ruta);
        } catch (IOException e) {
            VentanaPrincipal.agregarATxaRespuesta("Se produjo un error de IO al recorrer el Archivo AC" + ruta);
        } catch (Exception e) {
            VentanaPrincipal.agregarATxaRespuesta("Se produjo un error de general al recorrer el Archivo AC" + ruta + "\n" + e);
        } finally {

            if (errorArchivo) {
                VentanaPrincipal.mostrarMensaje("El archivo AC presenta errores.\nPor favor corrijalos manualmente.\nLea es archivo log para mas información");
                VentanaPrincipal.agregarATxaRespuesta("\r\nEl archivo AC presenta errores");
            }
            if (advertencia) {
                VentanaPrincipal.agregarATxaRespuesta("\r\nEl archivo AC presenta advertencias");
            }
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


    //ToDo: verificar esto en al rama
}