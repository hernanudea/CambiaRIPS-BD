package rips.cam.cod.rips.imp;

import java.util.Date;
import java.util.Vector;

import rips.cam.cod.log.Log;
import rips.cam.cod.proc.VentanaPrincipal;
import rips.cam.cod.rips.JuegoRips;

public class ArchivosCT {
	String codigoPrestador;
	Date fechaRemision;
	String codigoArchivo;
	int totalRegistros;
	boolean cargado;
	String rutaJuegoRips;
	public static Vector<ArchivosCT> vectorCT = new Vector<ArchivosCT>();
	public static boolean error = false;
	public static boolean advertencia = false;

	public ArchivosCT(String codigoPrestador, Date fechaRemision, String codigoArchivo, int totalRegistros, boolean cargado, String rutaJuegoRips) {
		this.codigoPrestador = codigoPrestador;
		this.fechaRemision = fechaRemision;
		this.codigoArchivo = codigoArchivo;
		this.totalRegistros = totalRegistros;
		this.cargado = cargado;
		this.rutaJuegoRips = rutaJuegoRips;
	}

	public String getCodigoPrestador() {
		return codigoPrestador;
	}

	public void setCodigoPrestador(String codigoPrestador) {
		this.codigoPrestador = codigoPrestador;
	}

	public Date getFechaRemision() {
		return fechaRemision;
	}

	public void setFechaRemision(Date fechaRemision) {
		this.fechaRemision = fechaRemision;
	}

	public String getCodigoArchivo() {
		return codigoArchivo;
	}

	public void setCodigoArchivo(String codigoArchivo) {
		this.codigoArchivo = codigoArchivo;
	}

	public int getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public boolean isCargado() {
		return cargado;
	}

	public void setCargado(boolean cargado) {
		this.cargado = cargado;
	}

	public String getRutaJuegoRips() {
		return rutaJuegoRips;
	}

	public void setRutaJuegoRips(String rutaJuegoRips) {
		this.rutaJuegoRips = rutaJuegoRips;
	}

	public void agregarAlVector(ArchivosCT ct) {
		vectorCT.add(ct);
	}

	public static void limpiarVector() {
		vectorCT.removeAllElements();
	}

	public static void determinarArchivosEnJuegoRips(JuegoRips jp) {
		for (int i = 0; i < vectorCT.size(); i++) {
			switch ((char) vectorCT.get(i).codigoArchivo.toUpperCase().charAt(1)) {
			case 'F':
				jp.setContieneAF(true);
				break;
			case 'D':
				jp.setContieneAD(true);
				break;
			case 'C':
				jp.setContieneAC(true);
				break;
			case 'P':
				jp.setContieneAP(true);
				break;
			case 'U':
				jp.setContieneAU(true);
				break;
			case 'M':
				jp.setContieneAM(true);
				break;
			case 'N':
				jp.setContieneRN(true);
				break;
			case 'S':
				jp.setContieneUS(true);
				break;
			case 'H':
				jp.setContieneAH(true);
				break;
			case 'T':
				if ((char) vectorCT.get(i).codigoArchivo.toUpperCase().charAt(0) == 'A') {
					jp.setContieneAT(true);
				} else {
					if ((char) vectorCT.get(i).codigoArchivo.toUpperCase().charAt(0) == 'A') {
						jp.setContieneCT(true);
					} else {
						VentanaPrincipal.agregarATxaRespuesta("\nError,ArchivosCT,Linea " + i + ",Segundo caracter del jombre es la T. pero el primero no el valido");
					}
				}
				break;
			default:
				Log.guardarIncidencia(0, "CT", i, vectorCT.get(i).codigoArchivo, "No pertenece a un tipo de archchivo");
				break;
			}
		}
	}
}
