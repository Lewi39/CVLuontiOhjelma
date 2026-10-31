package cv;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.Color;

public class CVProfiili {
	private String savePath = CVAlkusivu.savePath;

	private JFrame frmProfiili;

	private JFrame f = frmProfiili;

	private JLabel lblLisKuva;

	private JTextArea txtrTextarea;

	private BufferedImage imgProfile = null;

	private String savedImage = "";

	public static String imageFile = "";

	public static float profileSize = 1;

	public static Point offset = new Point();

	public static JPanel panelImage;

	public static boolean forceAskToSave = false;

	public String profiili = "";

	private int count = 1500;

	private JButton btnMuokkaa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					CVProfiili window = new CVProfiili();
					window.frmProfiili.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CVProfiili() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		forceAskToSave = false;
		frmProfiili = new JFrame();
		frmProfiili.setResizable(false);
		frmProfiili.getContentPane().setBackground(new Color(0xD8E8F0));
		frmProfiili.setIconImage(
				Toolkit.getDefaultToolkit().getImage(CVProfiili.class.getResource("/Images/Profiili.png")));
		frmProfiili.setTitle("Profiili");
		frmProfiili.setSize(900, 600);
		frmProfiili.setLocationRelativeTo(null);
		frmProfiili.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProfiili.getContentPane().setLayout(null);
		lataaKuvat(null);
		JLabel lblProfiili = new JLabel("Profiili");
		lblProfiili.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblProfiili.setBounds(10, 11, 389, 50);
		frmProfiili.getContentPane().add(lblProfiili);
		JButton btnTallenna = new JGradientButton("Tallenna");
		btnTallenna.setBackground(new Color(0xB0F0A8));
		btnTallenna.setIcon(new ImageIcon(CVProfiili.class.getResource("/Images/Tallenna.png")));
		btnTallenna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				forceAskToSave = false;
				profiili = txtrTextarea.getText();

