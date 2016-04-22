package rips.cam.cod.proc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import rips.cam.cod.codigos.Codigos;
import rips.cam.cod.log.Log;
import rips.cam.cod.rips.imp.ArchivoCT;
import rips.cam.cod.rips.JuegoRips;
import rips.cam.cod.validaciones.Validaciones;

/**
 * @author hernan
 */
public class VentanaPrincipal extends javax.swing.JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static String version = "v1.0.0";

    public static boolean advertenciasArchivoCT = false;
    public static boolean advertenciasArchivoCodigos = false;
    public static boolean erroresArchivoCT = false;
    public static boolean erroresArchivoCodigos = false;
    public static final String ARCHIVO_NO_CARGADO = "Ningún archivo seleccionado";

    JFileChooser codigos = new JFileChooser();
    JFileChooser archivoCT = new JFileChooser();

    JLabel lblInstruccionArchivo = new JLabel("Seleccione el archivo CT de los RIPS.");
    JButton btnExaminarArchivo = new JButton("Buscar CT");
    JLabel lblArchivoArchivo = new JLabel("Ningún archivo seleccionado");
    JLabel lblInstruccionCodigos = new JLabel("Seleccione el archivo con los códigos de procedimientos");
    JButton btnExaminarCodigos = new JButton("Buscar Códigos");
    JLabel lblArchivoCodigos = new JLabel("Ningún archivo seleccionado");
    JButton btnEjecutar = new JButton("Ejecutar");
    public static JTextArea txaResultados = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(txaResultados);
    JButton btnIniciar = new JButton("Iniciar");
    JButton btnSalir = new JButton("Salir");
    JLabel lblAutor = new JLabel("Hernán Velásquez | hernandvo@gmail.com");
    JLabel lblVersion = new JLabel(version);

    public VentanaPrincipal() {
        super("RIPS - Cambiar Códigos");
        configurarVentana();
        inicializarComponentes();
        setIconImage(new ImageIcon("fv").getImage());
    }

    private void configurarVentana() {
        this.setSize(640, 310);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new java.awt.Color(144, 202, 249));
    }

    private void inicializarComponentes() {
        // configuramos los componentes
        lblInstruccionArchivo.setBounds(20, 20, 600, 20);
        lblInstruccionArchivo.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstruccionArchivo.setFont(new java.awt.Font("Tahoma", 0, 17));
        btnExaminarArchivo.setBounds(150, 50, 100, 30);
        lblArchivoArchivo.setBounds(270, 50, 350, 20);

        lblInstruccionCodigos.setBounds(20, 100, 600, 20);
        lblInstruccionCodigos.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstruccionCodigos.setFont(new java.awt.Font("Tahoma", 0, 17));
        btnExaminarCodigos.setBounds(150, 130, 100, 30);
        lblArchivoCodigos.setBounds(270, 130, 350, 20);

        btnEjecutar.setBounds(270, 210, 100, 30);
        btnIniciar.setBounds(85, 210, 100, 30);
        btnSalir.setBounds(455, 210, 100, 30);

        scrollPane.setBounds(20, 260, 600, 110);

        lblAutor.setFont(new java.awt.Font("Tahoma", 0, 10));
        lblVersion.setFont(new java.awt.Font("Tahoma", 0, 10));
        ocultarResultado();

        btnExaminarArchivo.addActionListener(this);
        btnExaminarCodigos.addActionListener(this);
        btnEjecutar.addActionListener(this);
        btnSalir.addActionListener(this);
        btnIniciar.addActionListener(this);

        // Agregamos los componentes
        this.add(lblInstruccionArchivo);
        this.add(btnExaminarArchivo);
        this.add(lblArchivoArchivo);
        this.add(lblInstruccionCodigos);
        this.add(btnExaminarCodigos);
        this.add(lblArchivoCodigos);
        this.add(btnEjecutar);
        this.add(btnSalir);
        this.add(btnIniciar);
        this.add(scrollPane);
        this.add(lblAutor);
        this.add(lblVersion);
        agregarATxaRespuesta("A continuación se muestra la lista de errores y advertencias.\n\nTipoINconsistencia,Archivo,Linea,DescripcionError");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Salir")) {
            Salir();
        }

        if (e.getActionCommand().equals("Buscar CT")) {
            buscarCT();
        }

        if (e.getActionCommand().equals("Buscar Códigos")) {
            buscarCodigos();
        }
        if (e.getActionCommand().equals("Iniciar")) {
            iniciar();
        }

        if (e.getActionCommand().equals("Ejecutar")) {
            ejecutar();
        }
    }

    private void ejecutar() {
        if (archivoCT.getSelectedFile() == null) {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún archivo de RIPS", "Advertencia", 1);
        } else {
            if (codigos.getSelectedFile() == null) {
                JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún archivo de Códigos", "Advertencia", 1);
            } else {
                if (erroresArchivoCodigos || erroresArchivoCT) { // verificamos si se presento otro tipo de eror en los archivos
                    JOptionPane.showMessageDialog(this, "Se presentaron errores durante la carga de los archivos.\nPor favor corrijalos he intentelo de nuevo", "Advertencia", 1);
                } else {
                    JuegoRips juegoRips;
                    juegoRips = new JuegoRips(archivoCT.getSelectedFile().toString());
                    ArchivoCT.determinarArchivosEnJuegoRips(juegoRips);
                    JuegoRips.abrirTodosLosArchivo(juegoRips);
                }
                mostrarResultado();
            }// validacion codigos
        }// validacion archivoCT
        Log.guardarArchivo(); // Guardamos los Log al finalizar el evento del boton Ejecutar.
        // ToDo Escribir mensajes de errores y advertencias en el txarea
    }

    private void Salir() {
        //JOptionPane.showMessageDialog(this, "Muchas gracias por usar este programa");
        System.exit(DISPOSE_ON_CLOSE);
    }

    private void mostrarResultado() {
        this.setSize(640, 420);
        scrollPane.setVisible(true);
        lblVersion.setBounds(605, 375, 40, 20);
        lblAutor.setBounds(0, 375, 200, 20);

        txaResultados.setLineWrap(true);
        txaResultados.setEditable(true);

    }

    private void ocultarResultado() {
        this.setSize(640, 310);
        scrollPane.setVisible(false);
        lblVersion.setBounds(605, 265, 40, 20);
        lblAutor.setBounds(0, 265, 200, 20);
        txaResultados.setText(null);
        lblArchivoArchivo.setText(ARCHIVO_NO_CARGADO);
        lblArchivoCodigos.setText(ARCHIVO_NO_CARGADO);
        codigos.setSelectedFile(null);
        archivoCT.setSelectedFile(null);
    }

    private void iniciar() {
        ocultarResultado();
        ArchivoCT.limpiarVector();
        Codigos.limpiarMap();
    }

    private void buscarCT() {
        // JOptionPane.showMessageDialog(this, "Busque la carpeta de los RIPS y Seleccione el Archivo de la forma CT*.txt", "Instrucción", 1);
        FileNameExtensionFilter filtroArchivoCT = new FileNameExtensionFilter("Archivos CT*.TXT", "txt");
        archivoCT.setFileFilter(filtroArchivoCT);
        archivoCT.showOpenDialog(this);
        String nombreArchivo = archivoCT.getSelectedFile().getAbsoluteFile().toString().toUpperCase();
        if (nombreArchivo.contains("CT")) {
            lblArchivoArchivo.setText(nombreArchivo);
            try {
                // leemos el archivo
                ArchivoCT.limpiarVector();
                BufferedReader archivoLeer = new BufferedReader(new FileReader(nombreArchivo));
                int lineasArchivoCT = 0;
                while (archivoLeer.ready()) {
                    lineasArchivoCT++;
                    String linea = archivoLeer.readLine();
                    String[] arreglo = linea.split(",");
                    if (arreglo.length != 4) {
                        erroresArchivoCT = true;
                        JOptionPane.showMessageDialog(this, "Se presentó un eror en el archivo.\nLa linea " + lineasArchivoCT + " no cumple con la estructura.");
                    } else {
                        // ToDo Validaciones a cada dato
                        boolean aa = Validaciones.isDate(arreglo[1]);
                        ArchivoCT ct = new ArchivoCT(arreglo[0], new Date(arreglo[1]), arreglo[2], Integer.parseInt(arreglo[3]), false, nombreArchivo);
                        ct.agregarAlVector(ct);
                        erroresArchivoCT = false;
                    }
                }
                archivoLeer.close();
                JOptionPane.showMessageDialog(this, "El Archivo fue procesado.\nSe cargaron " + ArchivoCT.vectorCT.size() + " registros.");

            } catch (FileNotFoundException fnf) {
                JOptionPane.showMessageDialog(this, "El archivo no fue encontrado " + fnf.getMessage(), "Error", 1);
                System.out.println("El archivo no fue encontrado " + fnf.getMessage());
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(this, "El archivo no fue encontrado " + e1.getMessage(), "Error", 1);
                e1.printStackTrace();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "El archivo no fue encontrado " + ex.getMessage(), "Error", 1);
            }
        } else {
            JOptionPane.showMessageDialog(this, "El archivo seleccionado no es un archivo CT*.txt de RIPS");
            archivoCT.setSelectedFile(null);
        }
    }

    private void buscarCodigos() {
        advertenciasArchivoCodigos = false;
        // JOptionPane.showMessageDialog(this, "Busque un archivo de texto con extencion *.txt.\n-En la primera fila del archivo debe estar el código SOAT.\n-En la segunda el código CUPS.\n-En la tercera una S o N para saber si se procesa esa lina.\n-En la cuarta el nombre del procedimiento o insumo", "Instrucción", 1);
        FileNameExtensionFilter filtroCodigos = new FileNameExtensionFilter("Archivos *.TXT", "txt");
        codigos.setFileFilter(filtroCodigos);
        codigos.showOpenDialog(this);
        lblArchivoCodigos.setText(codigos.getSelectedFile().toString());
        try {
            // leemos el archivo
            Codigos.limpiarMap();
            int lineaArchivoCodigo = 0;
            BufferedReader archivoLeer = new BufferedReader(new FileReader(codigos.getSelectedFile().toString()));
            while (archivoLeer.ready()) {
                lineaArchivoCodigo++;
                String linea = archivoLeer.readLine();
                String[] arreglo = linea.split(",");
                if (arreglo.length != 4) {
                    advertenciasArchivoCodigos = true;
                    agregarATxaRespuesta("\nAdvertencia,ArchivoCodigos,Linea " + lineaArchivoCodigo + ",no tiene la forma del archivo de codigos");
                } else {
                    // ToDo Verificar los datos de cada columna
                    erroresArchivoCodigos = false;
                    boolean procesar;
                    if (arreglo[2].toUpperCase().equals("S")) {
                        procesar = true;
                    } else {
                        procesar = false;
                    }
                    Codigos codigos = new Codigos(arreglo[0], arreglo[1], procesar, arreglo[3]);
                    codigos.agregarAlVector(codigos);
                }
            }
            archivoLeer.close();
            if (advertenciasArchivoCodigos) {
                JOptionPane.showMessageDialog(this, "Se presentaron advertencias.\nPor lo menos una linea no cumplió con el formato.\nSe cargaron " + Codigos.mapaCodigos.size() + " registros.", "Advertencia", 2);
            } else {
                JOptionPane.showMessageDialog(this, "El Archivo fue procesado.\nSe cargaron " + Codigos.mapaCodigos.size() + " registros.");
            }
        } catch (FileNotFoundException fnf) {
            JOptionPane.showMessageDialog(this, "El archivo no fue encontrado " + fnf.getMessage(), "Error", 1);
            System.out.println("El archivo no fue encontrado " + fnf.getMessage());
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(this, "El archivo no fue encontrado " + e1.getMessage(), "Error", 1);
            e1.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "El archivo no cumple con la estructura indicada. \n" + ex.getMessage(), "Error", 1);
        }
    }

    public static void agregarATxaRespuesta(String cadena) {
        txaResultados.append(cadena);
    }

    public static void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}