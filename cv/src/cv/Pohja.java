package cv;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Pohja {
	
	private JFrame frame;
	private JTextField txtNimike;
	private JTextField txtAntaja;
	private final Color ERROR_BORDER = new Color(220, 53, 69);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pohja window = new Pohja();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public Pohja() {
		initialize();
		CVLuoPDF luoPDF = new CVLuoPDF("random", CVAlkusivu.savePath);
		luoPDF.luo();
		System.exit(0);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(900, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(0, 0, 886, 563);
		frame.getContentPane().add(panel_1);
		
		JLabel lblNewLabel = new JLabel("Otsikko");
		lblNewLabel.setBounds(10, 11, 389, 50);
		panel_1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		txtNimike = new JTextField();
		txtNimike.setBounds(10, 90, 150, 37);
		panel_1.add(txtNimike);
		txtNimike.setColumns(10);
		
		JButton btnNewButton = new JButton("Lisää uusi työkokemus");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtNimike.getText().length()<1) {
					markError(txtNimike);
				}
			}
		});
		btnNewButton.setBounds(20, 478, 316, 50);
		panel_1.add(btnNewButton);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnTallenna = new JButton("Tallenna");
		btnTallenna.setBounds(459, 417, 160, 50);
		panel_1.add(btnTallenna);
		btnTallenna.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnTyhjenn = new JButton("Tyhjennä");
		btnTyhjenn.setBounds(459, 478, 160, 50);
		panel_1.add(btnTyhjenn);
		btnTyhjenn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnPoista = new JButton("Poista");
		btnPoista.setBounds(691, 417, 160, 50);
		panel_1.add(btnPoista);
		btnPoista.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnTallenna_2_1 = new JButton("Alkusivu");
		btnTallenna_2_1.setBounds(691, 478, 160, 50);
		panel_1.add(btnTallenna_2_1);
		btnTallenna_2_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JComboBox<String> cboxAlkamisKuu = new JComboBox<String>();
		cboxAlkamisKuu.setBounds(10, 189, 130, 37);
		panel_1.add(cboxAlkamisKuu);
		cboxAlkamisKuu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JComboBox<String> cboxAlkamisVuosi = new JComboBox<String>();
		cboxAlkamisVuosi.setBounds(198, 189, 130, 37);
		panel_1.add(cboxAlkamisVuosi);
		cboxAlkamisVuosi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel_1 = new JLabel("Työnimike");
		lblNewLabel_1.setBounds(10, 61, 200, 31);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		txtAntaja = new JTextField();
		txtAntaja.setBounds(199, 90, 150, 37);
		panel_1.add(txtAntaja);
		txtAntaja.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Työnantaja");
		lblNewLabel_1_1.setBounds(199, 61, 200, 31);
		panel_1.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblNewLabel_1_2 = new JLabel("Alkamispäivä");
		lblNewLabel_1_2.setBounds(10, 147, 200, 31);
		panel_1.add(lblNewLabel_1_2);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblNewLabel_1_3 = new JLabel("Päättymispäivä");
		lblNewLabel_1_3.setBounds(10, 237, 200, 31);
		panel_1.add(lblNewLabel_1_3);
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JComboBox<String> cboxPaattymisVuosi = new JComboBox<String>();
		cboxPaattymisVuosi.setBounds(198, 280, 130, 37);
		panel_1.add(cboxPaattymisVuosi);
		cboxPaattymisVuosi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JComboBox<String> cboxPaattymisKuu = new JComboBox<String>();
		cboxPaattymisKuu.setBounds(10, 280, 130, 37);
		panel_1.add(cboxPaattymisKuu);
		cboxPaattymisKuu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JTextArea txtLisatieto = new JTextArea();
		txtLisatieto.setBounds(10, 370, 338, 89);
		panel_1.add(txtLisatieto);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Lisätieto");
		lblNewLabel_1_3_1.setBounds(10, 328, 200, 31);
		panel_1.add(lblNewLabel_1_3_1);
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblEsikatselu = new JLabel("Esikatselu");
		lblEsikatselu.setBounds(445, 11, 212, 37);
		panel_1.add(lblEsikatselu);
		lblEsikatselu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JTextArea txtrTextarea = new JTextArea();
		txtrTextarea.setBounds(445, 48, 429, 347);
		panel_1.add(txtrTextarea);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 886, 563);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnYhteystiedot = new JButton("Yhteystiedot / Henkilötiedot");
		btnYhteystiedot.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnYhteystiedot.setBounds(112, 150, 280, 50);
		panel.add(btnYhteystiedot);
		
		JButton btnProfiili = new JButton("Profiili");
		btnProfiili.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnProfiili.setBounds(112, 250, 280, 50);
		panel.add(btnProfiili);
		
		JButton btnVahvuudet = new JButton("Taidot / Vahvuudet");
		btnVahvuudet.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnVahvuudet.setBounds(112, 350, 280, 50);
		panel.add(btnVahvuudet);
		
		JButton btnTyokokemus = new JButton("Työkokemus");
		btnTyokokemus.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnTyokokemus.setBounds(585, 150, 280, 50);
		panel.add(btnTyokokemus);
		
		JButton btnKoulutus = new JButton("Koulutus");
		btnKoulutus.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnKoulutus.setBounds(585, 250, 280, 50);
		panel.add(btnKoulutus);
		
		JButton btnSuosittelijat = new JButton("Suosittelijat");
		btnSuosittelijat.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSuosittelijat.setBounds(585, 350, 280, 50);
		panel.add(btnSuosittelijat);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Pohja.class.getResource("/Images/YhteystiedotPieni.png")));
		lblNewLabel_2.setBounds(27, 125, 75, 75);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("");
		lblNewLabel_2_1.setIcon(new ImageIcon(Pohja.class.getResource("/Images/ProfiiliPieni.png")));
		lblNewLabel_2_1.setBounds(27, 225, 75, 75);
		panel.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("");
		lblNewLabel_2_2.setIcon(new ImageIcon(Pohja.class.getResource("/Images/VahvuudetPieni.png")));
		lblNewLabel_2_2.setBounds(27, 325, 75, 75);
		panel.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_3 = new JLabel("");
		lblNewLabel_2_3.setIcon(new ImageIcon(Pohja.class.getResource("/Images/TyokokemusPieni.png")));
		lblNewLabel_2_3.setBounds(500, 125, 75, 75);
		panel.add(lblNewLabel_2_3);
		
		JLabel lblNewLabel_2_4 = new JLabel("");
		lblNewLabel_2_4.setIcon(new ImageIcon(Pohja.class.getResource("/Images/KoulutusPieni.png")));
		lblNewLabel_2_4.setBounds(500, 225, 75, 75);
		panel.add(lblNewLabel_2_4);
		
		JLabel lblNewLabel_2_5 = new JLabel("");
		lblNewLabel_2_5.setIcon(new ImageIcon(Pohja.class.getResource("/Images/SuosittelijaPieni.png")));
		lblNewLabel_2_5.setBounds(500, 325, 75, 75);
		panel.add(lblNewLabel_2_5);
		
		JLabel lblNewLabel_3 = new JLabel("CV luonti ohjelma");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblNewLabel_3.setBounds(168, 11, 554, 75);
		panel.add(lblNewLabel_3);
		
		JButton btnPoistu = new JButton("Poistu");
		btnPoistu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPoistu.setBounds(705, 485, 160, 50);
		panel.add(btnPoistu);
		
		JLabel lblNewLabel_2_2_1 = new JLabel("");
		lblNewLabel_2_2_1.setIcon(new ImageIcon(Pohja.class.getResource("/Images/LuoPdfPieni.png")));
		lblNewLabel_2_2_1.setBounds(27, 460, 75, 75);
		panel.add(lblNewLabel_2_2_1);
		
		JButton btnLuoPdf = new JButton("Luo PDF");
		btnLuoPdf.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLuoPdf.setBounds(112, 487, 280, 50);
		panel.add(btnLuoPdf);
	}
	private void markError(JComponent comp) {
		comp.setBorder(new LineBorder(ERROR_BORDER, 1));
		comp.requestFocus();
	}
}
