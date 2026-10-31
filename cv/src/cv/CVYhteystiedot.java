package cv;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class CVYhteystiedot
{
	private String savePath = CVAlkusivu.savePath;

	private JFrame frmYhteystiedot;
	private JFrame f = frmYhteystiedot;

	private JTextField txtEtunimi;

	private JTextField txtSukunimi;

	private JTextField txtPuhelin;

	private JTextField txtSahkoposti;

	private JTextField txtKaupunki;

	private JTextField txtNettisivu;

	private JTextArea txtEsi;

	public String Tiedot = "";

	public boolean hasError;

	Object[] options =
	{ "Kyllä", "Peruuta" };

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
					CVYhteystiedot window = new CVYhteystiedot();
					window.frmYhteystiedot.setVisible(true);
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
	public CVYhteystiedot()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmYhteystiedot = new JFrame();
		frmYhteystiedot.getContentPane().setBackground(new Color(0xD8E8F0));
		frmYhteystiedot.setIconImage(
		        Toolkit.getDefaultToolkit().getImage(CVYhteystiedot.class.getResource("/Images/Yhteystiedot.png")));
		frmYhteystiedot.setTitle("Yhteystiedot");
		frmYhteystiedot.setResizable(false);
		frmYhteystiedot.setSize(900, 600);
		frmYhteystiedot.setLocationRelativeTo(null);
		frmYhteystiedot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmYhteystiedot.getContentPane().setLayout(null);
		JLabel lblOtsikko = new JLabel("Yhteystiedot / Henkilötiedot");
		lblOtsikko.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblOtsikko.setBounds(10, 11, 389, 50);
		frmYhteystiedot.getContentPane().add(lblOtsikko);
		JButton btnTallenna = new JGradientButton("Tallenna");
		btnTallenna.setBackground(new Color(0xB0F0A8));
		btnTallenna.setIcon(new ImageIcon(CVYhteystiedot.class.getResource("/Images/Tallenna.png")));
		btnTallenna.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				testPreview();

				// Aikaisemmassa koodissa vaikka tuli virheitä, heitti tallenna nappi, tiedot
				// tallennettu
				// System.out.printf("onclick error status %b\n", hasError);
				if (hasError == false)
				{
					// System.out.println("testi\n");
					showPreview();
					SaveData();
				}
			}
		});
		btnTallenna.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTallenna.setBounds(459, 417, 160, 50);
		btnTallenna.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, lisäämäsi tiedot tallennetaa.");
		frmYhteystiedot.getContentPane().add(btnTallenna);
		JButton btnTyhjenn = new JButton("Tyhjennä");
		btnTyhjenn.setIcon(new ImageIcon(CVYhteystiedot.class.getResource("/Images/Tyhjenna.png")));
		btnTyhjenn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Clear();
			}
		});
		btnTyhjenn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTyhjenn.setBounds(459, 478, 160, 50);
		btnTyhjenn.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, tyhjennät syöttökentissä olevat tiedot.");
		frmYhteystiedot.getContentPane().add(btnTyhjenn);
		JButton btnPoista = new JGradientButton("Poista");
		btnPoista.setBackground(new Color(0xE8A0B0));
		btnPoista.setIcon(new ImageIcon(CVYhteystiedot.class.getResource("/Images/Poista.png")));
		btnPoista.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int result = JcustomDialog.showOptionDialog(f, "Haluatko varmasti poistaa tiedot", "Varoitus!",
				        JcustomDialog.QUESTION_MESSAGE,JcustomDialog.YES_NO_OPTION,JcustomDialog.NO_OPTION);

				if (result == JcustomDialog.YES_OPTION)
				{

					try
					{
						FileWriter fw = new FileWriter(savePath + "Yhteystiedot.txt", false);
						fw.write("");
						fw.close();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
					txtEsi.setText(null);
					Tiedot = "";
					JcustomDialog.showMessageDialog(f, "Tiedot poistettu","Ilmoitus",JcustomDialog.PLAIN_MESSAGE);
				}
			}
		});
		btnPoista.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPoista.setBounds(691, 417, 160, 50);
		btnPoista.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, poistat tallentamasi tiedot.");
		frmYhteystiedot.getContentPane().add(btnPoista);
		JButton btnAlkusivu = new JButton("Alkusivu");
		btnAlkusivu.setIcon(new ImageIcon(CVYhteystiedot.class.getResource("/Images/Alkusivu.png")));
		btnAlkusivu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				if (Tiedot.compareTo(txtEsi.getText()) == 0)
				{
					frmYhteystiedot.setVisible(false);
					CVAlkusivu.main(null);
				}
				else
				{
					int result = JcustomDialog.showOptionDialog(f, "Haluatko poistua tallentamatta muutoksia",
					        "Varoitus!", JcustomDialog.QUESTION_MESSAGE ,JcustomDialog.YES_NO_OPTION,JcustomDialog.NO_OPTION);

					if (result == JcustomDialog.YES_OPTION)
					{ frmYhteystiedot.setVisible(false); CVAlkusivu.main(null); }
				}
			}
		});
		btnAlkusivu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAlkusivu.setBounds(691, 478, 160, 50);
		btnAlkusivu.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, siirryt takaisin alkusivulle.");
		frmYhteystiedot.getContentPane().add(btnAlkusivu);
		JLabel lblEsikatselu = new JLabel("Esikatselu");
		lblEsikatselu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEsikatselu.setBounds(445, 11, 212, 37);
		frmYhteystiedot.getContentPane().add(lblEsikatselu);
		txtEsi = new JTextArea();
		txtEsi.setLineWrap(true);
		txtEsi.setWrapStyleWord(true);
		txtEsi.setEditable(false);
		txtEsi.setBounds(445, 48, 429, 347);
		yhteystiedotHaku(); // yhteystietojen haku jos olemassa
		frmYhteystiedot.getContentPane().add(txtEsi);
		txtEtunimi = new JTextField();
		txtEtunimi.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtEtunimi.setBounds(20, 110, 160, 37);
		txtEtunimi.getAccessibleContext().getAccessibleEditableText();
		frmYhteystiedot.getContentPane().add(txtEtunimi);
		txtEtunimi.setColumns(10);
		txtSukunimi = new JTextField();
		txtSukunimi.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSukunimi.setColumns(10);
		txtSukunimi.setBounds(210, 110, 160, 37);
		txtSukunimi.getAccessibleContext().getAccessibleEditableText();
		frmYhteystiedot.getContentPane().add(txtSukunimi);
		JLabel lblEtunimi = new JLabel("Etunimi *");
		lblEtunimi.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEtunimi.setBounds(20, 72, 160, 30);
		lblEtunimi.setLabelFor(txtEtunimi);
		frmYhteystiedot.getContentPane().add(lblEtunimi);
		JLabel lblSukunimi = new JLabel("Sukunimi *");
		lblSukunimi.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSukunimi.setBounds(210, 72, 160, 30);
		lblSukunimi.setLabelFor(txtSukunimi);
		frmYhteystiedot.getContentPane().add(lblSukunimi);
		JLabel lblPuhelin = new JLabel("Puhelinnumero *");
		lblPuhelin.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPuhelin.setBounds(20, 180, 160, 30);
		lblPuhelin.setLabelFor(txtPuhelin);
		frmYhteystiedot.getContentPane().add(lblPuhelin);
		txtPuhelin = new JTextField();
		txtPuhelin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtPuhelin.setColumns(10);
		txtPuhelin.setBounds(20, 218, 160, 37);
		txtPuhelin.getAccessibleContext().getAccessibleEditableText();
		frmYhteystiedot.getContentPane().add(txtPuhelin);
		JLabel lblSahkoposti = new JLabel("Sähköposti *");
		lblSahkoposti.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSahkoposti.setBounds(210, 180, 160, 30);
		lblSahkoposti.setLabelFor(txtSahkoposti);
		frmYhteystiedot.getContentPane().add(lblSahkoposti);
		txtSahkoposti = new JTextField();
		txtSahkoposti.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSahkoposti.setColumns(10);
		txtSahkoposti.setBounds(210, 218, 160, 37);
		txtSahkoposti.getAccessibleContext().getAccessibleEditableText();
		frmYhteystiedot.getContentPane().add(txtSahkoposti);
		JLabel lblKaupunki = new JLabel("Kaupunki");
		lblKaupunki.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblKaupunki.setBounds(20, 288, 160, 30);
		lblKaupunki.setLabelFor(txtKaupunki);
		frmYhteystiedot.getContentPane().add(lblKaupunki);
		txtKaupunki = new JTextField();
		txtKaupunki.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtKaupunki.setColumns(10);
		txtKaupunki.setBounds(20, 326, 160, 37);
		txtKaupunki.getAccessibleContext().getAccessibleEditableText();
		frmYhteystiedot.getContentPane().add(txtKaupunki);
		JLabel lblNettisivu = new JLabel("Nettisivu/blogi");
		lblNettisivu.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNettisivu.setBounds(210, 288, 160, 30);
		lblNettisivu.setLabelFor(txtNettisivu);
		frmYhteystiedot.getContentPane().add(lblNettisivu);
		txtNettisivu = new JTextField();
		txtNettisivu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtNettisivu.setColumns(10);
		txtNettisivu.setBounds(210, 326, 160, 37);
		txtNettisivu.getAccessibleContext().getAccessibleEditableText();
		frmYhteystiedot.getContentPane().add(txtNettisivu);
		JButton btnLisaa = new JButton("Lisää yhteystiedot");
		btnLisaa.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				if (!txtEsi.getText().isEmpty())
				{
					changeData();
				}
				else
				{
					testPreview();
					if (hasError == false) showPreview();
				}
			}
		});
		btnLisaa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLisaa.setBounds(20, 420, 350, 50);
		btnLisaa.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, lisäät syöttämäsi tiedot esikatseluun.");
		frmYhteystiedot.getContentPane().add(btnLisaa);
		JLabel lblPakollinen = new JLabel("* Pakollinen");
		lblPakollinen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPakollinen.setBounds(314, 27, 142, 26);
		frmYhteystiedot.getContentPane().add(lblPakollinen);
		JLabel imgBG = new JLabel("");
		imgBG.setIcon(new ImageIcon(getClass().getResource("/Images/background_overlay.png")));
		imgBG.setBounds(0, 0, 884, 561);
		frmYhteystiedot.getContentPane().add(imgBG);
		borderChange b = new borderChange();
		b.addBorder(frmYhteystiedot);
		Util util = new Util();
		util.setKeyListener(frmYhteystiedot);
	}

	private void yhteystiedotHaku()
	{
		String yhteystiedot = "";
		File tiedosto = new File(savePath + "Yhteystiedot.txt");

		if (tiedosto.exists())
		{

			try
			{
				String rivi;
				BufferedReader br = new BufferedReader(new FileReader(savePath + "Yhteystiedot.txt"));

				while ((rivi = br.readLine()) != null)
				{
					yhteystiedot += rivi + "\n";
				}
				br.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			txtEsi.setText(yhteystiedot);
			Tiedot = yhteystiedot;
		}
	}

	isValid valid = new isValid();

	/**
	 * Methodi, joka päivittää hasError Boolean muuttujaa sen mukaan palautuuko
	 * käyttäjän tiedoista virhettä
	 */
	public void testPreview()
	{
		StringBuilder er = new StringBuilder();
		er.append("Tarkista seuraavat kentät\n");
		setNormalBorders();
		var firstName = txtEtunimi.getText();
		var _fName = (firstName != null && !firstName.isEmpty())
		        ? firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase()
		        : "";
		var lastName = txtSukunimi.getText();
		var _lName = (lastName != null && !lastName.isEmpty())
		        ? lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase()
		        : "";
		var _mobile = txtPuhelin.getText();
		var _email = txtSahkoposti.getText();

		try
		{

			// Muuttaa puuttuvien kenttien rajan punaiseksi
			if (_fName.isEmpty() || _lName.isEmpty() || _mobile.isEmpty() || _email.isEmpty())
			{
				if (_fName.isEmpty()) markError(txtEtunimi);
				if (_lName.isEmpty()) markError(txtSukunimi);
				if (_mobile.isEmpty()) markError(txtPuhelin);
				if (_email.isEmpty()) markError(txtSahkoposti);
				JcustomDialog.showMessageDialog(null, "Täytä kaikki kentät ennen tallentamisen yrittämistä.", "Virhe!",
				        0);
			}
			hasError = false;

			if (!valid.isValidFName(_fName))
			{ markError(txtEtunimi); er.append("- Etunimi\n"); hasError = true; }

			if (!valid.isValidLName(_lName))
			{ er.append("- Sukunimi\n"); markError(txtSukunimi); hasError = true; }

			if (!valid.isValidPhone(_mobile))
			{ er.append("- Puhelinnumero\n"); markError(txtPuhelin); hasError = true; }

			if (!valid.isValidEmail(_email))
			{ er.append("- Sähköposti\n"); markError(txtSahkoposti); hasError = true; }
			if (hasError)
			    JcustomDialog.showMessageDialog(null, er.toString(), "Virhe Tiedoissa!", JcustomDialog.ERROR_MESSAGE);
			else hasError = false;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Kutsu testPreviewin jälkeen, mikäli hasError == false
	 */
	public void showPreview()
	{
		StringBuilder sb = new StringBuilder();
		var firstName = txtEtunimi.getText();
		var _fName = (firstName != null && !firstName.isEmpty())
		        ? firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase()
		        : "";
		var lastName = txtSukunimi.getText();
		var _lName = (lastName != null && !lastName.isEmpty())
		        ? lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase()
		        : "";
		var _mobile = txtPuhelin.getText();
		var _email = txtSahkoposti.getText();
		var city = txtKaupunki.getText();
		var _city = !city.isEmpty() ? city.substring(0, 1).toUpperCase() + city.substring(1) : "";
		var _website = txtNettisivu.getText();
		sb.append(String.format("Nimi: %s %s \nPuhelinnumero: %s \nSähköposti: %s\n", _fName, _lName, _mobile, _email));
		if (!_city.isEmpty()) sb.append(String.format("Kaupunki: %s\n", _city));
		if (!_website.isEmpty()) sb.append(String.format("Nettisivut: %s\n", _website));
		txtEsi.setText(sb.toString());
	}

	/*
	 * Mahdollisuus lisätä nettisivut tai kaupunki jälkeenpäin, jos näin haluaa
	 */
	public void addOptional()
	{
		var prw = txtEsi.getText().toString();
		StringBuilder sb = new StringBuilder();
		sb.append(txtEsi.getText());

		if (!prw.toString().contains("Kaupunki") && !txtKaupunki.getText().isEmpty())
		{
			var pane = JcustomDialog.showOptionDialog(
					f,
					"Haluatko Lisätä Kaupungin?",
					"Huomio!",
			        JcustomDialog.INFORMATION_MESSAGE,
			        JcustomDialog.OK_CANCEL_OPTION,
			        JcustomDialog.CANCEL_OPTION);

			if (pane == JcustomDialog.OK_OPTION)
			{
				sb.append(String.format("Kaupunki: %s\n", txtKaupunki.getText().trim()));
				txtEsi.setText(sb.toString());
				SaveData();
			}
			else return;
		}

		if (!prw.toString().contains("Nettisivu") && !txtNettisivu.getText().isEmpty())
		{
			var pane = JcustomDialog.showOptionDialog(f, "Haluatko Lisätä Nettisivun", "Huomio!",
			        JcustomDialog.INFORMATION_MESSAGE,JcustomDialog.OK_CANCEL_OPTION, JcustomDialog.CANCEL_OPTION);

			if (pane == JcustomDialog.OK_OPTION)
			{
				sb.append(String.format("Nettisivut: %s\n", txtNettisivu.getText()));
				txtEsi.setText(sb.toString());
				SaveData();
			}
			else return;
		}
	}

	/**
	 * Mahdollistaa tietojen muuttamisen jälkikäteen
	 */
	public void changeData()
	{
		/**
		 * 20.8/26 10:30 atm kentät, joissa ei toimi data muutos on Nettisivu 21.8/26
		 * 6:30 Lyhyellä testauksella kaikkien kenttien pitäis toimia 21.8/26 12:30
		 * Kaikkien kenttiein pitäis toimia
		 * 
		 */
		// Tallentaa kentän datan stringbuilderiin, mikäli se ei ole sama, kuin
		// esikatselussa
		StringBuilder addToString = new StringBuilder();
		StringBuilder er = new StringBuilder();
		var prw = txtEsi.getText().toString();
		er.append("Tarkista seuraavat kentät\n");
		hasError = false;
		addToString.append("Haluatko varmasti muuttaa seuraavia kenttiä\n");

		if (!valid.isValidFName(txtEtunimi.getText().trim()) && !txtEtunimi.getText().isEmpty())
		{ markError(txtEtunimi); er.append("- Etunimi\n"); hasError = true; }

		if (!valid.isValidLName(txtSukunimi.getText()) && !txtSukunimi.getText().isEmpty())
		{ er.append("- Sukunimi\n"); markError(txtSukunimi); hasError = true; }

		if (!valid.isValidPhone(txtPuhelin.getText()) && !txtPuhelin.getText().isEmpty())
		{ er.append("- Puhelinnumero\n"); markError(txtPuhelin); hasError = true; }

		if (!valid.isValidEmail(txtSahkoposti.getText()) && !txtSahkoposti.getText().isEmpty())
		{ er.append("- Sähköposti\n"); markError(txtSahkoposti); hasError = true; }
		if (hasError) JcustomDialog.showMessageDialog(null, er.toString(), "Virhe Tiedoissa!", JcustomDialog.ERROR_MESSAGE);
		else hasError = false;

		try
		{
			// Tarkastaa esikatselusta löytyvät arvot ja vertaavat niitä uusiin, jos
			// esikatselusta löytyy arvot
			if (valid.isValidFName(txtEtunimi.getText().trim())
			        && !prw.substring(prw.indexOf("Nimi: ") + "Nimi: ".length(), getIndexOf(prw.toString(), " ", 2))
			                .equals(txtEtunimi.getText().trim()))
			    addToString.append("- Etunimi\n");
			if (valid.isValidLName(txtSukunimi.getText().trim())
			        && !prw.substring(getIndexOf(prw.toString(), " ", 2), getIndexOf(prw.toString(), " ", 3))
			                .equals(txtSukunimi.getText().trim()))
			    addToString.append("- Sukunimi\n");
			if (valid.isValidPhone(txtPuhelin.getText().trim())
			        && !prw.substring(prw.indexOf("Puhelin: ") + "Puhelin: ".length(), getIndexOf(prw, "\n", 2))
			                .equals(txtPuhelin.getText().trim()))
			    addToString.append("- Puhelinnumero\n");
			if (valid.isValidEmail(txtSahkoposti.getText().trim())
			        && !prw.substring(prw.indexOf("Sähköposti: ") + "Sähköposti: ".length(), getIndexOf(prw, "\n", 3))
			                .equals(txtSahkoposti.getText().trim()))
			    addToString.append("- Sähköposti\n");

			try
			{
				if (valid.isValidFName(txtKaupunki.getText().trim()) && !txtKaupunki.getText().trim().isEmpty()
				        && !prw.substring(prw.indexOf("Kaupunki: ") + "Kaupunki: ".length())
				                .equals(txtKaupunki.getText().trim()))
				    addToString.append("- Kaupunki\n");
				if (valid.isValidString(txtNettisivu.getText().trim()) && !txtNettisivu.getText().isEmpty()
				        && !prw.substring(prw.indexOf("Nettisivut: ") + "Nettisivut: ".length())
				                .equals(txtNettisivu.getText()))
				    addToString.append("- Nettisivut");
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.exit(0);
				return;
			}

			// --------------------------------------------------------------------------------------------
			// Jos käyttäjä yrittää jälkikäteen antaa nettisivua tai kaupunkia, niin
			// saadan ne lisättyä addOptional() methodilla
			if (!prw.contains("Kaupunki") && !txtKaupunki.getText().isEmpty()
			        || !prw.contains("Nettisivut") && !txtNettisivu.getText().isEmpty())
			{
				addOptional();
				prw = txtEsi.getText().toString();
			}
			else
			{
				var ans = JcustomDialog.showOptionDialog(
						f,
						addToString.toString(),
						"Huomio!",
						JcustomDialog.INFORMATION_MESSAGE,
						JcustomDialog.OK_CANCEL_OPTION,
						JcustomDialog.CANCEL_OPTION);

				if (ans == JcustomDialog.OK_OPTION)
				{

					// Etunimi -------------------------------
					if (addToString.toString().contains("Etunimi"))
					{

						if (valid.isValidFName(txtEtunimi.getText().trim())) prw = prw.replaceFirst(
						        prw.substring(prw.indexOf("Nimi: ") + "Nimi: ".length(),
						                prw.indexOf(" ", prw.indexOf("Nimi: ") + "Nimi: ".length())),
						        txtEtunimi.getText().trim());
						else
						{
							er.append("- Etunimi\n");
							hasError = true;
							markError(txtEtunimi);
						}
					}

					// Sukunimi ------------------------
					if (addToString.toString().contains("Sukunimi"))
					{
						var nameStart = prw.indexOf("Nimi: ") + "Nimi: ".length();
						var lastNameStart = prw.indexOf(" ", nameStart) + 1;
						var nameEnd = prw.indexOf("\n", nameStart);

						if (valid.isValidLName(txtSukunimi.getText().trim())) prw = prw.toString()
						        .replaceFirst(prw.substring(lastNameStart, nameEnd), txtSukunimi.getText().trim());
						else
						{
							er.append("- Sukunimi\n");
							hasError = true;
							markError(txtSukunimi);
						}
					}

					// Muuttaa esikatselu kentän arvoja, ja tallentaa ne lopuksi
					// Puh ---------------------
					if (addToString.toString().contains("Puhelinnumero"))
					{
						var numbStart = prw.indexOf("Puhelinnumero: ") + "Puhelinnumero: ".length();
						var numbEnd = prw.indexOf("\n", numbStart);

						if (valid.isValidPhone(txtPuhelin.getText().trim())) prw = prw.toString()
						        .replace(prw.substring(numbStart, numbEnd), txtPuhelin.getText().trim());
						else
						{
							er.append("- Puhelinnumero\n");
							hasError = true;
							markError(txtPuhelin);
						}
					}
					// Sposti----------------------

					if (addToString.toString().contains("Sähköposti"))
					{ // Jos käyttäjä ei ole antanut kaupunkiin
					  // tai
					  // nettisivuihin arvoja palauttaa
					  // indexOf tällöin -1
						var emailEnd = prw.indexOf("\n", prw.indexOf("Sähköposti: ") + "Sähköposti: ".length());
						if (emailEnd == -1) emailEnd = prw.length();

						if (valid.isValidEmail(txtSahkoposti.getText().trim())) prw = prw.toString().replaceFirst(
						        prw.substring(prw.indexOf("Sähköposti: ") + "Sähköposti: ".length(), emailEnd),
						        txtSahkoposti.getText().trim());
						else
						{
							er.append("- Sähköposti\n");
							hasError = true;
							markError(txtSahkoposti);
						}
					}
					System.out.println(prw);
					System.out.println(prw.indexOf("Nettisivut: "));

					// Kaupunki ------------------------
					if (addToString.toString().contains("Kaupunki"))
					{
						var cityStart = prw.indexOf("Kaupunki: ") + "Kaupunki: ".length();
						var cityEnd = prw.indexOf("\n", cityStart);
						if (cityEnd == -1) cityEnd = prw.length();
						else prw = prw.replaceFirst(prw.substring(cityStart, cityEnd), txtKaupunki.getText().trim());
					}
					// Nettisivut ----------------------------------

					if (addToString.toString().contains("Nettisivut"))
					{
						var webLabel = prw.indexOf("Nettisivut: ");
						var webStart = webLabel + "Nettisivut: ".length();
						var webEnd = prw.indexOf("\n", webStart);
						if (webEnd == -1) webEnd = prw.length();
						else prw = prw.replaceFirst(prw.substring(webStart, webEnd), txtNettisivu.getText().trim());
					}
					// --------------------------------------------------------------------------------------------------------

					// Muuttaa esikatselu kentän tekstin muutetuilla arvoilla prw muuttujasta
					if (hasError == false)
					{
						txtEsi.setText(prw);
						SaveData();
					}
					else JcustomDialog.showMessageDialog(null, er.toString(), "Virhe Tiedoissa!",
					        JcustomDialog.ERROR_MESSAGE);
				}
				else if (ans == JcustomDialog.CANCEL_OPTION) System.out.println(prw.toString());
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * str == alkuperäinen stringi, str2 == Etsittävä merkki, Index == määrä mistä
	 * etsitään str2 stringistä Tarkoittaen, että preview stringistä str2 = "\n"
	 * Index = 2 antaisi arvon seuraavasta \n ensinmäisen jälkeen?
	 */
	public int getIndexOf(String str, String str2, int appearanceAmount)
	{
		// https://stackoverflow.com/questions/64979871/how-do-i-return-the-second-instance-of-a-substring-in-java
		var count = 0;
		var pos = 0;

		for (var i = 0; i < str.length(); i++)
		{
			if (str.substring(i, i + 1).equals(str2)) count++;

			// Jos count 2 ja str2 == "\n" etsii silloin stringistä toista "\n" ensinmäisen
			// sijaan
			if (count == appearanceAmount)
			{ pos = i; break; }
		}
		return pos;
	}

	/**
	 * Tallentaa käyttäjän antaman datan AppData:an tehtyyn kansioon
	 */
	public void SaveData()
	{
		Tiedot = txtEsi.getText();

		if (txtEsi.getText() != null)
		{

			try
			{
				FileWriter fw = new FileWriter(savePath + "Yhteystiedot.txt", false);
				fw.write(txtEsi.getText());
				fw.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		if (CVAlkusivu.esikatselu) CVEsikatselu.PreviewRefresh(null);
		JcustomDialog.showMessageDialog(f,"Tiedot tallennettu","Ilmoitus",JcustomDialog.PLAIN_MESSAGE);
		Clear();
	}

	/**
	 * Tyhjentää kaikki kentät sivulta
	 */
	public void Clear()
	{
		txtEtunimi.setText(null);
		txtSukunimi.setText(null);
		txtPuhelin.setText(null);
		txtSahkoposti.setText(null);
		txtKaupunki.setText(null);
		txtNettisivu.setText(null);
		setNormalBorders();
	}

	/**
	 * Muuttaa teksti kentän ulkoreunan punaiseksi, mikäli virheitä löytyy
	 * 
	 * @param comp
	 */
	private void markError(JComponent comp)
	{
		comp.setBorder(new LineBorder(new Color(220, 53, 69), 2));
	}

	/**
	 * Muutta kenttien reunat normaaliksi
	 */
	private void setNormalBorders()
	{
		txtEtunimi.setBorder(BorderFactory.createLineBorder(Color.black));
		txtSukunimi.setBorder(BorderFactory.createLineBorder(Color.black));
		txtPuhelin.setBorder(BorderFactory.createLineBorder(Color.black));
		txtSahkoposti.setBorder(BorderFactory.createLineBorder(Color.black));
	}
}
