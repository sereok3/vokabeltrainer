package net.tfobz.vokabeltrainer.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;
import net.tfobz.vokabeltrainer.utils.AppSettings;

public class FensterLernen extends JFrame {
    private DefaultTableModel table_model;
    private JTable table;
    private JScrollPane scroll_pane;
    private JButton home_btn;
    private int home_btn_width = 40;
    private int home_btn_height = 40;
    private ImageIcon home_icon;
    private JPanel topLeft_panel; 
    private JButton erinnerungAbgelaufen_btn;

    public FensterLernen() {
        this.setSize(800, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Lernen");
        this.setLayout(null);
        this.setMinimumSize(new Dimension(700, 500));
        this.setMaximumSize(new Dimension(1000, 800));
        this.setLayout(new BorderLayout());

        // Create the table model with one column
        this.table_model = new DefaultTableModel();
        this.table_model.addColumn("<html><b><font size=+2>Karteien</font></b></html>"); // karteien Column with BOLD font + bigger font size
        this.table = new JTable(this.table_model);
        this.table.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        this.table.setDefaultEditor(Object.class, null); // disable editing of the table
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Set the selection mode to single selection
        this.scroll_pane = new JScrollPane(this.table);

        // make table rows bigger + center text + Font size
        this.table.setRowHeight(30);
        this.table.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 16));
        // center text
        this.table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (c instanceof javax.swing.JLabel) {
                    ((javax.swing.JLabel) c).setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                }
                return c;
            }
        });

        // load all Karteien from the database and add them to the table multithreaded
        ArrayList<Integer> karteiIDs = new ArrayList<>();
        for (Lernkartei kartei : VokabeltrainerDB.getLernkarteien())
            karteiIDs.add(kartei.getNummer());
        for (int karteiID : karteiIDs) {
            this.table_model.addRow(new Object[] {VokabeltrainerDB.getLernkartei(karteiID).getBeschreibung()});
        }

    	// Add a ListSelectionListener to the table
		this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// Check if the selection is not empty
				if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
					int selectedRow = table.getSelectedRow();
					if(selectedRow != -1) {
                        ArrayList<Integer> karteiIDs = new ArrayList<>();
                        for (Lernkartei kartei : VokabeltrainerDB.getLernkarteien())
                            karteiIDs.add(kartei.getNummer());
                        int karteiID = karteiIDs.get(selectedRow);
                        
                        FensterKarteiLernen karteiLernen = new FensterKarteiLernen(karteiID);
                        karteiLernen.setVisible(true);
                        karteiLernen.setLocation(getLocation());
                        dispose();	
                    }
				}
			}
		});
        
        // create a panel for the top-left buttons
        this.topLeft_panel = new JPanel(new BorderLayout());

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
        this.home_btn.setPreferredSize(new Dimension(this.home_btn_width+ 5, this.home_btn_height));
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
        this.topLeft_panel.add(home_btn, BorderLayout.WEST);

        // Check for expired flashcards
        List<Lernkartei> abgelaufen_lernkarteien = VokabeltrainerDB.getLernkarteienErinnerung();
        if (!abgelaufen_lernkarteien.isEmpty()) {
            int size = abgelaufen_lernkarteien.size();
            // If there are expired flashcards, create and configure the button
            this.erinnerungAbgelaufen_btn = new JButton(size + " Fach zu lernen!");
            this.erinnerungAbgelaufen_btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // When the button is clicked, show a message box with the names of expired flashcards
                    StringBuilder message = new StringBuilder("Lernkarteien:\n\n");
                    for (Lernkartei lernkartei : abgelaufen_lernkarteien) {
                        message.append(lernkartei.getBeschreibung()).append("\n");
                    }
                    JOptionPane.showMessageDialog(FensterLernen.this, message.toString(), "Diese Lernkarteien sind noch zu lernen", JOptionPane.ERROR_MESSAGE);
                }
            });
            this.topLeft_panel.add(erinnerungAbgelaufen_btn, BorderLayout.EAST); // Add the button to the top left panel
        }

        // Add components to the main frame
        this.add(this.scroll_pane, BorderLayout.CENTER);
        this.add(this.topLeft_panel, BorderLayout.NORTH);

        if (AppSettings.isDarkMode()) {
			applyDarkModeStyles();
		}
        else {
			applyLightModeStyles();
		}
    }

    private void applyDarkModeStyles() {
        // Update styles for dark mode
    	this.getContentPane().setBackground(new Color(62, 63, 65)); // Dunkles Schwarz

		// Tabelle
		this.table.setBackground(new Color(62, 63, 65)); // Dunkleres Grau
		this.table.setForeground(Color.WHITE);

		this.table.getColumnModel().getColumn(0).setHeaderValue("<html><b><font size=+2 color = 'white'>Karteien</font></b></html>");	
		this.table.getTableHeader().setBackground(new Color(62, 63, 65));
		this.scroll_pane.getViewport().setBackground(new Color(62, 63, 65));
		this.scroll_pane.setBackground(new Color(62, 63, 65)); // Dunkles Schwarz

        if (this.erinnerungAbgelaufen_btn != null) {
            this.erinnerungAbgelaufen_btn.setBackground(new Color(62, 63, 65)); // Dunkles Schwarz
            this.erinnerungAbgelaufen_btn.setForeground(Color.WHITE);
        }

		this.topLeft_panel.setBackground(new Color(62, 63, 65)); // Dunkles Schwarz
    }

    private void applyLightModeStyles() {
        // Update styles for light mode
    	this.getContentPane().setBackground(new Color(241, 241, 241)); // Dunkles Schwarz

		// Tabelle
		this.table.setBackground(new Color(241, 241, 241)); // Dunkleres Grau
		this.table.setForeground(Color.BLACK);

		this.table.getColumnModel().getColumn(0).setHeaderValue("<html><b><font size=+2 color = 'black'>Karteien</font></b></html>");	
		this.table.getTableHeader().setBackground(new Color(241, 241, 241));
		this.scroll_pane.getViewport().setBackground(new Color(241, 241, 241));
		this.scroll_pane.setBackground(new Color(241, 241, 241)); // Dunkles Schwarz

        if (this.erinnerungAbgelaufen_btn != null) {
            this.erinnerungAbgelaufen_btn.setBackground(new Color(241, 241, 241)); // Dunkles Schwarz
            this.erinnerungAbgelaufen_btn.setForeground(Color.BLACK);
        }

		this.topLeft_panel.setBackground(new Color(241, 241, 241)); // Dunkles Schwarz

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