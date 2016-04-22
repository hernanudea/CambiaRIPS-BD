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
import rips.cam.cod.proc.VentanaPrincipal;
import rips.cam.cod.rips.JuegoRips;
import rips.cam.cod.rips.Rips;

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
	public static ArrayList<ArchivosAC> arrayAC = new ArrayList<ArchivosAC>();
	public static boolean error = false;
	public static boolean advertencia = false;
	public static String ruta;

	public ArchivosAC(int numeroFactura, String codigoPrestador, String tipoIdentificacionUsuario, String numeroIdentificacionUsuario, Date fechaProcedimientoConsulta, String numeroAutorizacion, String codigoConsultaProcedimiento, String finalidadConsulta, String causaExterna, String codigoDiagnosticoPrincipal,
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
	}

	public static void limpiarArrayList() {
		arrayAC.clear();
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

	public void agregarAlArray(ArchivosAC archivoAC) {
		arrayAC.add(archivoAC);
	}

	public static void cambiarCodigos() {
		try {
			BufferedWriter archivoACGuardar = new BufferedWriter(new FileWriter(ruta + ".txt"));
			BufferedReader archivoLeer = new BufferedReader(new FileReader(ruta));
			int lineaArchivoAC = 0;

			while (archivoLeer.ready()) {
				lineaArchivoAC++;
				String linea = archivoLeer.readLine();
				String[] arreglo = linea.split(",");
				String codigoNuevo = Codigos.cambiarCodigo(arreglo[6]);
				if (codigoNuevo == null) {
					Log.guardarIncidencia(1, "AC", lineaArchivoAC, arreglo[6], "Este valor no esta en el archivo de códigos");
					archivoACGuardar.write(linea + "\r\n");
					error = true;
					continue;
				} else {
					if (codigoNuevo.equals("-1")) {
						advertencia = true;
						archivoACGuardar.write(arreglo[0] + "," + arreglo[1] + "," + arreglo[2] + "," + arreglo[3] + "," + arreglo[4] + "," + arreglo[5] + "," + arreglo[6] + "," + arreglo[7] + "," + arreglo[8] + "," + arreglo[9] + "," + arreglo[10] + "," + arreglo[11] + "," + arreglo[12] + "," + arreglo[13] + "," + arreglo[14] + "," + arreglo[15]
								+ "," + arreglo[16] + "\r\n");
						Log.guardarIncidencia(1, "AC", lineaArchivoAC, arreglo[6], "No se debe procesar según la configuración del archivo de códigos");
					} else {
						advertencia = true;
						archivoACGuardar.write(arreglo[0] + "," + arreglo[1] + "," + arreglo[2] + "," + arreglo[3] + "," + arreglo[4] + "," + arreglo[5] + "," + codigoNuevo + "," + arreglo[7] + "," + arreglo[8] + "," + arreglo[9] + "," + arreglo[10] + "," + arreglo[11] + "," + arreglo[12] + "," + arreglo[13] + "," + arreglo[14] + "," + arreglo[15]
								+ "," + arreglo[16] + "\r\n");
					}
				}
			}
			archivoLeer.close();
			archivoACGuardar.close();
			JuegoRips.borrarRenombrar(ruta, ruta + ".txt");
		} catch (FileNotFoundException e) {
			VentanaPrincipal.agregarATxaRespuesta("NO se encontro el Archivo " + ruta);
		} catch (IOException e) {
			VentanaPrincipal.agregarATxaRespuesta("Se produjo un error de IO al recorrer el Archivo AC" + ruta);
		} finally {
			if (error) {
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
}
