package gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import util.Util;

@SuppressWarnings("serial")
public class MainSAE extends JFrame{

	JLabel header;
	JButton create;
	JButton update;
	JButton delete;
	JButton show;
	
	JButton generate;
	
	
	private final String [] format = {"N","Cedula","CedEscolar","Apellidos","Nombres","Grado",
							"Seccion","Sexo","Fecha_Naci","Lugar_Naci","Estad_Naci","EF","CedulaRepr",
							"ApelliRepr","NombreRepr","Parentesco","Direccion","Telefono","Tlf_Sms","Email"}; 
	
	
	public MainSAE(String title)
	{
		super(title);
		JPanel root = new JPanel(new GridBagLayout());
		header = new JLabel();//"<html><b>Registro Escolar Para el SAE</b><html>");
		ImageIcon ic = new ImageIcon("Resources/logoliceo32.jpg");
		header.setIcon(ic);
		//header.setBackground(Color.white);
		//header.setOpaque(true);
		//
		//header.setPreferredSize(new Dimension(100,50));
		header.setBorder(BorderFactory.createEmptyBorder(10,20,20,0));
		root.add(header,Register.getConstraints(0, 0, 2, 1));
		
		create = new JButton("Nuevo Registro",new ImageIcon("Resources/add.png"));
		//create.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		update = new JButton("Actualizar Registro",new ImageIcon("Resources/update.png"));
		//update.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		delete = new JButton("Borrar Registro",new ImageIcon("Resources/delete.png"));
		//delete.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 0));
		generate = new JButton("Exportar a Excel",new ImageIcon("Resources/table.png"));
		//generate.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		show = new JButton("Mostrar Registros");
		actions();
		
		root.add(create,Register.getConstraints(0, 2, 1, 1));
		root.add(update,Register.getConstraints(1, 2, 1, 1));
		root.add(delete,Register.getConstraints(0, 3, 1, 1));
		root.add(generate,Register.getConstraints(1, 3, 1, 1));
		root.add(show,Register.getConstraints(0, 4, 1, 1));
		
		JButton exit = new JButton("Salir", new ImageIcon("Resources/close.png"));
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Util.getConection().close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.exit(0);
				}
				System.exit(0);
			}
		});
		
		root.add(exit,Register.getConstraints(1, 4, 1, 1));
		
		root.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(root);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width-getWidth())/2, (dim.height-getHeight())/2);
		setVisible(true);
	}
	
	
	private void actions()
	{
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						new Register("Nuevo Registro",false);
					}
				});
			}
		});
		
		create.setMnemonic(KeyEvent.VK_N);
		
		generate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				export();
			}
		});
		
		
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nr = JOptionPane.showInputDialog("Ingrese Numero de cedula: ");
				Object [][]obj = Register.getAsObject(Integer.parseInt(nr));
				if(obj != null)
				{
					Register up = new Register("Actualizar registro", true);
					up.loadData(obj[0], obj[1]);
				}
				else
					JOptionPane.showMessageDialog(null, "Cedula no encontrada", "cedula", JOptionPane.ERROR_MESSAGE);
				
			}
		});
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nr = JOptionPane.showInputDialog("Ingrese Numero de cedula: ");
				if(nr == null)
					return;
				try {
					Statement st = Util.getConection().createStatement();
					int c = st.executeUpdate("DELETE FROM sae_reg WHERE cedula="+nr+";");
					if(c == 0)
						JOptionPane.showMessageDialog(null, "Cedula no encontrada", "cedula", JOptionPane.ERROR_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "Registro eliminado", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
				} catch(SQLException ex)
				{
					ex.printStackTrace();
				}
				
			}
		});
		
		show.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ShowRegisters("Registros");
			}
		});
	
	}

	public void export()
	{
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel 2003 (*.xls)","xls");
		chooser.addChoosableFileFilter(filter);
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel 2010 (*.xlsx)","xlsx"));
		int op = chooser.showSaveDialog(null);
		if(op == JFileChooser.APPROVE_OPTION)
		{
			String result = chooser.getSelectedFile().getAbsolutePath();
			String fil = chooser.getFileFilter().getDescription();
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			if(fil.equals("Excel 2003 (*.xls)"))
			{
				result = result + ".xls";
				generateXLS(result);
			}
			else
			{
				result = result + ".xlsx";
				generateXLXS(result);
			}
			setCursor(Cursor.getDefaultCursor());
			Path p = Paths.get(result);
			JOptionPane.showMessageDialog(this, p.getName(p.getNameCount()-1) + " generado !!", "Excel", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	 
	/// MODIFICADO
	// EN DONDE CARRIZO VA LA COLUMMNA MUNICIPIO?
	// SUPUSE QUE VA ANTES DE LOS DATOS DEL REPRESENTANTE, MODIFIQUELA SEGUN
	public void generateXLXS(String result)
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Registro de Estudiantes");
		XSSFRow row = sheet.createRow(0);
		int s = format.length;
		for(int i = 0; i < s; i++)
		{
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(format[i]);
		}
		
		int r = 1;
		Statement st;
		try {
			st = Util.getConection().createStatement();
			ResultSet rs = st.executeQuery("select * from sae_reg order by cedula;");
			while(rs.next())
			{
				System.out.println(r);
				row = sheet.createRow(r);
				row.createCell(0).setCellValue(rs.getString("nac"));
				row.createCell(1).setCellValue(rs.getInt("cedula"));
				row.createCell(2).setCellValue(rs.getString("cod_escolar"));
				row.createCell(3).setCellValue(rs.getString("apellidos"));
				row.createCell(4).setCellValue(rs.getString("nombres"));
				row.createCell(5).setCellValue(rs.getInt("grado"));
				row.createCell(6).setCellValue(rs.getString("seccion"));
				row.createCell(7).setCellValue(rs.getString("sexo"));
				row.createCell(8).setCellValue(rs.getString("fecha_nac"));
				row.createCell(9).setCellValue(rs.getString("lugar_nac"));
				row.createCell(10).setCellValue(rs.getString("estado_nac"));
				row.createCell(11).setCellValue(rs.getString("ef"));
				
				// AGREGADO
				row.createCell(12).setCellValue("PORDEFECTO"); // rs.getString("municipio") o algo asi
				
				// LOS INDICES SE CORRIERON EN UNA POSICION
				row.createCell(13).setCellValue(rs.getInt("cedula_rep"));
				row.createCell(14).setCellValue(rs.getString("apellido_rep"));
				row.createCell(15).setCellValue(rs.getString("nombre_rep"));
				row.createCell(16).setCellValue(rs.getString("parentezco"));
				row.createCell(17).setCellValue(rs.getString("direccion"));
				row.createCell(18).setCellValue(rs.getString("tlfn"));
				row.createCell(19).setCellValue(rs.getString("celular"));
				row.createCell(20).setCellValue(rs.getString("email"));
				r++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
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
	
	
	///MODIFICADO
	public void generateXLS(String result)
	{
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Registro de Estudiantes") ;

		HSSFRow row = sheet.createRow(0);
		int s = format.length;
		for(int i = 0; i < s; i++)
		{
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(format[i]);
		}
		
		int r = 1;
		Statement st;
		try {
			st = Util.getConection().createStatement();
			ResultSet rs = st.executeQuery("select * from sae_reg order by cedula;");
			while(rs.next())
			{
				System.out.println(r);
				row = sheet.createRow(r);
				row.createCell(0).setCellValue(rs.getString("nac"));
				row.createCell(1).setCellValue(rs.getInt("cedula"));
				row.createCell(2).setCellValue(rs.getString("cod_escolar"));
				row.createCell(3).setCellValue(rs.getString("apellidos"));
				row.createCell(4).setCellValue(rs.getString("nombres"));
				row.createCell(5).setCellValue(rs.getInt("grado"));
				row.createCell(6).setCellValue(rs.getString("seccion"));
				row.createCell(7).setCellValue(rs.getString("sexo"));
				row.createCell(8).setCellValue(rs.getString("fecha_nac"));
				row.createCell(9).setCellValue(rs.getString("lugar_nac"));
				row.createCell(10).setCellValue(rs.getString("estado_nac"));
				row.createCell(11).setCellValue(rs.getString("ef"));
				// AGREGADO
				row.createCell(12).setCellValue("PORDEFECTO"); // rs.getString("municipio") o algo asi
				
				// LOS INDICES SE CORRIERON EN UNA POSICION
				row.createCell(13).setCellValue(rs.getInt("cedula_rep"));
				row.createCell(14).setCellValue(rs.getString("apellido_rep"));
				row.createCell(15).setCellValue(rs.getString("nombre_rep"));
				row.createCell(16).setCellValue(rs.getString("parentezco"));
				row.createCell(17).setCellValue(rs.getString("direccion"));
				row.createCell(18).setCellValue(rs.getString("tlfn"));
				row.createCell(19).setCellValue(rs.getString("celular"));
				row.createCell(20).setCellValue(rs.getString("email"));
				r++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		
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
	public static void main(String[] args)
	{
		try{
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		new MainSAE("Registro para SAE");
	}
}
