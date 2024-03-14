package net.tfobz.vokabeltrainer.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;

import net.tfobz.vokabeltrainer.model.Fach;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;
import net.tfobz.vokabeltrainer.utils.AppSettings;
import net.tfobz.vokabeltrainer.utils.DynamicHelper;

public class FensterKarteiHinzufuegen extends JFrame {
	private JLabel titel_lbl;
	private DynamicHelper titel_lbl_dh;
	private JLabel beschreibung_lbl;
	private DynamicHelper beschreibung_lbl_dh;
	private JTextField beschreibung_txt;
	private DynamicHelper beschreibung_txt_dh;
	private JLabel errinerung_lbl;
	private DynamicHelper errinerung_lbl_dh;
	private JSpinner erinnerung_spinner;
	private DynamicHelper erinnerung_spinner_dh;
	private JLabel tage_lbl;
	private DynamicHelper tage_lbl_dh;
	private JComboBox<String> sprachSrc_box;
	private DynamicHelper sprachSrc_box_dh;
	private JComboBox<String> sprachDest_box;
	private DynamicHelper sprachDest_box_dh;
	private JLabel sprachSrc_lbl;
	private DynamicHelper sprachSrc_lbl_dh;
	private JLabel sprachDest_lbl;
	private DynamicHelper sprachDest_lbl_dh;
	private JButton flag1_btn;
	private DynamicHelper flag1_btn_dh;
	private ImageIcon flag1_icon;
	private JButton flag2_btn;
	private DynamicHelper flag2_btn_dh;
	private ImageIcon flag2_icon;
	private JCheckBox gross_kleinschreibung_cb;
	private DynamicHelper gross_kleinschreibung_cb_dh;
	private JButton abbrechen_btn;
	private DynamicHelper abbrechen_btn_dh;
	private JButton hinzufuegen_btn;
	private DynamicHelper hinzufuegen_btn_dh;

