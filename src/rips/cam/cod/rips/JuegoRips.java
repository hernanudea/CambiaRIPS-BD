package rips.cam.cod.rips;

import java.io.File;

import rips.cam.cod.proc.VentanaPrincipal;
import rips.cam.cod.rips.imp.*;

public class JuegoRips {

    public boolean contieneAF = false;
    public boolean contieneAD = false;
    public boolean contieneAC = false;
    public boolean contieneAP = false;
    public boolean contieneAU = false;
    public boolean contieneAH = false;
    public boolean contieneAM = false;
    public boolean contieneAT = false;

    public boolean contieneCT = false;
    public boolean contieneRN = false;
    public boolean contieneUS = false;
    public String ruta;

    public JuegoRips(String ruta) {
        this.ruta = ruta;
    }

    public boolean getContieneAF() {
        return contieneAF;
    }

    public void setContieneAF(boolean contieneAF) {
        this.contieneAF = contieneAF;
    }

    public boolean getContieneAD() {
        return contieneAD;
    }

    public void setContieneAD(boolean contieneAD) {
        this.contieneAD = contieneAD;
    }

    public boolean getContieneAC() {
        return contieneAC;
    }

    public void setContieneAC(boolean contieneAC) {
        this.contieneAC = contieneAC;
    }

    public boolean getContieneAP() {
        return contieneAP;
    }

    public void setContieneAP(boolean contieneAP) {
        this.contieneAP = contieneAP;
    }

    public boolean getContieneAU() {
        return contieneAU;
    }

    public void setContieneAU(boolean contieneAU) {
        this.contieneAU = contieneAU;
    }

    public boolean getContieneAH() {
        return contieneAH;
    }

    public void setContieneAH(boolean contieneAH) {
        this.contieneAH = contieneAH;
    }

    public boolean getContieneAM() {
        return contieneAM;
    }

    public void setContieneAM(boolean contieneAM) {
        this.contieneAM = contieneAM;
    }

    public boolean getContieneAT() {
        return contieneAT;
    }

    public void setContieneAT(boolean contieneAT) {
        this.contieneAT = contieneAT;
    }

    public boolean getContieneCT() {
        return contieneCT;
    }

    public void setContieneCT(boolean contieneCT) {
        this.contieneCT = contieneCT;
    }

    public boolean getContieneRN() {
        return contieneRN;
    }

    public void setContieneRN(boolean contieneRN) {
        this.contieneRN = contieneRN;
    }

    public boolean getContieneUS() {
        return contieneUS;
    }

    public void setContieneUS(boolean contieneUS) {
        this.contieneUS = contieneUS;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public static void abrirTodosLosArchivo(JuegoRips jp) {
        if (jp.contieneAF) {
            ArchivosAF.calularRutaArchivoAF(jp.ruta);
            ArchivosAF.llenarCamposVaciosAF();
        }
        if (jp.contieneAC) {
            ArchivosAC.calularRutaArchivoAC(jp.ruta);
            ArchivosAC.recorrerArchivo();
        }
        if (jp.contieneAD) {
            /*ArchivosAD.calularRutaArchivoAC(jp.ruta);
            ArchivosAD.verificarLinea();*/
        }
        if (jp.contieneAP) {
            ArchivosAP.calularRutaArchivoAP(jp.ruta);
            ArchivosAP.recorrerArchivo();
        }
        if (jp.contieneAU) {
            ArchivosAU.calularRutaArchivoAU(jp.ruta);
            ArchivosAU.verificarCampos();
        }
        if (jp.contieneAH) {
            VentanaPrincipal.agregarATxaRespuesta("\nContieneAH, acciones no implementadas");
        }
        if (jp.contieneAM) {
            ArchivosAM.calularRutaArchivoAM(jp.ruta);
            ArchivosAM.recorrerArchivo();
        }
        if (jp.contieneAT) {
            ArchivosAT.calularRutaArchivoAT(jp.ruta);
            ArchivosAT.recorrerArchivo();
        }
        if (jp.contieneCT) {
            VentanaPrincipal.agregarATxaRespuesta("\nContieneCT, acciones no implementadas");
        }
        if (jp.contieneUS) {
            VentanaPrincipal.agregarATxaRespuesta("\nContieneUS, acciones no implementadas");
        }
        if (jp.contieneRN) {
            VentanaPrincipal.agregarATxaRespuesta("\nContieneRN, acciones no implementadas");
        }
    }

    public static void borrarRenombrar(String rutaBorrar, String rutaRenombrar) {
        File archivoBorrar = new File(rutaBorrar);
        File archivoRenombrar = new File(rutaRenombrar);
        if (!archivoBorrar.delete()) {
            VentanaPrincipal.mostrarMensaje("No fue posible borrar el siguiente archivo\n" + rutaBorrar);
        }
        if (!archivoRenombrar.renameTo(archivoBorrar)) {
            VentanaPrincipal.mostrarMensaje("No fue posible renombrar el siguiente archivo\n" + rutaRenombrar);
        }
    }

    public static void compararValores() {
        int valorEntidadAC = ArchivosAC.sumarValorModeradoraNeto();
        int valorEntidadAT = ArchivosAT.sumarValorModeradoraNeto();
        int valorEntidadAP = ArchivosAP.sumarValorModeradoraNeto();
        int valorEntidadAM = ArchivosAM.sumarValorModeradoraNeto();
        int valorEntidadAF = ArchivosAF.sumarValorModeradoraNeto();

        int valorEntidad = valorEntidadAC + valorEntidadAM + valorEntidadAP + valorEntidadAT;
        if (valorEntidad != valorEntidadAF) {
            String mensaje = ("Existe una diferencia entre la suma de los detalles y el archivo AF.\n" +
                    "\tArchivo AF: " + valorEntidadAF + "\n" +
                    "\tSuma detalles: " + valorEntidad + "\n" +
                    "\tDiferencia: " + (valorEntidad - valorEntidadAF));
            VentanaPrincipal.agregarATxaRespuesta("\n" + mensaje);
            VentanaPrincipal.mostrarMensaje(mensaje);
        }
    }
}
