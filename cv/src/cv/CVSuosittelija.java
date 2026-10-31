package cv;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class CVSuosittelija
{
	private String savePath = CVAlkusivu.savePath;

	private JFrame frmSuosittelija;
	private JFrame f = frmSuosittelija;

	private JTextField txtYritys;

	private JTextField txtPuh;

	private JTextField txtYhteys;

	private JTextField txtSposti;

	private JTextArea txtEsi;

	public String suosittelija = "";

	public String tallennettu = "";

	private JTextField txtTitteli;

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
					CVSuosittelija window = new CVSuosittelija();
					window.frmSuosittelija.setVisible(true);
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
	public CVSuosittelija()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmSuosittelija = new JFrame();
		frmSuosittelija.setResizable(false);
		frmSuosittelija.getContentPane().setBackground(new Color(0xD8E8F0));
		frmSuosittelija.setIconImage(
		        Toolkit.getDefaultToolkit().getImage(CVSuosittelija.class.getResource("/Images/Suosittelija.png")));
		frmSuosittelija.setTitle("Suosittelija");
		frmSuosittelija.setSize(900, 600);
		frmSuosittelija.setLocationRelativeTo(null);
		frmSuosittelija.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSuosittelija.getContentPane().setLayout(null);
		JButton btnTallenna = new JGradientButton("Tallenna");
		btnTallenna.setBackground(new Color(0xB0F0A8));
		btnTallenna.setIcon(new ImageIcon(CVSuosittelija.class.getResource("/Images/Tallenna.png")));
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
		frmSuosittelija.getContentPane().add(btnTallenna);
		JButton btnTyhjenna = new JButton("Tyhjennä");
		btnTyhjenna.setIcon(new ImageIcon(CVSuosittelija.class.getResource("/Images/Tyhjenna.png")));
		btnTyhjenna.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ClearContact();
			}
		});
		btnTyhjenna.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTyhjenna.setBounds(459, 478, 160, 50);
		btnTyhjenna.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, tyhjennät syöttökentissä olevat tiedot.");
		frmSuosittelija.getContentPane().add(btnTyhjenna);
		JButton btnPoista = new JGradientButton("Poista");
		btnPoista.setBackground(new Color(0xE8A0B0));
		btnPoista.setIcon(new ImageIcon(CVSuosittelija.class.getResource("/Images/Poista.png")));
		btnPoista.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DelData();
			}
		});
		txtEsi = new JTextArea();
		txtEsi.setLineWrap(true);
		txtEsi.setWrapStyleWord(true);
		txtEsi.setEditable(false);
		btnPoista.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPoista.setBounds(691, 417, 160, 50);
		btnPoista.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, poistat tallentamasi tiedot.");
		frmSuosittelija.getContentPane().add(btnPoista);
		JButton btnTakaisin = new JButton("Alkusivu");
		btnTakaisin.setIcon(new ImageIcon(CVSuosittelija.class.getResource("/Images/Alkusivu.png")));
		btnTakaisin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				if (tallennettu.compareTo(txtEsi.getText()) == 0)
				{
					frmSuosittelija.setVisible(false);
					CVAlkusivu.main(null);
				}
				else
				{
					int result = JcustomDialog.showConfirmDialog(frmSuosittelija,
					        "Haluatko poistua tallentamatta muutoksia", "Varoitus!", JcustomDialog.QUESTION_MESSAGE,
					        JcustomDialog.YES_NO_OPTION, JcustomDialog.NO_OPTION);

					if (result == JcustomDialog.YES_OPTION)
					{ frmSuosittelija.setVisible(false); CVAlkusivu.main(null); }
				}
			}
		});
		btnTakaisin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTakaisin.setBounds(691, 478, 160, 50);
		btnTakaisin.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, siirryt takaisin alkusivulle.");
		frmSuosittelija.getContentPane().add(btnTakaisin);
		JLabel lblEsikatselu = new JLabel("Esikatselu");
		lblEsikatselu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEsikatselu.setBounds(445, 11, 212, 37);
		frmSuosittelija.getContentPane().add(lblEsikatselu);
		suosittelijaHaku();
		JLabel lblYhteysHenk = new JLabel("Yhteyshenkilö");
		lblYhteysHenk.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblYhteysHenk.setBounds(10, 61, 326, 31);
		lblYhteysHenk.setLabelFor(txtYhteys);
		frmSuosittelija.getContentPane().add(lblYhteysHenk);
		txtYhteys = new JTextField();
		txtYhteys.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtYhteys.setColumns(10);
		txtYhteys.setBounds(10, 90, 300, 37);
		txtYhteys.getAccessibleContext().getAccessibleEditableText();
		frmSuosittelija.getContentPane().add(txtYhteys);
		JLabel lblPuh = new JLabel("Puhelinnumero");
		lblPuh.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPuh.setBounds(10, 296, 200, 31);
		lblPuh.setLabelFor(txtPuh);
		frmSuosittelija.getContentPane().add(lblPuh);
		txtPuh = new JTextField();
		txtPuh.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtPuh.setColumns(10);
		txtPuh.setBounds(10, 325, 300, 37);
		txtPuh.getAccessibleContext().getAccessibleEditableText();
		frmSuosittelija.getContentPane().add(txtPuh);
		JLabel lblSposti = new JLabel("Sähköpostiosoite");
		lblSposti.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSposti.setBounds(10, 373, 200, 31);
		lblSposti.setLabelFor(txtSposti);
		frmSuosittelija.getContentPane().add(lblSposti);
		JLabel lblYritys = new JLabel("Yrityksen nimi");
		lblYritys.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblYritys.setBounds(10, 215, 200, 31);
		lblYritys.setLabelFor(txtYritys);
		frmSuosittelija.getContentPane().add(lblYritys);
		txtYritys = new JTextField();
		txtYritys.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtYritys.setColumns(10);
		txtYritys.setBounds(10, 248, 300, 37);
		txtYritys.getAccessibleContext().getAccessibleEditableText();
		frmSuosittelija.getContentPane().add(txtYritys);
		txtSposti = new JTextField();
		txtSposti.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtSposti.setColumns(10);
		txtSposti.setBounds(10, 402, 300, 37);
		txtSposti.getAccessibleContext().getAccessibleEditableText();
		frmSuosittelija.getContentPane().add(txtSposti);
		JButton btnLisaa = new JButton("Lisää uusi suosittelija");
		btnLisaa.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ShowPreview();
			}
		});
		btnLisaa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLisaa.setBounds(20, 467, 316, 50);
		btnLisaa.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, lisäät syöttämäsi tiedot esikatseluun.");
		frmSuosittelija.getContentPane().add(btnLisaa);
		JLabel lblSuosittelija = new JLabel("Suosittelija");
		lblSuosittelija.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblSuosittelija.setBounds(10, 11, 389, 50);
		frmSuosittelija.getContentPane().add(lblSuosittelija);
		JPanel panel = new JPanel();
		panel.setBounds(445, 48, 429, 347);
		frmSuosittelija.getContentPane().add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		scrollPane.setViewportView(txtEsi);
		txtTitteli = new JTextField();
		txtTitteli.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtTitteli.setColumns(10);
		txtTitteli.setBounds(10, 167, 300, 37);
		txtTitteli.getAccessibleContext().getAccessibleEditableText();
		frmSuosittelija.getContentPane().add(txtTitteli);
		JLabel lblYhteysTitteli = new JLabel("Yhteyshenkilön titteli");
		lblYhteysTitteli.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblYhteysTitteli.setBounds(10, 138, 200, 31);
		lblYhteysTitteli.setLabelFor(txtTitteli);
		JLabel imgBG = new JLabel("");
		imgBG.setIcon(new ImageIcon(getClass().getResource("/Images/background_overlay.png")));
		imgBG.setBounds(0, 0, 884, 561);
		frmSuosittelija.getContentPane().add(imgBG);
		frmSuosittelija.getContentPane().add(lblYhteysTitteli);
		borderChange b = new borderChange();
		b.addBorder(frmSuosittelija);
		Util util = new Util();
		util.setKeyListener(frmSuosittelija);
	}

	public void ShowPreview()
	{
		setNormalBorders();
		isValid valid = new isValid();
		// Lokaali muuttuja, mikä on käytössä vain ShowPreview methodissa
		// var antaa coderunnerin päättä mikä on tämän muuttuja tyyppi
		var IcontactPerson = txtYhteys.getText();
		var _IcontactPerson = (IcontactPerson != null && !IcontactPerson.isEmpty())
		        ? IcontactPerson.substring(0, 1).toUpperCase() + IcontactPerson.substring(1)
		        : "";
		var _IcontactPersonTitle = txtTitteli.getText();
		var _Iorganization = txtYritys.getText();
		var _Imobile = txtPuh.getText();
		var _Iemail = txtSposti.getText();

		try
		{
			// Muuttaa puuttuvien kenttien rajan punaiseksi
			if (_IcontactPerson.isEmpty()) markError(txtYhteys);
			if (_IcontactPersonTitle.isEmpty()) markError(txtTitteli);
			if (_Iorganization.isEmpty()) markError(txtYritys);
			if (_Imobile.isEmpty()) markError(txtPuh);
			if (_Iemail.isEmpty()) markError(txtSposti);

			// Tarkistaa onko pakolliset kentät täytetty
			if (!_Iorganization.isEmpty() && !_IcontactPerson.isEmpty() && !_IcontactPersonTitle.isEmpty()
			        && !_Imobile.isEmpty() && !_Iemail.isEmpty())
			{

				// Kutsuu methodia, joka palauttaa true tai false onko stringi
				// numero vai ei
				// numeron ja sähköpostin alemmassa if-lauseessa, jotta käyttäjä saa tarkemman
				// virhe ilmoituksen
				if (!valid.isValidPhone(_Imobile))
				{
					markError(txtPuh);
					JcustomDialog.showMessageDialog(null, "Voit antaa vain numero arvoja puhelinnumeroksi", "Virhe!", 0);
				}
				// Kutsuu methodia, joka palauttaa true tai false onko stringi
				// sähköposti formaattia
				else if (!valid.isValidEmail(_Iemail))
				{
					markError(txtSposti);
					JcustomDialog.showMessageDialog(null, "Anna kelvollinen sähköpostiosoite", "Virhe!", 0);
					// Jos numero ja sposti on methodissa määritellyn formaatin mukainen
					// Palautuu tällöin Formatoitu stringi Esikatselu kenttään
				}
				else
				{
					String UusiTeksti = (String.format(
					        "Yhteyshenkilö: %s\nYhteyshenkilön titteli: %s\nYrityksen nimi: %s\nPuhelinnumero: %s\nSähköposti: %s\n",
					        _IcontactPerson, _IcontactPersonTitle, _Iorganization, _Imobile, _Iemail));
					txtEsi.setText(suosittelija += UusiTeksti + "\n");
					ClearContact();
				}
				// Jos jokin tekstikentistä on tyhjillään palautuu virhe viesti käyttäjälle
			}
			else
			{
				JcustomDialog.showMessageDialog(null, "Täytä lomakkeesta kaikki kentät ennen tallentamisen yrittämistä",
				        "Virhe!", 0);
			}
			// Palauttaa virheen hallinta viestin & kertoo mikä sen aiheutti
		}
		catch (Exception ex)
		{
			System.err.println(String.format("Try-Catch error:\n\n%s", ex));
		}
	}

	public void ClearContact()
	{
		txtYritys.setText(null);
		txtTitteli.setText(null);
		txtYhteys.setText(null);
		txtPuh.setText(null);
		txtSposti.setText(null);
		setNormalBorders();
	}

	private void suosittelijaHaku()
	{
		File tiedosto = new File(savePath + "Suosittelija.txt");

		if (tiedosto.exists())
		{

			try
			{
				String rivi;
				BufferedReader br = new BufferedReader(new FileReader(savePath + "Suosittelija.txt"));

				while ((rivi = br.readLine()) != null)
				{
					suosittelija += rivi + "\n";
				}
				br.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			txtEsi.setText(suosittelija);
			tallennettu = suosittelija;
		}
	}

	public void SaveData()
	{
		tallennettu = txtEsi.getText();

		if (txtEsi != null)
		{

			try
			{ // Tiedontallennus
				FileWriter fw = new FileWriter(savePath + "Suosittelija.txt", false);
				fw.write(txtEsi.getText());
				fw.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		if (CVAlkusivu.esikatselu) CVEsikatselu.PreviewRefresh(null);
		JcustomDialog.showMessageDialog(f,"Tiedot tallennettu","Ilmoitus",JcustomDialog.INFORMATION_MESSAGE);
	}

	public void DelData()
	{
		int result = JcustomDialog.showConfirmDialog(frmSuosittelija, "Haluatko varmasti poistaa tiedot", "Varoitus!",
		        JcustomDialog.QUESTION_MESSAGE, JcustomDialog.YES_NO_OPTION,JcustomDialog.NO_OPTION);

		if (result == JcustomDialog.YES_OPTION)
		{

			try
			{
				FileWriter fw = new FileWriter(savePath + "Suosittelija.txt", false);
				fw.write("");
				fw.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			suosittelija = "";
			txtEsi.setText(null);
			tallennettu = "";
			JcustomDialog.showMessageDialog(f,"Tiedot poistettu","Ilmoitus",JcustomDialog.INFORMATION_MESSAGE);
		}
	}

	private void markError(JComponent comp)
	{
		comp.setBorder(new LineBorder(new Color(220, 53, 69), 2));
	}

	private void setNormalBorders()
	{
		txtYritys.setBorder(BorderFactory.createLineBorder(Color.black));
		txtTitteli.setBorder(BorderFactory.createLineBorder(Color.black));
		txtYhteys.setBorder(BorderFactory.createLineBorder(Color.black));
		txtPuh.setBorder(BorderFactory.createLineBorder(Color.black));
		txtSposti.setBorder(BorderFactory.createLineBorder(Color.black));
	}
}
