package net.tfobz.vokabeltrainer.GUI;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import net.tfobz.vokabeltrainer.model.Fach;
import net.tfobz.vokabeltrainer.model.Karte;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;
import net.tfobz.vokabeltrainer.utils.AppSettings;
import net.tfobz.vokabeltrainer.utils.DynamicHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FensterKarteiLernen extends JFrame {
    private JLabel titel_lbl;
    private DynamicHelper titel_lbl_dh;
    private int index_selected_lernkartei;
	private int index_selected_fach;
    private JTabbedPane tabbedPane;
	private DynamicHelper tabbedPane_dh;
	private JButton home_btn;
	private DynamicHelper home_btn_dh;
	private int home_btn_width = 40;
	private int home_btn_height = 40;
	private ImageIcon home_icon;
	private JButton back_btn;
	private DynamicHelper back_btn_dh;
	private int back_btn_width = 40;
	private int back_btn_height = 40;
	private ImageIcon back_icon;
	private JLabel sprache1_lbl;
	private DynamicHelper sprache1_lbl_dh;
	private JLabel sprache2_lbl;
	private DynamicHelper sprache2_lbl_dh;
	private JTextField sprache1_tf;
	private DynamicHelper sprache1_tf_dh;
	private JTextField sprache2_tf;
	private DynamicHelper sprache2_tf_dh;
	private JButton tipp_btn;
	private DynamicHelper tipp_btn_dh;
	private JButton weiter_btn;
	private DynamicHelper weiter_btn_dh;
	private JLabel falsch_beantwortet_lbl;
	private DynamicHelper falsch_beantwortet_lbl_dh;
	private JLabel richtig_beantwortet_lbl;
	private DynamicHelper richtig_beantwortet_lbl_dh;
	private JTable richtigTable;
	private JTable falschTable;
	private DynamicHelper richtigTable_dh;
	private DynamicHelper falschTable_dh;
	private DefaultTableModel richtigTableModel;
	private DefaultTableModel falschTableModel;
	private Lernkartei lernkartei;
    private List<Fach> faecherListe;
	private Karte curKarte;

    public FensterKarteiLernen(int index_selected_lernkartei) {
        this.setSize(800, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Vokabeltrainer");
        this.setLayout(null);
        this.setMinimumSize(new Dimension(700, 500));
        this.setMaximumSize(new Dimension(1000, 800));

        this.index_selected_lernkartei = index_selected_lernkartei;
		this.index_selected_fach = 0;
		this.faecherListe =  VokabeltrainerDB.getFaecher(this.index_selected_lernkartei);
		if (AppSettings.getLernkartei() != null)
			this.lernkartei = AppSettings.getLernkartei();
		else {
			this.lernkartei = VokabeltrainerDB.getLernkartei(index_selected_lernkartei);
			AppSettings.setLernkartei(this.lernkartei);
		}

		if (this.faecherListe.size() == 0) {
			Fach tmp = new Fach();
			tmp.setGelerntAm(new Date(0)); // set the date to 1970-01-01

			// hinzufügen eines Faches an die Lernkartei			
			int ret = VokabeltrainerDB.hinzufuegenFach(this.index_selected_lernkartei, tmp);
			if (ret == -1) 
				JOptionPane.showMessageDialog(null, "Fehler beim erstellen der Fächer", "Fehler", JOptionPane.ERROR_MESSAGE);
		
			List<Fach> faecher = faecherListe;
			for (int i = 0; i < faecher.size(); i++) { // update erinnerungsIntervall
				Fach f = faecher.get(i);
				f.setErinnerungsIntervall(this.lernkartei.erinnerungsIntervall + i);
				VokabeltrainerDB.aendernFach(f);
			}
		}

		for (Fach fach: faecherListe) { // find the first fach with cards
			int fach_nummer = fach.getNummer();
			if (VokabeltrainerDB.getKarten(fach_nummer).size() > 0)
				break;
			this.index_selected_fach += 1; // increment the index of the selected fach
		}
		if (this.index_selected_fach >= this.faecherListe.size()) {
			this.index_selected_fach = 0;
		}

		if (AppSettings.getLernkartei() != null)
			this.lernkartei = AppSettings.getLernkartei();
		else {
			this.lernkartei = VokabeltrainerDB.getLernkartei(index_selected_lernkartei);
			AppSettings.setLernkartei(this.lernkartei);
		}

        // titel label
        this.titel_lbl = new JLabel("Lernen");
        this.titel_lbl_dh = new DynamicHelper(32, 6, 49, 10, this.getWidth(), this.getHeight(), 300, 82, DynamicHelper.Location.CENTER);
        this.titel_lbl.setBounds(this.titel_lbl_dh.getX(), this.titel_lbl_dh.getY(), this.titel_lbl_dh.getWidth(), this.titel_lbl_dh.getHeight());
        Font titlefFont = this.titel_lbl.getFont().deriveFont(Font.BOLD, 37f);
        this.titel_lbl.setFont(titlefFont);
        this.add(this.titel_lbl);

		// Back button
		this.back_btn = new JButton();
		this.back_btn_dh = new DynamicHelper(1, 11, 1, 2, this.getWidth(), this.getHeight());
		this.back_btn.setBounds(this.back_btn_dh.getX(), this.back_btn_dh.getY(), this.back_btn_width, this.back_btn_height);
		this.back_btn.setContentAreaFilled(false); // Set background to be transparent
		this.back_btn.setBorderPainted(false); // Remove button border
		this.back_btn.setFocusPainted(false); // Remove button focus border
		this.back_icon = new ImageIcon(getClass().getResource("icons/back.png"));
		Image originalImageBack = this.back_icon.getImage();
		Image scaledImageBack = originalImageBack.getScaledInstance(this.back_btn_width, this.back_btn_height, Image.SCALE_SMOOTH);
		ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
		this.back_btn.setIcon(scaledIconBack);
		this.back_btn.setPreferredSize(new Dimension(this.back_btn_width + 5, this.back_btn_height));
		this.back_btn.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					AppSettings.setLernkartei(null);
					FensterLernen lernen = new FensterLernen();
					lernen.setVisible(true);
					lernen.setLocation(getLocation());
					dispose();
				});
			}
		});
		this.add(this.back_btn);

		// Home button
		this.home_btn = new JButton();
		this.home_btn_dh = new DynamicHelper(1, 11, 7, 2, this.getWidth(), this.getHeight());
		this.home_btn.setBounds(this.home_btn_dh.getX(), this.home_btn_dh.getY(), this.home_btn_width, this.home_btn_height);
		this.home_btn.setContentAreaFilled(false); // Set background to be transparent
		this.home_btn.setBorderPainted(false); // Remove button border
		this.home_btn.setFocusPainted(false); // Remove button focus border
		this.home_icon = new ImageIcon(getClass().getResource("icons/home.png"));
		Image originalImage = this.home_icon.getImage();
		Image scaledImage = originalImage.getScaledInstance(this.home_btn_width, this.home_btn_height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		this.home_btn.setIcon(scaledIcon);
		this.home_btn.setPreferredSize(new Dimension(this.home_btn_width + 5, this.home_btn_height));
		this.home_btn.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					AppSettings.setLernkartei(null);
					FensterStartSeite startSeite = new FensterStartSeite();
					startSeite.setVisible(true);
					startSeite.setLocation(getLocation());
					dispose();
				});
			}
		});
		this.add(this.home_btn);

		// Sprache 1 label
		String srcLanguage = this.lernkartei.getWortEinsBeschreibung();
		this.sprache1_lbl = new JLabel(srcLanguage);
		this.sprache1_lbl_dh = new DynamicHelper(10, 2, 25, 25, this.getWidth(), this.getHeight(), 250, 50);
		this.sprache1_lbl.setBounds(this.sprache1_lbl_dh.getX(), this.sprache1_lbl_dh.getY(), this.sprache1_lbl_dh.getWidth(), this.sprache1_lbl_dh.getHeight());
		Font sprache1Font = this.sprache1_lbl.getFont().deriveFont(Font.PLAIN, 18f);
		this.sprache1_lbl.setFont(sprache1Font);
		this.add(this.sprache1_lbl);

		// Sprache 2 label
		String destLanguage = this.lernkartei.getWortZweiBeschreibung();
		this.sprache2_lbl = new JLabel(destLanguage);
		this.sprache2_lbl_dh = new DynamicHelper(10, 2, 25, 35, this.getWidth(), this.getHeight(), 250, 50);
		this.sprache2_lbl.setBounds(this.sprache2_lbl_dh.getX(), this.sprache2_lbl_dh.getY(), this.sprache2_lbl_dh.getWidth(), this.sprache2_lbl_dh.getHeight());
		Font sprache2Font = this.sprache2_lbl.getFont().deriveFont(Font.PLAIN, 18f);
		this.sprache2_lbl.setFont(sprache2Font);
		this.add(this.sprache2_lbl);

		// Sprache 1 text field to the right of the label
		this.sprache1_tf = new JTextField();
		this.sprache1_tf_dh = new DynamicHelper(20, 5, 60, 25, this.getWidth(), this.getHeight());
		this.sprache1_tf.setBounds(this.sprache1_tf_dh.getX(), this.sprache1_tf_dh.getY(), this.sprache1_tf_dh.getWidth(), this.sprache1_tf_dh.getHeight());
		this.add(this.sprache1_tf);

		// Sprache 2 text field to the right of the label
		this.sprache2_tf = new JTextField();
		this.sprache2_tf_dh = new DynamicHelper(20, 5, 60, 35, this.getWidth(), this.getHeight());
		this.sprache2_tf.setBounds(this.sprache2_tf_dh.getX(), this.sprache2_tf_dh.getY(), this.sprache2_tf_dh.getWidth(), this.sprache2_tf_dh.getHeight());
		this.add(this.sprache2_tf);

		// Tipp button
		this.tipp_btn = new JButton("Tipp");
		this.tipp_btn_dh = new DynamicHelper(15, 7, 25, 45, this.getWidth(), this.getHeight());
		this.tipp_btn.setBounds(this.tipp_btn_dh.getX(), this.tipp_btn_dh.getY(), this.tipp_btn_dh.getWidth(), this.tipp_btn_dh.getHeight());
		this.tipp_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				tipp_btnActionPerformed(evt);
			}
		});
		this.add(this.tipp_btn);

		// Weiter button
		this.weiter_btn = new JButton("Weiter");
		this.weiter_btn_dh = new DynamicHelper(15, 7, 60, 45, this.getWidth(), this.getHeight());
		this.weiter_btn.setBounds(this.weiter_btn_dh.getX(), this.weiter_btn_dh.getY(), this.weiter_btn_dh.getWidth(), this.weiter_btn_dh.getHeight());
		this.weiter_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				weiter_btnActionPerformed(evt);
			}
		});
		this.add(this.weiter_btn);

		// Falsch beantwortet label
		this.falsch_beantwortet_lbl = new JLabel("Falsch beantwortet");
		this.falsch_beantwortet_lbl_dh = new DynamicHelper(1, 1, 25, 55, this.getWidth(), this.getHeight(), 300, 30);
		this.falsch_beantwortet_lbl.setBounds(this.falsch_beantwortet_lbl_dh.getX(), this.falsch_beantwortet_lbl_dh.getY(), this.falsch_beantwortet_lbl_dh.getWidth(), this.falsch_beantwortet_lbl_dh.getHeight());
		Font falschFont = this.falsch_beantwortet_lbl.getFont().deriveFont(Font.PLAIN, 17f);
		this.falsch_beantwortet_lbl.setFont(falschFont);
		this.add(this.falsch_beantwortet_lbl);

		// Richtig beantwortet label
		this.richtig_beantwortet_lbl = new JLabel("Richtig beantwortet");
		this.richtig_beantwortet_lbl_dh = new DynamicHelper(1, 1, 60, 55, this.getWidth(), this.getHeight(), 300, 38);
		this.richtig_beantwortet_lbl.setBounds(this.richtig_beantwortet_lbl_dh.getX(), this.richtig_beantwortet_lbl_dh.getY(), this.richtig_beantwortet_lbl_dh.getWidth(), this.richtig_beantwortet_lbl_dh.getHeight());
		Font richtigFont = this.richtig_beantwortet_lbl.getFont().deriveFont(Font.PLAIN, 17f);
		this.richtig_beantwortet_lbl.setFont(richtigFont);
		this.add(this.richtig_beantwortet_lbl);

		// Falsch table
		String[] falschColumnNames = {srcLanguage, destLanguage};
		Object[][] falschData = {};
		this.falschTableModel = new DefaultTableModel(falschData, falschColumnNames);
		this.falschTable = new JTable(this.falschTableModel);
		this.falschTable_dh = new DynamicHelper(20, 28, 23, 60, this.getWidth(), this.getHeight());
		this.falschTable.setBounds(this.falschTable_dh.getX(), this.falschTable_dh.getY(), this.falschTable_dh.getWidth(), this.falschTable_dh.getHeight());
		this.falschTable.setBackground(Color.RED);
		Font falschTableFont = this.falschTable.getFont().deriveFont(Font.PLAIN, 15f);
		this.falschTable.setFont(falschTableFont);
		this.add(this.falschTable);

		// Richtig table
		String[] richtigColumnNames = {srcLanguage, destLanguage};
		Object[][] richtigData = {};
		this.richtigTableModel = new DefaultTableModel(richtigData, richtigColumnNames);
		this.richtigTable = new JTable(this.richtigTableModel);
		this.richtigTable_dh = new DynamicHelper(20, 28, 58, 60, this.getWidth(), this.getHeight());
		this.richtigTable.setBounds(this.richtigTable_dh.getX(), this.richtigTable_dh.getY(), this.richtigTable_dh.getWidth(), this.richtigTable_dh.getHeight());
		this.richtigTable.setBackground(Color.GREEN);
		Font richtigTableFont = this.richtigTable.getFont().deriveFont(Font.PLAIN, 15f);
		this.richtigTable.setFont(richtigTableFont);
		this.add(this.richtigTable);
		
        // Initialize the tabbed pane
        this.tabbedPane = new JTabbedPane();
		this.tabbedPane_dh = new DynamicHelper(94, 75, 2, 15, this.getWidth(), this.getHeight());
		for (int fach = 0; fach < this.faecherListe.size(); fach++) {
			
			JPanel tab = new JPanel();
			this.tabbedPane.addTab("Fach " + (fach + 1), null, tab, "Zum Fach " + (fach + 1) + " wechseln");	

			// Add an action listener to the tab
			this.tabbedPane.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					tipp_btn_pressed = 0;
					index_selected_fach = tabbedPane.getSelectedIndex();
					setNewKarte();

					// clear the tables
					richtigTableModel.setRowCount(0);
					falschTableModel.setRowCount(0);
				}
			});		
		}
		this.tabbedPane.setBounds(this.tabbedPane_dh.getX(), this.tabbedPane_dh.getY(), this.tabbedPane_dh.getWidth(), this.tabbedPane_dh.getHeight());
		this.tabbedPane.setSelectedIndex(this.index_selected_fach);
		this.add(this.tabbedPane);		

		// Set the current Karte
		this.setNewKarte();

		// Apply the styles
        if (AppSettings.isDarkMode()) {
            applyDarkModeStyles();
        }
		else {
            applyLightModeStyles();
        }

        // update the size and location of the components when the window is resized
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                titel_lbl_dh.updateWindowSize(getWidth(), getHeight());
                titel_lbl.setBounds(titel_lbl_dh.getX(), titel_lbl_dh.getY(), titel_lbl_dh.getWidth(), titel_lbl_dh.getHeight());
            
				home_btn_dh.updateWindowSize(getWidth(), getHeight());
				home_btn.setBounds(home_btn_dh.getX(), home_btn_dh.getY(), home_btn_width, home_btn_height);
			
				back_btn_dh.updateWindowSize(getWidth(), getHeight());
				back_btn.setBounds(back_btn_dh.getX(), back_btn_dh.getY(), back_btn_width, back_btn_height);
			
				sprache1_lbl_dh.updateWindowSize(getWidth(), getHeight());
				sprache1_lbl.setBounds(sprache1_lbl_dh.getX(), sprache1_lbl_dh.getY(), sprache1_lbl_dh.getWidth(), sprache1_lbl_dh.getHeight());

				tabbedPane_dh.updateWindowSize(getWidth(), getHeight());
				tabbedPane.setBounds(tabbedPane_dh.getX(), tabbedPane_dh.getY(), tabbedPane_dh.getWidth(), tabbedPane_dh.getHeight());
			
				sprache2_lbl_dh.updateWindowSize(getWidth(), getHeight());
				sprache2_lbl.setBounds(sprache2_lbl_dh.getX(), sprache2_lbl_dh.getY(), sprache2_lbl_dh.getWidth(), sprache2_lbl_dh.getHeight());

				sprache1_tf_dh.updateWindowSize(getWidth(), getHeight());
				sprache1_tf.setBounds(sprache1_tf_dh.getX(), sprache1_tf_dh.getY(), sprache1_tf_dh.getWidth(), sprache1_tf_dh.getHeight());

				sprache2_tf_dh.updateWindowSize(getWidth(), getHeight());
				sprache2_tf.setBounds(sprache2_tf_dh.getX(), sprache2_tf_dh.getY(), sprache2_tf_dh.getWidth(), sprache2_tf_dh.getHeight());

				tipp_btn_dh.updateWindowSize(getWidth(), getHeight());
				tipp_btn.setBounds(tipp_btn_dh.getX(), tipp_btn_dh.getY(), tipp_btn_dh.getWidth(), tipp_btn_dh.getHeight());
			
				weiter_btn_dh.updateWindowSize(getWidth(), getHeight());
				weiter_btn.setBounds(weiter_btn_dh.getX(), weiter_btn_dh.getY(), weiter_btn_dh.getWidth(), weiter_btn_dh.getHeight());

				falsch_beantwortet_lbl_dh.updateWindowSize(getWidth(), getHeight());
				falsch_beantwortet_lbl.setBounds(falsch_beantwortet_lbl_dh.getX(), falsch_beantwortet_lbl_dh.getY(), falsch_beantwortet_lbl_dh.getWidth(), falsch_beantwortet_lbl_dh.getHeight());
			
				richtig_beantwortet_lbl_dh.updateWindowSize(getWidth(), getHeight());
				richtig_beantwortet_lbl.setBounds(richtig_beantwortet_lbl_dh.getX(), richtig_beantwortet_lbl_dh.getY(), richtig_beantwortet_lbl_dh.getWidth(), richtig_beantwortet_lbl_dh.getHeight());
			
				falschTable_dh.updateWindowSize(getWidth(), getHeight());
				falschTable.setBounds(falschTable_dh.getX(), falschTable_dh.getY(), falschTable_dh.getWidth(), falschTable_dh.getHeight());

				richtigTable_dh.updateWindowSize(getWidth(), getHeight());
				richtigTable.setBounds(richtigTable_dh.getX(), richtigTable_dh.getY(), richtigTable_dh.getWidth(), richtigTable_dh.getHeight());
			}
		});
	}

    private void applyDarkModeStyles() {
		this.getContentPane().setBackground(new Color(62, 63, 65)); // Dunkelgrau

        // Schriftfarben
        titel_lbl.setForeground(Color.WHITE);
        sprache1_lbl.setForeground(Color.BLACK);
        sprache2_lbl.setForeground(Color.BLACK);
        falsch_beantwortet_lbl.setForeground(Color.BLACK);
        richtig_beantwortet_lbl.setForeground(Color.BLACK);

        // Textfelder
       // sprache1_tf.setBackground(new Color(62, 63, 65)); // Dunkleres Grau
       // sprache1_tf.setForeground(Color.WHITE);
       // sprache2_tf.setBackground(new Color(62, 63, 65)); // Dunkleres Grau
      //  sprache2_tf.setForeground(Color.WHITE);
        sprache1_tf.setBackground(Color.WHITE);
        sprache1_tf.setForeground(Color.BLACK);
        sprache2_tf.setBackground(Color.WHITE);
        sprache2_tf.setForeground(Color.BLACK);
        
        
        // Buttons
		this.tabbedPane.setBackground(new Color(62, 63, 65));
		this.tabbedPane.setForeground(Color.WHITE);
		//tabbedPane.setBackgroundAt(5, Color.BLACK);
		
        tipp_btn.setBackground(new Color(62, 63, 65)); // Hellgrau
        tipp_btn.setBorder(new LineBorder(Color.WHITE, 2));
        tipp_btn.setForeground(Color.WHITE);

        weiter_btn.setBackground(new Color(62, 63, 65)); // Dunkelgrau
        weiter_btn.setBorder(new LineBorder(Color.WHITE, 2));
        weiter_btn.setForeground(Color.WHITE);    }

    private void applyLightModeStyles() {
        // Hintergrundfarbe für das gesamte Fenster
        this.getContentPane().setBackground(new Color(241, 241, 241)); // Hellgrau

        // Schriftfarben
        titel_lbl.setForeground(Color.BLACK);
        sprache1_lbl.setForeground(Color.BLACK);
        sprache2_lbl.setForeground(Color.BLACK);
        falsch_beantwortet_lbl.setForeground(Color.BLACK);
        richtig_beantwortet_lbl.setForeground(Color.BLACK);

        // Textfelder
        sprache1_tf.setBackground(Color.WHITE);
        sprache1_tf.setForeground(Color.BLACK);
        sprache2_tf.setBackground(Color.WHITE);
        sprache2_tf.setForeground(Color.BLACK);

        // Buttons


        tipp_btn.setBackground(new Color(241, 241, 241)); // Hellgrau
        tipp_btn.setBorder(new LineBorder(Color.BLACK, 2));
        tipp_btn.setForeground(Color.BLACK);

        weiter_btn.setBackground(new Color(241, 241, 241)); // Hellgrau
        weiter_btn.setBorder(new LineBorder(Color.BLACK, 2));
        weiter_btn.setForeground(Color.BLACK);
    }

	private void weiter_btnActionPerformed(ActionEvent evt) {
		if (this.sprache1_tf.getText() == null || this.sprache1_tf.getText().isEmpty() || this.sprache2_tf.getText() == null || this.sprache2_tf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Bitte geben Sie eine Antwort ein", "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		}
	
		tipp_btn_pressed = 0;
		String sprache1_user_antwort = this.sprache1_tf.getText();
		String sprache2_user_antwort = this.sprache2_tf.getText();
		String karte1_antwort = this.curKarte.getWortEins();
		String karte2_antwort = this.curKarte.getWortZwei();

		// remove leading and trailing whitespaces
		sprache1_user_antwort = sprache1_user_antwort.trim();
		sprache2_user_antwort = sprache2_user_antwort.trim();

		if (this.lernkartei.getGrossKleinschreibung() == false) {
			sprache1_user_antwort = sprache1_user_antwort.toLowerCase();
			sprache2_user_antwort = sprache2_user_antwort.toLowerCase();
			karte1_antwort = karte1_antwort.toLowerCase();
			karte2_antwort = karte2_antwort.toLowerCase();
		}

		boolean isCorrect = false;
		if (sprache1_user_antwort.equals(karte1_antwort) && sprache2_user_antwort.equals(karte2_antwort))
			isCorrect = true;
		
		if (isCorrect) { // add if correct to the richtig table
			this.richtigTableModel.addRow(new Object[]{this.curKarte.getWortEins(), this.curKarte.getWortZwei()});
			this.richtigTable.setModel(this.richtigTableModel);
		}
		else { // add if wrong to the falsch table
			this.falschTableModel.addRow(new Object[]{this.curKarte.getWortEins(), this.curKarte.getWortZwei()});
			this.falschTable.setModel(this.falschTableModel);
		}

		if (isCorrect) {
			if (VokabeltrainerDB.setKarteRichtig(this.curKarte) == -2) {
				Fach tmp = new Fach();
				//tmp.setGelerntAm(new Date(0)); // set the date to 1970-01-01

				VokabeltrainerDB.hinzufuegenFach(this.index_selected_lernkartei, tmp);
				faecherListe = VokabeltrainerDB.getFaecher(this.index_selected_lernkartei);
				VokabeltrainerDB.setKarteRichtig(this.curKarte);

				List<Fach> faecher = VokabeltrainerDB.getFaecher(lernkartei.getNummer()); // update erinnerungsIntervall
				for (int i = 0; i < faecher.size(); i++) {
					Fach f = faecher.get(i);
					f.setErinnerungsIntervall(lernkartei.erinnerungsIntervall + i);
					VokabeltrainerDB.aendernFach(f);
				}
	
				// add tab to the tabbed pane
				JPanel tab = new JPanel();
				this.tabbedPane.addTab("Fach " + faecherListe.size(), null, tab, "Zum Fach " + faecherListe.size() + " wechseln");
			}
		}
		else {
			VokabeltrainerDB.setKarteFalsch(this.curKarte);

			JOptionPane.showMessageDialog(null, "Die richtige Antwort ist: " + this.curKarte.getWortEins() + " - " + this.curKarte.getWortZwei(), "Falsch", JOptionPane.ERROR_MESSAGE);
		}

		this.setNewKarte();
	}

	private int tipp_btn_pressed = 0;
	private void tipp_btnActionPerformed(ActionEvent evt) {
		// this function adds an letter to the answer text field
		if (this.curKarte == null)
			return;
		
		String sprache1_user_antwort = this.sprache1_tf.getText();
		String sprache2_user_antwort = this.sprache2_tf.getText();
		String karte1_antwort = this.curKarte.getWortEins();
		String karte2_antwort = this.curKarte.getWortZwei();

		tipp_btn_pressed += 1;
		// write the first letter of the answer to the text field
		if (sprache1_user_antwort.isEmpty() || sprache1_user_antwort.equals(karte1_antwort) == false) {
			this.sprache1_tf.setText(karte1_antwort.substring(0, tipp_btn_pressed > karte1_antwort.length() ? karte1_antwort.length() : tipp_btn_pressed));
		}
		else if (sprache2_user_antwort.isEmpty() || sprache2_user_antwort.equals(karte2_antwort) == false) {
			this.sprache2_tf.setText(karte2_antwort.substring(0, tipp_btn_pressed > karte2_antwort.length() ? karte2_antwort.length() : tipp_btn_pressed));
		}
	}

	private void setNewKarte() {
        this.curKarte = VokabeltrainerDB.getZufaelligeKarte(this.lernkartei.getNummer(), this.faecherListe.get(this.index_selected_fach).getNummer());
        if (this.curKarte == null) {
            this.sprache1_tf.setText("");
            this.sprache2_tf.setText("");
            return;
        }
        this.sprache1_tf.setText(this.curKarte.getWortEins());
        this.sprache2_tf.setText("");
	}

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