package cv;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JCheckBox;

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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.YearMonth;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class CVTyokokemus
{
	private YearMonth ym = YearMonth.now();

	private String savePath = CVAlkusivu.savePath;

	private JFrame frmTykokemus;

	private JFrame f = frmTykokemus;

	private JTextField txtNimike;

	private JTextField txtTyonantaja;

	private JTextArea txtLisa;

	private JTextArea txtEsi;

	private JComboBox <String> cbAloitusKuu;

	private JComboBox <String> cbAloitusVuosi;

	private JComboBox <String> cbLopetusKuu;

	private JComboBox <String> cbLopetusVuosi;

	public String tyokokemus = "";

	public String tallennettu = "";

	private boolean jatkuva = false;

	Util util = new Util();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{

				try
				{
					CVTyokokemus window = new CVTyokokemus();
					window.frmTykokemus.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CVTyokokemus()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmTykokemus = new JFrame();
		frmTykokemus.setResizable(false);
		frmTykokemus.setTitle("Työkokemus");
		frmTykokemus.getContentPane().setBackground(new Color(0xD8E8F0));
		frmTykokemus.setIconImage(
		        Toolkit.getDefaultToolkit().getImage(CVTyokokemus.class.getResource("/Images/Tyokokemus.png")));
		frmTykokemus.setSize(900, 600);
		frmTykokemus.setLocationRelativeTo(null);
		frmTykokemus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTykokemus.getContentPane().setLayout(null);
		JLabel lblNewLabel = new JLabel("Työkokemus");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setBounds(10, 11, 150, 50);
		frmTykokemus.getContentPane().add(lblNewLabel);
		txtNimike = new JTextField();
		txtNimike.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtNimike.setColumns(10);
		txtNimike.setBounds(10, 90, 150, 37);
		txtNimike.getAccessibleContext().getAccessibleEditableText();
		frmTykokemus.getContentPane().add(txtNimike);
		JButton btnNewButton = new JButton("Lisää uusi työkokemus");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ShowPreview();
			}
		});
		txtEsi = new JTextArea();
		txtEsi.setLineWrap(true);
		txtEsi.setWrapStyleWord(true);
		txtEsi.setEditable(false);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(20, 478, 316, 50);
		btnNewButton.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, lisäät syöttämäsi tiedot esikatseluun.");
		frmTykokemus.getContentPane().add(btnNewButton);
		JButton btnTallenna = new JGradientButton("Tallenna");
		btnTallenna.setBackground(new Color(0xB0F0A8));
		btnTallenna.setIcon(new ImageIcon(CVTyokokemus.class.getResource("/Images/Tallenna.png")));
		btnTallenna.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SaveData();
			}
		});
		btnTallenna.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTallenna.setBounds(459, 417, 160, 50);
		btnTallenna.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, lisäämäsi tiedot tallennetaa.");
		frmTykokemus.getContentPane().add(btnTallenna);
		JButton btnTyhjenna = new JButton("Tyhjennä");
		btnTyhjenna.setIcon(new ImageIcon(CVTyokokemus.class.getResource("/Images/Tyhjenna.png")));
		btnTyhjenna.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Clear();
			}
		});
		btnTyhjenna.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTyhjenna.setBounds(459, 478, 160, 50);
		btnTyhjenna.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, tyhjennät syöttökentissä olevat tiedot.");
		frmTykokemus.getContentPane().add(btnTyhjenna);
		JButton btnPoista = new JGradientButton("Poista");
		btnPoista.setBackground(new Color(0xE8A0B0));
		btnPoista.setIcon(new ImageIcon(CVTyokokemus.class.getResource("/Images/Poista.png")));
		btnPoista.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DelData();
			}
		});
		btnPoista.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPoista.setBounds(691, 417, 160, 50);
		btnPoista.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, poistat tallentamasi tiedot.");
		frmTykokemus.getContentPane().add(btnPoista);
		JButton btnTakaisin = new JButton("Alkusivu");
		btnTakaisin.setIcon(new ImageIcon(CVTyokokemus.class.getResource("/Images/Alkusivu.png")));
		btnTakaisin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				if (tallennettu.compareTo(txtEsi.getText()) == 0)
				{
					frmTykokemus.setVisible(false);
					CVAlkusivu.main(null);
				}
				else
				{
					int result = JcustomDialog.showConfirmDialog(frmTykokemus, "Haluatko poistua tallentamatta muutoksia",
					        "Varoitus!", JcustomDialog.QUESTION_MESSAGE, JcustomDialog.YES_NO_OPTION,
					        JcustomDialog.NO_OPTION);

					if (result == JcustomDialog.YES_OPTION)
					{ frmTykokemus.setVisible(false); CVAlkusivu.main(null); }
				}
			}
		});
		btnTakaisin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTakaisin.setBounds(691, 478, 160, 50);
		btnTakaisin.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, siirryt takaisin alkusivulle.");
		frmTykokemus.getContentPane().add(btnTakaisin);
		cbAloitusKuu = new JComboBox <String>();
		cbAloitusKuu.setModel(new DefaultComboBoxModel <String>(new String[]
		{ "Tammikuu", "Helmikuu", "Maaliskuu", "Huhtikuu", "Toukokuu", "Kesäkuu", "Heinäkuu", "Elokuu", "Syyskuu",
		        "Lokakuu", "Marraskuu", "Joulukuu" }));
		cbAloitusKuu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbAloitusKuu.setBounds(10, 189, 130, 37);
		cbAloitusKuu.setSelectedIndex(0);
		frmTykokemus.getContentPane().add(cbAloitusKuu);
		cbAloitusVuosi = new JComboBox <String>();
		cbAloitusVuosi.setModel(new DefaultComboBoxModel <String>(getYearList(0)));
		cbAloitusVuosi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbAloitusVuosi.setBounds(198, 189, 130, 37);
		cbAloitusVuosi.setSelectedIndex(100);
		frmTykokemus.getContentPane().add(cbAloitusVuosi);
		JLabel lblTyoNimike = new JLabel("Työnimike");
		lblTyoNimike.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTyoNimike.setBounds(10, 61, 200, 31);
		lblTyoNimike.setLabelFor(txtNimike);
		frmTykokemus.getContentPane().add(lblTyoNimike);
		txtTyonantaja = new JTextField();
		txtTyonantaja.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtTyonantaja.setColumns(10);
		txtTyonantaja.setBounds(199, 90, 150, 37);
		txtTyonantaja.getAccessibleContext().getAccessibleEditableText();
		frmTykokemus.getContentPane().add(txtTyonantaja);
		JLabel lblTyonantaja = new JLabel("Työnantaja");
		lblTyonantaja.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTyonantaja.setBounds(199, 61, 200, 31);
		lblTyonantaja.setLabelFor(txtTyonantaja);
		frmTykokemus.getContentPane().add(lblTyonantaja);
		JLabel lblAlkamis = new JLabel("Alkamispäivä");
		lblAlkamis.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAlkamis.setBounds(10, 147, 200, 31);
		lblAlkamis.setLabelFor(cbAloitusKuu);
		lblAlkamis.setLabelFor(cbAloitusVuosi);
		frmTykokemus.getContentPane().add(lblAlkamis);
		JLabel lblLopetus = new JLabel("Päättymispäivä");
		lblLopetus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLopetus.setBounds(10, 237, 200, 31);
		lblLopetus.setLabelFor(cbLopetusKuu);
		lblLopetus.setLabelFor(cbLopetusVuosi);
		frmTykokemus.getContentPane().add(lblLopetus);
		cbLopetusVuosi = new JComboBox <String>();
		cbLopetusVuosi.setModel(new DefaultComboBoxModel <String>(getYearList(0)));
		cbLopetusVuosi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbLopetusVuosi.setBounds(198, 280, 130, 37);
		cbLopetusVuosi.setSelectedIndex(100);
		frmTykokemus.getContentPane().add(cbLopetusVuosi);
		cbLopetusKuu = new JComboBox <String>();
		cbLopetusKuu.setModel(new DefaultComboBoxModel <String>(new String[]
		{ "Tammikuu", "Helmikuu", "Maaliskuu", "Huhtikuu", "Toukokuu", "Kesäkuu", "Heinäkuu", "Elokuu", "Syyskuu",
		        "Lokakuu", "Marraskuu", "Joulukuu" }));
		cbLopetusKuu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbLopetusKuu.setBounds(10, 280, 130, 37);
		cbLopetusKuu.setSelectedIndex(0);
		frmTykokemus.getContentPane().add(cbLopetusKuu);
		JLabel lblLisa = new JLabel("Lisätieto");
		lblLisa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLisa.setBounds(10, 328, 200, 31);
		lblLisa.setLabelFor(txtLisa);
		frmTykokemus.getContentPane().add(lblLisa);
		JLabel lblEsikatselu = new JLabel("Esikatselu");
		lblEsikatselu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEsikatselu.setBounds(445, 11, 212, 37);
		frmTykokemus.getContentPane().add(lblEsikatselu);
		tyokokemusHaku();
		JLabel lblHarjoittelu = new JLabel("(Myös työharjoittelu)");
		lblHarjoittelu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHarjoittelu.setBounds(159, 29, 142, 26);
		frmTykokemus.getContentPane().add(lblHarjoittelu);
		JPanel panel = new JPanel();
		panel.setBounds(445, 48, 429, 347);
		frmTykokemus.getContentPane().add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		scrollPane.setViewportView(txtEsi);
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 370, 338, 89);
		frmTykokemus.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1);
		txtLisa = new JTextArea();
		scrollPane_1.setViewportView(txtLisa);
		txtLisa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtLisa.setLineWrap(true);
		txtLisa.setWrapStyleWord(true);
		txtLisa.getAccessibleContext().getAccessibleEditableText();
		txtLisa.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{

				if (e.getKeyCode() == KeyEvent.VK_TAB)
				{
					if (e.getModifiersEx() > 0) txtLisa.transferFocusBackward();
					else txtLisa.transferFocus();
					e.consume();
				}
			}
		});
		util.addFocusListener(frmTykokemus, txtLisa);
		JCheckBox chckbxJatkuva = new JCheckBox("jatkuva");
		chckbxJatkuva.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				jatkuva = e.getStateChange() == ItemEvent.SELECTED;
				cbLopetusKuu.setEnabled(!jatkuva);
				cbLopetusVuosi.setEnabled(!jatkuva);
			}
		});
		chckbxJatkuva.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxJatkuva.setBounds(334, 287, 80, 23);
		chckbxJatkuva.setOpaque(false);
		chckbxJatkuva.getAccessibleContext().setAccessibleDescription("Rastita tämä kohta, jos työsi jatkuvat");
		frmTykokemus.getContentPane().add(chckbxJatkuva);
		JLabel imgBG = new JLabel("");
		imgBG.setIcon(new ImageIcon(getClass().getResource("/Images/background_overlay.png")));
		imgBG.setBounds(0, 0, 884, 561);
		frmTykokemus.getContentPane().add(imgBG);
		borderChange b = new borderChange();
		b.addBorder(frmTykokemus);
		util.setKeyListener(frmTykokemus);
	}

	public void ShowPreview()
	{
		setNormalBorders();
		var _workTitle = txtNimike.getText();
		var _employer = txtTyonantaja.getText();
		var _info = txtLisa.getText();
		var _startingM = cbAloitusKuu;
		var _startingY = cbAloitusVuosi;
		var _endingM = cbLopetusKuu;
		var _endingY = cbLopetusVuosi;

		try
		{
			// Muuttaa puuttuvien kenttien rajan punaiseksi
			if (_workTitle.isEmpty()) markError(txtNimike);
			if (_employer.isEmpty()) markError(txtTyonantaja);
			if (_info.isEmpty()) markError(txtLisa);

			// Tarkistaa, että pakolliset kentät on täytetty
			if (!_workTitle.isEmpty() && !_employer.isEmpty() && !_info.isEmpty())
			{
				String UusiTeksti = (String.format(
				        "Työnimike: %s\nTyönantaja: %s\nLisätiedot työstä: %s\nAloitus pvm: %s %s\nLopetus pvm: %s %s\n",
				        _workTitle, _employer, _info, _startingM.getSelectedItem(), _startingY.getSelectedItem(),
				        jatkuva ? "jatkuva" : _endingM.getSelectedItem(), jatkuva ? "" : _endingY.getSelectedItem()));
				txtEsi.setText(tyokokemus += UusiTeksti + "\n");
				System.out.println(txtEsi.getText());
				Clear();
				// Tarkistaa, että comboboxeihin on annettu arvot
			} /*
			   * else if (_startingM.getSelectedIndex() == 0
			   * || _endingM.getSelectedIndex() == 0
			   * || _startingY.getSelectedIndex() == 0
			   * || _endingY.getSelectedIndex() == 0)
			   * JDialogDemo.showMessageDialog(null,
			   * "Valitse kaikki päivämäärät työkokemuksiisi", "Virhe!",
			   * 0);
			   */
			// Jos jokin kenttä on tyhjillään palautuu virhe viesti käyttäjälle
			else JcustomDialog.showMessageDialog(null, "Täytä lomakkeesta kaikki tiedot ennen tallentamisen yrittämistä",
			        "Virhe", 0);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void tyokokemusHaku()
	{
		File tiedosto = new File(savePath + "Tyokokemus.txt");

		if (tiedosto.exists())
		{

			try
			{
				String rivi;
				BufferedReader br = new BufferedReader(new FileReader(savePath + "Tyokokemus.txt"));

				while ((rivi = br.readLine()) != null)
				{
					tyokokemus += rivi + "\n";
				}
				br.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			txtEsi.setText(tyokokemus);
			tallennettu = tyokokemus;
		}
	}

	public void Clear()
	{
		txtNimike.setText("");
		txtTyonantaja.setText("");
		cbAloitusKuu.setSelectedIndex(0);
		cbAloitusVuosi.setSelectedIndex(100);
		cbLopetusKuu.setSelectedIndex(0);
		cbLopetusVuosi.setSelectedIndex(100);
		txtLisa.setText("");
		setNormalBorders();
	}

	public void DelData()
	{
		int result = JcustomDialog.showConfirmDialog(frmTykokemus, "Haluatko varmasti poistaa tiedot", "Varoitus!",
		       JcustomDialog.QUESTION_MESSAGE,  JcustomDialog.YES_NO_OPTION,JcustomDialog.NO_OPTION);

		if (result == JcustomDialog.YES_OPTION)
		{

			try
			{
				FileWriter fw = new FileWriter(savePath + "Tyokokemus.txt", false);
				fw.write("");
				fw.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			tyokokemus = "";
			tallennettu = "";
			txtEsi.setText(null);
			JcustomDialog.showMessageDialog(f,"Tiedot poistettu","Ilmoitus",JcustomDialog.INFORMATION_MESSAGE);
		}
	}

	public void SaveData()
	{
		tallennettu = txtEsi.getText();

		if (txtEsi != null)
		{

			try
			{ // Tiedontallennus
				FileWriter fw = new FileWriter(savePath + "Tyokokemus.txt", false);
				fw.write(txtEsi.getText());
				fw.close();
				if (CVAlkusivu.esikatselu) CVEsikatselu.PreviewRefresh(null);
				JcustomDialog.showMessageDialog(f,"Tiedot tallennettu","Ilmoitus",JcustomDialog.INFORMATION_MESSAGE);
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		else JcustomDialog.showMessageDialog(f,"Täytä kaikki kentät ennen tallentamisen yrittämistä.","Ilmoitus",JcustomDialog.INFORMATION_MESSAGE);
	}

	private void markError(JComponent comp)
	{
		comp.setBorder(new LineBorder(new Color(220, 53, 69), 2));
	}

	private void setNormalBorders()
	{
		txtNimike.setBorder(BorderFactory.createLineBorder(Color.black));
		txtTyonantaja.setBorder(BorderFactory.createLineBorder(Color.black));
		txtLisa.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	private String[] getYearList(int offset)
	{
		int year = ym.getYear();
		String[] years = new String[201];
		for (var i = 0; i < years.length; i++) years[i] = String.valueOf((year + 100 + offset) - i);
		return years;
	}
}