				if (imageFile != null && imgProfile != null) {

					try { // Kuvan tallennus
						savedImage = imageFile;
						FileWriter fw = new FileWriter(savePath + "Kuva.txt", false);
						var xOff = ((double) offset.x / 150);
						var yOff = ((double) offset.y / 150);
						fw.write(imageFile + "\n" + profileSize + "\n" + xOff + "\n" + yOff);
						fw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				if (txtrTextarea.getText() != null) {

					try { // Profiili tallennus
						FileWriter fw = new FileWriter(savePath + "Profiili.txt", false);
						fw.write(txtrTextarea.getText());
						fw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (CVAlkusivu.esikatselu)
					CVEsikatselu.PreviewRefresh(null);
				JcustomDialog.showMessageDialog(f,"Tiedot tallennettu", "Ilmoitus", JcustomDialog.PLAIN_MESSAGE);
			}
		});
		btnTallenna.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTallenna.setBounds(459, 417, 160, 50);
		btnTallenna.getAccessibleContext()
				.setAccessibleDescription("Painamalla tätä nappia, lisäämäsi tiedot ja kuva tallennetaa.");
		frmProfiili.getContentPane().add(btnTallenna);
		JButton btnTyhjenna = new JButton("Tyhjennä");
		btnTyhjenna.setIcon(new ImageIcon(CVProfiili.class.getResource("/Images/Tyhjenna.png")));
		btnTyhjenna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtrTextarea.setText("");
			}
		});
		btnTyhjenna.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTyhjenna.setBounds(459, 478, 160, 50);
		btnTyhjenna.getAccessibleContext()
				.setAccessibleDescription("Painamalla tätä nappia, tyhjennät syöttökentissä olevat tiedot.");
		frmProfiili.getContentPane().add(btnTyhjenna);
		JButton btnPoista = new JGradientButton("Poista");
		btnPoista.setBackground(new Color(0xE8A0B0));
		btnPoista.setIcon(new ImageIcon(CVProfiili.class.getResource("/Images/Poista.png")));
		btnPoista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JcustomDialog.showConfirmDialog(f, "Haluatko varmasti poistaa tiedot", "Varoitus!",
						JcustomDialog.QUESTION_MESSAGE, JcustomDialog.YES_NO_OPTION, JcustomDialog.NO_OPTION);

				if (result == JcustomDialog.YES_OPTION) {

					try {
						FileWriter fw = new FileWriter(savePath + "Kuva.txt", false);
						fw.write("");
						fw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					try {
						FileWriter fw = new FileWriter(savePath + "Profiili.txt", false);
						fw.write("");
						fw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					profiili = "";
					imageFile = "";
					savedImage = "";
					if (imgProfile != null)
						imgProfile.flush();
					imgProfile = null;
					panelImage.repaint();
					lblLisKuva.setText("Lisää kuva");
					txtrTextarea.setText(null);
					JcustomDialog.showMessageDialog(f,"Tiedot poistettu", "Ilmoitus", JcustomDialog.PLAIN_MESSAGE);
					btnMuokkaa.setEnabled(false);
				}
			}
		});
		btnPoista.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPoista.setBounds(691, 417, 160, 50);
		btnPoista.getAccessibleContext()
				.setAccessibleDescription("Painamalla tätä nappia, poistat tallentamasi tiedot ja kuvan.");
		frmProfiili.getContentPane().add(btnPoista);
		JButton btnTakaisin = new JButton("Alkusivu");
		btnTakaisin.setIcon(new ImageIcon(CVProfiili.class.getResource("/Images/Alkusivu.png")));
		btnTakaisin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (profiili.compareTo(txtrTextarea.getText()) == 0 && imageFile == savedImage && !forceAskToSave) {
					frmProfiili.setVisible(false);
					CVAlkusivu.main(null);
					imageFile = "";
				} else {
					System.out.println("imageFile= " + imageFile);
					System.out.println("savedImage= " + savedImage);
					System.out.println(imageFile == savedImage);
					int result = JcustomDialog.showOptionDialog(f, "Haluatko poistua tallentamatta muutoksia?",
							"Varoitus!", JcustomDialog.QUESTION_MESSAGE, JcustomDialog.YES_NO_OPTION,
							JcustomDialog.NO_OPTION);

					if (result == JcustomDialog.YES_OPTION) {
						frmProfiili.setVisible(false);
						CVAlkusivu.main(null);
					}
				}
			}
		});
		btnTakaisin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTakaisin.setBounds(691, 478, 160, 50);
		btnTakaisin.getAccessibleContext()
				.setAccessibleDescription("Painamalla tätä nappia, siirryt takaisin alkusivulle.");
		frmProfiili.getContentPane().add(btnTakaisin);
		JLabel lblEsikatselu = new JLabel("Kuvaa itseäsi vapaasti (Max 1500 kirjainta)");
		lblEsikatselu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEsikatselu.setBounds(10, 57, 429, 37);
		lblEsikatselu.setLabelFor(txtrTextarea);
		frmProfiili.getContentPane().add(lblEsikatselu);
		txtrTextarea = new JTextArea();
		txtrTextarea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

				if (txtrTextarea.getText().length() >= count + 1) {
					e.consume();
					txtrTextarea.setText(txtrTextarea.getText().substring(0, count));
				} else if (txtrTextarea.getText().length() >= count) {
					e.consume();
				}
			}
		});
		txtrTextarea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtrTextarea.setWrapStyleWord(true);
		txtrTextarea.setLineWrap(true);
		txtrTextarea.setBounds(10, 95, 429, 433);
		txtrTextarea.getAccessibleContext().getAccessibleEditableText();
		profiiliHaku(); // hakee tiedostossa olevan profiili tekstin
		frmProfiili.getContentPane().add(txtrTextarea);
		JButton btnLisUusiKuva = new JButton("Lisää uusi kuva itsestäsi");
		btnLisUusiKuva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.put("FileChooser.cancelButtonText", "Peruuta");
				JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				fc.setAcceptAllFileFilterUsed(false);
				fc.addChoosableFileFilter(
						new FileNameExtensionFilter("Image File (jpg, jpeg, png)", "jpg", "jpeg", "png", "gif"));
				int ret = fc.showDialog(null, "Avaa");

				if (ret == JFileChooser.APPROVE_OPTION) {
					imageFile = fc.getSelectedFile().getAbsolutePath();
					lataaKuvat(imageFile);
					panelImage.repaint();
					lblLisKuva.setText(null);
					btnMuokkaa.setEnabled(true);
				}
			}
		});
		btnLisUusiKuva.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLisUusiKuva.setBounds(511, 363, 316, 37);
		frmProfiili.getContentPane().add(btnLisUusiKuva);
		panelImage = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				float cPos = (300 / 2);
				float cSize = (300 / 2.575f) * 1.035f;
				g.setColor(new Color(0x9FC2FD));
				g.fillOval((int) (cPos - cSize), (int) (cPos - cSize), (int) (cSize * 2), (int) (cSize * 2));
				cSize = (300 / 2.575f);
				Ellipse2D clip = new Ellipse2D.Float(cPos - cSize, cPos - cSize, cSize * 2, cSize * 2);
				g.setClip(clip);

				if (imgProfile != null) {
					float height = (float) imgProfile.getHeight() / (float) imgProfile.getWidth();
					int ww = (int) (300 * profileSize);
					int hh = (int) ((300 * height) * profileSize);
					int x = (int) (300 / 2 - ww / 2);
					int y = (int) (300 / 2 - hh / 2);
					g.drawImage(imgProfile, x + offset.x, y + offset.y, ww, hh, null);
				}
			}
		};
		panelImage.setBounds(519, 11, 300, 300);
		panelImage.setOpaque(false);
		frmProfiili.getContentPane().add(panelImage);
		panelImage.setLayout(new BorderLayout(0, 0));
		lblLisKuva = new JLabel("Lisää kuva");
		lblLisKuva.setHorizontalAlignment(SwingConstants.CENTER);
		lblLisKuva.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelImage.add(lblLisKuva, BorderLayout.CENTER);
		btnMuokkaa = new JButton("Muokkaa kuvan kokoa ja sijaintia");
		btnMuokkaa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CVProfiiliKuva.main(new String[] { "o" });
			}
		});
		btnMuokkaa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnMuokkaa.setBounds(511, 319, 316, 37);
		btnMuokkaa.setEnabled(false);
		frmProfiili.getContentPane().add(btnMuokkaa);
		JLabel imgBG = new JLabel("");
		imgBG.setIcon(new ImageIcon(getClass().getResource("/Images/background_overlay.png")));
		imgBG.setBounds(0, 0, 884, 561);
		frmProfiili.getContentPane().add(imgBG);
		kuvanHaku();
		borderChange b = new borderChange();
		b.addBorder(frmProfiili);
		Util util = new Util();
		util.setKeyListener(frmProfiili);
	}

	private void lataaKuvat(String profiiliKuva) {

		try {
			if (profiiliKuva != null)
				imgProfile = ImageIO.read(new File(profiiliKuva));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void kuvanHaku() {
		String kuva = null;
		String size = null;
		String xOff = null;
		String yOff = null;
		File tiedosto = new File(savePath + "Kuva.txt");

		if (tiedosto.exists()) {

			try {
				BufferedReader br = new BufferedReader(new FileReader(savePath + "Kuva.txt"));
				kuva = br.readLine();
				size = br.readLine();
				xOff = br.readLine();
				yOff = br.readLine();
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			if (kuva != null) {
				imageFile = kuva;
				savedImage = kuva;
				lataaKuvat(kuva);
				panelImage.repaint();
				lblLisKuva.setText(null);
				btnMuokkaa.setEnabled(true);
			}

			if (size != null) {
				profileSize = Float.parseFloat(size);
				// slider.setValue((int) (profileSize * 100));
			}

			if (xOff != null && yOff != null) {
				offset.x = (int) (Double.parseDouble(xOff) * 150d);
				offset.y = (int) (Double.parseDouble(yOff) * 150d);
			}
		}
	}

	private void profiiliHaku() {
		File tiedosto = new File(savePath + "Profiili.txt");

		if (tiedosto.exists()) {

			try {
				String rivi;
				BufferedReader br = new BufferedReader(new FileReader(savePath + "Profiili.txt"));

				while ((rivi = br.readLine()) != null) {
					profiili += rivi + "\n";
				}
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// profiili = profiili.substring(0, profiili.length() - 1);
			txtrTextarea.setText(profiili);
		}
	}
}
