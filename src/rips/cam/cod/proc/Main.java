package rips.cam.cod.proc;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		//saludar();
		new VentanaPrincipal().setVisible(true);
	}
	
	private static void saludar() {
		JOptionPane.showMessageDialog(null, "Bienvenido(a).\n" + "Esta aplicaci�n sirve para cambiar los c�digos de los procedimientos a los archivos RIPS" + "!", null, 1, null);
	}
}

// En el boton Archivo CT, validar los datos.