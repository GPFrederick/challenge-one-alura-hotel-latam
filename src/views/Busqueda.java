package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controller.BoarderController;
import controller.BookingController;
import model.Boarders;
import model.Booking;
import utils.Util;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;

@SuppressWarnings("serial")
public class Busqueda extends JFrame implements ChangeListener {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	private BookingController bookingController;
	private BoarderController boarderController;
	private TableRowSorter<DefaultTableModel> tableRowSorter;
	private boolean isTbHuespedes;
	private boolean isTbReservas;
	private int selectedIndex;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				if (isTbHuespedes) {
					filterBy(selectedIndex);
				} else {
					filterBy(selectedIndex);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		panel.addChangeListener(this);
		contentPane.add(panel);
		

		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		tbReservas.setAutoCreateRowSorter(true);
		tableRowSorter = new TableRowSorter<>(modelo);
		tbReservas.setRowSorter(tableRowSorter);
		loadBookingTable();
		scroll_table.setVisible(true);
		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		tbHuespedes.setAutoCreateRowSorter(true);
		tableRowSorter = new TableRowSorter<>(modeloHuesped);
		tbHuespedes.setRowSorter(tableRowSorter);
		loadBoardersTable();
		scroll_tableHuespedes.setVisible(true);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JButton btnEditar = new JButton();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedIndex == 0) {
					updateBooking();
					clearBookingTable();
					loadBookingTable();
				} else {
					updateBoarder();
					clearBookingTable();
					loadBoardersTable();
				}
				
			}
		});
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JButton btnEliminar = new JButton();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnEliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedIndex == 0) {
					deleteBooking();
					clearBookingTable();
					loadBookingTable();
				} else {
					deleteBoarder();
					clearBoardersTable();
					loadBoardersTable();
				}
			}
		});
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}

	//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
	}
	
	public void loadBookingTable() {
		bookingController = new BookingController();
		var bookingList = bookingController.read();
		
		bookingList.forEach(booking -> modelo.addRow(
				new Object[] {
						booking.getId(),
						booking.getDateIn(),
						booking.getDateOut(),
						booking.getPrice(),
						booking.getMethodOfPayment()
				}));
	}
	
	public void loadBoardersTable() {
		boarderController = new BoarderController();
		var boardersList = boarderController.read();
		
		boardersList.forEach(boarder -> modeloHuesped.addRow(
				new Object[] {
						boarder.getId(),
						boarder.getName(),
						boarder.getLastName(),
						boarder.getDateOfBirth(),
						boarder.getCitizenship(),
						boarder.getPhoneNumber(),
						boarder.getIdBooking()
				}));
	}
	
	public void filterBy(int index) {
		try {
			switch (index) {
				case 0:
					tableRowSorter.setRowFilter(RowFilter.regexFilter(txtBuscar.getText(), 0));
					break;
				case 1:
					tableRowSorter.setRowFilter(RowFilter.regexFilter(txtBuscar.getText(), 2));
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
		
		selectedIndex = tabbedPane.getSelectedIndex();
		if (selectedIndex == 1) {
			isTbHuespedes = true;
			isTbReservas = false;
		} else { 
			isTbReservas = true;
			isTbHuespedes = false;
		}
	}
	
	public void updateBooking() {
		bookingController = new BookingController();
		if (isRowSelected(selectedIndex)) {
			JOptionPane.showMessageDialog(null, "Por favor elije un item");
			return;
		}
		
		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
				.ifPresentOrElse(file -> {
					var booking = new Booking(
							Integer.parseInt(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString()),
							Date.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString()),
							Date.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString()),
							Util.priceCalculation(
									Util.numberOfDays(
											modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString(), 
											modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString())),			
							modelo.getValueAt(tbReservas.getSelectedRow(), 4).toString());
					int rowModified;
					rowModified = bookingController.update(booking);
					
					JOptionPane.showMessageDialog(null, String.format("%d item modificado con exito!", rowModified));
				}, () -> JOptionPane.showMessageDialog(null, "Por favor elije un item"));
	}
	
	public void updateBoarder() {
		boarderController = new BoarderController();
		
		if (isRowSelected(selectedIndex)) {
			JOptionPane.showMessageDialog(null, "Por favor elije un item");
			return;
		}
		
		Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
				.ifPresentOrElse(file -> {
					var boarder = new Boarders(
							Integer.parseInt(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString()),
							modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1).toString(),
							modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2).toString(),
							Date.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString()),
							modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4).toString(),
							modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5).toString(),
							Integer.parseInt(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString()));
					int rowModified;
					rowModified = boarderController.update(boarder);
					
					JOptionPane.showMessageDialog(null, String.format("%d item modificado con exito!", rowModified));
				}, () -> JOptionPane.showMessageDialog(null, "Por favor elije un item"));
	}
	
	public void deleteBooking() {
		if (isRowSelected(selectedIndex)) {
			JOptionPane.showMessageDialog(null, "Por favor seleccione un item");
			return;
		}
		
		int res = JOptionPane.showOptionDialog(
				null, 
				"Esta seguro de eliminar la reserva?", 
				"Hey!", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				new Object[] {"Si", "No"},
				null
				);
		if (res == JOptionPane.YES_OPTION) {
			Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
					.ifPresentOrElse(row -> {
						Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());	
						int deleted;
						deleted = this.bookingController.delete(id);
						
						modelo.removeRow(tbReservas.getSelectedRow());
						
						JOptionPane.showMessageDialog(null, String.format("%s item eliminado con exito!", deleted));
					}, () -> JOptionPane.showMessageDialog(null, "Por favor elija un item"));
		}
	}
	
	public void deleteBoarder() {
		if (isRowSelected(selectedIndex)) {
			JOptionPane.showMessageDialog(null, "Por favor seleccione un item");
			return;
		}
		
		
		int res = JOptionPane.showOptionDialog(
				null, 
				"Esta seguro de eliminar al huesped?", 
				"Hey!", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				new Object[] {"Si", "No"},
				null
				);
		
		if (res == JOptionPane.YES_OPTION) {
			Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
					.ifPresentOrElse(row -> {
						Integer boarderId = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
						Integer bookingId = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());
						int boarderDeleted;
						boarderDeleted = this.boarderController.delete(boarderId, bookingId);
						
						modeloHuesped.removeRow(tbHuespedes.getSelectedRow());
				
						JOptionPane.showMessageDialog(null, String.format("%s item eliminado con exito!", boarderDeleted));
					}, () -> JOptionPane.showMessageDialog(null, "Por favor seleccione un item"));
		}
	}
	
	private boolean isRowSelected(int tabSelected) {
		if (tabSelected == 0) {
			return tbReservas.getSelectedRowCount() == 0 || tbReservas.getSelectedColumnCount() == 0;
		} else {
			return tbHuespedes.getSelectedRowCount() == 0 || tbHuespedes.getSelectedColumnCount() == 0;
		}
	}
	
	public void clearBookingTable() {
		modelo.getDataVector().clear();
	}
	
	public void clearBoardersTable() {
		modeloHuesped.getDataVector().clear();
	}
}
