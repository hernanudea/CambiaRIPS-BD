package rips.cam.cod.proc;

import java.io.File;

import rips.cam.cod.proc.VentanaPrincipal;

public class Operaciones {

	/*public static String operarArchivos(File archivoCT, File archivoCodigos) {
        String a = archivoCodigos.getParentFile().toString();
		String b = archivoCT.getParentFile().toString();
		VentanaPrincipal.txaResultados.setVisible(true);
		VentanaPrincipal.agregarATxaRespuesta("los archivos son:\n" + a + "\n" + b);
		return ("los archivos son:\n" + a + "\n" + b);
	}*/

    public static String reemplazarCerosFinales(String cadena) {
        return cadena.replace(".00", "");
    }
}
