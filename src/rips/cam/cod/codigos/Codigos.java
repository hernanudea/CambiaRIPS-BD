package rips.cam.cod.codigos;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Codigos {
    public String codigoSoat;
    public String codigoCups;
    public boolean procesar;
    public String nombreProcedimiento;
    public static Map<String, Codigos> mapaCodigos = new HashMap<String, Codigos>();// creamos collection HashMap

    public Codigos(String codigoSoat, String codigoCups, boolean procesar, String nombreProcedimiento) {
        this.codigoSoat = codigoSoat;
        this.codigoCups = codigoCups;
        this.procesar = procesar;
        this.nombreProcedimiento = nombreProcedimiento;
    }

    public String getCodigoSoat() {
        return codigoSoat;
    }

    public void setCodigoSoat(String codigoSoat) {
        this.codigoSoat = codigoSoat;
    }

    public String getCodigoCups() {
        return codigoCups;
    }

    public void setCodigoCups(String codigoCups) {
        this.codigoCups = codigoCups;
    }

    public boolean isProcesar() {
        return procesar;
    }

    public void setProcesar(boolean procesar) {
        this.procesar = procesar;
    }

    public String getNombreProcedimiento() {
        return nombreProcedimiento;
    }

    public void setNombreProcedimiento(String nombreProcedimiento) {
        this.nombreProcedimiento = nombreProcedimiento;
    }

    public static void agregarAlVector(Codigos codigo) {
        mapaCodigos.put(codigo.getCodigoSoat(), codigo);
    }

    public static void limpiarMap() {
        mapaCodigos.clear();
    }

    public static String cambiarCodigo(String codigo) {
        if (mapaCodigos.containsKey(codigo)) {
            if (mapaCodigos.get(codigo).procesar) {
                return mapaCodigos.get(codigo).getCodigoCups();
            } else {
                return "-1";
            }
        } else {
            return null;
        }
    }

}
