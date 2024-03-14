package net.tfobz.vokabeltrainer.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.JFileChooser;

import net.tfobz.vokabeltrainer.model.Fach;
import net.tfobz.vokabeltrainer.model.Karte;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;
import net.tfobz.vokabeltrainer.utils.AppSettings;
import net.tfobz.vokabeltrainer.utils.CustomTableModel;

public class FensterWoerterBearbeiten extends JFrame {
	private CustomTableModel table_model;
	private JTable table;
	private JScrollPane scroll_pane;
	private JButton abbrechen_btn;
	private JButton speichern_btn;
	private int index_selected_kartei;
	private JPanel button_panel = new JPanel();
	private JButton exportieren_btn;
	private JButton importieren_btn;
	private Lernkartei lernkartei;

	public FensterWoerterBearbeiten(int index_selected_kartei) {
		this.setSize(800, 550);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Woerter bearbeiten");
		this.setLayout(new BorderLayout());
		this.setMinimumSize(new Dimension(700, 500));
		this.setMaximumSize(new Dimension(1000, 800));

		this.index_selected_kartei = index_selected_kartei;

		// Create the table model with three columns and an empty row: src-lang, dest-lang, fachnummer
		if (AppSettings.getLernkartei() == null) {
			this.lernkartei = VokabeltrainerDB.getLernkartei(index_selected_kartei);
			AppSettings.setLernkartei(lernkartei);
		}
		else {
			this.lernkartei = AppSettings.getLernkartei();
		}
		this.table_model = new CustomTableModel(new Object[] {lernkartei.getWortEinsBeschreibung(), lernkartei.getWortZweiBeschreibung(), "Fachnummer"}, 1);
		this.table = new JTable(this.table_model);

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

		// add all words from the selected kartei to the table
		int db_index_lernkartei = this.lernkartei.getNummer();
		for (Fach fach : VokabeltrainerDB.getFaecher(db_index_lernkartei)) {
			// add all words from the fach to the table
			int db_index_fach = fach.getNummer();
			for (Karte karte : VokabeltrainerDB.getKarten(db_index_fach)) {
				String sprache1 = karte.getWortEins();
				String sprache2 = karte.getWortZwei();
				int fachnummer = VokabeltrainerDB.getFaecher(db_index_lernkartei).indexOf(fach) + 1;
				table_model.addRow(new Object[] { sprache1, sprache2, fachnummer });
			}
		}

		// Add a listener to the table to detect changes
		this.table.getModel().addTableModelListener(e -> {
			// if in every cell is a value, add a new row
			SwingUtilities.invokeLater(() -> {
				boolean add_new_row = true;
				for (int i = 0; i < table_model.getRowCount(); i++) {
					for (int j = 0; j < table_model.getColumnCount() - 1; j++) {
						if (table_model.getValueAt(i, j) == null || table_model.getValueAt(i, j).equals("")) {
							add_new_row = false;
						}
					}
				}
				if (add_new_row) {
					table_model.addRow(new Object[] { "", "", "" });
				}
			});
		});

		// Create a scroll pane for the table
		this.scroll_pane = new JScrollPane(this.table);
		this.add(this.scroll_pane, BorderLayout.CENTER);

		// Create panel for buttons
		this.button_panel = new JPanel();

		// exportieren button
		this.exportieren_btn = new JButton("Exportieren");
		this.exportieren_btn.addActionListener((ActionEvent evt) -> {
			exportieren_btnActionPerformed(evt);
		});
		this.button_panel.add(exportieren_btn);

		// importieren button
		this.importieren_btn = new JButton("Importieren");
		this.importieren_btn.addActionListener((ActionEvent evt) -> {
			importieren_btnActionPerformed(evt);
		});
		this.button_panel.add(importieren_btn);

		// abbrechen button
		this.abbrechen_btn = new JButton("Abbrechen");
		this.abbrechen_btn.addActionListener((ActionEvent evt) -> {
			abbrechen_btnActionPerformed(evt);
		});
		this.button_panel.add(abbrechen_btn);

		// speichern button
		this.speichern_btn = new JButton("Speichern");
		this.speichern_btn.addActionListener((ActionEvent evt) -> {
			speichern_btnActionPerformed(evt);
		});
		this.button_panel.add(speichern_btn);

		// add button panel to the frame
		this.add(this.button_panel, BorderLayout.SOUTH);

		if (AppSettings.isDarkMode()) {
			applyDarkModeStyles();
		} else {
			applyLightModeStyles();
		}
	}

