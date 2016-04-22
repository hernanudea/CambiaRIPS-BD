package rips.cam.cod.proc;

import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        //saludar();
        new VentanaPrincipal().setVisible(true);
    }

    private static void saludar() {
        JOptionPane.showMessageDialog(null, "Bienvenido(a).\n" + "Esta aplicación sirve para cambiar los códigos de los procedimientos a los archivos RIPS" + "!", null, 1, null);
    }
}


//ToDo: En el boton Archivo CT, validar los datos.
//ToDo: Cargar archivo de codigos automaticametne
//ToDo: Usar la interface en todos los archivos dfe RIPS
//ToDo: Quitar las advertencias del log
//ToDo: Unir varios juegos de RIPS


