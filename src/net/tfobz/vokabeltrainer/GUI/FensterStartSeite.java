package net.tfobz.vokabeltrainer.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import net.tfobz.vokabeltrainer.utils.DynamicHelper;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;
import net.tfobz.vokabeltrainer.utils.AppSettings;
import net.tfobz.vokabeltrainer.utils.ToggleSwitch;
import net.tfobz.vokabeltrainer.utils.ToggleSwitchListener;

public class FensterStartSeite extends JFrame {
    private JButton lernen_btn;
    private DynamicHelper lernen_btn_dh;
    private JLabel titel_lbl;
    private DynamicHelper titel_lbl_dh;
    private JLabel subtitle_lbl;
    private DynamicHelper subtitle_lbl_dh;
    private JButton einstellungen_btn;
    private DynamicHelper einstellungen_btn_dh;
    private JButton github_btn;
    private DynamicHelper github_btn_dh;
    private ImageIcon github_icon;
    private int github_btn_width = 55;
    private int github_btn_height = 55;
    private JLabel darkmode_lbl;
    private DynamicHelper darkmode_lbl_dh;
    private ToggleSwitch darkmode_tgl;
    private DynamicHelper darkmode_tgl_dh;
    private int darkmode_tgl_width = 41;
    private int darkmode_tgl_height = 21;
	private JButton bell_btn;
	private DynamicHelper bell_btn_dh;
	private ImageIcon bell_icon;
    private int bell_btn_width = 40;
    private int bell_btn_height = 40;

