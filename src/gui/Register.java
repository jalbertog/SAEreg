package gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile.ThresholdInputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import util.Util;


public class Register extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String result = "reg";
	
	JTabbedPane tabs;
	JPanel repTab;
	JPanel estTab;
	
	JTextField cedula;
	JTextField nombres;
	JTextField apellidos;
	JSpinner fecha_nac;
	JComboBox<String> edo_civil;
	JTextField parentezco;
	JTextField nacionalidad;
	JTextField pais_nac;
	JTextField estado_nac;
	ButtonGroup genero;
	JRadioButton m;
	JRadioButton f;
	JTextArea dir;
	JTextField tipoVivienda;
	JTextField ubicacion;
	JTextField condicion_vivienda;
	JComboBox<String> codCelular;
	JTextField celular;
	JComboBox<String> codTelfn;
	JTextField telefono;
	JTextField correo;
	JTextField profesion;
	JTextField lugar_trabajo;
	JTextField ingreso;
	///////////////////////////////
	
	
	JTextField ciEstu;
	JComboBox<String> nivel;
	JTextField seccion;
	JTextField nombreEst;
	JTextField apellidoEst;
	JComboBox<String> codCelularEst;
	JTextField celularEst;
	JComboBox<String> codTelfnEst;
	JTextField telefonoEst;
	JTextField correoEst;
	JRadioButton zurdoY;
	JRadioButton zurdoN;
	ButtonGroup zurdo;
	JRadioButton becaY;
	JRadioButton becaN;
	ButtonGroup beca;
	JRadioButton canaimaY;
	JRadioButton canaimaN;
	ButtonGroup canaima;
	JSpinner estatura;
	JSpinner peso;
	JSpinner tallaCamisa;
	JSpinner tallaPantalon;
	JSpinner tallaZapato;
	//////////
	/////////ESTO LO AGREGE
	String municipio = "algun municipio";
	String deporte = "Algun deporte";
	
	
	
	String cods[] = {"0416","0414","0412","0426","0424","0274"};
	private final String [] header = {"cedula","nombres","apellidos","Fecha de Nacimiento",
							"Edo Civil","parentezco","nacionalidad","pais","estado","genero",
							"dirección","tipo de vivienda","ubicación","condicion",
							"celular","telefono","correo","profesion","lugar de trabajo",
							"ingreso"};
	
	JScrollPane sc;
	JScrollPane sSt;
	JButton add;
	JButton exit;
	JButton clear;
	JButton pdf;
	JButton update;
	
	Integer OldCi = 0;
	
	public Register(String title, boolean upd)
	{
		super(title);
		tabs = new JTabbedPane();
		
		buildRepTab();
		buildEstTab();
		buildButtons();
		sc = new JScrollPane(repTab);
		sSt = new JScrollPane(estTab);
		tabs.addTab("Repesentante", sc);
		tabs.addTab("Estudiante", sSt);
		tabs.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		add(tabs,BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(pdf);
		buttonPanel.add(clear);//,getConstraints(1, 0, 1, 1));
		if(!upd)
			buttonPanel.add(add);//,getConstraints(2, 0, 1, 1));
		else
			buttonPanel.add(update);//,getConstraints(2, 0, 1, 1));
		buttonPanel.add(exit);//,getConstraints(3, 0, 1, 1));
		
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		add(buttonPanel,BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	
	private void buildRepTab()
	{
		repTab = new JPanel(new GridBagLayout());
		
		cedula = new JTextField(10);
		repTab.add(new JLabel("<html><b>Numero de Cedula: </b></html>"),getConstraints(0, 0, 1, 1));
		repTab.add(cedula,getConstraints(1, 0, 1, 1));
		
		nombres = new JTextField(25);
		repTab.add(new JLabel("<html><b>Nombres:</b></html>"),getConstraints(0, 1, 2, 1));
		repTab.add(nombres,getConstraints(1, 1, 4, 1));		
		
		apellidos = new JTextField(25);
		repTab.add(new JLabel("<html><b>Apellidos:</b></html>"),getConstraints(0, 2, 1, 1));
		repTab.add(apellidos,getConstraints(1, 2, 3, 1));
		
		fecha_nac = new JSpinner(new SpinnerDateModel());
		fecha_nac.setEditor(new JSpinner.DateEditor(fecha_nac,"dd-MM-yyyy"));
		repTab.add(new JLabel("<html><b>Fecha de Nacimiento:</b></html>"),getConstraints(0, 3, 1, 1));
		repTab.add(fecha_nac,getConstraints(1, 3, 1, 1));
		
		edo_civil = new JComboBox<String>(new String[]{"S","C","V","D"});
		JPanel edPanel = new JPanel();
		edPanel.add(new JLabel("<html><b>Estado Civil:</b></html>"));
		edPanel.add(edo_civil);
		repTab.add(edPanel,getConstraints(2, 3, 1, 1));
		
		parentezco = new JTextField(20);
		JLabel aff = new JLabel("<html><b>Afinidad con el estudiante:</b></html>");
		aff.setToolTipText("Madre,Padre,Tio,Abuelo....");
		repTab.add(aff,getConstraints(0, 4, 1, 1));
		repTab.add(parentezco,getConstraints(1, 4, 3, 1));
		
		nacionalidad = new JTextField(20);
		repTab.add(new JLabel("<html><b>Nacionalidad:</b></html>"),getConstraints(0, 5, 1, 1));
		repTab.add(nacionalidad,getConstraints(1, 5, 1, 1));
		
		pais_nac = new JTextField(20);
		repTab.add(new JLabel("<html><b>Pais de Nacimiento:</b></html>"),getConstraints(2, 5, 1, 1));
		repTab.add(pais_nac,getConstraints(3, 5, 1, 1));
		
		estado_nac = new JTextField(20);
		repTab.add(new JLabel("<html><b>Estado de Nacimiento:</b></html>"),getConstraints(0, 6, 1, 1));
		repTab.add(estado_nac,getConstraints(1, 6, 1, 1));
		
		f = new JRadioButton("F");
		f.setSelected(true);
		m = new JRadioButton("M");
		genero = new ButtonGroup();
		genero.add(f);
		genero.add(m);
		JPanel genPanel = new JPanel();
		genPanel.add(new JLabel("<html><b>Genero:</b></html>"));
		genPanel.add(f);
		genPanel.add(m);
		repTab.add(genPanel,getConstraints(3, 6, 1, 1));
		
		dir = new JTextArea(2, 20);
		JLabel tp = new JLabel("<html><b>Dirección completa:</b></html>");
		tp.setToolTipText("Dirección donde vive");
		repTab.add(tp,getConstraints(0, 7, 1, 1));
		repTab.add(dir,getConstraints(1, 7, 3, 2));
		
		tipoVivienda = new JTextField(10);
		tp = new JLabel("<html><b>Tipo de vivienda: </b></html>");
		tp.setToolTipText("Casa, Apartamento ETC...");
		repTab.add(tp, getConstraints(0, 9, 1, 1));
		repTab.add(tipoVivienda,getConstraints(1, 9, 3, 1));
		
		ubicacion = new JTextField(20);
		JLabel ub = new JLabel("<html><b>Ubicación de la vivienda: </b></html>");
		repTab.add(ub,getConstraints(0, 10, 1, 1));
		repTab.add(ubicacion,getConstraints(1, 10, 3, 1));
		
		condicion_vivienda = new JTextField(20);
		JLabel cond = new JLabel("<html><b>Condicion: </b></html>");
		cond.setToolTipText("Propia,Alquilada, de un familiar ....");
		repTab.add(cond,getConstraints(0, 11, 1, 1));
		repTab.add(condicion_vivienda,getConstraints(1, 11, 3, 1));
		
		celular = new JTextField(7);
		codCelular = new JComboBox<String>(cods);
		codTelfn = new JComboBox<String>(cods);
		Dimension d = new Dimension(80,22);
		codCelular.setPreferredSize(d);
		codTelfn.setPreferredSize(d);
		telefono = new JTextField(7);
		
		JPanel celPanel = new JPanel();
		JPanel tlfnPanel = new JPanel();
		celPanel.add(codCelular);
		celPanel.add(celular);
		tlfnPanel.add(codTelfn);
		tlfnPanel.add(telefono);
		
		repTab.add(new JLabel("<html><b>Telefono celular: </b></html>"),getConstraints(0, 12, 1, 1));
		repTab.add(celPanel,getConstraints(1, 12, 1, 1,0));
		repTab.add(new JLabel("<html><b>Telefono de Casa: </b></html>"),getConstraints(2, 12, 1, 1));
		repTab.add(tlfnPanel,getConstraints(3, 12, 1, 1,0));
		
		correo = new JTextField(20);
		repTab.add(new JLabel("<html><b>Correo: </b></html>"),getConstraints(0, 13, 1, 1));
		repTab.add(correo,getConstraints(1, 13, 1, 1));
		
		profesion = new JTextField(15);
		repTab.add(new JLabel("<html><b>Profesión: </b></html>"),getConstraints(2, 13, 1, 1));
		repTab.add(profesion,getConstraints(3, 13, 1, 1));
		
		lugar_trabajo = new JTextField(20);
		repTab.add(new JLabel("<html><b>Lugar o Empresa donde Trabaja: </b></html>"),getConstraints(0, 14, 1, 1));
		repTab.add(lugar_trabajo,getConstraints(1, 14, 3, 1));
		
		ingreso = new JTextField(8);
		repTab.add(new JLabel("<html><b>Ingreso Promedio Familiar Mensual: </b></html>"),getConstraints(0, 15, 1, 1));
		repTab.add(ingreso,getConstraints(1, 15, 3, 1));
		
		
		TitledBorder ttle = BorderFactory.createTitledBorder("<html><b>Información del Representante</b></html>");
		repTab.setBorder(ttle);
	}
	
	
	private void buildEstTab()
	{
		estTab = new JPanel(new GridBagLayout());
		
		ciEstu = new JTextField(10);
		estTab.add(new JLabel("<html><b>Numero de Cedula</b>:</html>"),getConstraints(0, 0, 1, 1));
		estTab.add(ciEstu,getConstraints(1, 0, 1, 1));
		
		String nv[] = {"1","2","3","4","5"};
		nivel = new JComboBox<String>(nv);
		seccion = new JTextField(1);
		JPanel ayS = new JPanel();
		ayS.add(nivel);
		ayS.add(seccion);
		estTab.add(new JLabel("<html><b>Año y Sección</b>:</html>"), getConstraints(2, 0, 1, 1) );
		estTab.add(ayS,getConstraints(3, 0, 1, 1));
		
		nombreEst = new JTextField(20);
		estTab.add(new JLabel("<html><b>Nombres</b>:</html>"),getConstraints(0, 1, 1, 1));
		estTab.add(nombreEst,getConstraints(1, 1, 3, 1));
		
		apellidoEst = new JTextField(20);
		estTab.add(new JLabel("<html><b>Apellidos</b>:</html>"),getConstraints(0, 2, 1, 1));
		estTab.add(apellidoEst,getConstraints(1, 2, 3, 1));
		
		codCelularEst = new JComboBox<String>(cods);
		celularEst = new JTextField(7);
		JPanel celPanel = new JPanel();
		estTab.add(new JLabel("<html><b>Celular</b>:</html>"),getConstraints(0, 3, 1, 1));
		celPanel.add(codCelularEst);
		celPanel.add(celularEst);
		estTab.add(celPanel,getConstraints(1, 3, 1, 1));
		
		codTelfnEst = new JComboBox<String>(cods);
		telefonoEst = new JTextField(7);
		JPanel telPanel = new JPanel();
		estTab.add(new JLabel("<html><b>Telefono de casa</b>:</html>"),getConstraints(2, 3, 1, 1));
		telPanel.add(codTelfnEst);
		telPanel.add(telefonoEst);
		estTab.add(telPanel,getConstraints(3, 3, 1, 1));
		
		correoEst = new JTextField(10);
		estTab.add(new JLabel("<html><b>Correo</b>:</html>"),getConstraints(0, 4, 1, 1));
		estTab.add(correoEst,getConstraints(1, 4, 1, 1));
		
		zurdoY = new JRadioButton("Sí");
		zurdoN = new JRadioButton("No");
		zurdo = new ButtonGroup();
		zurdo.add(zurdoY);
		zurdo.add(zurdoN);
		zurdoN.setSelected(true);
		JPanel zurdoP = new JPanel();
		estTab.add(new JLabel("<html><b>¿Es zurdo?</b>:</html>"),getConstraints(2, 4, 1, 1));
		zurdoP.add(zurdoY);
		zurdoP.add(zurdoN);
		estTab.add(zurdoP,getConstraints(3, 4, 1, 1));
		
		becaY = new JRadioButton("Sí");
		becaN = new JRadioButton("No");
		beca = new ButtonGroup();
		beca.add(becaY);
		beca.add(becaN);
		becaN.setSelected(true);
		JPanel becaP = new JPanel();
		estTab.add(new JLabel("<html><b>Posee Beca Estudiantil</b>:</html>"),getConstraints(0, 5, 1, 1));
		becaP.add(becaY);
		becaP.add(becaN);
		estTab.add(becaP,getConstraints(1, 5, 1, 1));	

		canaimaY = new JRadioButton("Sí");
		canaimaN = new JRadioButton("No");
		canaima = new ButtonGroup();
		canaima.add(canaimaY);
		canaima.add(canaimaN);
		canaimaY.setSelected(true);
		JPanel canaimaP = new JPanel();
		estTab.add(new JLabel("<html><b>Posee Canaima</b>:</html>"),getConstraints(2, 5, 1, 1));
		canaimaP.add(canaimaY);
		canaimaP.add(canaimaN);
		estTab.add(canaimaP,getConstraints(3, 5, 1, 1));
		
		JSeparator js = new JSeparator();
		js.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		estTab.add(js,getConstraints(0, 6, 4, 1));
		
		estatura = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 2.5, 0.01));
		estTab.add(new JLabel("<html><b>Estatura</b>:</html>"),getConstraints(0, 7, 1, 1));
		estTab.add(estatura,getConstraints(1, 7, 1, 1));
		
		peso = new JSpinner(new SpinnerNumberModel(50.0, 0.0, 200.0, 0.01));
		estTab.add(new JLabel("<html><b>Peso</b>:</html>"),getConstraints(2, 7, 1, 1));
		estTab.add(peso,getConstraints(3, 7, 1, 1));
		
		tallaCamisa = new JSpinner(new SpinnerNumberModel(10, 5, 30, 1));
		estTab.add(new JLabel("<html><b>Talla de Camisa</b>:</html>"),getConstraints(0, 8, 1, 1));
		estTab.add(tallaCamisa,getConstraints(1, 8, 1, 1));	
		
		tallaPantalon = new JSpinner(new SpinnerNumberModel(10, 5, 30, 1));
		estTab.add(new JLabel("<html><b>Talla de Pantalon</b>:</html>"),getConstraints(2, 8, 1, 1));
		estTab.add(tallaPantalon,getConstraints(3, 8, 1, 1));	
		
		tallaZapato = new JSpinner(new SpinnerNumberModel(30, 30, 45, 1));
		estTab.add(new JLabel("<html><b>Talla de Zapatos</b>:</html>"),getConstraints(0, 9, 1, 1));
		estTab.add(tallaZapato,getConstraints(1, 9, 1, 1));	
		
		estTab.setBorder(BorderFactory.createTitledBorder("<html><b>Información del Estudiante</b></html>"));
	}
	
	
	private void buildButtons()
	{
		add = new JButton("Agregar",new ImageIcon("Resources/dialog-ok.png"));
		exit = new JButton("Salir",new ImageIcon("Resources/dialog-no.png"));
		clear = new JButton("Borrar", new ImageIcon("Resources/clear.png"));
		update = new JButton("Actualizar", new ImageIcon("Resources/dialog-ok.png"));
		pdf = new JButton("Generar Pdf",new ImageIcon("Resources/pdf.png"));
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clearAllData();
			}
		});
		
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Statement st = Util.getConection().createStatement();
					String query = "INSERT INTO sae_reg VALUES(" + getAsString() + ");";
					System.out.println(query);
					st.execute(query);
					Util.getConection().commit();
					
				} catch (SQLException e1) {
					if(e1.getErrorCode() == 19)
					{
						JOptionPane.showMessageDialog(null, "Esta cedula ya existe \n Si desea cambiar el registro vaya a actualizar", "Error", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null, "Registro agregado", "crear registro", JOptionPane.INFORMATION_MESSAGE);
				clearAllData();
				
			}
		});
		
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Statement st = Util.getConection().createStatement();
					String query = "UPDATE sae_reg SET " + getUpdateString() + " WHERE cedula="+OldCi.toString()+";";
					System.out.println(query);
					st.executeUpdate(query);
					Util.getConection().commit();
					
				} catch (SQLException e1) {
					System.out.println(e1.getErrorCode());
					//e1.printStackTrace();
					//System.out.println(e1.getErrorCode());
				}
				
				JOptionPane.showMessageDialog(null, "Registro actualizado", "Actualizar registro", JOptionPane.INFORMATION_MESSAGE);
				clearAllData();
				dispose();
			}
		});
		
		pdf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				Object [][] obj = getCurrentAsObject();
				String ci = (String)obj[1][0];
				String result = ci.equals("") ? "default.pdf" : ci+".pdf";
				Util.generatePdf(result, obj);
				setCursor(Cursor.getDefaultCursor());
				JOptionPane.showMessageDialog(null, "Pdf creado!!", "Pdf", JOptionPane.INFORMATION_MESSAGE);
			}			
		});
	}
	
	
	public String getAsString()
	{
	    String cedula = getStringFormat(ciEstu.getText());
	    String nac = getStringFormat(nacionalidad.getText());
	    String cod_escolar = getStringFormat("-");
	    String apellidos = getStringFormat(apellidoEst.getText());
	    String nombres = getStringFormat(nombreEst.getText());
	    String grado = (String)nivel.getSelectedItem();
	    String seccion = getStringFormat(this.seccion.getText());
	    String sexo = f.isSelected() ? "F" : "M";
	    sexo = getStringFormat(sexo);
	    SimpleDateFormat fo = new SimpleDateFormat("yyyy-MM-dd");
	    String fecha_nac = getStringFormat((fo.format((Date)this.fecha_nac.getValue())));
	    String lugar_nac = getStringFormat(this.pais_nac.getText());
	    String estado_nac = getStringFormat(this.estado_nac.getText());
	    String ef = "\'n\'";
	    String cedula_rep = getStringFormat(this.cedula.getText());
	    String apellido_rep  = getStringFormat(this.apellidos.getText());
	    String nombre_rep = getStringFormat(this.nombres.getText());
	    String parentezco = getStringFormat(this.parentezco.getText());
	    String direccion = getStringFormat(dir.getText());
	    String tlfn = (String)codTelfn.getSelectedItem()+"-"+telefono.getText();
	    tlfn = getStringFormat(tlfn);
	    String celular = (String)codCelular.getSelectedItem()+"-"+this.celular.getText();
	    celular = getStringFormat(celular);
	    String email = getStringFormat(this.correo.getText());
	    
	    String edo_civil = getStringFormat((String)this.edo_civil.getSelectedItem());
	    String tipo_vivienda = getStringFormat(tipoVivienda.getText());
	    String ubicacion = getStringFormat(this.ubicacion.getText());
	    String condicion_Vivienda = getStringFormat(condicion_vivienda.getText());
	    String profesion = getStringFormat(this.profesion.getText());
	    String lugar_trabajo = getStringFormat(this.lugar_trabajo.getText());
	    String ingreso =  getStringFormat(this.ingreso.getText());
	    String celular_est = getStringFormat((String)codCelularEst.getSelectedItem()+"-"+celularEst.getText());
	    String tlfn_est = getStringFormat((String)codTelfnEst.getSelectedItem()+"-"+telefonoEst.getText());
	    String corre_est = getStringFormat(this.correoEst.getText());
	    String zurdo = zurdoY.isSelected() ? "\'Y\'" : "\'N\'";
	    String beca = becaY.isSelected() ?  "\'Y\'" : "\'N\'";
	    String canaima = canaimaY.isSelected() ?  "\'Y\'" : "\'N\'";
	    String estatura = ((Double)this.estatura.getValue()).toString();
	    String peso = ((Double)this.peso.getValue()).toString();
	    String talla_camisa = tallaCamisa.getValue().toString();
	    String talla_pantalon = tallaPantalon.getValue().toString();
	    String talla_zapato = tallaZapato.getValue().toString();
	    String pais_nac = getStringFormat(this.pais_nac.getText());
	    
	    return cedula+","+nac+","+cod_escolar+","+apellidos+","+nombres+","+grado+","+seccion+","+
	    		sexo+","+fecha_nac+","+lugar_nac+","+estado_nac+","+ef+","+cedula_rep+","+
	    		apellido_rep+","+nombre_rep+","+parentezco+","+direccion+","+tlfn+","+
	    		celular+","+email+","+edo_civil+","+tipo_vivienda+","+ubicacion+","+condicion_Vivienda+
	    		","+profesion+","+lugar_trabajo+","+ingreso+","+celular_est+","+tlfn_est+","+
	    		corre_est+","+zurdo+","+beca+","+canaima+","+estatura+","+peso+","+talla_camisa+","+talla_pantalon+","+
	    		talla_zapato+","+pais_nac;
	}
	
	public String getUpdateString()
	{
	    String cedula = getStringFormat(ciEstu.getText());
	    String nac = getStringFormat("V");
	    String cod_escolar = getStringFormat("-");
	    String apellidos = getStringFormat(apellidoEst.getText());
	    String nombres = getStringFormat(nombreEst.getText());
	    String grado = (String)nivel.getSelectedItem();
	    String seccion = getStringFormat(this.seccion.getText());
	    String sexo = f.isSelected() ? "F" : "M";
	    sexo = getStringFormat(sexo);
	    SimpleDateFormat fo = new SimpleDateFormat("yyyy-MM-dd");
	    String fecha_nac = getStringFormat((fo.format((Date)this.fecha_nac.getValue())));
	    String lugar_nac = getStringFormat(this.pais_nac.getText());
	    String estado_nac = getStringFormat(this.estado_nac.getText());
	    String ef = "\'n\'";
	    String cedula_rep = getStringFormat(this.cedula.getText());
	    String apellido_rep  = getStringFormat(this.apellidos.getText());
	    String nombre_rep = getStringFormat(this.nombres.getText());
	    String parentezco = getStringFormat(this.parentezco.getText());
	    String direccion = getStringFormat(dir.getText());
	    String tlfn = (String)codTelfn.getSelectedItem()+"-"+telefono.getText();
	    tlfn = getStringFormat(tlfn);
	    String celular = (String)codCelular.getSelectedItem()+"-"+this.celular.getText();
	    celular = getStringFormat(celular);
	    String email = getStringFormat(this.correo.getText());
	    
	    String edo_civil = getStringFormat((String)this.edo_civil.getSelectedItem());
	    String tipo_vivienda = getStringFormat(tipoVivienda.getText());
	    String ubicacion = getStringFormat(this.ubicacion.getText());
	    String condicion_Vivienda = getStringFormat(condicion_vivienda.getText());
	    String profesion = getStringFormat(this.profesion.getText());
	    String lugar_trabajo = getStringFormat(this.lugar_trabajo.getText());
	    String ingreso =  getStringFormat(this.ingreso.getText());
	    String celular_est = getStringFormat((String)codCelularEst.getSelectedItem()+"-"+celularEst.getText());
	    String tlfn_est = getStringFormat((String)codTelfnEst.getSelectedItem()+"-"+telefonoEst.getText());
	    String corre_est = getStringFormat(this.correoEst.getText());
	    String zurdo = zurdoY.isSelected() ? "\'Y\'" : "\'N\'";
	    String beca = becaY.isSelected() ?  "\'Y\'" : "\'N\'";
	    String canaima = canaimaY.isSelected() ?  "\'Y\'" : "\'N\'";
	    String estatura = ((Double)this.estatura.getValue()).toString();
	    String peso = ((Double)this.peso.getValue()).toString();
	    String talla_camisa = tallaCamisa.getValue().toString();
	    String talla_pantalon = tallaPantalon.getValue().toString();
	    String talla_zapato = tallaZapato.getValue().toString();
	    String pais_nac = getStringFormat(this.pais_nac.getText());
	    
	    return "cedula="+cedula+",nac="+nac+",cod_escolar="+cod_escolar+",apellidos="+apellidos+",nombres="+nombres+
	    		",grado="+grado+",seccion="+seccion+",sexo="+sexo+",fecha_nac="+fecha_nac+",lugar_nac="+lugar_nac+
	    		",estado_nac="+estado_nac+",ef="+ef+",cedula_rep="+cedula_rep+",apellido_rep="+
	    		apellido_rep+",nombre_rep="+nombre_rep+",parentezco="+parentezco+",direccion="+direccion+
	    		",tlfn="+tlfn+",celular="+celular+",email="+email+",edo_civil="+edo_civil+",tipo_Vivienda="+tipo_vivienda+",ubicacion="+ubicacion+
	    		",condicion_vivienda="+condicion_Vivienda+",profesion="+profesion+",lugar_trabajo="+lugar_trabajo+
	    		",ingreso="+ingreso+",celular="+celular_est+",tlfn="+tlfn_est+",correo_est="+corre_est+",zurdo="+zurdo+
	    		",beca="+beca+",canaima="+canaima+",estatura="+estatura+",peso="+peso+",talla_camisa="+talla_camisa+
	    		",talla_pantalon="+talla_pantalon+",talla_zapato="+talla_zapato+",pais_nac="+pais_nac;
	}
	
	static public String getStringFormat(String str)
	{
		return "\'"+str+"\'";
	}
	
	private void clearAllData()
	{
		cedula.setText("");
		nombres.setText("");
		apellidos.setText("");
		try {
			fecha_nac.getModel().setValue((Date)(new SimpleDateFormat("YYYY-MM-dd")).parseObject("1990-01-01"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		edo_civil.setSelectedIndex(0);
		parentezco.setText("");
		nacionalidad.setText("");
		pais_nac.setText("");
		estado_nac.setText("");;
		f.setSelected(true);
		dir.setText("");;
		tipoVivienda.setText("");
		ubicacion.setText("");
		condicion_vivienda.setText("");
		codCelular.setSelectedIndex(0);;
		celular.setText("");
		codTelfn.setSelectedIndex(cods.length-1);
		telefono.setText("");
		correo.setText("");
		profesion.setText("");
		lugar_trabajo.setText("");
		ingreso.setText("");
		
		///////
		ciEstu.setText("");
		nivel.setSelectedIndex(0);
		seccion.setText("");
		nombreEst.setText("");
		apellidoEst.setText("");
		codCelularEst.setSelectedIndex(0);
		celularEst.setText("");
		codTelfnEst.setSelectedItem("0274");
		telefonoEst.setText("");
		correoEst.setText("");
		zurdoN.setSelected(true);;
		becaN.setSelected(false);
		canaimaY.setSelected(true);
		estatura.setValue(new Double(1.60));
		peso.setValue(new Double(50.0));
	}
	
	public void loadData(Object [] dat, Object [] est)
	{
		OldCi = Integer.parseInt((String)est[0]);
		cedula.setText((String)dat[0]);
		nombres.setText((String)dat[1]);
		apellidos.setText((String)dat[2]);
		try {
			fecha_nac.getModel().setValue((Date)(new SimpleDateFormat("YYYY-MM-dd")).parseObject((String)dat[3]));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		edo_civil.setSelectedItem(dat[4]);
		parentezco.setText((String)dat[5]);
		nacionalidad.setText((String)dat[6]);
		pais_nac.setText((String)dat[7]);
		estado_nac.setText((String)dat[8]);
		if(((String)dat[9]).equals("F"))
			f.setSelected(true);
		else
			m.setSelected(true);
		dir.setText((String)dat[10]);
		tipoVivienda.setText((String)dat[11]);
		ubicacion.setText((String)dat[12]);
		condicion_vivienda.setText((String)dat[13]);
		codCelular.setSelectedItem(((String)dat[14]).substring(0, 3));
		celular.setText(((String)dat[14]).substring(5, 11));
		codTelfn.setSelectedItem(((String)dat[15]).substring(0, 3));
		telefono.setText(((String)dat[15]).substring(5));
		correo.setText((String)dat[16]);
		profesion.setText((String)dat[17]);
		lugar_trabajo.setText((String)dat[18]);
		ingreso.setText((String)dat[19]);
		
		///////
		
		ciEstu.setText((String)est[0]);
		nivel.setSelectedItem((String)est[1]);
		seccion.setText((String)est[2]);
		nombreEst.setText((String)est[3]);
		apellidoEst.setText((String)est[4]);
		codCelularEst.setSelectedItem(((String)est[5]).substring(0, 3));
		celularEst.setText(((String)est[5]).substring(5));
		codTelfnEst.setSelectedItem(((String)est[6]).substring(0, 3));
		telefonoEst.setText(((String)est[6]).substring(5));
		correoEst.setText((String)est[7]);
		if(((String)est[8]).equals("Y"))
			zurdoY.setSelected(true);
		else
			zurdoN.setSelected(true);
		
		if(((String)est[9]).equals("Y"))
			becaY.setSelected(true);
		else
			becaN.setSelected(true);
		becaN.setSelected(false);
		
		if(((String)est[10]).equals("Y"))
			canaimaY.setSelected(true);
		else
			canaimaN.setSelected(true);
		
		estatura.setValue(Double.parseDouble((String)est[11]));
		peso.setValue(Double.parseDouble(((String)est[12])));
		tallaCamisa.setValue(Integer.parseInt((String)est[13]));
		tallaPantalon.setValue(Integer.parseInt((String)est[14]));
		tallaZapato.setValue(Integer.parseInt((String)est[15]));
	}
	
	
	
	//MODIFICADO
	static public Object[][] getAsObject(Integer nr)
	{
		
		try {
			Statement st = Util.getConection().createStatement();
			String query = "SELECT * FROM sae_reg  WHERE cedula="+nr.toString()+";";
			System.out.println(query);
			ResultSet rs = st.executeQuery(query);
			if(rs.next())
			{
				String cedula = rs.getString("cedula_rep");
				String nombres = rs.getString("nombre_rep");
				String apellidos = rs.getString("apellido_rep");
				String fecha_nac = rs.getString("fecha_nac");
				String edo_civil = rs.getString("edo_civil");
				String parentezco = rs.getString("parentezco");
				String nacionalidad = rs.getString("nac");
				String pais_nac = rs.getString("pais_nac");
				String estado_nac = rs.getString("estado_nac");
				String sexo = rs.getString("sexo");
				String dir = rs.getString("direccion");
				String tipoVivienda = rs.getString("tipo_vivienda");
				String ubicacion = rs.getString("ubicacion");
				String condicion_vivienda = rs.getString("condicion_Vivienda");
				String celular = rs.getString("celular");
				String telefono = rs.getString("tlfn");
				String correo = rs.getString("email");
				String profesion = rs.getString("profesion");
				String lugar_trabajo = rs.getString("lugar_trabajo");
				String ingreso = rs.getString("ingreso");
				
				Object [] rep = {cedula,nombres,apellidos,fecha_nac,edo_civil,parentezco,nacionalidad,
							pais_nac,estado_nac,sexo,dir,tipoVivienda,ubicacion,condicion_vivienda,
							celular,telefono,correo,profesion,lugar_trabajo,ingreso};
				
				String ciEstu = rs.getString("cedula");
				String nivel = rs.getString("grado");
				String seccion = rs.getString("seccion");
				String nombreEst = rs.getString("nombres");
				String apellidoEst = rs.getString("apellidos");
				String celularEst = rs.getString("celular_est");
				String tlfnEst = rs.getString("tlfn_est");
				String correoEst = rs.getString("correo_est");
				String zurdo = rs.getString("zurdo");
				String beca = rs.getString("beca");
				String canaima = rs.getString("canaima");
				String estatura = rs.getString("estatura");
				String peso = rs.getString("peso");
				String tallaCamisa = rs.getString("talla_camisa");
				String tallaPantalon = rs.getString("talla_pantalon");
				String tallaZapato = rs.getString("talla_zapato");
				
				//// AGREGADO NUEVO
				String municipio = "Algun municipio"; // Aqui deberia sacarlo de la BD (rs.getString("municipio"));
				String deporte = "Deporte "; // Aqui  rs.getString("deporte");
				
				Object [] est = {ciEstu,nivel,seccion,nombreEst,apellidoEst,celularEst,tlfnEst,correoEst,zurdo,
						beca,canaima,estatura,peso,tallaCamisa,tallaPantalon,tallaZapato,
						// AGREGADO
						municipio, deporte
						};
				
				Object [][] ret = {rep,est};
				return ret;
				
			}
			Util.getConection().commit();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	
	///// MODIFICADO
	 public Object[][] getCurrentAsObject()
		{
			String cedula = this.cedula.getText();
			String nombres = this.nombres.getText();
			String apellidos = this.apellidos.getText();
			SimpleDateFormat fo = new SimpleDateFormat("yyyy-MM-dd");
		    String fecha_nac = fo.format((Date)this.fecha_nac.getValue());
			String edo_civil = (String)this.edo_civil.getSelectedItem();
			String parentezco = this.parentezco.getText();
			String nacionalidad = this.nacionalidad.getText();
			String pais_nac = this.pais_nac.getText();
			String estado_nac = this.estado_nac.getText();
			String sexo = this.f.isSelected() ? "F" : "M";
			String dir = this.dir.getText();
			String tipoVivienda = this.tipoVivienda.getText();
			String ubicacion = this.ubicacion.getText();
			String condicion_vivienda = this.condicion_vivienda.getText();
			String celular = (String)codCelular.getSelectedItem()+"-"+this.celular.getText();
			String telefono = (String)codTelfn.getSelectedItem()+"-"+this.telefono.getText();
			String correo = this.correo.getText();
			String profesion = this.profesion.getText();
			String lugar_trabajo = this.lugar_trabajo.getText();
			String ingreso = this.ingreso.getText();
			
			Object [] rep = {cedula,nombres,apellidos,fecha_nac,edo_civil,parentezco,nacionalidad,
						pais_nac,estado_nac,sexo,dir,tipoVivienda,ubicacion,condicion_vivienda,
						celular,telefono,correo,profesion,lugar_trabajo,ingreso};
			
			String ciEstu = this.ciEstu.getText();
			String nivel = (String)this.nivel.getSelectedItem();
			String seccion = this.seccion.getText();
			String nombreEst = this.nombreEst.getText();
			String apellidoEst = this.apellidoEst.getText();
			String celularEst = (String)codCelularEst.getSelectedItem()+"-"+this.celularEst.getText();
			String tlfnEst = (String)codTelfnEst.getSelectedItem()+"-"+telefonoEst.getText();
			String correoEst = this.correoEst.getText();
			String zurdo =zurdoY.isSelected() ? "Y" : "N";
			String beca = becaY.isSelected() ? "Y" : "N";
			String canaima = canaimaY.isSelected() ? "Y" : "N";
			String estatura = this.estatura.getValue().toString();
			String peso = this.peso.getValue().toString();
			String tallaCamisa = this.tallaCamisa.getValue().toString();
			String tallaPantalon = this.tallaPantalon.getValue().toString();
			String tallaZapato = this.tallaZapato.getValue().toString();
			//AGREGADO 
			String municipio = this.municipio;
			String deporte = this.deporte;
			///////////
			
			Object [] est = {ciEstu,nivel,seccion,nombreEst,apellidoEst,celularEst,tlfnEst,correoEst,zurdo,
					beca,canaima,estatura,peso,tallaCamisa,tallaPantalon,tallaZapato,
					//AGREGADO
					municipio, deporte};
					/////////
			Object [][] ret = {rep,est};
			return ret;
		}
	
	public void generateXLXS()
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Registro de Estudiantes");
		XSSFRow row = sheet.createRow(0);
		int s = header.length;
		for(int i = 0; i < s; i++)
		{
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(header[i]);
		}
		
		row = sheet.createRow(1);
		row.createCell(0).setCellValue(cedula.getText());
		row.createCell(1).setCellValue(nombres.getText());
		row.createCell(2).setCellValue(apellidos.getText());
		row.createCell(3).setCellValue((Date)fecha_nac.getValue());
		row.createCell(4).setCellValue((String)edo_civil.getSelectedItem());
		row.createCell(5).setCellValue(parentezco.getText());
		row.createCell(6).setCellValue(nacionalidad.getText());
		row.createCell(7).setCellValue(pais_nac.getText());
		row.createCell(8).setCellValue(estado_nac.getText());
		String g = f.isSelected() ? "F" : "M";
		row.createCell(9).setCellValue(g);
		row.createCell(10).setCellValue(dir.getText());
		row.createCell(11).setCellValue(tipoVivienda.getText());
		row.createCell(12).setCellValue(ubicacion.getText());
		row.createCell(13).setCellValue(condicion_vivienda.getText());
		String tlf = (String)codCelular.getSelectedItem()+"-"+celular.getText();
		row.createCell(14).setCellValue(tlf);
		tlf = (String)codTelfn.getSelectedItem()+"-"+telefono.getText();
		row.createCell(15).setCellValue(tlf);
		row.createCell(16).setCellValue(correo.getText());
		row.createCell(17).setCellValue(profesion.getText());
		row.createCell(18).setCellValue(lugar_trabajo.getText());
		row.createCell(19).setCellValue(ingreso.getText());
		
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(result);
			try {
				workbook.write(outputStream);
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public void generateXLS()
	{
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Registro de Estudiantes") ;

		HSSFRow row = sheet.createRow(0);
		int s = header.length;
		for(int i = 0; i < s; i++)
		{
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(header[i]);
		}
		
		row = sheet.createRow(1);
		row.createCell(0).setCellValue(cedula.getText());
		row.createCell(1).setCellValue(nombres.getText());
		row.createCell(2).setCellValue(apellidos.getText());
		row.createCell(3).setCellValue((Date)fecha_nac.getValue());
		row.createCell(4).setCellValue((String)edo_civil.getSelectedItem());
		row.createCell(5).setCellValue(parentezco.getText());
		row.createCell(6).setCellValue(nacionalidad.getText());
		row.createCell(7).setCellValue(pais_nac.getText());
		row.createCell(8).setCellValue(estado_nac.getText());
		String g = f.isSelected() ? "F" : "M";
		row.createCell(9).setCellValue(g);
		row.createCell(10).setCellValue(dir.getText());
		row.createCell(11).setCellValue(tipoVivienda.getText());
		row.createCell(12).setCellValue(ubicacion.getText());
		row.createCell(13).setCellValue(condicion_vivienda.getText());
		String tlf = (String)codCelular.getSelectedItem()+"-"+celular.getText();
		row.createCell(14).setCellValue(tlf);
		tlf = (String)codTelfn.getSelectedItem()+"-"+telefono.getText();
		row.createCell(15).setCellValue(tlf);
		row.createCell(16).setCellValue(correo.getText());
		row.createCell(17).setCellValue(profesion.getText());
		row.createCell(18).setCellValue(lugar_trabajo.getText());
		row.createCell(19).setCellValue(ingreso.getText());
		
		
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(result);
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static GridBagConstraints getConstraints(int x,int y,int width, int height, int fill)
	{
		GridBagConstraints gbc =  new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridheight = height;
		gbc.gridwidth = width;
		gbc.fill = fill;
		gbc.anchor = GridBagConstraints.WEST;
		
		return gbc;
	}
	
	
	public static GridBagConstraints getConstraints(int x,int y,int width, int height)
	{
		return getConstraints(x, y, width, height, GridBagConstraints.HORIZONTAL);
	}
	
}
