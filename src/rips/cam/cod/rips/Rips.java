package rips.cam.cod.rips;

import java.util.Date;

public class Rips {

    int numeroFactura;
    String codigoPrestador;
    String tipoIdentificacionUsuario;
    String numeroIdentificacionUsuario;
    String fechaProcedimientoConsultaIngreso;
    String numeroAutorizacion;
    String codigoConsultaProcedimiento;
    String codigoDiagnosticoPrincipal;
    String codigoDiagnosticoRelacionado1;

    int numeroLinea;
    boolean error;

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public Rips() {
        this.numeroFactura = numeroFactura;
    }

	public Rips(int numeroFactura, String codigoPrestador, String tipoIdentificacionUsuario, String numeroIdentificacionUsuario, String fechaProcedimientoConsulta, String numeroAutorizacion, String codigoConsultaProcedimiento, String codigoDiagnosticoPrincipal, String codigoDiagnosticoRelacionado1) {
        this.numeroFactura = numeroFactura;
		this.codigoPrestador = codigoPrestador;
		this.tipoIdentificacionUsuario = tipoIdentificacionUsuario;
		this.numeroIdentificacionUsuario = numeroIdentificacionUsuario;
		this.fechaProcedimientoConsultaIngreso = fechaProcedimientoConsultaIngreso;
		this.numeroAutorizacion = numeroAutorizacion;
		this.codigoConsultaProcedimiento = codigoConsultaProcedimiento;
		this.codigoDiagnosticoPrincipal = codigoDiagnosticoPrincipal;
		this.codigoDiagnosticoRelacionado1 = codigoDiagnosticoRelacionado1;
	}

    // este constructor es usado en la clase TipoAT
	public Rips(int numeroFactura, String codigoPrestador, String tipoIdentificacionUsuario, String numeroIdentificacionUsuario, String numeroAutorizacion) {
		this.numeroFactura = numeroFactura;
		this.codigoPrestador = codigoPrestador;
		this.tipoIdentificacionUsuario = tipoIdentificacionUsuario;
		this.numeroIdentificacionUsuario = numeroIdentificacionUsuario;
		this.numeroAutorizacion = numeroAutorizacion;
	}

    // este constructor es usado en la clase TipoAU
	public Rips(int numeroFactura, String codigoPrestador, String tipoIdentificacionUsuario, String numeroIdentificacionUsuario, String fechaProcedimientoConsultaIngreso, String numeroAutorizacion, String codigoDiagnosticoRelacionado1) {
		this.numeroFactura = numeroFactura;
		this.codigoPrestador = codigoPrestador;
		this.tipoIdentificacionUsuario = tipoIdentificacionUsuario;
		this.numeroIdentificacionUsuario = numeroIdentificacionUsuario;
		this.fechaProcedimientoConsultaIngreso = fechaProcedimientoConsultaIngreso;
		this.numeroAutorizacion = numeroAutorizacion;
		this.codigoDiagnosticoRelacionado1 = codigoDiagnosticoRelacionado1;
	}

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getCodigoPrestador() {
        return codigoPrestador;
    }

    public void setCodigoPrestador(String codigoPrestador) {
        this.codigoPrestador = codigoPrestador;
    }

    public String getTipoIdentificacionUsuario() {
        return tipoIdentificacionUsuario;
    }

    public void setTipoIdentificacionUsuario(String tipoIdentificacionUsuario) {
        this.tipoIdentificacionUsuario = tipoIdentificacionUsuario;
    }

    public String getNumeroIdentificacionUsuario() {
        return numeroIdentificacionUsuario;
    }

    public void setNumeroIdentificacionUsuario(String numeroIdentificacionUsuario) {
        this.numeroIdentificacionUsuario = numeroIdentificacionUsuario;
    }

    public String getFechaProcedimientoConsultaIngreso() {
        return fechaProcedimientoConsultaIngreso;
    }

    public void setFechaProcedimientoConsultaIngreso(String fechaProcedimientoConsultaIngreso) {
        this.fechaProcedimientoConsultaIngreso = fechaProcedimientoConsultaIngreso;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public String getCodigoConsultaProcedimiento() {
        return codigoConsultaProcedimiento;
    }

    public void setCodigoConsultaProcedimiento(String codigoConsultaProcedimiento) {
        this.codigoConsultaProcedimiento = codigoConsultaProcedimiento;
    }

    public String getCodigoDiagnosticoPrincipal() {
        return codigoDiagnosticoPrincipal;
    }

    public void setCodigoDiagnosticoPrincipal(String codigoDiagnosticoPrincipal) {
        this.codigoDiagnosticoPrincipal = codigoDiagnosticoPrincipal;
    }

    public String getCodigoDiagnosticoRelacionado1() {
        return codigoDiagnosticoRelacionado1;
    }

    public void setCodigoDiagnosticoRelacionado1(String codigoDiagnosticoRelacionado1) {
        this.codigoDiagnosticoRelacionado1 = codigoDiagnosticoRelacionado1;
    }
}