    public FensterStartSeite() {
        this.setSize(800, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Vokabeltrainer");
        this.setLayout(null);
        this.setMinimumSize(new Dimension(700, 500));
        this.setMaximumSize(new Dimension(1000, 800));

        // Title
        this.titel_lbl = new JLabel("ToRaDuden");
        this.titel_lbl_dh = new DynamicHelper(32, 6, 49, 10, this.getWidth(), this.getHeight(), 564, 74, DynamicHelper.Location.CENTER);
        this.titel_lbl.setBounds(this.titel_lbl_dh.getX(), this.titel_lbl_dh.getY(), this.titel_lbl_dh.getWidth(), this.titel_lbl_dh.getHeight());
        Font titlefFont = this.titel_lbl.getFont().deriveFont(Font.BOLD, 50f);
        this.titel_lbl.setFont(titlefFont);
        this.add(this.titel_lbl);

        // Subtitle: 'Deine Lernsoftware'
        this.subtitle_lbl = new JLabel("Deine Lernsoftware");
        this.subtitle_lbl_dh = new DynamicHelper(32, 6, 53, 25, this.getWidth(), this.getHeight(), 10, 10, DynamicHelper.Location.CENTER);
        this.subtitle_lbl.setBounds(this.subtitle_lbl_dh.getX(), this.subtitle_lbl_dh.getY(), this.subtitle_lbl_dh.getWidth(), this.subtitle_lbl_dh.getHeight());
        Font subtitleFont = this.subtitle_lbl.getFont().deriveFont(Font.PLAIN, 21f);
        this.subtitle_lbl.setFont(subtitleFont);
        this.add(this.subtitle_lbl);

        // Learn button
        this.lernen_btn = new JButton("Learn");
        Font lernenFont = this.lernen_btn.getFont().deriveFont(Font.PLAIN, 17f);  // make text bigger
        this.lernen_btn.setFont(lernenFont);
        this.lernen_btn_dh = new DynamicHelper(19, 20, 58, 44, this.getWidth(), this.getHeight());
        this.lernen_btn.setBounds(this.lernen_btn_dh.getX(), this.lernen_btn_dh.getY(), this.lernen_btn_dh.getWidth(), this.lernen_btn_dh.getHeight());
        this.lernen_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                lernen_btnActionPerformed(evt);
            }
        });
        this.add(this.lernen_btn);

        // Settings button
        this.einstellungen_btn = new JButton("Einstellungen");
        Font einstellungenFont = this.einstellungen_btn.getFont().deriveFont(Font.PLAIN, 17f);  // make text bigger
        this.einstellungen_btn.setFont(einstellungenFont);
        this.einstellungen_btn_dh = new DynamicHelper(19, 20, 21, 44, this.getWidth(), this.getHeight());
        this.einstellungen_btn.setBounds(this.einstellungen_btn_dh.getX(), this.einstellungen_btn_dh.getY(), this.einstellungen_btn_dh.getWidth(), this.einstellungen_btn_dh.getHeight());
        this.einstellungen_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                einstellungen_btnActionPerformed(evt);
            }
        });
        this.add(this.einstellungen_btn);

        // GitHub button, icon inside icons/github.png
        this.github_btn = new JButton();
        this.github_btn.setContentAreaFilled(false); // Set background to be transparent
        this.github_btn.setBorderPainted(false); // Remove button border
        this.github_btn.setFocusPainted(false); // Remove button focus border
        this.github_icon = new ImageIcon(getClass().getResource("icons/Github.png"));
        this.github_btn_dh = new DynamicHelper(15, 15, 10, 87, this.getWidth(), this.getHeight(), DynamicHelper.Location.CENTER);
        Image originalImage = this.github_icon.getImage();
        Image scaledImage = originalImage.getScaledInstance(this.github_btn_width, this.github_btn_height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        this.github_btn.setIcon(scaledIcon);
        this.github_btn.setBounds(this.github_btn_dh.getX(), this.github_btn_dh.getY() + this.github_btn_dh.getY()/85, this.github_btn_width, this.github_btn_height);
        this.github_btn.addActionListener(e -> { // when clicked open https://github in the default browser
            try {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/sereok3/vokabeltrainer"));
            } catch (java.io.IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        });
        this.github_btn.setToolTipText("link zum github repository");  // add tooltip: "link zum github repository"
        this.add(this.github_btn);

        // Darkmode label
        this.darkmode_lbl = new JLabel("Darkmode");
        this.darkmode_lbl_dh = new DynamicHelper(10, 15, 80, 86, this.getWidth(), this.getHeight(), 144, 0, DynamicHelper.Location.CENTER);
        this.darkmode_lbl.setBounds(this.darkmode_lbl_dh.getX(), this.darkmode_lbl_dh.getY(), this.darkmode_lbl_dh.getWidth(), this.darkmode_lbl_dh.getHeight());
        Font darkmodeFont = this.darkmode_lbl.getFont().deriveFont(Font.BOLD, 15f);
        this.darkmode_lbl.setFont(darkmodeFont);
        this.add(this.darkmode_lbl);

        // Darkmode toggle button
        this.darkmode_tgl = new ToggleSwitch();
        this.darkmode_tgl_dh = new DynamicHelper(8, 4, 90, 86, this.getWidth(), this.getHeight(), DynamicHelper.Location.CENTER);
        this.darkmode_tgl.setBounds(this.darkmode_tgl_dh.getX(), this.darkmode_tgl_dh.getY() + this.darkmode_tgl_dh.getY()/82, this.darkmode_tgl_width, this.darkmode_tgl_height);
        this.darkmode_tgl.setActivated(AppSettings.isDarkMode());
        this.darkmode_tgl.setSwitchColor(java.awt.Color.LIGHT_GRAY);
        this.darkmode_tgl.setActiveSwitch(java.awt.Color.DARK_GRAY);
        this.darkmode_tgl.setBorderRadius(0);
        this.darkmode_tgl.setToggleSwitchListener(new ToggleSwitchListener() {
        	@Override
			public void onToggle(boolean activated) {
				if (AppSettings.isDarkMode())
					AppSettings.setDarkMode(false);
				else
					AppSettings.setDarkMode(true);
				
				if (AppSettings.isDarkMode())
					applyDarkModeStyles();
				else
					applyLightModeStyles();
			}

        });
        this.darkmode_tgl.setToolTipText("Darkmode aktivieren/deaktivieren");
        this.add(this.darkmode_tgl);

        // check if there is are some Lernkarteien to learn and show a bell icon
        if (VokabeltrainerDB.getLernkarteienErinnerung().size() > 0) {
            this.bell_btn = new JButton();
        	this.bell_btn.setBorderPainted(false); // Remove button border
            this.bell_btn.setFocusPainted(false); // Remove button focus border
        	this.bell_icon = new ImageIcon(getClass().getResource("icons/Bell.png"));
        	this.bell_btn_dh = new DynamicHelper(15, 20, 85, 60, this.getWidth(), this.getHeight(), DynamicHelper.Location.CENTER);
            Image originalBellImage = this.bell_icon.getImage();
        	Image scaledBellImage = originalBellImage.getScaledInstance(this.bell_btn_width, this.bell_btn_height, Image.SCALE_SMOOTH);
        	ImageIcon scaledBellIcon = new ImageIcon(scaledBellImage);
        	this.bell_btn.setIcon(scaledBellIcon);
            this.bell_btn.setBounds(this.bell_btn_dh.getX(), this.bell_btn_dh.getY() + this.bell_btn_dh.getY()/85, this.bell_btn_width, this.bell_btn_height);
        	this.bell_btn.setToolTipText("Du hast Lernkarteien, die du noch lernen musst!");
        	this.add(this.bell_btn);
        }

        // update the size and location of the components when the window is resized
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                titel_lbl_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
                titel_lbl.setBounds(titel_lbl_dh.getX(), titel_lbl_dh.getY(), titel_lbl_dh.getWidth(), titel_lbl_dh.getHeight());

                subtitle_lbl_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
                subtitle_lbl.setBounds(subtitle_lbl_dh.getX(), subtitle_lbl_dh.getY(), subtitle_lbl_dh.getWidth(), subtitle_lbl_dh.getHeight());

                lernen_btn_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
                lernen_btn.setBounds(lernen_btn_dh.getX(), lernen_btn_dh.getY(), lernen_btn_dh.getWidth(), lernen_btn_dh.getHeight());

                einstellungen_btn_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
                einstellungen_btn.setBounds(einstellungen_btn_dh.getX(), einstellungen_btn_dh.getY(), einstellungen_btn_dh.getWidth(), einstellungen_btn_dh.getHeight());

                github_btn_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
                github_btn.setBounds(github_btn_dh.getX(), github_btn_dh.getY() + github_btn_dh.getY()/85, github_btn_width, github_btn_height);

                darkmode_lbl_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
                darkmode_lbl.setBounds(darkmode_lbl_dh.getX(), darkmode_lbl_dh.getY(), darkmode_lbl_dh.getWidth(), darkmode_lbl_dh.getHeight());

                darkmode_tgl_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
                darkmode_tgl.setBounds(darkmode_tgl_dh.getX(), darkmode_tgl_dh.getY(), darkmode_tgl_width, darkmode_tgl_height);
                
                if (VokabeltrainerDB.getLernkarteienErinnerung().size() > 0) {
                    bell_btn_dh.updateWindowSize(evt.getComponent().getWidth(), evt.getComponent().getHeight());
                    bell_btn.setBounds(bell_btn_dh.getX(), bell_btn_dh.getY() + bell_btn_dh.getY()/85, bell_btn_width, bell_btn_height);
                }

             // set LightMode
        		if (AppSettings.isDarkMode())
        			applyDarkModeStyles();
        		else // set DarkMode
        			applyLightModeStyles();
            }
        });
    }
    

    private void einstellungen_btnActionPerformed(ActionEvent evt) {
        FensterEinstellungen einstellungen = new FensterEinstellungen();
        einstellungen.setVisible(true);
        einstellungen.setLocation(this.getLocation());
        dispose();
    }

    private void lernen_btnActionPerformed(ActionEvent evt) {
        FensterLernen lernen = new FensterLernen();
        lernen.setVisible(true);
        lernen.setLocation(this.getLocation());
        dispose();
    }

    private void applyDarkModeStyles() {
		// Update styles for dark mode
		this.getContentPane().setBackground(new Color(62, 63, 65)); // Dunkelgrau

		// Labels
		this.titel_lbl.setForeground(Color.WHITE);
		this.subtitle_lbl.setForeground(Color.WHITE);
		this.darkmode_lbl.setForeground(Color.WHITE);
        this.darkmode_tgl.setBackground(Color.BLACK); // Hintergrundfarbe
        this.darkmode_tgl.setActiveSwitch(new Color(226, 226, 226)); // Hintergrundfarbe


		// Buttons
		this.lernen_btn.setBackground(new Color(62, 63, 65)); // Mittelgrau
		this.lernen_btn.setBorder(new LineBorder(Color.WHITE, 2));
		this.lernen_btn.setForeground(Color.WHITE);
		this.einstellungen_btn.setBackground(new Color(62, 63, 65)); // Mittelgrau
		this.einstellungen_btn.setBorder(new LineBorder(Color.WHITE, 2));
		this.einstellungen_btn.setForeground(Color.WHITE);

	}

	private void applyLightModeStyles() {
		// Update styles for light mode
		this.getContentPane().setBackground(new Color(241, 241, 241));

		// Labels
		this.titel_lbl.setForeground(Color.BLACK);
		this.subtitle_lbl.setForeground(Color.BLACK);
		this.darkmode_lbl.setForeground(Color.BLACK);
        this.darkmode_tgl.setBackground(Color.BLACK);
        this.darkmode_tgl.setActiveSwitch(new Color(226, 226, 226)); 


		// Buttons
		this.lernen_btn.setBackground(new Color(241, 241, 241)); 
		this.lernen_btn.setBorder(new LineBorder(Color.BLACK, 2));
		this.lernen_btn.setForeground(Color.BLACK);
		this.einstellungen_btn.setBackground(new Color(241, 241, 241)); 
		this.einstellungen_btn.setBorder(new LineBorder(Color.BLACK, 2));
		this.einstellungen_btn.setForeground(Color.BLACK);
	}

    
    /*
    This is a fix to prevent the window from being resized larger than
    Because this.setMaximumSize() doesn't work !!!
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