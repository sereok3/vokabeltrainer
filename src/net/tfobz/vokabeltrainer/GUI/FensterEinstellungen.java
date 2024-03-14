package net.tfobz.vokabeltrainer.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;
import net.tfobz.vokabeltrainer.utils.AppSettings;

public class FensterEinstellungen extends JFrame {
	private DefaultTableModel table_model;
	private JTable table;
	private JScrollPane scroll_pane;
	private JPanel button_panel;
	private JButton karteiHinzufuegen_btn;
	private JButton karteiBearbeiten_btn;
	private JButton karteiLoeschen_btn;
	private JButton zuruecksetzen_btn;
	private JPanel rightButton_panel;
	private JButton home_btn;
	private int home_btn_width = 40;
	private int home_btn_height = 40;
	private ImageIcon home_icon;
	private JPanel topLeft_panel;
	private List<Lernkartei> lernkarteien;
	
	public FensterEinstellungen() {
		this.setSize(800, 550);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Einstellungen");
		this.setMinimumSize(new Dimension(700, 500));
		this.setMaximumSize(new Dimension(1000, 800));
		this.setLayout(new BorderLayout());
		
		this.lernkarteien = VokabeltrainerDB.getLernkarteien();

		// Create the table model with one column
		this.table_model = new DefaultTableModel();
		this.table_model.addColumn("<html><b><font size=+2>Karteien</font></b></html>"); // karteien Column with BOLD
																							// font + bigger font size
		this.table = new JTable(this.table_model);
		this.table.getTableHeader().setReorderingAllowed(false); // Disable column reordering
		this.table.setDefaultEditor(Object.class, null); // disable editing of the table
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Set the selection mode to single selection
		this.scroll_pane = new JScrollPane(this.table);

		// Add a ListSelectionListener to the table
		this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// Check if the selection is not empty
				if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
					// Enable the "Kartei bearbeiten" button
					karteiBearbeiten_btn.setEnabled(true);
					// Enable the "Kartei loeschen" button
					karteiLoeschen_btn.setEnabled(true);
				} else {
					// Disable the "Kartei bearbeiten" button
					karteiBearbeiten_btn.setEnabled(false);
					// Disable the "Kartei loeschen" button
					karteiLoeschen_btn.setEnabled(false);
				}
			}
		});

		// make table rows bigger + center text + Font size
		this.table.setRowHeight(30);
		this.table.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 16));
		// center text
		this.table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
			@Override
			public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				final java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
						row, column);
				if (c instanceof javax.swing.JLabel) {
					((javax.swing.JLabel) c).setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
				}
				return c;
			}
		});

		// load all Karteien from the database and add them to the table multithreaded
		ArrayList<Integer> karteiIDs = new ArrayList<>();
		for (Lernkartei kartei : this.lernkarteien)
			karteiIDs.add(kartei.getNummer());
		for (int karteiID : karteiIDs)
			this.table_model.addRow(new Object[] { VokabeltrainerDB.getLernkartei(karteiID).getBeschreibung() });

		// panel for buttons
		this.button_panel = new JPanel(new BorderLayout());

		// Create an inner panel for the other buttons on the right
		this.rightButton_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		// create a panel for the top-left buttons
		this.topLeft_panel = new JPanel(new BorderLayout());

		// Create a "Reset" button on the left
		this.zuruecksetzen_btn = new JButton("Zuruecksetzen");
		this.zuruecksetzen_btn.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Custom button text
				Object[] options = { "Ja", "Nein" };
				int response = JOptionPane.showOptionDialog(null, "Willst du wirklich alle Karteien loeschen?",
						"Zuruecksetzen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, // do not use a
																										// custom Icon
						options, // the titles of buttons
						options[0]); // default button title

				if (response == JOptionPane.YES_OPTION) { // if user clicks "Ja"
					VokabeltrainerDB.loeschenTabellen();
					// Erstellen der Tabellen falls nicht vorhanden
					VokabeltrainerDB.erstellenTabellen();
					// Hinzufuegen von Testdaten
					VokabeltrainerDB.hinzufuegenTestdaten();

					// go to FensterStartSeite
					SwingUtilities.invokeLater(() -> {
						FensterStartSeite startSeite = new FensterStartSeite();
						startSeite.setVisible(true);
						startSeite.setLocation(getLocation());
						dispose();
					});
				}
			}
		});
		this.zuruecksetzen_btn.setBackground(new Color(207, 42, 39));
		this.zuruecksetzen_btn.setForeground(Color.WHITE);
		this.zuruecksetzen_btn.setPreferredSize(new Dimension(150, 30));
		this.zuruecksetzen_btn.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				karteiLoeschen_btnActionPerformed(e);
			}
		});
		this.button_panel.add(this.zuruecksetzen_btn, BorderLayout.WEST);

		// create a "Home" button on the top left, icon inside icons/home.png
		this.home_btn = new JButton("Home");
		this.home_btn.setContentAreaFilled(false); // Set background to be transparent
		this.home_btn.setBorderPainted(false); // Remove button border
		this.home_btn.setFocusPainted(false); // Remove button focus border
		this.home_icon = new ImageIcon(getClass().getResource("icons/home.png"));
		Image originalImage = this.home_icon.getImage();
		Image scaledImage = originalImage.getScaledInstance(this.home_btn_width, this.home_btn_height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		this.home_btn.setIcon(scaledIcon);
		this.home_btn.setPreferredSize(new Dimension(this.home_btn_width + 5, this.home_btn_height));
		int padding = 15; // You can adjust this value as needed
		this.home_btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, padding, 0, 0));
		this.home_btn.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					FensterStartSeite startSeite = new FensterStartSeite();
					startSeite.setVisible(true);
					startSeite.setLocation(getLocation());
					dispose();
				});
			}
		});
		this.topLeft_panel.add(this.home_btn, BorderLayout.WEST);

		// Create an "Add" button
		this.karteiHinzufuegen_btn = new JButton("Kartei hinzufuegen");
		this.karteiHinzufuegen_btn.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				karteiHinzufuegen_btnActionPerformed(e);
			}
		});
		this.rightButton_panel.add(this.karteiHinzufuegen_btn);

		// Create an "Edit" button, button should be disabled if no row is selected
		this.karteiBearbeiten_btn = new JButton("Kartei bearbeiten");
		this.karteiBearbeiten_btn.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				karteiBearbeiten_btnActionPerformed(e);
			}
		});
		this.karteiBearbeiten_btn.setEnabled(false);
		this.rightButton_panel.add(this.karteiBearbeiten_btn);

		// Create a "Delete" button, button should be disabled if no row is selected
		this.karteiLoeschen_btn = new JButton("Kartei loeschen");
		this.karteiLoeschen_btn.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				karteiLoeschen_btnActionPerformed(e);
			}
		});
		this.karteiLoeschen_btn.setEnabled(false);
		this.rightButton_panel.add(this.karteiLoeschen_btn);

		// Add the inner panel to the button panel
		this.button_panel.add(rightButton_panel, BorderLayout.CENTER);

		// Add components to the main frame
		this.add(this.scroll_pane, BorderLayout.CENTER);
		this.add(this.button_panel, BorderLayout.SOUTH);
		this.add(this.topLeft_panel, BorderLayout.NORTH);

		if(AppSettings.isDarkMode()) {
			applyDarkModeStyles();
		} else {
			applyLightModeStyles();
		}
	}

	private void karteiHinzufuegen_btnActionPerformed(ActionEvent evt) {
		FensterKarteiHinzufuegen karteiHinzufuegen = new FensterKarteiHinzufuegen();
		karteiHinzufuegen.setVisible(true);
		karteiHinzufuegen.setLocation(getLocation());
		dispose();
	}

	private void karteiBearbeiten_btnActionPerformed(ActionEvent evt) {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			ArrayList<Integer> karteiIDs = new ArrayList<>();
			for (Lernkartei kartei : this.lernkarteien)
				karteiIDs.add(kartei.getNummer());
			int karteiID = karteiIDs.get(selectedRow);
			FensterKarteiBearbeiten karteiBearbeiten = new FensterKarteiBearbeiten(karteiID);
			karteiBearbeiten.setVisible(true);
			karteiBearbeiten.setLocation(getLocation());
			dispose();
		}
	}

	private void karteiLoeschen_btnActionPerformed(ActionEvent evt) {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			ArrayList<Integer> karteiIDs = new ArrayList<>();
			for (Lernkartei kartei : this.lernkarteien)
				karteiIDs.add(kartei.getNummer());
			int karteiID = karteiIDs.get(selectedRow);
			int retVok = VokabeltrainerDB.loeschenLernkartei(karteiID);
			if (retVok == -1) // Message box that an error occurred
				JOptionPane.showMessageDialog(null, "Fehler beim Loeschen der Kartei", "Fehler", JOptionPane.ERROR_MESSAGE);
			else {
				this.table_model.removeRow(selectedRow);
				this.lernkarteien = VokabeltrainerDB.getLernkarteien();
			}
		}
	}

	// darkmode
	private void applyDarkModeStyles() {

		// Hintergrund
		this.getContentPane().setBackground(new Color(62, 63, 65)); // Dunkles Schwarz

		// Tabelle
		this.table.setBackground(new Color(62, 63, 65)); // Dunkleres Grau
		this.table.setForeground(Color.WHITE);

		this.table.getColumnModel().getColumn(0)
				.setHeaderValue("<html><b><font size=+2 color = 'white'>Karteien</font></b></html>");	
		this.table.getTableHeader().setBackground(new Color(62, 63, 65));
		
		this.scroll_pane.getViewport().setBackground(new Color(62, 63, 65));
		
		this.scroll_pane.setBackground(new Color(62, 63, 65)); // Dunkles Schwarz

		// Button-Panel
		this.button_panel.setBackground(new Color(62, 63, 65)); // Dunkles Schwarz

		// Buttons 
		this.karteiHinzufuegen_btn.setBackground(new Color(62, 63, 65)); // Dunkleres Grau
		this.karteiHinzufuegen_btn.setForeground(Color.WHITE);

		this.karteiBearbeiten_btn.setBackground(new Color(62, 63, 65)); // Dunkleres Grau
		this.karteiBearbeiten_btn.setForeground(Color.WHITE);

		this.karteiLoeschen_btn.setBackground(new Color(62, 63, 65)); // Dunkleres Grau
		this.karteiLoeschen_btn.setForeground(Color.WHITE);

		// Right Button Panel
		this.rightButton_panel.setBackground(new Color(62, 63, 65)); // Dunkles Schwarz

		// Top Left Panel
		this.topLeft_panel.setBackground(new Color(62, 63, 65)); // Dunkles Schwarz
	}
 
	// lightmode
	private void applyLightModeStyles() {
		this.getContentPane().setBackground(new Color(241, 241, 241)); // Dunkles Schwarz

		// Tabelle
		this.table.setBackground(new Color(241, 241, 241)); // Dunkleres Grau
		this.table.setForeground(Color.BLACK);

		this.table.getColumnModel().getColumn(0)
				.setHeaderValue("<html><b><font size=+2 color = 'black'>Karteien</font></b></html>");	
		this.table.getTableHeader().setBackground(new Color(241, 241, 241));
		
		this.scroll_pane.getViewport().setBackground(new Color(241, 241, 241));
		
		this.scroll_pane.setBackground(new Color(241, 241, 241)); // Dunkles Schwarz

		// Button-Panel
		this.button_panel.setBackground(new Color(241, 241, 241)); // Dunkles Schwarz

		// Buttons 
		this.karteiHinzufuegen_btn.setBackground(new Color(241, 241, 241)); // Dunkleres Grau
		this.karteiHinzufuegen_btn.setForeground(Color.BLACK);
		this.karteiHinzufuegen_btn.setBorder(new LineBorder(Color.BLACK, 2));

		this.karteiBearbeiten_btn.setBackground(new Color(241, 241, 241)); // Dunkleres Grau
		this.karteiBearbeiten_btn.setForeground(Color.BLACK);
		this.karteiBearbeiten_btn.setBorder(new LineBorder(Color.BLACK, 2));

		this.karteiLoeschen_btn.setBackground(new Color(241, 241, 241)); // Dunkleres Grau
		this.karteiLoeschen_btn.setForeground(Color.BLACK);
		this.karteiLoeschen_btn.setBorder(new LineBorder(Color.BLACK, 2));

		// Right Button Panel
		this.rightButton_panel.setBackground(new Color(241, 241, 241)); // Dunkles Schwarz

		// Top Left Panel
		this.topLeft_panel.setBackground(new Color(241, 241, 241));
	}

	/*
	 * This is a fix to prevent the window from being resized larger than Because
	 * this.setMaximumSize() doesn't work !!!
	 */
	@Override
	public void paint(Graphics g) {
		Dimension d = getSize();
		Dimension m = getMaximumSize();
		boolean resize = d.width > m.width || d.height > m.height;

		d.width = Math.min(m.width, d.width);
		d.height = Math.min(m.height, d.height);

		if (resize) {
			Point p = getLocation();
			setVisible(false);
			setSize(d);
			setLocation(p);
			setVisible(true);
		}
		super.paint(g);
	}
}