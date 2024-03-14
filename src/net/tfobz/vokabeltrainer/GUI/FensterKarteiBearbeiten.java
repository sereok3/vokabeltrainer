package net.tfobz.vokabeltrainer.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;
import net.tfobz.vokabeltrainer.utils.AppSettings;
import net.tfobz.vokabeltrainer.utils.DynamicHelper;

public class FensterKarteiBearbeiten extends JFrame {
	private JLabel titel_lbl;
	private DynamicHelper titel_lbl_dh;
	private JLabel beschreibugn_lbl;
	private DynamicHelper beschreibung_lbl_dh;
	private JTextField beschreibung_txt;
	private DynamicHelper beschreibung_txt_dh;
	private JLabel sprache_aendern_lbl;
	private DynamicHelper sprache_aendern_lbl_dh;
	private JComboBox<String> sprachSrc_box;
	private DynamicHelper sprachSrc_box_dh;
	private JComboBox<String> sprachDest_box;
	private DynamicHelper sprachDest_box_dh;
	private JButton flag1_btn;
	private DynamicHelper flag1_btn_dh;
	private ImageIcon flag1_icon;
	private JButton flag2_btn;
	private DynamicHelper flag2_btn_dh;
	private ImageIcon flag2_icon;
	private JButton upDownArrows_btn;
	private DynamicHelper upDownArrows_btn_dh;
	private ImageIcon upDownArrows_icon;
	private JButton woerterBeartbeiten_btn;
	private DynamicHelper woerterBeartbeiten_btn_dh;
	private JButton abbrechen_btn;
	private DynamicHelper abbrechen_btn_dh;
	private JButton speichern_btn;
	private DynamicHelper speichern_btn_dh;
	private int index_selected_kartei;
	private Lernkartei lernkartei;

