package cv;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class CVVahvuudet
{
	private String savePath = CVAlkusivu.savePath;

	private JFrame frmVahvuudet;

	private JFrame f = frmVahvuudet;

	private JTextField txtLang;

	private JComboBox <String> cboxKieli;

	private JComboBox <String> cboxTaito;

	private JTextArea txtMuu;

	private JTextArea txtEsi;

	public String taito = "";

	public String taitoMuu = "";

	public String tallennettu = "";

	public String Stringtiedosto;

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
					CVVahvuudet window = new CVVahvuudet();
					window.frmVahvuudet.setVisible(true);
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
	public CVVahvuudet()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmVahvuudet = new JFrame();
		frmVahvuudet.getContentPane().setBackground(new Color(0xD8E8F0));
		frmVahvuudet.setIconImage(
		        Toolkit.getDefaultToolkit().getImage(CVVahvuudet.class.getResource("/Images/Vahvuudet.png")));
		frmVahvuudet.setTitle("Vahvuudet");
		frmVahvuudet.setResizable(false);
		frmVahvuudet.setSize(900, 600);
		frmVahvuudet.setLocationRelativeTo(null);
		frmVahvuudet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVahvuudet.getContentPane().setLayout(null);
		JLabel lblOtsikko = new JLabel("Taidot ja Vahvuudet");
		lblOtsikko.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblOtsikko.setBounds(10, 11, 389, 50);
		frmVahvuudet.getContentPane().add(lblOtsikko);
		JButton btnLisaa = new JButton("Lisää kielitaidot");
		btnLisaa.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ShowPreview();
			}
		});
		btnLisaa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLisaa.setBounds(20, 200, 320, 50);
		btnLisaa.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, lisäät syöttämäsi tiedot esikatseluun.");
		frmVahvuudet.getContentPane().add(btnLisaa);
		JButton btnTallenna = new JGradientButton("Tallenna");
		btnTallenna.setBackground(new Color(0xB0F0A8));
		btnTallenna.setIcon(new ImageIcon(CVVahvuudet.class.getResource("/Images/Tallenna.png")));
		btnTallenna.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tallennettu = txtEsi.getText();

				if (txtEsi.getText() != null)
				{

					try
					{
						FileWriter fw = new FileWriter(savePath + "Kieli.txt", false);
						fw.write(taito);
						fw.close();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}

					try
					{
						FileWriter fw = new FileWriter(savePath + "Taidot.txt", false);
						fw.write(taitoMuu);
						fw.close();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}
				CVEsikatselu.PreviewRefresh(null);
				JcustomDialog.showMessageDialog(f, "Tiedot tallennettu", "", JcustomDialog.INFORMATION_MESSAGE);
			}
		});
		txtEsi = new JTextArea();
		txtEsi.setLineWrap(true);
		txtEsi.setWrapStyleWord(true);
		txtEsi.setEditable(false);
		btnTallenna.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTallenna.setBounds(459, 417, 160, 50);
		btnTallenna.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, lisäämäsi tiedot tallennetaa.");
		frmVahvuudet.getContentPane().add(btnTallenna);
		JButton btnTyhjenna = new JButton("Tyhjennä");
		btnTyhjenna.setIcon(new ImageIcon(CVVahvuudet.class.getResource("/Images/Tyhjenna.png")));
		btnTyhjenna.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ClearLang();
				ClearSkill();
			}
		});
		btnTyhjenna.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTyhjenna.setBounds(459, 478, 160, 50);
		btnTyhjenna.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, tyhjennät syöttökentissä olevat tiedot.");
		frmVahvuudet.getContentPane().add(btnTyhjenna);
		JButton btnPoista = new JGradientButton("Poista");
		btnPoista.setBackground(new Color(0xE8A0B0));
		btnPoista.setIcon(new ImageIcon(CVVahvuudet.class.getResource("/Images/Poista.png")));
		btnPoista.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int result = JcustomDialog.showConfirmDialog(frmVahvuudet, "Haluatko varmasti poistaa tiedot",
				        "Varoitus!", JcustomDialog.QUESTION_MESSAGE, JcustomDialog.YES_NO_OPTION, JcustomDialog.NO_OPTION);

				if (result == JcustomDialog.YES_OPTION)
				{

					try
					{
						FileWriter fw = new FileWriter(savePath + "Taidot.txt", false);
						fw.write("");
						fw.close();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}

					try
					{
						FileWriter fw = new FileWriter(savePath + "Kieli.txt", false);
						fw.write("");
						fw.close();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
					taito = "";
					taitoMuu = "";
					tallennettu = "";
					txtEsi.setText(null);
					JcustomDialog.showMessageDialog(f,"Tiedot poistettu","Ilmoitus",JcustomDialog.INFORMATION_MESSAGE);
				}
			}
		});
		btnPoista.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPoista.setBounds(691, 417, 160, 50);
		btnPoista.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, poistat tallentamasi tiedot.");
		frmVahvuudet.getContentPane().add(btnPoista);
		JButton btnAlkusivu = new JButton("Alkusivu");
		btnAlkusivu.setIcon(new ImageIcon(CVVahvuudet.class.getResource("/Images/Alkusivu.png")));
		btnAlkusivu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				if (tallennettu.compareTo(txtEsi.getText()) == 0)
				{
					frmVahvuudet.setVisible(false);
					CVAlkusivu.main(null);
				}
				else
				{
					int result = JcustomDialog.showConfirmDialog(frmVahvuudet, "Haluatko poistua tallentamatta muutoksia",
					        "Varoitus!", JcustomDialog.QUESTION_MESSAGE, JcustomDialog.YES_NO_OPTION,
					        JcustomDialog.NO_OPTION);

					if (result == JcustomDialog.YES_OPTION)
					{ frmVahvuudet.setVisible(false); CVAlkusivu.main(null); }
				}
			}
		});
		btnAlkusivu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAlkusivu.setBounds(691, 478, 160, 50);
		btnAlkusivu.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, siirryt takaisin alkusivulle.");
		frmVahvuudet.getContentPane().add(btnAlkusivu);
		JLabel lblEsikatselu = new JLabel("Esikatselu");
		lblEsikatselu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEsikatselu.setBounds(445, 11, 212, 37);
		frmVahvuudet.getContentPane().add(lblEsikatselu);
		TaitoHaku();
		cboxKieli = new JComboBox <String>();
		cboxKieli.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				txtLang.setEnabled(cboxKieli.getSelectedIndex() == 4);
				txtLang.setBackground(new Color((cboxKieli.getSelectedIndex() == 4) ? 0xFFFFFF : 0xF0F0F0));
			}
		});
		cboxKieli.setModel(new DefaultComboBoxModel <String>(new String[]
		{ "", "Suomi", "Englanti", "Ruotsi", "Muu" }));
		cboxKieli.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cboxKieli.setBounds(20, 110, 150, 37);
		frmVahvuudet.getContentPane().add(cboxKieli);
		JLabel lblNewLabel = new JLabel("Kielitaidot");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(20, 72, 320, 27);
		lblNewLabel.setLabelFor(cboxKieli);
		lblNewLabel.setLabelFor(cboxTaito);
		frmVahvuudet.getContentPane().add(lblNewLabel);
		txtMuu = new JTextArea();
		txtMuu.setWrapStyleWord(true);
		txtMuu.setLineWrap(true);
		txtMuu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtMuu.setBounds(20, 310, 320, 157);
		txtMuu.getAccessibleContext().getAccessibleEditableText();
		frmVahvuudet.getContentPane().add(txtMuu);
		cboxTaito = new JComboBox <String>();
		cboxTaito.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cboxTaito.setModel(new DefaultComboBoxModel <String>(new String[]
		{ "", "alkeet", "tyydyttävä", "hyvä", "erinomainen", "äidinkieli" }));
		cboxTaito.setBounds(190, 110, 150, 37);
		frmVahvuudet.getContentPane().add(cboxTaito);
		JLabel lblMuutTaidot = new JLabel("Muita taitoja tai vahvuuksia");
		lblMuutTaidot.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMuutTaidot.setBounds(20, 272, 320, 27);
		lblMuutTaidot.setLabelFor(txtMuu);
		frmVahvuudet.getContentPane().add(lblMuutTaidot);
		JButton btnLisMuutTaidot = new JButton("Lisää muut taidot");
		btnLisMuutTaidot.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ShowPreviewOther();
				// ClearSkill();
			}
		});
		btnLisMuutTaidot.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLisMuutTaidot.setBounds(20, 478, 320, 50);
		btnLisMuutTaidot.getAccessibleContext()
		        .setAccessibleDescription("Painamalla tätä nappia, lisäät syöttämäsi muut tiedot esikatseluun.");
		frmVahvuudet.getContentPane().add(btnLisMuutTaidot);
		JPanel panel = new JPanel();
		panel.setBounds(445, 48, 429, 347);
		frmVahvuudet.getContentPane().add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		scrollPane.setViewportView(txtEsi);
		JLabel imgBG = new JLabel("");
		imgBG.setIcon(new ImageIcon(getClass().getResource("/Images/background_overlay.png")));
		imgBG.setBounds(0, 0, 884, 561);
		frmVahvuudet.getContentPane().add(imgBG);
		borderChange _b = new borderChange();
		_b.addBorder(frmVahvuudet);
		txtLang = new JTextField();
		txtLang.setEnabled(false);
		txtLang.setColumns(1);
		txtLang.setBackground(new Color(0xF0F0F0));
		txtLang.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtLang.setBounds(20, 158, 320, 37);
		txtLang.getAccessibleContext().getAccessibleEditableText();
		frmVahvuudet.getContentPane().add(txtLang);
		Util util = new Util();
		util.setKeyListener(frmVahvuudet);
	}

	public void ShowPreview()
	{
		setNormalBorders();
		StringBuilder sb = new StringBuilder();
		var _lang = cboxKieli;
		var _langSkill = cboxTaito;
		var _elseLang = txtLang.getText();
		// var _elseSkill = txtMuu.getText();
		sb.append(String.format("Kieli: %s\nKielitaito: %s\n", _lang.getSelectedItem(), _langSkill.getSelectedItem()));

		try
		{
			// Muuttaa puuttuvien kenttien rajan punaiseksi
			if (_lang.getSelectedIndex() == 0) markError(cboxKieli);
			if (_langSkill.getSelectedIndex() == 0) markError(cboxTaito);

			// Tarkistaa, että kieli ja taitotaso laatikkoon ollaan annettu arvot
			if (_lang.getSelectedIndex() == 0 || _langSkill.getSelectedIndex() == 0)
			{
				JcustomDialog.showMessageDialog(null, "Valitse kielitaitosi.", "Virhe!", 0);
			}
			// Tarkistaa onko käyttäjän valitsema kieli on "muuta"
			else if (_lang.getSelectedIndex() == 4)
			{

				try
				{

					// Tarkistaa, että käyttäjä on vastannut tekstikenttään, mistä kielestä on kyse
					if (_elseLang.isEmpty())
					{
						markError(txtLang);
						JcustomDialog.showMessageDialog(null, "Täytä lomakkeesta kielitaitosi", "Virhe", 0);
					}
					// Jos kielitaito on täytetty palautuu se suoraan
					else
					{
						String UusiTeksti = (String.format("Kieli: %s\nKielitaito: %s\n", txtLang.getText(),
						        _langSkill.getSelectedItem()));
						taito += UusiTeksti + "\n";
						txtEsi.setText(taito + taitoMuu);
						ClearLang();
					}
					// Palauttaa virheen hallinta viestin, joka kertoo, että virhe on "Muu" kielen
					// kanssa
				}
				catch (Exception ex)
				{
					System.err.println(String.format("Try-catch error on Language 'Muu'\n\n%s", ex));
				}
			}
			else
			{
				String UusiTeksti = sb.toString();
				taito += UusiTeksti + "\n";
				txtEsi.setText(taito + taitoMuu + "\n");
				ClearLang();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void ShowPreviewOther()
	{
		setNormalBorders();
		var _iElse = stringFix(txtMuu.getText());

		try
		{

			if (_iElse.isEmpty())
			{
				markError(txtMuu);
				JcustomDialog.showMessageDialog(null, "Täytä kohta muut taidot", "Virhe", 0);
			}
			else
			{
				String UusiTeksti = (String.format("Muut taidot: %s\n", _iElse));
				taitoMuu += UusiTeksti;
				// taitoMuu += "\n" + UusiTeksti;
				txtEsi.setText(taito + taitoMuu);
				ClearSkill();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void ClearLang()
	{
		cboxKieli.setSelectedIndex(0);
		cboxTaito.setSelectedIndex(0);
		txtLang.setText("");
		setNormalBorders();
	}

	public void ClearSkill()
	{
		txtLang.setText(null);
		txtMuu.setText(null);
		setNormalBorders();
	}

	public void TaitoHaku()
	{
		File tiedosto = new File(savePath + "Taidot.txt");
		File tiedosto2 = new File(savePath + "Kieli.txt");

		if (tiedosto.exists() && tiedosto2.exists())
		{

			try
			{
				String rivi;
				BufferedReader br = new BufferedReader(new FileReader(savePath + "Kieli.txt"));

				while ((rivi = br.readLine()) != null)
				{
					taito += rivi + "\n";
				}
				br.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}

			try
			{
				String rivi;
				BufferedReader br = new BufferedReader(new FileReader(savePath + "Taidot.txt"));

				while ((rivi = br.readLine()) != null)
				{
					taitoMuu += rivi + "\n";
				}
				br.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			txtEsi.setText(taito + taitoMuu);
			tallennettu = taito + taitoMuu;
		}
	}

	private String stringFix(String str)
	{

		while (str.endsWith("\n"))
		{
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	private void markError(JComponent comp)
	{
		comp.setBorder(new LineBorder(new Color(220, 53, 69), 2));
	}

	private void setNormalBorders()
	{
		cboxKieli.setBorder(null);
		cboxTaito.setBorder(null);
		txtLang.setBorder(BorderFactory.createLineBorder(Color.black));
		txtMuu.setBorder(BorderFactory.createLineBorder(Color.black));
	}
}