	public FensterKarteiHinzufuegen() {
		this.setSize(800, 550);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Kartei hinzufuegen");
		this.setLayout(null);
		this.setMinimumSize(new Dimension(700, 500));
		this.setMaximumSize(new Dimension(1000, 800));

		// Title
		this.titel_lbl = new JLabel("Lernkartei hinzufuegen");
		this.titel_lbl_dh = new DynamicHelper(32, 6, 49, 10, this.getWidth(), this.getHeight(), 880, 90,
				DynamicHelper.Location.CENTER);
		this.titel_lbl.setBounds(this.titel_lbl_dh.getX(), this.titel_lbl_dh.getY(), this.titel_lbl_dh.getWidth(),
				this.titel_lbl_dh.getHeight());
		Font titlefFont = this.titel_lbl.getFont().deriveFont(Font.BOLD, 37f);
		this.titel_lbl.setFont(titlefFont);
		this.add(this.titel_lbl);

		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				titel_lbl_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				titel_lbl.setBounds(titel_lbl_dh.getX(), titel_lbl_dh.getY(), titel_lbl_dh.getWidth(),
						titel_lbl_dh.getHeight());
			}
		});

		// Beschreibung label
		this.beschreibung_lbl = new JLabel("Beschreibung");
		Font beschreibung_font = this.beschreibung_lbl.getFont();
		this.beschreibung_lbl.setFont(new Font(beschreibung_font.getName(), Font.PLAIN, 19));
		this.beschreibung_lbl_dh = new DynamicHelper(32, 6, 20, 25, this.getWidth(), this.getHeight(),
				DynamicHelper.Location.CENTER);
		this.beschreibung_lbl.setBounds(this.beschreibung_lbl_dh.getX(), this.beschreibung_lbl_dh.getY(),
				this.beschreibung_lbl_dh.getWidth(), this.beschreibung_lbl_dh.getHeight());
		this.add(this.beschreibung_lbl);

		// Beschreibung textfield
		this.beschreibung_txt = new JTextField();
		this.beschreibung_txt_dh = new DynamicHelper(35, 5, 21, 30, this.getWidth(), this.getHeight(),
				DynamicHelper.Location.CENTER);
		this.beschreibung_txt.setBounds(this.beschreibung_txt_dh.getX(), this.beschreibung_txt_dh.getY(),
				this.beschreibung_txt_dh.getWidth(), this.beschreibung_txt_dh.getHeight());
		this.add(this.beschreibung_txt);

		// Erinnerung label
		this.errinerung_lbl = new JLabel("Erinnere mich alle");
		Font erinnerung_font = this.errinerung_lbl.getFont();
		this.errinerung_lbl.setFont(new Font(erinnerung_font.getName(), Font.PLAIN, 17));
		this.errinerung_lbl_dh = new DynamicHelper(32, 6, 20, 48, this.getWidth(), this.getHeight(),
				DynamicHelper.Location.CENTER);
		this.errinerung_lbl.setBounds(this.errinerung_lbl_dh.getX(), this.errinerung_lbl_dh.getY(),
				this.errinerung_lbl_dh.getWidth(), this.errinerung_lbl_dh.getHeight());
		this.add(this.errinerung_lbl);

		// Erinnerung spinner
		SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 7, 1); // (initial, min, max, step)
		this.erinnerung_spinner = new JSpinner(spinnerModel);
		this.erinnerung_spinner_dh = new DynamicHelper(6, 5, 5, 50, this.getWidth(), this.getHeight());
		this.erinnerung_spinner.setBounds(this.erinnerung_spinner_dh.getX(), this.erinnerung_spinner_dh.getY(),
				this.erinnerung_spinner_dh.getWidth(), this.erinnerung_spinner_dh.getHeight());
		this.add(this.erinnerung_spinner);

		// Tage label next to spinner
		this.tage_lbl = new JLabel(" Tage in der Woche");
		Font tage_font = this.tage_lbl.getFont();
		this.tage_lbl.setFont(new Font(tage_font.getName(), Font.PLAIN, 17));
		this.tage_lbl_dh = new DynamicHelper(6, 5, 5, 50, this.getWidth(), this.getHeight());
		this.tage_lbl.setBounds(this.erinnerung_spinner_dh.getX() + this.erinnerung_spinner_dh.getWidth() + 5,
				this.erinnerung_spinner_dh.getY(), 150, this.erinnerung_spinner_dh.getHeight());
		this.add(this.tage_lbl);

		// SprachSrc label
		this.sprachSrc_lbl = new JLabel("Sprache 1");
		Font sprachSrc_font = this.sprachSrc_lbl.getFont();
		this.sprachSrc_lbl.setFont(new Font(sprachSrc_font.getName(), Font.PLAIN, 17));
		this.sprachSrc_lbl_dh = new DynamicHelper(32, 6, 73, 40, this.getWidth(), this.getHeight(),
				DynamicHelper.Location.CENTER);
		this.sprachSrc_lbl.setBounds(this.sprachSrc_lbl_dh.getX(), this.sprachSrc_lbl_dh.getY(),
				this.sprachSrc_lbl_dh.getWidth(), this.sprachSrc_lbl_dh.getHeight());
		this.add(this.sprachSrc_lbl);

		// SprachSrc combobox
		this.sprachSrc_box = new JComboBox<String>(AppSettings.languages);
		this.sprachSrc_box_dh = new DynamicHelper(13, 8, 76, 40, this.getWidth(), this.getHeight(),
				DynamicHelper.Location.CENTER);
		this.sprachSrc_box.setBounds(this.sprachSrc_box_dh.getX(), this.sprachSrc_box_dh.getY(),
				this.sprachSrc_box_dh.getWidth(), this.sprachSrc_box_dh.getHeight());
		this.sprachSrc_box.setSelectedIndex(0);
		// if the user selects a language, change the flag icon
		this.sprachSrc_box.addActionListener(e -> {
			int index = this.sprachSrc_box.getSelectedIndex();
			switch (index) {
			case 0:
				this.flag1_icon = new ImageIcon(getClass().getResource("icons/Germany-Flag.png"));
				break;
			case 1:
				this.flag1_icon = new ImageIcon(getClass().getResource("icons/United-Kingdom-Flag.png"));
				break;
			case 3:
				this.flag1_icon = new ImageIcon(getClass().getResource("icons/Italy-Flag.png"));
				break;
			case 2:
				this.flag1_icon = new ImageIcon(getClass().getResource("icons/France-Flag.png"));
				break;
			}
			Image originalImage_src = this.flag1_icon.getImage();
			Image scaledImage_src = originalImage_src.getScaledInstance(this.flag1_btn.getWidth(),
					this.flag1_btn.getHeight(), Image.SCALE_SMOOTH);
			this.flag1_icon = new ImageIcon(scaledImage_src);
			this.flag1_btn.setIcon(this.flag1_icon);
		});
		this.add(this.sprachSrc_box);

		// next to the combobox, add a flag icon and make it as big as the button
		this.flag1_btn = new JButton();
		this.flag1_btn_dh = new DynamicHelper(10, 10, 89, 40, this.getWidth(), this.getHeight(),
				DynamicHelper.Location.CENTER);
		this.flag1_btn.setBounds(this.flag1_btn_dh.getX(), this.flag1_btn_dh.getY(), this.flag1_btn_dh.getWidth(),
				this.flag1_btn_dh.getHeight());
		this.flag1_icon = new ImageIcon(getClass().getResource("icons/Germany-Flag.png"));
		Image originalImage = this.flag1_icon.getImage();
		Image scaledImage = originalImage.getScaledInstance(this.flag1_btn.getWidth(), this.flag1_btn.getHeight(),
				Image.SCALE_SMOOTH);
		this.flag1_icon = new ImageIcon(scaledImage);
		this.flag1_btn.setIcon(this.flag1_icon);
		this.flag1_btn.setBorder(null);
		this.flag1_btn.setContentAreaFilled(false);
		this.add(this.flag1_btn);

		// SprachDest label
		this.sprachDest_lbl = new JLabel("Sprache 2");
		Font sprachDest_font = this.sprachDest_lbl.getFont();
		this.sprachDest_lbl.setFont(new Font(sprachDest_font.getName(), Font.PLAIN, 17));
		this.sprachDest_lbl_dh = new DynamicHelper(32, 6, 73, 50, this.getWidth(), this.getHeight(),
				DynamicHelper.Location.CENTER);
		this.sprachDest_lbl.setBounds(this.sprachDest_lbl_dh.getX(), this.sprachDest_lbl_dh.getY(),
				this.sprachDest_lbl_dh.getWidth(), this.sprachDest_lbl_dh.getHeight());
		this.add(this.sprachDest_lbl);

		// SprachDest combobox
		this.sprachDest_box = new JComboBox<String>(AppSettings.languages);
		this.sprachDest_box_dh = new DynamicHelper(13, 8, 76, 50, this.getWidth(), this.getHeight(),
				DynamicHelper.Location.CENTER);
		this.sprachDest_box.setBounds(this.sprachDest_box_dh.getX(), this.sprachDest_box_dh.getY(),
				this.sprachDest_box_dh.getWidth(), this.sprachDest_box_dh.getHeight());
		this.sprachDest_box.setSelectedIndex(1);
		// if the user selects a language, change the flag icon
		this.sprachDest_box.addActionListener(e -> {
			int index = this.sprachDest_box.getSelectedIndex();
			switch (index) {
			case 0:
				this.flag2_icon = new ImageIcon(getClass().getResource("icons/Germany-Flag.png"));
				break;
			case 1:
				this.flag2_icon = new ImageIcon(getClass().getResource("icons/United-Kingdom-Flag.png"));
				break;
			case 3:
				this.flag2_icon = new ImageIcon(getClass().getResource("icons/Italy-Flag.png"));
				break;
			case 2:
				this.flag2_icon = new ImageIcon(getClass().getResource("icons/France-Flag.png"));
				break;
			}
			Image originalImage_dest = this.flag2_icon.getImage();
			Image scaledImage_dest = originalImage_dest.getScaledInstance(this.flag2_btn.getWidth(),
					this.flag2_btn.getHeight(), Image.SCALE_SMOOTH);
			this.flag2_icon = new ImageIcon(scaledImage_dest);
			this.flag2_btn.setIcon(this.flag2_icon);
		});
		this.add(this.sprachDest_box);

		// next to the combobox, add a flag icon and make it as big as the button
		this.flag2_btn = new JButton();
		this.flag2_btn_dh = new DynamicHelper(10, 10, 89, 50, this.getWidth(), this.getHeight(),
				DynamicHelper.Location.CENTER);
		this.flag2_btn.setBounds(this.flag2_btn_dh.getX(), this.flag2_btn_dh.getY(), this.flag2_btn_dh.getWidth(),
				this.flag2_btn_dh.getHeight());
		this.flag2_icon = new ImageIcon(getClass().getResource("icons/United-Kingdom-Flag.png"));
		Image originalImage2 = this.flag2_icon.getImage();
		Image scaledImage2 = originalImage2.getScaledInstance(this.flag2_btn.getWidth(), this.flag2_btn.getHeight(),
				Image.SCALE_SMOOTH);
		this.flag2_icon = new ImageIcon(scaledImage2);
		this.flag2_btn.setIcon(this.flag2_icon);
		this.flag2_btn.setBorder(null);
		this.flag2_btn.setContentAreaFilled(false);
		this.add(this.flag2_btn);

		/// Checkbox for gross/kleinschreibung beachten
		this.gross_kleinschreibung_cb = new JCheckBox("Groß-/Kleinschreibung beachten");
		this.gross_kleinschreibung_cb_dh = new DynamicHelper(32, 5, 27, 72, this.getWidth(), this.getHeight(), 524, 10,
				DynamicHelper.Location.CENTER);
		this.gross_kleinschreibung_cb.setBounds(this.gross_kleinschreibung_cb_dh.getX(),
				this.gross_kleinschreibung_cb_dh.getY(), this.gross_kleinschreibung_cb_dh.getWidth(),
				this.gross_kleinschreibung_cb_dh.getHeight());
		Font cb_font = this.gross_kleinschreibung_cb.getFont(); // bigger font
		this.gross_kleinschreibung_cb.setFont(new Font(cb_font.getName(), Font.PLAIN, 17));
		this.add(this.gross_kleinschreibung_cb);

		// Abbrechen button
		this.abbrechen_btn = new JButton("Abbrechen");
		this.abbrechen_btn_dh = new DynamicHelper(20, 8, 52, 85, this.getWidth(), this.getHeight(),
				DynamicHelper.Location.CENTER);
		this.abbrechen_btn.setBounds(this.abbrechen_btn_dh.getX(), this.abbrechen_btn_dh.getY(),
				this.abbrechen_btn_dh.getWidth(), this.abbrechen_btn_dh.getHeight());
		this.abbrechen_btn.addActionListener((ActionEvent evt) -> {
			abbrechen_btnActionPerformed(evt);
		});
		this.add(this.abbrechen_btn);

		// Hinzufuegen button
		this.hinzufuegen_btn = new JButton("Hinzufuegen");
		this.hinzufuegen_btn_dh = new DynamicHelper(20, 8, 80, 85, this.getWidth(), this.getHeight(),
				DynamicHelper.Location.CENTER);
		this.hinzufuegen_btn.setBounds(this.hinzufuegen_btn_dh.getX(), this.hinzufuegen_btn_dh.getY(),
				this.hinzufuegen_btn_dh.getWidth(), this.hinzufuegen_btn_dh.getHeight());
		this.hinzufuegen_btn.addActionListener((ActionEvent evt) -> {
			hinzufuegen_btnActionPerformed(evt);
		});
		this.add(this.hinzufuegen_btn);

		// Add a listener to the window to update the components when the window is
		// resized
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				titel_lbl_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				titel_lbl.setBounds(titel_lbl_dh.getX(), titel_lbl_dh.getY(), titel_lbl_dh.getWidth(),
						titel_lbl_dh.getHeight());

				beschreibung_lbl_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				beschreibung_lbl.setBounds(beschreibung_lbl_dh.getX(), beschreibung_lbl_dh.getY(),
						beschreibung_lbl_dh.getWidth(), beschreibung_lbl_dh.getHeight());

				beschreibung_txt_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				beschreibung_txt.setBounds(beschreibung_txt_dh.getX(), beschreibung_txt_dh.getY(),
						beschreibung_txt_dh.getWidth(), beschreibung_txt_dh.getHeight());

				errinerung_lbl_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				errinerung_lbl.setBounds(errinerung_lbl_dh.getX(), errinerung_lbl_dh.getY(),
						errinerung_lbl_dh.getWidth(), errinerung_lbl_dh.getHeight());

				erinnerung_spinner_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				erinnerung_spinner.setBounds(erinnerung_spinner_dh.getX(), erinnerung_spinner_dh.getY(),
						erinnerung_spinner_dh.getWidth(), erinnerung_spinner_dh.getHeight());

				tage_lbl_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				tage_lbl.setBounds(erinnerung_spinner_dh.getX() + erinnerung_spinner_dh.getWidth() + 5,
						erinnerung_spinner_dh.getY(), 150, erinnerung_spinner_dh.getHeight());

				sprachSrc_lbl_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				sprachSrc_lbl.setBounds(sprachSrc_lbl_dh.getX(), sprachSrc_lbl_dh.getY(), sprachSrc_lbl_dh.getWidth(),
						sprachSrc_lbl_dh.getHeight());

				sprachSrc_box_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				sprachSrc_box.setBounds(sprachSrc_box_dh.getX(), sprachSrc_box_dh.getY(), sprachSrc_box_dh.getWidth(),
						sprachSrc_box_dh.getHeight());

				sprachDest_lbl_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				sprachDest_lbl.setBounds(sprachDest_lbl_dh.getX(), sprachDest_lbl_dh.getY(),
						sprachDest_lbl_dh.getWidth(), sprachDest_lbl_dh.getHeight());

				sprachDest_box_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				sprachDest_box.setBounds(sprachDest_box_dh.getX(), sprachDest_box_dh.getY(),
						sprachDest_box_dh.getWidth(), sprachDest_box_dh.getHeight());

				flag1_btn_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				flag1_btn.setBounds(flag1_btn_dh.getX(), flag1_btn_dh.getY(), flag1_btn_dh.getWidth(),
						flag1_btn_dh.getHeight());

				flag2_btn_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				flag2_btn.setBounds(flag2_btn_dh.getX(), flag2_btn_dh.getY(), flag2_btn_dh.getWidth(),
						flag2_btn_dh.getHeight());

				gross_kleinschreibung_cb_dh.updateWindowSize(evt.getComponent().getWidth(),
						evt.getComponent().getHeight());
				gross_kleinschreibung_cb.setBounds(gross_kleinschreibung_cb_dh.getX(),
						gross_kleinschreibung_cb_dh.getY(), gross_kleinschreibung_cb_dh.getWidth(),
						gross_kleinschreibung_cb_dh.getHeight());

				abbrechen_btn_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				abbrechen_btn.setBounds(abbrechen_btn_dh.getX(), abbrechen_btn_dh.getY(), abbrechen_btn_dh.getWidth(),
						abbrechen_btn_dh.getHeight());

				hinzufuegen_btn_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
				hinzufuegen_btn.setBounds(hinzufuegen_btn_dh.getX(), hinzufuegen_btn_dh.getY(),
						hinzufuegen_btn_dh.getWidth(), hinzufuegen_btn_dh.getHeight());
			}
		});

		if (AppSettings.isDarkMode()) {
			this.applyDarkModeStyles();
		}
		else {
			this.applyLightModeStyles();
		}
	}

	private void abbrechen_btnActionPerformed(ActionEvent evt) {
		AppSettings.setLernkartei(null);
		FensterEinstellungen einstellungen = new FensterEinstellungen();
		einstellungen.setVisible(true);
		einstellungen.setLocation(getLocation());
		dispose();
	}

	private void hinzufuegen_btnActionPerformed(ActionEvent evt) { // TODO: add erinnerung
		// get values from textfield, spinner, comboboxes and checkbox
		String beschreibung = this.beschreibung_txt.getText();
		int erinnerung = (int) this.erinnerung_spinner.getValue();
		String sprache1 = (String) this.sprachSrc_box.getSelectedItem();
		String sprache2 = (String) this.sprachDest_box.getSelectedItem();
		boolean gross_kleinschreibung = this.gross_kleinschreibung_cb.isSelected();

		// save to database
		Lernkartei kartei = new Lernkartei(beschreibung, sprache1, sprache2, true, gross_kleinschreibung);
		int result = VokabeltrainerDB.hinzufuegenLernkartei(kartei);
		switch (result) {
		case 0:
			Fach tmp = new Fach();
			tmp.setGelerntAm(new Date(0)); // set gelerntAm to '1970'

			// hinzufügen eines Faches an die Lernkartei
			int ret = VokabeltrainerDB.hinzufuegenFach(kartei.getNummer(), tmp);
			if (ret == -1) 
				JOptionPane.showMessageDialog(null, "Fehler beim erstellen der Fächer", "Fehler", JOptionPane.ERROR_MESSAGE);
			kartei.erinnerungsIntervall = erinnerung;

			List<Fach> faecher = VokabeltrainerDB.getFaecher(kartei.getNummer());
			for (int i = 0; i < faecher.size(); i++) { // set erinnerungsintervall for each fach
				Fach f = faecher.get(i);
				f.setErinnerungsIntervall(erinnerung + i);
				VokabeltrainerDB.aendernFach(f);
			}

			FensterEinstellungen einstellungen = new FensterEinstellungen();
			einstellungen.setVisible(true);
			einstellungen.setLocation(getLocation());
			dispose();
			break;
		case -1:
			JOptionPane.showMessageDialog(null, "Ein Datenbankfehler ist aufgetreten", "Error", JOptionPane.ERROR_MESSAGE); // display message box with error:
			break;
		case -2:
			JOptionPane.showMessageDialog(null, kartei.getFehler().get("beschreibung"), "Error", JOptionPane.ERROR_MESSAGE); // display message box with error:
			break;
		}
	}

	private void applyDarkModeStyles() {
		this.getContentPane().setBackground(new Color(62, 63, 65)); // Dunkles Schwarz

		// Labels
		titel_lbl.setForeground(Color.WHITE);
		beschreibung_lbl.setForeground(Color.WHITE);
		errinerung_lbl.setForeground(Color.WHITE);
		tage_lbl.setForeground(Color.WHITE);
		sprachSrc_lbl.setForeground(Color.WHITE);
		sprachDest_lbl.setForeground(Color.WHITE);

		// TextFields
		beschreibung_txt.setForeground(Color.WHITE);
		beschreibung_txt.setBackground(new Color(62, 63, 65)); // Dunkelgrau

		// Spinners
		erinnerung_spinner.setForeground(Color.WHITE);
		erinnerung_spinner.setBackground(new Color(62, 63, 65)); // Dunkelgrau

		// ComboBoxes
		sprachSrc_box.setForeground(Color.WHITE);
		sprachSrc_box.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		sprachDest_box.setForeground(Color.WHITE);
		sprachDest_box.setBackground(new Color(62, 63, 65)); // Dunkelgrau

		// Buttons
		flag1_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		// flag1_btn.setBorder(new LineBorder(Color.WHITE, 2));
		flag1_btn.setForeground(Color.WHITE);
		flag2_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		// flag2_btn.setBorder(new LineBorder(Color.WHITE, 2));
		flag2_btn.setForeground(Color.WHITE);
		abbrechen_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		abbrechen_btn.setBorder(new LineBorder(Color.WHITE, 2));
		abbrechen_btn.setForeground(Color.WHITE);
		hinzufuegen_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		hinzufuegen_btn.setBorder(new LineBorder(Color.WHITE, 2));
		hinzufuegen_btn.setForeground(Color.WHITE);

		// CheckBox
		gross_kleinschreibung_cb.setForeground(Color.WHITE);
		gross_kleinschreibung_cb.setBackground(new Color(62, 63, 65)); // Dunkelgrau
	}

	private void applyLightModeStyles() {
		this.getContentPane().setBackground(new Color(241, 241, 241)); // Dunkles Schwarz

		// Labels
		titel_lbl.setForeground(Color.BLACK);
		beschreibung_lbl.setForeground(Color.BLACK);
		errinerung_lbl.setForeground(Color.BLACK);
		tage_lbl.setForeground(Color.BLACK);
		sprachSrc_lbl.setForeground(Color.BLACK);
		sprachDest_lbl.setForeground(Color.BLACK);

		// TextFields
		beschreibung_txt.setForeground(Color.BLACK);
		beschreibung_txt.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		beschreibung_txt.setBorder(new LineBorder(Color.BLACK, 2));
		// Spinners
		erinnerung_spinner.setForeground(Color.BLACK);
		erinnerung_spinner.setBackground(new Color(241, 241, 241)); // Dunkelgrau

		// ComboBoxes
		sprachSrc_box.setForeground(Color.BLACK);
		sprachSrc_box.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		sprachDest_box.setForeground(Color.BLACK);
		sprachDest_box.setBackground(new Color(241, 241, 241)); // Dunkelgrau

		// Buttons
		flag1_btn.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		// flag1_btn.setBorder(new LineBorder(Color.WHITE, 2));
		flag1_btn.setForeground(Color.BLACK);
		flag2_btn.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		// flag2_btn.setBorder(new LineBorder(Color.WHITE, 2));
		flag2_btn.setForeground(Color.BLACK);
		abbrechen_btn.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		abbrechen_btn.setBorder(new LineBorder(Color.BLACK, 2));
		abbrechen_btn.setForeground(Color.BLACK);
		hinzufuegen_btn.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		hinzufuegen_btn.setBorder(new LineBorder(Color.BLACK, 2));
		hinzufuegen_btn.setForeground(Color.BLACK);

		// CheckBox
		gross_kleinschreibung_cb.setForeground(Color.BLACK);
		gross_kleinschreibung_cb.setBackground(new Color(241, 241, 241)); // Dunkelgrau
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