	public FensterKarteiBearbeiten(int index_selected_kartei) {
		this.setSize(800, 550);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Vokabeltrainer");
		this.setLayout(null);
		this.setMinimumSize(new Dimension(700, 500));
		this.setMaximumSize(new Dimension(1000, 800));

		this.index_selected_kartei = index_selected_kartei;
		if (AppSettings.getLernkartei() != null) {
			this.lernkartei = AppSettings.getLernkartei();
		} else {
			this.lernkartei = VokabeltrainerDB.getLernkartei(index_selected_kartei);
			AppSettings.setLernkartei(this.lernkartei);
		}

		// titel label
		this.titel_lbl = new JLabel("Lernkartei bearbeiten");
		this.titel_lbl_dh = new DynamicHelper(32, 6, 49, 10, this.getWidth(), this.getHeight(), 800, 82, DynamicHelper.Location.CENTER);
		this.titel_lbl.setBounds(this.titel_lbl_dh.getX(), this.titel_lbl_dh.getY(), this.titel_lbl_dh.getWidth(), this.titel_lbl_dh.getHeight());
		Font titlefFont = this.titel_lbl.getFont().deriveFont(Font.BOLD, 37f);
		this.titel_lbl.setFont(titlefFont);
		this.add(this.titel_lbl);

		// beschreibung aendern label
		this.beschreibugn_lbl = new JLabel("Beschreibung aendern");
		this.beschreibung_lbl_dh = new DynamicHelper(12, 1, 49, 25, this.getWidth(), this.getHeight(), 500, 60, DynamicHelper.Location.CENTER);
		this.beschreibugn_lbl.setBounds(this.beschreibung_lbl_dh.getX(), this.beschreibung_lbl_dh.getY(), this.beschreibung_lbl_dh.getWidth(), this.beschreibung_lbl_dh.getHeight());
		Font beschreibungFont = this.beschreibugn_lbl.getFont().deriveFont(Font.BOLD, 20f);
		this.beschreibugn_lbl.setFont(beschreibungFont);
		this.add(this.beschreibugn_lbl);

		// beschreibung textfield, derzeitiger beschreibungstext vom
		// index_selected_kartei
		String old_text = this.lernkartei.getBeschreibung();
		this.beschreibung_txt = new JTextField(old_text);
		this.beschreibung_txt_dh = new DynamicHelper(40, 6, 49, 35, this.getWidth(), this.getHeight(), DynamicHelper.Location.CENTER);
		this.beschreibung_txt.setBounds(this.beschreibung_txt_dh.getX(), this.beschreibung_txt_dh.getY(), this.beschreibung_txt_dh.getWidth(), this.beschreibung_txt_dh.getHeight());
		this.add(this.beschreibung_txt);

		// sprache aendern label
		this.sprache_aendern_lbl = new JLabel("Sprache aendern");
		this.sprache_aendern_lbl_dh = new DynamicHelper(30, 1, 49, 52, this.getWidth(), this.getHeight(), 322, 60, DynamicHelper.Location.CENTER);
		this.sprache_aendern_lbl.setBounds(this.sprache_aendern_lbl_dh.getX(), this.sprache_aendern_lbl_dh.getY(), this.sprache_aendern_lbl_dh.getWidth(), this.sprache_aendern_lbl_dh.getHeight());
		Font spracheFont = this.sprache_aendern_lbl.getFont().deriveFont(Font.BOLD, 20f);
		this.sprache_aendern_lbl.setFont(spracheFont);
		this.add(this.sprache_aendern_lbl);

		// the 2 comboboxes for the languages, are under each other
		this.sprachSrc_box = new JComboBox<String>(AppSettings.languages);
		this.sprachSrc_box_dh = new DynamicHelper(13, 6, 49, 60, this.getWidth(), this.getHeight(), DynamicHelper.Location.CENTER);
		this.sprachSrc_box.setBounds(this.sprachSrc_box_dh.getX(), this.sprachSrc_box_dh.getY(), this.sprachSrc_box_dh.getWidth(), this.sprachSrc_box_dh.getHeight());
		// set the selected language to the language of the selected kartei
		this.sprachSrc_box.setSelectedIndex(AppSettings.getLanguageIndex(this.lernkartei.getWortEinsBeschreibung()));
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
			Image originalImage_dest = this.flag1_icon.getImage();
			Image scaledImage_dest = originalImage_dest.getScaledInstance(this.flag1_btn.getWidth(), this.flag1_btn.getHeight(), Image.SCALE_SMOOTH);
			this.flag1_icon = new ImageIcon(scaledImage_dest);
			this.flag1_btn.setIcon(this.flag1_icon);
		});
		this.add(this.sprachSrc_box);

		// before to the combobox, add a flag icon and make it as big as the button
		this.flag1_btn = new JButton();
		this.flag1_btn_dh = new DynamicHelper(10, 10, 31, 60, this.getWidth(), this.getHeight(), DynamicHelper.Location.CENTER);
		this.flag1_btn.setBounds(this.flag1_btn_dh.getX(), this.flag1_btn_dh.getY(), this.flag1_btn_dh.getWidth(), this.flag1_btn_dh.getHeight());
		// flag icon to the index_selected_kartei language
		switch (this.sprachSrc_box.getSelectedIndex()) {
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
		Image originalImage = this.flag1_icon.getImage();
		Image scaledImage = originalImage.getScaledInstance(this.flag1_btn.getWidth(), this.flag1_btn.getHeight(),
				Image.SCALE_SMOOTH);
		this.flag1_icon = new ImageIcon(scaledImage);
		this.flag1_btn.setIcon(this.flag1_icon);
		this.flag1_btn.setBorder(null);
		this.flag1_btn.setContentAreaFilled(false);
		this.add(this.flag1_btn);

		// second language combobox
		this.sprachDest_box = new JComboBox<String>(AppSettings.languages);
		this.sprachDest_box_dh = new DynamicHelper(13, 6, 49, 71, this.getWidth(), this.getHeight(), DynamicHelper.Location.CENTER);
		this.sprachDest_box.setBounds(this.sprachDest_box_dh.getX(), this.sprachDest_box_dh.getY(), this.sprachDest_box_dh.getWidth(), this.sprachDest_box_dh.getHeight());
		// set the selected language to the language of the selected kartei
		this.sprachDest_box.setSelectedIndex(AppSettings.getLanguageIndex(this.lernkartei.getWortZweiBeschreibung()));
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

		// before to the combobox, add a flag icon and make it as big as the button
		this.flag2_btn = new JButton();
		this.flag2_btn_dh = new DynamicHelper(10, 10, 31, 71, this.getWidth(), this.getHeight(),
				DynamicHelper.Location.CENTER);
		this.flag2_btn.setBounds(this.flag2_btn_dh.getX(), this.flag2_btn_dh.getY(), this.flag2_btn_dh.getWidth(),
				this.flag2_btn_dh.getHeight());
		// flag icon to the index_selected_kartei language
		switch (this.sprachDest_box.getSelectedIndex()) {
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
		Image scaledImage_dest = originalImage_dest.getScaledInstance(this.flag2_btn.getWidth(),this.flag2_btn.getHeight(), Image.SCALE_SMOOTH);
		this.flag2_icon = new ImageIcon(scaledImage_dest);
		this.flag2_btn.setIcon(this.flag2_icon);
		this.flag2_btn.setBorder(null);
		this.flag2_btn.setContentAreaFilled(false);
		this.add(this.flag2_btn);

		// up down arrows button next to the comboboxes in the middle, to switch the
		// languages
		this.upDownArrows_btn = new JButton();
		this.upDownArrows_btn_dh = new DynamicHelper(9, 11, 65, 65, this.getWidth(), this.getHeight(), 145, 120,DynamicHelper.Location.CENTER);
		this.upDownArrows_btn.setBounds(this.upDownArrows_btn_dh.getX(), this.upDownArrows_btn_dh.getY(),this.upDownArrows_btn_dh.getWidth(), this.upDownArrows_btn_dh.getHeight());
		this.upDownArrows_icon = new ImageIcon(getClass().getResource("icons/up_down_arrows.png"));
		Image originalImage_arrows = this.upDownArrows_icon.getImage();
		Image scaledImage_arrows = originalImage_arrows.getScaledInstance(this.upDownArrows_btn.getWidth(),this.upDownArrows_btn.getHeight(), Image.SCALE_SMOOTH);
		this.upDownArrows_icon = new ImageIcon(scaledImage_arrows);
		this.upDownArrows_btn.setIcon(this.upDownArrows_icon);
		this.upDownArrows_btn.setBorder(null);
		this.upDownArrows_btn.setContentAreaFilled(false);
		this.upDownArrows_btn.setFocusable(false);
		this.upDownArrows_btn.addActionListener(e -> {
			int index_src = this.sprachSrc_box.getSelectedIndex();
			int index_dest = this.sprachDest_box.getSelectedIndex();
			this.sprachSrc_box.setSelectedIndex(index_dest);
			this.sprachDest_box.setSelectedIndex(index_src);
		});
		this.upDownArrows_btn.setToolTipText("Sprachen tauschen");
		this.add(this.upDownArrows_btn);

		// woerter bearbeiten button
		this.woerterBeartbeiten_btn = new JButton("Woerter bearbeiten");
		this.woerterBeartbeiten_btn_dh = new DynamicHelper(12, 1, 11, 88, this.getWidth(), this.getHeight(), 322, 60,DynamicHelper.Location.CENTER);
		this.woerterBeartbeiten_btn.setBounds(this.woerterBeartbeiten_btn_dh.getX(), this.woerterBeartbeiten_btn_dh.getY(), this.woerterBeartbeiten_btn_dh.getWidth(), this.woerterBeartbeiten_btn_dh.getHeight());
		this.woerterBeartbeiten_btn.addActionListener((ActionEvent evt) -> {
			woerterBeartbeiten_btnActionPerformed(evt);
		});
		this.add(this.woerterBeartbeiten_btn);

		// abbrechen button
		this.abbrechen_btn = new JButton("Abbrechen");
		this.abbrechen_btn_dh = new DynamicHelper(7, 1, 65, 88, this.getWidth(), this.getHeight(), 292, 60, DynamicHelper.Location.CENTER);
		this.abbrechen_btn.setBounds(this.abbrechen_btn_dh.getX(), this.abbrechen_btn_dh.getY(),
				this.abbrechen_btn_dh.getWidth(), this.abbrechen_btn_dh.getHeight());
		this.abbrechen_btn.addActionListener((ActionEvent evt) -> {
			abbrechen_btnActionPerformed(evt);
		});
		this.add(this.abbrechen_btn);

		// speichern button
		this.speichern_btn = new JButton("Speichern");
		this.speichern_btn_dh = new DynamicHelper(7, 1, 87, 88, this.getWidth(), this.getHeight(), 292, 60, DynamicHelper.Location.CENTER);
		this.speichern_btn.setBounds(this.speichern_btn_dh.getX(), this.speichern_btn_dh.getY(), this.speichern_btn_dh.getWidth(), this.speichern_btn_dh.getHeight());
		this.speichern_btn.addActionListener((ActionEvent evt) -> {
			speichern_btnActionPerformed(evt);
		});
		this.add(this.speichern_btn);

		if (AppSettings.isDarkMode()) {
			applyDarkModeStyles();
		} else {
			applyLightModeStyles();
		}

		// update the size and location of the components when the window is resized
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				titel_lbl_dh.updateWindowSize(getWidth(), getHeight());
				titel_lbl.setBounds(titel_lbl_dh.getX(), titel_lbl_dh.getY(), titel_lbl_dh.getWidth(), titel_lbl_dh.getHeight());

				beschreibung_lbl_dh.updateWindowSize(getWidth(), getHeight());
				beschreibugn_lbl.setBounds(beschreibung_lbl_dh.getX(), beschreibung_lbl_dh.getY(), beschreibung_lbl_dh.getWidth(), beschreibung_lbl_dh.getHeight());

				beschreibung_txt_dh.updateWindowSize(getWidth(), getHeight());
				beschreibung_txt.setBounds(beschreibung_txt_dh.getX(), beschreibung_txt_dh.getY(), beschreibung_txt_dh.getWidth(), beschreibung_txt_dh.getHeight());

				sprache_aendern_lbl_dh.updateWindowSize(getWidth(), getHeight());
				sprache_aendern_lbl.setBounds(sprache_aendern_lbl_dh.getX(), sprache_aendern_lbl_dh.getY(), sprache_aendern_lbl_dh.getWidth(), sprache_aendern_lbl_dh.getHeight());

				sprachSrc_box_dh.updateWindowSize(getWidth(), getHeight());
				sprachSrc_box.setBounds(sprachSrc_box_dh.getX(), sprachSrc_box_dh.getY(), sprachSrc_box_dh.getWidth(), sprachSrc_box_dh.getHeight());

				sprachDest_box_dh.updateWindowSize(getWidth(), getHeight());
				sprachDest_box.setBounds(sprachDest_box_dh.getX(), sprachDest_box_dh.getY(), sprachDest_box_dh.getWidth(), sprachDest_box_dh.getHeight());

				flag1_btn_dh.updateWindowSize(getWidth(), getHeight());
				flag1_btn.setBounds(flag1_btn_dh.getX(), flag1_btn_dh.getY(), flag1_btn_dh.getWidth(), flag1_btn_dh.getHeight());

				flag2_btn_dh.updateWindowSize(getWidth(), getHeight());
				flag2_btn.setBounds(flag2_btn_dh.getX(), flag2_btn_dh.getY(), flag2_btn_dh.getWidth(), flag2_btn_dh.getHeight());

				upDownArrows_btn_dh.updateWindowSize(getWidth(), getHeight());
				upDownArrows_btn.setBounds(upDownArrows_btn_dh.getX(), upDownArrows_btn_dh.getY(), upDownArrows_btn_dh.getWidth(), upDownArrows_btn_dh.getHeight());

				woerterBeartbeiten_btn_dh.updateWindowSize(getWidth(), getHeight());
				woerterBeartbeiten_btn.setBounds(woerterBeartbeiten_btn_dh.getX(), woerterBeartbeiten_btn_dh.getY(), woerterBeartbeiten_btn_dh.getWidth(), woerterBeartbeiten_btn_dh.getHeight());

				abbrechen_btn_dh.updateWindowSize(getWidth(), getHeight());
				abbrechen_btn.setBounds(abbrechen_btn_dh.getX(), abbrechen_btn_dh.getY(), abbrechen_btn_dh.getWidth(), abbrechen_btn_dh.getHeight());

				speichern_btn_dh.updateWindowSize(getWidth(), getHeight());
				speichern_btn.setBounds(speichern_btn_dh.getX(), speichern_btn_dh.getY(), speichern_btn_dh.getWidth(), speichern_btn_dh.getHeight());
			}
		});
	}

	private void abbrechen_btnActionPerformed(ActionEvent evt) {
		AppSettings.setLernkartei(null);
		FensterEinstellungen einstellungen = new FensterEinstellungen();
		einstellungen.setVisible(true);
		einstellungen.setLocation(getLocation());
		dispose();
	}

	private void speichern_btnActionPerformed(ActionEvent evt) {
		String new_description = this.beschreibung_txt.getText();
		String new_lang1 = this.sprachSrc_box.getSelectedItem().toString();
		String new_lang2 = this.sprachDest_box.getSelectedItem().toString();
		//Lernkartei lernkartei = VokabeltrainerDB.getLernkartei(index_selected_kartei);
		if (AppSettings.getLernkartei() == null) {
			AppSettings.setLernkartei(VokabeltrainerDB.getLernkartei(index_selected_kartei));
		}
		Lernkartei lernkartei = AppSettings.getLernkartei();
		lernkartei.setBeschreibung(new_description);
		lernkartei.setWortEinsBeschreibung(new_lang1);
		lernkartei.setWortZweiBeschreibung(new_lang2);
		VokabeltrainerDB.aendernLernkartei(lernkartei);

		AppSettings.setLernkartei(null);
		FensterEinstellungen einstellungen = new FensterEinstellungen();
		einstellungen.setVisible(true);
		einstellungen.setLocation(getLocation());
		dispose();
	}

	private void woerterBeartbeiten_btnActionPerformed(ActionEvent evt) {
		FensterWoerterBearbeiten woerterBearbeiten = new FensterWoerterBearbeiten(this.index_selected_kartei);
		woerterBearbeiten.setVisible(true);
		woerterBearbeiten.setLocation(getLocation());
		dispose();
	}

	private void applyDarkModeStyles() {
		this.getContentPane().setBackground(new Color(62, 63, 65)); // Dunkles Schwarz

		// Labels
		titel_lbl.setForeground(Color.WHITE);
		beschreibugn_lbl.setForeground(Color.WHITE);
		sprache_aendern_lbl.setForeground(Color.WHITE);

		// TextFields
		beschreibung_txt.setForeground(Color.WHITE);
		beschreibung_txt.setBackground(new Color(62, 63, 65)); // Dunkelgrau

		// ComboBoxes
		sprachSrc_box.setForeground(Color.WHITE);
		sprachSrc_box.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		sprachDest_box.setForeground(Color.WHITE);
		sprachDest_box.setBackground(new Color(62, 63, 65)); // Dunkelgrau

		// Buttons
		flag1_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		flag1_btn.setForeground(Color.WHITE);
		flag2_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		flag2_btn.setForeground(Color.WHITE);
		upDownArrows_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		upDownArrows_btn.setForeground(Color.WHITE);
		woerterBeartbeiten_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		woerterBeartbeiten_btn.setBorder(new LineBorder(Color.WHITE, 2));
		woerterBeartbeiten_btn.setForeground(Color.WHITE);
		abbrechen_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		abbrechen_btn.setBorder(new LineBorder(Color.WHITE, 2));
		abbrechen_btn.setForeground(Color.WHITE);
		speichern_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
		speichern_btn.setBorder(new LineBorder(Color.WHITE, 2));
		speichern_btn.setForeground(Color.WHITE);
	}

	private void applyLightModeStyles() {
		this.getContentPane().setBackground(new Color(241, 241, 241)); // Dunkles Schwarz

		// Labels
		titel_lbl.setForeground(Color.BLACK);
		beschreibugn_lbl.setForeground(Color.BLACK);
		sprache_aendern_lbl.setForeground(Color.BLACK);

		// TextFields
		beschreibung_txt.setForeground(Color.BLACK);
		beschreibung_txt.setBackground(new Color(241, 241, 241)); // Dunkelgrau

		// ComboBoxes
		sprachSrc_box.setForeground(Color.BLACK);
		sprachSrc_box.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		// sprachSrc_box.setBorder(new LineBorder(Color.BLACK, 2));
		sprachDest_box.setForeground(Color.BLACK);
		sprachDest_box.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		// sprachDest_box.setBorder(new LineBorder(Color.BLACK, 2));

		// Buttons
		flag1_btn.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		flag1_btn.setForeground(Color.BLACK);
		flag2_btn.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		flag2_btn.setForeground(Color.BLACK);
		upDownArrows_btn.setForeground(Color.BLACK);
		woerterBeartbeiten_btn.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		woerterBeartbeiten_btn.setBorder(new LineBorder(Color.BLACK, 2));
		woerterBeartbeiten_btn.setForeground(Color.BLACK);
		abbrechen_btn.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		abbrechen_btn.setBorder(new LineBorder(Color.BLACK, 2));
		abbrechen_btn.setForeground(Color.BLACK);
		speichern_btn.setBackground(new Color(241, 241, 241)); // Dunkelgrau
		speichern_btn.setBorder(new LineBorder(Color.BLACK, 2));
		speichern_btn.setForeground(Color.BLACK);
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