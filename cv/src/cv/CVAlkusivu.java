package cv;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CVAlkusivu
{
	private JFrame frmCvLuontiOhjelma;

	private JFrame f = frmCvLuontiOhjelma;

	// public static String savePath = System.getenv(",,") + "/CVLuontiOhjelma/";
	public static String savePath = System.getProperty("user.home") + File.separator + "CVLuontiOhjelma"
	        + File.separator;

	public static String borderColor = "red";

	public static boolean esikatselu = false;

	private CVLuoPDF pdf = new CVLuoPDF(borderColor, savePath);

	public Util util = new Util();

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
					CVAlkusivu window = new CVAlkusivu();
					window.frmCvLuontiOhjelma.setVisible(true);
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
	public CVAlkusivu()
	{
		File saveDir = new File(savePath);

		if (!saveDir.exists())
		{ saveDir.mkdirs(); }
		String[] tiedostot =
		{ "Kieli", "Koulutus", "KoulutusMuu", "Kuva", "Profiili", "Suosittelija", "Taidot", "Tyokokemus",
		        "Yhteystiedot" };

		for (String name : tiedostot)
		{
			File txtFile = new File(saveDir, name + ".txt");
			/*!Files.exists(Paths.get(savePath))*/
			if (!txtFile.exists())
			{

				try
				{
					txtFile.createNewFile();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmCvLuontiOhjelma = new JFrame();
		frmCvLuontiOhjelma.getContentPane().setBackground(new Color(0xD8E8F0));
		frmCvLuontiOhjelma
		        .setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/CVAlkusivu.png")));
		frmCvLuontiOhjelma.setTitle("CV luontiohjelma");
		frmCvLuontiOhjelma.setResizable(false);
		frmCvLuontiOhjelma.setSize(900, 600);
		frmCvLuontiOhjelma.setLocationRelativeTo(null);
		frmCvLuontiOhjelma.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCvLuontiOhjelma.getContentPane().setLayout(null);
		JButton btnYhteystiedot = new JButton("Yhteystiedot / Henkilötiedot");
		btnYhteystiedot.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CVYhteystiedot.main(null);
				frmCvLuontiOhjelma.setVisible(false);
			}
		});
		btnYhteystiedot.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnYhteystiedot.setBounds(109, 150, 280, 50);
		btnYhteystiedot.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, voit muokata yhteys- ja henkilötietoja");
		frmCvLuontiOhjelma.getContentPane().add(btnYhteystiedot);
		JButton btnProfiili = new JButton("Profiili");
		btnProfiili.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CVProfiili.main(null);
				frmCvLuontiOhjelma.setVisible(false);
			}
		});
		btnProfiili.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnProfiili.setBounds(109, 250, 280, 50);
		btnProfiili.getAccessibleContext().setAccessibleDescription("Painamalla tätä nappia, voit muokata profiiliasi");
		frmCvLuontiOhjelma.getContentPane().add(btnProfiili);
		JButton btnVahvuudet = new JButton("Taidot ja Vahvuudet");
		btnVahvuudet.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CVVahvuudet.main(null);
				frmCvLuontiOhjelma.setVisible(false);
			}
		});
		btnVahvuudet.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnVahvuudet.setBounds(109, 350, 280, 50);
		btnVahvuudet.getAccessibleContext().setAccessibleDescription("Painamalla tätä nappia, voit lisätä vahvuuksia");
		frmCvLuontiOhjelma.getContentPane().add(btnVahvuudet);
		JButton btnTyokokemus = new JButton("Työkokemus");
		btnTyokokemus.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CVTyokokemus.main(null);
				frmCvLuontiOhjelma.setVisible(false);
			}
		});
		btnTyokokemus.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnTyokokemus.setBounds(582, 150, 280, 50);
		btnTyokokemus.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, voit lisätä työkokemuksia");
		frmCvLuontiOhjelma.getContentPane().add(btnTyokokemus);
		JButton btnKoulutus = new JButton("Koulutus");
		btnKoulutus.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CVKoulutus.main(null);
				frmCvLuontiOhjelma.setVisible(false);
			}
		});
		btnKoulutus.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnKoulutus.setBounds(582, 250, 280, 50);
		btnKoulutus.getAccessibleContext().setAccessibleDescription("Painamalla tätä nappia, voit lisätä koulutuksia");
		frmCvLuontiOhjelma.getContentPane().add(btnKoulutus);
		JButton btnSuosittelijat = new JButton("Suosittelijat");
		btnSuosittelijat.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CVSuosittelija.main(null);
				frmCvLuontiOhjelma.setVisible(false);
			}
		});
		btnSuosittelijat.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSuosittelijat.setBounds(582, 350, 280, 50);
		btnSuosittelijat.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, voit lisätä suosittelijoita");
		frmCvLuontiOhjelma.getContentPane().add(btnSuosittelijat);
		JButton btnLataaPdf = new JButton("Lataa PDF");
		btnLataaPdf.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLataaPdf.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				checkData();
			}
		});
		btnLataaPdf.setBounds(109, 485, 140, 50);
		btnLataaPdf.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, lataat cv:n pdf tiedostona tietokoneelle");
		btnLataaPdf.setIcon(new ImageIcon(CVYhteystiedot.class.getResource("/Images/Lataa.png")));
		frmCvLuontiOhjelma.getContentPane().add(btnLataaPdf);
		JLabel lblOtsikko = new JLabel("CV:n luontiohjelma");
		lblOtsikko.setForeground(new Color(0, 0, 0));
		lblOtsikko.setHorizontalAlignment(SwingConstants.CENTER);
		lblOtsikko.setFont(new Font("DialogInput", Font.BOLD, 40));
		lblOtsikko.setBounds(165, 11, 554, 75);
		frmCvLuontiOhjelma.getContentPane().add(lblOtsikko);
		JLabel lblYhteysIcon = new JLabel("");
		lblYhteysIcon.setIcon(new ImageIcon(CVAlkusivu.class.getResource("/Images/YhteystiedotPieni.png")));
		lblYhteysIcon.setBounds(24, 125, 75, 75);
		frmCvLuontiOhjelma.getContentPane().add(lblYhteysIcon);
		JLabel lblProfiiliIcon = new JLabel("");
		lblProfiiliIcon.setIcon(new ImageIcon(CVAlkusivu.class.getResource("/Images/ProfiiliPieni.png")));
		lblProfiiliIcon.setBounds(24, 225, 75, 75);
		frmCvLuontiOhjelma.getContentPane().add(lblProfiiliIcon);
		JLabel lblVahvuudetIcon = new JLabel("");
		lblVahvuudetIcon.setIcon(new ImageIcon(CVAlkusivu.class.getResource("/Images/VahvuudetPieni.png")));
		lblVahvuudetIcon.setBounds(24, 325, 75, 75);
		frmCvLuontiOhjelma.getContentPane().add(lblVahvuudetIcon);
		JLabel lblSuosittelijaIcon = new JLabel("");
		lblSuosittelijaIcon.setIcon(new ImageIcon(CVAlkusivu.class.getResource("/Images/SuosittelijaPieni.png")));
		lblSuosittelijaIcon.setBounds(497, 325, 75, 75);
		frmCvLuontiOhjelma.getContentPane().add(lblSuosittelijaIcon);
		JLabel lblKoulutusP = new JLabel("");
		lblKoulutusP.setIcon(new ImageIcon(CVAlkusivu.class.getResource("/Images/KoulutusPieni.png")));
		lblKoulutusP.setBounds(497, 225, 75, 75);
		frmCvLuontiOhjelma.getContentPane().add(lblKoulutusP);
		JLabel lblTyoP = new JLabel("");
		lblTyoP.setIcon(new ImageIcon(CVAlkusivu.class.getResource("/Images/TyokokemusPieni.png")));
		lblTyoP.setBounds(497, 125, 75, 75);
		frmCvLuontiOhjelma.getContentPane().add(lblTyoP);
		JLabel lblPDF = new JLabel("");
		lblPDF.setIcon(new ImageIcon(CVAlkusivu.class.getResource("/Images/LuoPdfPieni.png")));
		lblPDF.setBounds(24, 460, 75, 75);
		frmCvLuontiOhjelma.getContentPane().add(lblPDF);
		JButton btnPoistu = new JButton("Poistu");
		btnPoistu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int ret = JcustomDialog.showOptionDialog(f, "Oletko varma, että haluat lopettaa?", "Lopeta",
				        JcustomDialog.QUESTION_MESSAGE, JcustomDialog.YES_NO_OPTION, JcustomDialog.NO_OPTION);
				if (ret == JcustomDialog.YES_OPTION) System.exit(0);
			}
		});
		btnPoistu.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnPoistu.setBounds(582, 485, 280, 50);
		btnPoistu.getAccessibleContext().setAccessibleDescription("Painamalla tätä nappia, poistut ohjelmasta");
		frmCvLuontiOhjelma.getContentPane().add(btnPoistu);
		JButton btnPoista = new JGradientButton("Poista kaikki tiedot");
		btnPoista.setBackground(new Color(0xE8A0B0));
		btnPoista.setIcon(new ImageIcon(CVYhteystiedot.class.getResource("/Images/Poista.png")));
		btnPoista.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int result = JcustomDialog.showOptionDialog(f, "Haluatko varmasti poistaa KAIKKI tallentamasi tiedot",
				        "Varoitus!", JcustomDialog.QUESTION_MESSAGE, JcustomDialog.YES_NO_OPTION,
				        JcustomDialog.NO_OPTION);

				// Tyhjentää kaikki tekstitiedostot
				if (result == JcustomDialog.YES_OPTION)
				{
					String[] tiedosto =
					{ "Kieli", "Koulutus", "KoulutusMuu", "Kuva", "Profiili", "Suosittelija", "Taidot", "Tyokokemus",
					        "Yhteystiedot" };

					try
					{

						for (int i = 0; i < tiedosto.length; i++)
						{
							FileWriter fw = new FileWriter(savePath + tiedosto[i] + ".txt", false);
							fw.write("");
							fw.close();
						}
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
					JcustomDialog.showMessageDialog(f, "Tiedot poistettu", "Ilmoitus!", JcustomDialog.PLAIN_MESSAGE);
				}
			}
		});
		btnPoista.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnPoista.setBounds(582, 430, 280, 50);
		btnPoista.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, poistat kaikki tallennetut tiedot");
		frmCvLuontiOhjelma.getContentPane().add(btnPoista);
		JComboBox <String> comboBox = new JComboBox <String>();
		comboBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				switch (comboBox.getSelectedIndex())
				{
					default:
						borderColor = CVLuoPDF.getRandomColorString();
						break;

					case 1:
						borderColor = "red";
						break;

					case 2:
						borderColor = "blue";
						break;

					case 3:
						borderColor = "green";
						break;

					case 4:
						borderColor = "gray";
						break;

					case 5:
						borderColor = "black";
						break;

					case 6:
						borderColor = "maroon";
						break;

					case 7:
						borderColor = "purple";
						break;

					case 8:
						borderColor = "magenta";
						break;

					case 9:
						borderColor = "spesia";
						break;
				}
				frmCvLuontiOhjelma.getContentPane().setBackground(CVLuoPDF.getColor(borderColor)[1]);
				pdf.setBorderColor(borderColor);
				CVEsikatselu.PreviewRefresh(CVAlkusivu.borderColor);
			}
		});
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBox.setModel(new DefaultComboBoxModel <String>(new String[]
		{ "PDF Sivun Väri", "Punainen", "Sininen", "Vihreä", "Harmaa", "Musta", "Punaisenruskea", "Violetti", "Magenta",
		        "Spesia Sininen" }));
		comboBox.setBounds(109, 430, 280, 50);
		comboBox.setSelectedIndex(0);
		frmCvLuontiOhjelma.getContentPane().add(comboBox);
		JButton btnPreview = new JButton("Esikatselu");
		btnPreview.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!esikatselu) checkDataPreview();
			}
		});
		btnPreview.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnPreview.setIcon(new ImageIcon(CVYhteystiedot.class.getResource("/Images/Esikatselu.png")));
		btnPreview.setBounds(250, 485, 140, 50);
		frmCvLuontiOhjelma.getContentPane().add(btnPreview);
		borderChange b = new borderChange();
		b.addBorder(frmCvLuontiOhjelma);
		JLabel IMAGE_BACKGROUND = new JLabel("");
		IMAGE_BACKGROUND.setIcon(new ImageIcon(CVAlkusivu.class.getResource("/Images/background_overlay.png")));
		IMAGE_BACKGROUND.setBounds(0, 0, 884, 561);
		frmCvLuontiOhjelma.getContentPane().add(IMAGE_BACKGROUND);
		util.setKeyListener(frmCvLuontiOhjelma);
	}

	/**
	 * Tarkistaa, että tiedostot löytyvät ja ne sisältävät dataa
	 */
	private void checkData()
	{

		try
		{
			var _cwd = Paths.get(savePath).toString().replace("\\", "/");
			String[] fname =
			{ "Kieli", "Koulutus"/* , "KoulutusMuu" */, "Kuva", "Profiili", "Suosittelija", "Taidot", "Tyokokemus",
			        "Yhteystiedot" };
			// Initial missing, empty files and Valid Files
			ArrayList <String> validFiles = new ArrayList <>();
			ArrayList <String> unValidFiles = new ArrayList <>();

			for (var name : fname)
			{
				File file = new File(_cwd, name + ".txt");
				if (file.exists()) try
				{
					var data = Files.readString(file.toPath());

					if (data.length() < 1)
					{
						unValidFiles.add(name);
						System.out.printf("\033[4;31mMissing & Empty Files\033[0m: %s\n", name);
					}
					else
					{
						validFiles.add(name);
						System.out.printf("\033[4;32mValid Files\033[0m: %s\n", name);
					}
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
					unValidFiles.add(name);
				}
			}
			var MissingString = unValidFiles.toString().replace("[", "").replace("]", "");

			if (MissingString.contains(","))
			{
				var comma = MissingString.lastIndexOf(",");
				MissingString = MissingString.substring(0, comma) + " &" + MissingString.substring(comma + 1);
			}
			var Stringi = String.format(
			        "Halautko varmasti ladata pdf tiedostosi,\n vaikka sinulta puuttuu tiedot vielä %s sivuilta",
			        MissingString);

			if (validFiles.isEmpty())
			{
				JcustomDialog.showMessageDialog(frmCvLuontiOhjelma, "Et voi tehdä pdf tiedostoa ilman tietoja",
				        "Virhe!", JcustomDialog.ERROR_MESSAGE);
			}
			else if (!validFiles.contains("Profiili") || !validFiles.contains("Yhteystiedot"))
			    JcustomDialog.showMessageDialog(frmCvLuontiOhjelma,
			            "PDF Tiedoston luomiseen tarvitset tiedot ainakin kohtiin Yhteystiedot ja Profiili", "Virhe!",
			            JcustomDialog.ERROR_MESSAGE);
			else if (!unValidFiles.isEmpty())
			{
				var ans = JcustomDialog.showOptionDialog(frmCvLuontiOhjelma, Stringi, "Varoitus!",
				        JcustomDialog.WARNING_MESSAGE, JcustomDialog.OK_CANCEL_OPTION, JcustomDialog.CANCEL_OPTION);

				if (ans == JcustomDialog.OK_OPTION)
				{ pdf.luo(); }
			}
			else
			{
				System.out.println("All files should exists");
				pdf.luo();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void checkDataPreview()
	{

		try
		{
			var _cwd = Paths.get(savePath).toString().replace("\\", "/");
			String[] fname =
			{ "Profiili", "Yhteystiedot" };
			// Initial missing files, existing files & existing files which are
			// empty
			ArrayList <String> existingFiles = new ArrayList <>();
			ArrayList <String> filesMissingData = new ArrayList <>();

			for (var name : fname)
			{
				File file = new File(_cwd, name + ".txt");
				if (file.exists()) try
				{
					var data = Files.readString(file.toPath());

					if (data.length() < 1)
					{
						filesMissingData.add(name);
						System.out.printf("\033[4;31m Missing & EmptyFiles\033[0m: %s\n", name);
					}
					else
					{
						existingFiles.add(name);
						System.out.printf("\033[4;32mValid Files\033[0m: %s\n", name);
					}
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
					filesMissingData.add(name);
				}
			}
			var MissingString = filesMissingData.toString().replace("[", "").replace("]", "");

			if (MissingString.contains(","))
			{
				var comma = MissingString.lastIndexOf(",");
				MissingString = MissingString.substring(0, comma) + " &" + MissingString.substring(comma + 1);
			}

			if (existingFiles.isEmpty())
			{
				JcustomDialog.showMessageDialog(null, "Tarvitset tiedot, jotta voi esikatsella pdf:ää", "Virhe!",
				        JcustomDialog.ERROR_MESSAGE);
			}
			else if (!existingFiles.contains("Profiili") || !existingFiles.contains("Yhteystiedot"))
			    JcustomDialog.showMessageDialog(null,
			            "PDF esikatselun luomiseen tarvitset tiedot ainakin kohtiin Yhteystiedot ja Profiili", "Virhe!",
			            JcustomDialog.ERROR_MESSAGE);
			else
			{
				System.out.println("All required files exists");
				CVEsikatselu.main(null);
				CVEsikatselu.PreviewRefresh(CVAlkusivu.borderColor);
				esikatselu = true;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
