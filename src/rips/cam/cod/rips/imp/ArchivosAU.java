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
import rips.cam.cod.proc.VentanaPrincipal;
import rips.cam.cod.rips.JuegoRips;
import rips.cam.cod.rips.Rips;
import rips.cam.cod.validaciones.Validaciones;

public class ArchivosAU extends Rips {
	Date horaIngresoUsuario;
	int causaExterna;
	String diagnosticoSalida;
	String diagnosticoRelacionado2;
	String diagnosticoRelacionado3;
	int destinoUsuario;
	int tipoServicio;
	int estadoSalida;
	int causaBasicaMuerte;
	Date fechaSalidaUsuario;
	Date horaSalidaUsuario;
	public static String ruta;
	public static boolean error = false;
	public static boolean advertencia = false;
	public static ArrayList<ArchivosAU> arrayAU = new ArrayList<ArchivosAU>();

	public ArchivosAU(int numeroFactura, String codigoPrestador, String tipoIdentificacionUsuario, String numeroIdentificacionUsuario, String fechaProcedimientoConsulta, String numeroAutorizacion, String codigoConsultaProcedimiento, String codigoDiagnosticoPrincipal, String codigoDiagnosticoRelacionado1, Date horaIngresoUsuario, int causaExterna,
					  String diagnosticoSalida, String diagnosticoRelacionado2, String diagnosticoRelacionado3, int destinoUsuario, int tipoServicio, int estadoSalida, int causaBasicaMuerte, Date fechaSalidaUsuario, Date horaSalidaUsuario) {
		super(numeroFactura, codigoPrestador, tipoIdentificacionUsuario, numeroIdentificacionUsuario, fechaProcedimientoConsulta, numeroAutorizacion, codigoConsultaProcedimiento, codigoDiagnosticoPrincipal, codigoDiagnosticoRelacionado1);
		this.horaIngresoUsuario = horaIngresoUsuario;
		this.causaExterna = causaExterna;
		this.diagnosticoSalida = diagnosticoSalida;
		this.diagnosticoRelacionado2 = diagnosticoRelacionado2;
		this.diagnosticoRelacionado3 = diagnosticoRelacionado3;
		this.destinoUsuario = destinoUsuario;
		this.tipoServicio = tipoServicio;
		this.estadoSalida = estadoSalida;
		this.causaBasicaMuerte = causaBasicaMuerte;
		this.fechaSalidaUsuario = fechaSalidaUsuario;
		this.horaSalidaUsuario = horaSalidaUsuario;
	}

	public static void calularRutaArchivoAU(String cadena) {
		ruta = cadena;
		if (cadena.contains("CT")) { // hacemos que ruta tenga el nombre del archivo de la misma forma que el CT
			ruta = cadena.replace("CT", "AU");
		} else {
			if (cadena.contains("ct")) {
				ruta = cadena.replace("ct", "au");
			} else {
				if (cadena.contains("Ct")) {
					ruta = cadena.replace("Ct", "Au");
				}
			}
		}
	}

	public static void verificarCampos() {
		try {
			BufferedWriter archivoAUGuardar = new BufferedWriter(new FileWriter(ruta + ".txt"));
			BufferedReader archivoLeer = new BufferedReader(new FileReader(ruta));
			int lineaArchivo = 0;

			while (archivoLeer.ready()) {
				lineaArchivo++;
				String linea = archivoLeer.readLine();
				String[] arreglo = linea.split(",");
				
				if (Validaciones.isObligatorio(arreglo[8])) {// validamos si el campo obligatorio esta diligenciado
					archivoAUGuardar.write(linea + "\r\n");
				} else {
					Log.guardarIncidencia(1, "AU", lineaArchivo, arreglo[8], "Este campo es ogligatorio y no se encuentra diligenciado");
					archivoAUGuardar.write(linea + "\r\n");
					error = true;
				}
			}
			archivoLeer.close();
			archivoAUGuardar.close();
			JuegoRips.borrarRenombrar(ruta, ruta + ".txt");
		} catch (FileNotFoundException e) {
			VentanaPrincipal.agregarATxaRespuesta("No se encontro el Archivo " + ruta);
		} catch (IOException e) {
			VentanaPrincipal.agregarATxaRespuesta("Se produjo un error de IO al recorrer el Archivo AC" + ruta);
		} finally {
			if (error) {
				VentanaPrincipal.mostrarMensaje("El archivo AU presenta errores.\nPor favor corrijalos manualmente.\nLea es archivo log para mas información");
				VentanaPrincipal.agregarATxaRespuesta("\r\nEl archivo AU presenta errores");
			}
			if (advertencia) {
				VentanaPrincipal.agregarATxaRespuesta("\r\nEl archivo AU presenta advertencias");
			}
		}
	}
}