	private void abbrechen_btnActionPerformed(ActionEvent evt) {
		AppSettings.setLernkartei(null);
		FensterKarteiBearbeiten karteiBearbeiten = new FensterKarteiBearbeiten(this.index_selected_kartei);
		karteiBearbeiten.setVisible(true);
		karteiBearbeiten.setLocation(getLocation());
		dispose();
	}

	private void speichern_btnActionPerformed(ActionEvent evt) {
		if (AppSettings.getLernkartei() == null) {
			AppSettings.setLernkartei(VokabeltrainerDB.getLernkartei(index_selected_kartei));
		}
		Lernkartei lernkartei = AppSettings.getLernkartei();
		CustomTableModel model = (CustomTableModel) table.getModel();
		int rowCount = model.getRowCount();

		// get all karten from the database and save them in a list
		ArrayList<Karte> db_karten = new ArrayList<>();
		for (Fach fach : VokabeltrainerDB.getFaecher(lernkartei.getNummer())) {
			for (Karte karte : VokabeltrainerDB.getKarten(fach.getNummer())) {
				db_karten.add(karte);
			}
		}

		// get all karten from the table and save them in a list
		ArrayList<Karte> table_karten = new ArrayList<>();
		for (int i = 0; i < rowCount; i++) {
			String sprache1 = (String) model.getValueAt(i, 0);
			String sprache2 = (String) model.getValueAt(i, 1);

			if (sprache1 == null || sprache2 == null || sprache1.equals("") || sprache2.equals("") || sprache1.equals(" ") || sprache2.equals(" "))
				continue;

			// Create a new Karte object
			Karte newKarte = new Karte(-1, sprache1, sprache2, lernkartei.getGrossKleinschreibung(), false);
			table_karten.add(newKarte);
		}

		// compare the two lists and add the new karten to the database
		for (Karte karte : table_karten) {
			if (!db_karten.contains(karte)) {
				
				int result = VokabeltrainerDB.hinzufuegenKarte(index_selected_kartei, karte);
				switch (result) {
					case -1:
						System.out.println(" sprache1: " + karte.getWortEins() + " sprache2: " + karte.getWortZwei() + " grossKleinschreibung: " + lernkartei.getGrossKleinschreibung());
						JOptionPane.showMessageDialog(null, "Datenbankfehler aufgetreten");
						break;
					case -2:
						JOptionPane.showMessageDialog(null, "Karte nicht vollständig");
						break;
					case -3:
						JOptionPane.showMessageDialog(null, "Lernkartei nicht existiert");
						break;
					case -4:
						JOptionPane.showMessageDialog(null, "Kein Fach in der Lernkartei existiert");
						break;
					case -5: // existiert schon
					default:
						break;
				}
			}
		}

		// compare the two lists and remove the karten from the database that are not in the table
		for (Karte karte : db_karten) {
			if (!table_karten.contains(karte)) {

				int result = VokabeltrainerDB.loeschenKarte(karte.getNummer());
				switch (result) {
					case -1:
						System.out.println(" sprache1: " + karte.getWortEins() + " sprache2: " + karte.getWortZwei() + " grossKleinschreibung: " + lernkartei.getGrossKleinschreibung());
						JOptionPane.showMessageDialog(null, "Datenbankfehler aufgetreten");
						break;
					case -2:
						JOptionPane.showMessageDialog(null, "Karte nicht vollständig");
						break;
					case -3:
						JOptionPane.showMessageDialog(null, "Lernkartei nicht existiert");
						break;
					case -4:
						JOptionPane.showMessageDialog(null, "Kein Fach in der Lernkartei existiert");
						break;
					case -5: // existiert schon
					default:
						break;
				}
			}
		}

		// go back to the previous window
		FensterKarteiBearbeiten karteiBearbeiten = new FensterKarteiBearbeiten(this.index_selected_kartei);
		karteiBearbeiten.setVisible(true);
		karteiBearbeiten.setLocation(getLocation());
		dispose();
	}

