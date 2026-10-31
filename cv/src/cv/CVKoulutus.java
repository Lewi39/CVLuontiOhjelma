package cv;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.YearMonth;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import java.awt.Toolkit;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

public class CVKoulutus {
	private YearMonth ym = YearMonth.now();

	private String savePath = CVAlkusivu.savePath;

	private JFrame frmKoulutus;

	private JFrame f = frmKoulutus;

	private JTextField txtTutkinto;

	private JTextField txtKoulu;

	private JComboBox<String> cbAloitusKuu;

	private JComboBox<String> cboxTutkinto;

	private JComboBox<String> cbAloitusVuosi;

	private JComboBox<String> cbLopetusKuu;

	private JComboBox<String> cbLopetusVuosi;

	public String koulutus = "";

	public String koulutusMuu = "";

	public String tallennettu = "";

	private JTextArea txtEsi;

	private JTextArea txtMuuta;

	private final ButtonGroup buttonGroup = new ButtonGroup();

	private JRadioButton rdbtnKoulutus;

	private JRadioButton rdbtnTyokortti;

	private JRadioButton rdbtnNollaus;

	private boolean jatkuva = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					CVKoulutus window = new CVKoulutus();
					window.frmKoulutus.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CVKoulutus() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmKoulutus = new JFrame();
		frmKoulutus.setResizable(false);
		frmKoulutus.getContentPane().setBackground(new Color(0xD8E8F0));
		frmKoulutus.setIconImage(
				Toolkit.getDefaultToolkit().getImage(CVKoulutus.class.getResource("/Images/Koulutus.png")));
		frmKoulutus.setTitle("Koulutus");
		frmKoulutus.setSize(900, 600);
		frmKoulutus.setLocationRelativeTo(null);
		frmKoulutus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKoulutus.getContentPane().setLayout(null);
		JLabel lblKoulutus = new JLabel("Koulutus");
		lblKoulutus.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblKoulutus.setBounds(10, 11, 389, 50);
		frmKoulutus.getContentPane().add(lblKoulutus);
		txtTutkinto = new JTextField();
		txtTutkinto.setEnabled(false);
		txtTutkinto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtTutkinto.setColumns(10);
		txtTutkinto.setBackground(new Color(0xF0F0F0));
		txtTutkinto.setBounds(10, 120, 230, 30);
		txtTutkinto.getAccessibleContext().getAccessibleEditableText();
		frmKoulutus.getContentPane().add(txtTutkinto);
		cboxTutkinto = new JComboBox<String>();
		cboxTutkinto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtTutkinto.setEnabled(cboxTutkinto.getSelectedIndex() == 1);
				txtTutkinto.setBackground(new Color((cboxTutkinto.getSelectedIndex() == 1) ? 0xFFFFFF : 0xF0F0F0));
			}
		});
		cboxTutkinto.setModel(new DefaultComboBoxModel<String>(new String[] { "",
				"Muu (Kirjoita alla olevaan kenttään)", "Ajoneuvoalan perustutkinto",
				"Kone- ja tuotantotekniikan perustutkinto", "Liiketoiminnan perustutkinto", "Logistiikan perustutkinto",
				"Media-alan ja kuvallisen ilmaisun perustutkinto", "Pintakäsittelyalan perustutkinto",
				"Puhtaus- ja kiinteistöpalvelualan perustutkinto", "Ravintola- ja catering-alan perustutkinto",
				"Taideteollisuusalan perustutkinto", "Tieto-ja viestintätekniikan perustutkinto" }));
		cboxTutkinto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cboxTutkinto.setBounds(10, 90, 230, 30);
		cboxTutkinto.setSelectedIndex(0);
		frmKoulutus.getContentPane().add(cboxTutkinto);
		txtEsi = new JTextArea();
		txtEsi.setLineWrap(true);
		txtEsi.setWrapStyleWord(true);
		txtEsi.setEditable(false);
		JButton btnLisaa = new JButton("Lisää uusi koulutus");
		btnLisaa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowPreview();
			}
		});
		btnLisaa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLisaa.setBounds(20, 345, 316, 50);
		btnLisaa.getAccessibleContext()
				.setAccessibleDescription("Painamalla tätä nappia, lisäät syöttämäsi tiedot esikatseluun.");
		frmKoulutus.getContentPane().add(btnLisaa);
		JButton btnTallenna = new JGradientButton("Tallenna");
		btnTallenna.setBackground(new Color(0xB0F0A8));
		btnTallenna.setIcon(new ImageIcon(CVKoulutus.class.getResource("/Images/Tallenna.png")));
		btnTallenna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveData();
			}
		});
		btnTallenna.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTallenna.setBounds(459, 417, 160, 50);
		btnTallenna.getAccessibleContext()
				.setAccessibleDescription("Painamalla tätä nappia, lisäämäsi tiedot tallennetaa.");
		frmKoulutus.getContentPane().add(btnTallenna);
		JButton btnTyhjenn = new JButton("Tyhjennä");
		btnTyhjenn.setIcon(new ImageIcon(CVKoulutus.class.getResource("/Images/Tyhjenna.png")));
		btnTyhjenn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clear();
			}
		});
		btnTyhjenn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTyhjenn.setBounds(459, 478, 160, 50);
		btnTyhjenn.getAccessibleContext()
				.setAccessibleDescription("Painamalla tätä nappia, tyhjennät syöttökentissä olevat tiedot.");
		frmKoulutus.getContentPane().add(btnTyhjenn);
		JButton btnPoista = new JGradientButton("Poista");
		btnPoista.setBackground(new Color(0xE8A0B0));
		btnPoista.setIcon(new ImageIcon(CVKoulutus.class.getResource("/Images/Poista.png")));
		btnPoista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DelData();
			}
		});
		btnPoista.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPoista.setBounds(691, 417, 160, 50);
		btnPoista.getAccessibleContext()
				.setAccessibleDescription("Painamalla tätä nappia, poistat tallentamasi tiedot.");
		frmKoulutus.getContentPane().add(btnPoista);
		JButton btnTakaisin = new JButton("Alkusivu");
		btnTakaisin.setIcon(new ImageIcon(CVKoulutus.class.getResource("/Images/Alkusivu.png")));
		btnTakaisin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tallennettu.compareTo(txtEsi.getText()) == 0) {
					frmKoulutus.setVisible(false);
					CVAlkusivu.main(null);
				} else {
					int result = JcustomDialog.showConfirmDialog(f, "Haluatko poistua tallentamatta muutoksia",
							"Varoitus!", JcustomDialog.QUESTION_MESSAGE, JcustomDialog.YES_NO_OPTION,
							JcustomDialog.NO_OPTION);

					if (result == JcustomDialog.YES_OPTION) {
						frmKoulutus.setVisible(false);
						CVAlkusivu.main(null);
					}
				}
			}
		});
		btnTakaisin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTakaisin.setBounds(691, 478, 160, 50);
		btnTakaisin.getAccessibleContext()
				.setAccessibleDescription("Painamalla tätä nappia, siirryt takaisin alkusivulle.");
		frmKoulutus.getContentPane().add(btnTakaisin);
		cbAloitusKuu = new JComboBox<String>();
		cbAloitusKuu.setModel(
				new DefaultComboBoxModel<String>(new String[] { "Tammikuu", "Helmikuu", "Maaliskuu", "Huhtikuu",
						"Toukokuu", "Kesäkuu", "Heinäkuu", "Elokuu", "Syyskuu", "Lokakuu", "Marraskuu", "Joulukuu" }));
		cbAloitusKuu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbAloitusKuu.setBounds(10, 189, 130, 37);
		cbAloitusKuu.setSelectedIndex(0);
		frmKoulutus.getContentPane().add(cbAloitusKuu);
		cbAloitusVuosi = new JComboBox<String>();
		cbAloitusVuosi.setModel(new DefaultComboBoxModel<String>(getYearList(0)));
		cbAloitusVuosi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbAloitusVuosi.setBounds(198, 189, 130, 37);
		cbAloitusVuosi.setSelectedIndex(100);
		frmKoulutus.getContentPane().add(cbAloitusVuosi);
		JLabel lblTutkinto = new JLabel("Tutkinto");
		lblTutkinto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTutkinto.setBounds(10, 61, 200, 31);
		lblTutkinto.setLabelFor(cboxTutkinto);
		frmKoulutus.getContentPane().add(lblTutkinto);
		txtKoulu = new JTextField();
		txtKoulu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtKoulu.setColumns(10);
		txtKoulu.setBounds(250, 90, 150, 37);
		txtKoulu.getAccessibleContext().getAccessibleEditableText();
		frmKoulutus.getContentPane().add(txtKoulu);
		JLabel lblKoulu = new JLabel("Oppilaitos");
		lblKoulu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblKoulu.setBounds(250, 61, 200, 31);
		lblKoulu.setLabelFor(txtKoulu);
		frmKoulutus.getContentPane().add(lblKoulu);
		JLabel lblAlkamisp = new JLabel("Alkamispäivä");
		lblAlkamisp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAlkamisp.setBounds(10, 147, 200, 31);
		lblAlkamisp.setLabelFor(cbAloitusKuu);
		lblAlkamisp.setLabelFor(cbAloitusVuosi);
		frmKoulutus.getContentPane().add(lblAlkamisp);
		JLabel lblPaattymisP = new JLabel("Päättymispäivä");
		lblPaattymisP.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPaattymisP.setBounds(10, 237, 200, 31);
		lblPaattymisP.setLabelFor(cbLopetusKuu);
		lblPaattymisP.setLabelFor(cbLopetusVuosi);
		frmKoulutus.getContentPane().add(lblPaattymisP);
		cbLopetusVuosi = new JComboBox<String>();
		cbLopetusVuosi.setModel(new DefaultComboBoxModel<String>(getYearList(0)));
		cbLopetusVuosi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbLopetusVuosi.setBounds(198, 280, 130, 37);
		cbLopetusVuosi.setSelectedIndex(100);
		frmKoulutus.getContentPane().add(cbLopetusVuosi);
		cbLopetusKuu = new JComboBox<String>();
		cbLopetusKuu.setModel(
				new DefaultComboBoxModel<String>(new String[] { "Tammikuu", "Helmikuu", "Maaliskuu", "Huhtikuu",
						"Toukokuu", "Kesäkuu", "Heinäkuu", "Elokuu", "Syyskuu", "Lokakuu", "Marraskuu", "Joulukuu" }));
		cbLopetusKuu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbLopetusKuu.setBounds(10, 280, 130, 37);
		cbLopetusKuu.setSelectedIndex(0);
		frmKoulutus.getContentPane().add(cbLopetusKuu);
		JLabel lblEsikatselu = new JLabel("Esikatselu");
		lblEsikatselu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEsikatselu.setBounds(445, 11, 212, 37);
		frmKoulutus.getContentPane().add(lblEsikatselu);
		koulutusHaku();
		// Paneeli scrollbaarille
		JPanel panel = new JPanel();
		panel.setBounds(445, 48, 429, 347);
		frmKoulutus.getContentPane().add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		scrollPane.setViewportView(txtEsi);
		txtMuuta = new JTextArea();
		txtMuuta.setWrapStyleWord(true);
		txtMuuta.setLineWrap(true);
		txtMuuta.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMuuta.setBounds(10, 448, 338, 37);
		txtMuuta.getAccessibleContext().getAccessibleEditableText();
		frmKoulutus.getContentPane().add(txtMuuta);
		JButton btnLisUusiMuu = new JButton("Lisää uusi Muu koulutus/työkortti");
		btnLisUusiMuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowPreviewOther();
			}
		});
		btnLisUusiMuu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLisUusiMuu.setBounds(20, 496, 316, 50);
		btnLisUusiMuu.getAccessibleContext()
				.setAccessibleDescription("Painamalla tätä nappia, lisäät syöttämäsi muut tiedot esikatseluun.");
		frmKoulutus.getContentPane().add(btnLisUusiMuu);
		rdbtnTyokortti = new JRadioButton("Työkortti");
		buttonGroup.add(rdbtnTyokortti);
		rdbtnTyokortti.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnTyokortti.setBounds(177, 402, 138, 39);
		rdbtnTyokortti.setOpaque(false);
		frmKoulutus.getContentPane().add(rdbtnTyokortti);
		rdbtnKoulutus = new JRadioButton("Muu koulutus");
		buttonGroup.add(rdbtnKoulutus);
		rdbtnKoulutus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnKoulutus.setBounds(17, 402, 143, 39);
		rdbtnKoulutus.setOpaque(false);
		frmKoulutus.getContentPane().add(rdbtnKoulutus);
		JCheckBox chckbxJatkuva = new JCheckBox("jatkuva");
		chckbxJatkuva.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				jatkuva = e.getStateChange() == ItemEvent.SELECTED;
				cbLopetusKuu.setEnabled(!jatkuva);
				cbLopetusVuosi.setEnabled(!jatkuva);
			}
		});
		chckbxJatkuva.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxJatkuva.setBounds(334, 287, 80, 23);
		chckbxJatkuva.setOpaque(false);
		chckbxJatkuva.getAccessibleContext().setAccessibleDescription("Rastita tämä kohta, jos opintosi jatkuvat");
		frmKoulutus.getContentPane().add(chckbxJatkuva);
		JLabel imgBG = new JLabel("");
		imgBG.setIcon(new ImageIcon(getClass().getResource("/Images/background_overlay.png")));
		imgBG.setBounds(0, 0, 884, 561);
		frmKoulutus.getContentPane().add(imgBG);
		rdbtnNollaus = new JRadioButton("");
		buttonGroup.add(rdbtnNollaus);
		borderChange b = new borderChange();
		b.addBorder(frmKoulutus);
		Util util = new Util();
		util.setKeyListener(frmKoulutus);
	}

	public void ShowPreview() {
		StringBuilder sb = new StringBuilder();
		setNormalBorders();
		// Lokaali muuttuja, mikä on käytössä vain ShowPreview methodissa
		// var antaa coderunnerin päättä mikä on tämän muuttuja tyyppi
		var _degree = txtTutkinto.getText();
		var _elsedegree = cboxTutkinto.getSelectedItem();
		var _school = txtKoulu.getText();
		var _startingM = cbAloitusKuu;
		var _startingY = cbAloitusVuosi;
		var _endingM = cbLopetusKuu;
		var _endingY = cbLopetusVuosi;

		if (cboxTutkinto.getSelectedIndex() == 1) {
			sb.append(String.format("Oppilaitos: %s\nTutkinto: %s\nAloitus pvm: %s %s\nLopetus pvm: %s %s\n", _school,
					_degree, _startingM.getSelectedItem(), _startingY.getSelectedItem(),
					jatkuva ? "jatkuva" : _endingM.getSelectedItem(), jatkuva ? "" : _endingY.getSelectedItem()));
		} else {
			sb.append(String.format("Oppilaitos: %s\nTutkinto: %s\nAloitus pvm: %s %s\nLopetus pvm: %s %s\n", _school,
					_elsedegree, _startingM.getSelectedItem(), _startingY.getSelectedItem(),
					jatkuva ? "jatkuva" : _endingM.getSelectedItem(), jatkuva ? "" : _endingY.getSelectedItem()));
		}

		try {
			// Muuttaa puuttuvien kenttien rajan punaiseksi
			if (cboxTutkinto.getSelectedIndex() == 0)
				markError(cboxTutkinto);
			else if (cboxTutkinto.getSelectedIndex() == 1 && _degree.isEmpty())
				markError(txtTutkinto);
			if (_school.isEmpty())
				markError(txtKoulu);

			/*
			 * if(_startingM.getSelectedIndex()==0) markError(cbAloitusKuu);
			 * if(_endingM.getSelectedIndex()==0&& !jatkuva) markError(cbLopetusKuu);
			 */
			// Tarkistaa, että kaikkiin kuukausiin & vuosiin on annettu arvot
			/*
			 * if (_startingM.getSelectedIndex() == 0 || _endingM.getSelectedIndex() == 0 &&
			 * !jatkuva) JDialogDemo.showMessageDialog(null,
			 * "Valitse kaikki päivämäärät koulutukseen", "Virhe!", 0);
			 */
			// Tarkistaa, että tekstikentät on täytetty pakollisista kentistä
			if ((!_degree.isEmpty() && !_school.isEmpty()) && cboxTutkinto.getSelectedIndex() != 0
					|| cboxTutkinto.getSelectedIndex() != 1 && cboxTutkinto.getSelectedIndex() != 0
							&& !_school.isEmpty()) {
				String UusiTeksti = sb.toString();
				koulutus += UusiTeksti + "\n";
				txtEsi.setText(koulutus + koulutusMuu);
				Clear();
			}
			// Viesti, joka palautetaan, jos mitään kenttää ei olla täytetty
			else
				JcustomDialog.showMessageDialog(null, "Täytä lomakkeesta kaikki kentät ennen tallentamisen yrittämistä",
						"Virhe", 0);
			// Palauttaa virheen hallinta viestin & kertoo mikä sen aiheutti
		} catch (Exception ex) {
			System.err.println(String.format("try-catch Error: \n%s", ex));
		}
	}

	public void ShowPreviewOther() {
		setNormalBorders();
		var _info = txtMuuta.getText();

		try {

			if (_info.isEmpty()) {
				markError(txtMuuta);
				JcustomDialog.showMessageDialog(null, "Täytä lomakkeesta kaikki tiedot ennen tallentamisen yrittämistä",
						"Virhe", 0);
			} else if (rdbtnKoulutus.isSelected()) {
				String UusiTeksti = "Muu koulutus: " + _info + "\n";
				koulutusMuu += UusiTeksti + "\n";
				txtEsi.setText(koulutus + koulutusMuu);
				txtMuuta.setText("");
			} else if (rdbtnTyokortti.isSelected()) {
				String UusiTeksti = "Työkortti: " + _info + "\n";
				koulutusMuu += UusiTeksti + "\n";
				txtEsi.setText(koulutus + koulutusMuu);
				txtMuuta.setText("");
			} else {
				JcustomDialog.showMessageDialog(null, "Valitse, haluatko lisätä Muun koulutuksen vai Työkortin", "Virhe",
						0);
			}
		} catch (Exception ex) {
			System.err.println(String.format("try-catch Error: \n%s", ex));
		}
	}

	private void koulutusHaku() {
		File tiedosto = new File(savePath + "Koulutus.txt");
		File tiedosto2 = new File(savePath + "KoulutusMuu.txt");

		if (tiedosto.exists() && tiedosto2.exists()) {

			try {
				String rivi;
				BufferedReader br = new BufferedReader(new FileReader(savePath + "Koulutus.txt"));

				while ((rivi = br.readLine()) != null) {
					koulutus += rivi + "\n";
				}
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				String rivi;
				BufferedReader br = new BufferedReader(new FileReader(savePath + "KoulutusMuu.txt"));

				while ((rivi = br.readLine()) != null) {
					koulutusMuu += rivi + "\n";
				}
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			txtEsi.setText(koulutus + koulutusMuu);
			tallennettu = txtEsi.getText();
		}
	}

	public void Clear() {
		txtTutkinto.setText("");
		txtKoulu.setText("");
		cboxTutkinto.setSelectedIndex(0);
		cbAloitusKuu.setSelectedIndex(0);
		cbAloitusVuosi.setSelectedIndex(100);
		cbLopetusKuu.setSelectedIndex(0);
		cbLopetusVuosi.setSelectedIndex(100);
		txtMuuta.setText("");
		rdbtnNollaus.setSelected(true);
		setNormalBorders();
	}

	public void SaveData() {
		tallennettu = txtEsi.getText();

		if (txtEsi.getText() != null) {

			try {
				FileWriter fw = new FileWriter(savePath + "Koulutus.txt", false);
				fw.write(koulutus);
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				FileWriter fw = new FileWriter(savePath + "KoulutusMuu.txt", false);
				fw.write(koulutusMuu);
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (CVAlkusivu.esikatselu)
			CVEsikatselu.PreviewRefresh(null);
		JcustomDialog.showMessageDialog(f,"Tiedot tallennettu", "Ilmoitus", JcustomDialog.PLAIN_MESSAGE);
	}

	public void DelData() {
		int result = JcustomDialog.showConfirmDialog(f, "Haluatko varmasti poistaa tiedot", "Varoitus!",
				JcustomDialog.QUESTION_MESSAGE, JcustomDialog.YES_NO_OPTION, JcustomDialog.NO_OPTION);

		if (result == JcustomDialog.YES_OPTION) {

			try {
				FileWriter fw = new FileWriter(savePath + "Koulutus.txt", false);
				fw.write("");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				FileWriter fw = new FileWriter(savePath + "KoulutusMuu.txt", false);
				fw.write("");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			koulutusMuu = "";
			koulutus = "";
			tallennettu = "";
			txtEsi.setText(null);
			JcustomDialog.showMessageDialog(f,"Tiedot poistettu", "Ilmoitus", JcustomDialog.PLAIN_MESSAGE);
		}
	}

	private void markError(JComponent comp) {
		comp.setBorder(new LineBorder(new Color(220, 53, 69), 2));
	}

	private void setNormalBorders() {
		txtTutkinto.setBorder(BorderFactory.createLineBorder(Color.black));
		txtKoulu.setBorder(BorderFactory.createLineBorder(Color.black));
		txtMuuta.setBorder(BorderFactory.createLineBorder(Color.black));
		cbAloitusKuu.setBorder(null);
		cbLopetusKuu.setBorder(null);
		cboxTutkinto.setBorder(null);
	}

	private String[] getYearList(int offset) {
		int year = ym.getYear();
		String[] years = new String[201];
		for (var i = 0; i < years.length; i++)
			years[i] = String.valueOf((year + 100 + offset) - i);
		return years;
	}
}
