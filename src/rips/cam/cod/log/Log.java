package rips.cam.cod.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import rips.cam.cod.proc.VentanaPrincipal;
import rips.cam.cod.rips.JuegoRips;

public class Log {
    public String tipo = "";
    public String archivo = "";
    public int linea = 0;
    public String valor = "";

    public Log(String tipo, String archivo, int linea, String valor, String descripcion) {
        super();
        this.tipo = tipo;
        this.archivo = archivo;
        this.linea = linea;
        this.valor = valor;
        this.descripcion = descripcion;
    }

    String descripcion = "";

    public static Vector<Log> vectorLog = new Vector<Log>();

    public static void guardarIncidencia(int tipo, String archivo, int linea, String valor, String descripcion) {
        String t = "";
        if (tipo == 0) {
            t = "Error";
        } else {
            t = "Advertencia";
        }
        Log l = new Log(t, archivo, linea, valor, descripcion);
        vectorLog.add(l);
    }

    public static void guardarArchivo() {
        if (vectorLog.size() > 0) { //validamos si se presentaron errores y advertencias antes de crear archivo
            File miDir = new File(".");
            try {
                BufferedWriter archivoLog = new BufferedWriter(new FileWriter(miDir.getCanonicalPath() + "/Log.csv"));
                archivoLog.write("Tipo_Error,Archivo,Linea,Valor_Error,Descripción");
                for (int i = 1; i < vectorLog.size(); i++) {
                    archivoLog.write("\r\n" + vectorLog.get(i).tipo + "," + vectorLog.get(i).archivo + "," + vectorLog.get(i).linea + "," + vectorLog.get(i).valor + "," + vectorLog.get(i).descripcion);
                }
                archivoLog.close();
            } catch (IOException e) {
                VentanaPrincipal.mostrarMensaje("No fue posible guardar el archivo Log");
            }
        } else {
            if (VentanaPrincipal.mostrarMensajeFelicitación) {
                VentanaPrincipal.mostrarMensaje("Felicidades!\nNo se encontraron errores.");
            }
        }
    }
}