	private void exportieren_btnActionPerformed(ActionEvent evt) {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String path = selectedFile.getAbsolutePath();
			int retVok = VokabeltrainerDB.exportierenKarten(this.index_selected_kartei, path, true);
			
			switch (retVok) {
				case -1:
					JOptionPane.showMessageDialog(null, "Datenbankfehler oder Schreibfehler in Datei aufgetreten");
					break;
				case -3:
					JOptionPane.showMessageDialog(null, "Lernkartei nicht existiert");
					break;
				default: // Erfolgreich
					break;
			}
		}
	}

	private void importieren_btnActionPerformed(ActionEvent evt) {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String path = selectedFile.getAbsolutePath();
			int retVok = VokabeltrainerDB.importierenKarten(this.index_selected_kartei, path);

			switch (retVok) {
				case -1:
					JOptionPane.showMessageDialog(null, "Importierfehler aufgetreten");
					break;
				case -2:
					JOptionPane.showMessageDialog(null, "Datei nicht gefunden");
					break;
				case -3:
					JOptionPane.showMessageDialog(null, "Lernkartei nicht existiert");
					break;
				default: // Erfolgreich
					// refresh the table
					this.table_model.setRowCount(0);
					for (Fach fach : VokabeltrainerDB.getFaecher(this.index_selected_kartei)) {
						// add all words from the fach to the table
						int db_index_fach = fach.getNummer();
						for (Karte karte : VokabeltrainerDB.getKarten(db_index_fach)) {
							String sprache1 = karte.getWortEins();
							String sprache2 = karte.getWortZwei();
							int fachnummer = VokabeltrainerDB.getFaecher(this.index_selected_kartei).indexOf(fach) + 1;
							table_model.addRow(new Object[] { sprache1, sprache2, fachnummer });
						}
					}
					break;
			}
		}
	}

	private void applyDarkModeStyles() {
		this.getContentPane().setBackground(new Color(62, 63, 65));

		// Table
		this.table.getColumnModel().getColumn(0).setHeaderValue("<html><b><font size=+0.5 color = 'white'>" + AppSettings.getLernkartei().getWortEinsBeschreibung() + "</font></b></html>");
		this.table.getColumnModel().getColumn(1).setHeaderValue("<html><b><font size=+0.5 color = 'white'>" + AppSettings.getLernkartei().getWortZweiBeschreibung() + "</font></b></html>");
		this.table.getColumnModel().getColumn(2).setHeaderValue("<html><b><font size=+0.5 color = 'white'>Fachnummer</font></b></html>");
		this.table.getTableHeader().setBackground(new Color(62, 63, 65));
		table.setForeground(Color.WHITE);
		table.setBackground(new Color(62, 63, 65)); // Dunkelgrau

		scroll_pane.setForeground(Color.WHITE);
		scroll_pane.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		this.scroll_pane.getViewport().setBackground(new Color(62, 63, 65));

		// Buttons
		abbrechen_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		abbrechen_btn.setBorder(new LineBorder(Color.WHITE, 2));
		abbrechen_btn.setForeground(Color.WHITE);

		speichern_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		speichern_btn.setBorder(new LineBorder(Color.WHITE, 2));
		speichern_btn.setForeground(Color.WHITE);

		exportieren_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		exportieren_btn.setBorder(new LineBorder(Color.WHITE, 2));
		exportieren_btn.setForeground(Color.WHITE);

		importieren_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		importieren_btn.setBorder(new LineBorder(Color.WHITE, 2));
		importieren_btn.setForeground(Color.WHITE);

		// Panel
		button_panel.setBackground(new Color(62, 63, 65)); // Dunkelgrau
	}

	private void applyLightModeStyles() {
		this.getContentPane().setBackground(new Color(241, 241, 241)); // Dunkles Schwarz

		// Table

		this.table.getColumnModel().getColumn(0).setHeaderValue("<html><b><font size=+0.5 color = 'black'>" + AppSettings.getLernkartei().getWortEinsBeschreibung() + "</font></b></html>");
		this.table.getColumnModel().getColumn(1).setHeaderValue("<html><b><font size=+0.5 color = 'black'>" + AppSettings.getLernkartei().getWortZweiBeschreibung() + "</font></b></html>");
		this.table.getColumnModel().getColumn(2).setHeaderValue("<html><b><font size=+0.5 color = 'black'>Fachnummer</font></b></html>");
		this.table.getTableHeader().setBackground(new Color(241, 241, 241));
		table.setForeground(Color.BLACK);
		table.setBackground(new Color(241, 241, 241)); // Dunkelgrau

		scroll_pane.setForeground(Color.BLACK);
		scroll_pane.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		this.scroll_pane.getViewport().setBackground(new Color(241, 241, 241));

		// Buttons
		abbrechen_btn.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		abbrechen_btn.setBorder(new LineBorder(Color.BLACK, 2));
		abbrechen_btn.setForeground(Color.BLACK);

		speichern_btn.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		speichern_btn.setBorder(new LineBorder(Color.BLACK, 2));
		speichern_btn.setForeground(Color.BLACK);

		exportieren_btn.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		exportieren_btn.setBorder(new LineBorder(Color.BLACK, 2));
		exportieren_btn.setForeground(Color.BLACK);

		importieren_btn.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		importieren_btn.setBorder(new LineBorder(Color.BLACK, 2));
		importieren_btn.setForeground(Color.BLACK);

		// Panel
		button_panel.setBackground(new Color(241, 241, 241)); // Dunkelgrau
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
