package cv;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;

public class CVProfiiliKuva {
	
	private JFrame frame;
	private BufferedImage imgProfile = null;
	private float profileSize = 1;
	private Point offset = new Point();
	private JPanel panelImage;
	private JSlider slider;
	private JSlider sliderYOffset;
	private JSlider sliderXOffset;
	private JButton btnNollaa;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CVProfiiliKuva window = new CVProfiiliKuva();
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
	public CVProfiiliKuva() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		lataaKuvat();
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0xD8E8F0));
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				profileSize = CVProfiili.profileSize;
				offset = CVProfiili.offset;
				sliderXOffset.setValue(offset.x);
				sliderYOffset.setValue(-offset.y);
				slider.setValue((int) (profileSize * 100));
				imgProfile.flush();
				frame.setVisible(false);
			}
		});
		frame.setSize(400, 470);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setTitle("Muokkaa Kuvaa");
		
		panelImage = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
				float cPos = (getWidth() / 2);
				float cSize = (300 / 2.575f) * 1.035f;
				g.setColor(new Color(0x9FC2FD));
				g.fillOval((int) (cPos - cSize), (int) (cPos - cSize), (int) (cSize * 2), (int) (cSize * 2));
				cSize = (300 / 2.575f);
				Ellipse2D clip = new Ellipse2D.Float(cPos - cSize, cPos - cSize, cSize * 2, cSize * 2);
				g.setClip(clip);
                float size = profileSize;
                if (imgProfile != null) {
                	float height = (float) imgProfile.getHeight() / (float) imgProfile.getWidth();
                	int ww = (int) (300 * size);
                	int hh = (int) ((300 * height) * size);
                	int x = (int) (getWidth() / 2 - ww / 2);
                	int y = (int) (getWidth() / 2 - hh / 2);
                	g.drawImage(imgProfile, x + offset.x, y + offset.y, ww, hh, null);
                }
            }
        };
		panelImage.setBounds(67, 30, 250, 250);
		panelImage.setOpaque(false);
		panelImage.repaint();
		frame.getContentPane().add(panelImage);
		
		Point off = new Point(offset);
		sliderXOffset = new JSlider();
		sliderXOffset.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				offset.x = sliderXOffset.getValue();
				panelImage.repaint();
			}
		});
		sliderXOffset.setMinimum(-150);
		sliderXOffset.setMaximum(150);
		sliderXOffset.setValue(off.x);
		sliderXOffset.setBounds(67, 291, 250, 26);
		sliderXOffset.setOpaque(false);
		frame.getContentPane().add(sliderXOffset);
		
		sliderYOffset = new JSlider();
		sliderYOffset.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				offset.y = -sliderYOffset.getValue();
				panelImage.repaint();
			}
		});
		sliderYOffset.setOrientation(SwingConstants.VERTICAL);
		sliderYOffset.setMinimum(-150);
		sliderYOffset.setMaximum(150);
		sliderYOffset.setValue(-off.y);
		sliderYOffset.setBounds(31, 30, 26, 250);
		sliderYOffset.setOpaque(false);
		frame.getContentPane().add(sliderYOffset);
		
		int size = (int) (profileSize * 100);
		slider = new JSlider();
		slider.setMaximum(200);
		slider.setMinimum(10);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				profileSize = ((float) slider.getValue() / 100);
				panelImage.repaint();
			}
		});
		slider.setValue(size);
		slider.setBounds(67, 328, 250, 26);
		slider.setOpaque(false);
		frame.getContentPane().add(slider);
		
		JLabel lblX = new JLabel("X");
		lblX.setHorizontalAlignment(SwingConstants.LEFT);
		lblX.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblX.setBounds(316, 291, 16, 26);
		frame.getContentPane().add(lblX);
		
		JLabel lblY = new JLabel("Y");
		lblY.setVerticalAlignment(SwingConstants.TOP);
		lblY.setHorizontalAlignment(SwingConstants.CENTER);
		lblY.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblY.setBounds(31, 281, 24, 26);
		frame.getContentPane().add(lblY);
		
		JLabel lblSize = new JLabel("Koko");
		lblSize.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSize.setBounds(316, 328, 46, 26);
		frame.getContentPane().add(lblSize);

		JButton btnTallenna = new JGradientButton("Tallenna");
		btnTallenna.setBackground(new Color(0xB0F0A8));
		btnTallenna.setIcon(new ImageIcon(CVProfiiliKuva.class.getResource("/Images/Tallenna.png")));
		btnTallenna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CVProfiili.forceAskToSave = (CVProfiili.profileSize != profileSize || CVProfiili.offset.x != offset.x || CVProfiili.offset.y != offset.y);
				CVProfiili.profileSize = profileSize;
				CVProfiili.offset = new Point(offset);
				CVProfiili.panelImage.repaint();
				imgProfile.flush();
				frame.setVisible(false);
			}
		});
		btnTallenna.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTallenna.setBounds(44, 365, 125, 50);
		frame.getContentPane().add(btnTallenna);
		
		btnNollaa = new JGradientButton("Nollaa");
		btnNollaa.setBackground(new Color(0xE8A0B0));
		btnNollaa.setIcon(new ImageIcon(CVProfiiliKuva.class.getResource("/Images/Tyhjenna.png")));
		btnNollaa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profileSize = 1;
				offset = new Point();
				sliderXOffset.setValue(0);
				sliderYOffset.setValue(0);
				slider.setValue(100);
				panelImage.repaint();
			}
		});
		btnNollaa.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNollaa.setBounds(213, 365, 125, 50);
		frame.getContentPane().add(btnNollaa);
		
		borderChange b = new borderChange();
		b.addBorder(frame);
	}
	
	private void lataaKuvat() {
		try {
			if (CVProfiili.imageFile != null)
				imgProfile = ImageIO.read(new File(CVProfiili.imageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		profileSize = CVProfiili.profileSize;
		offset = new Point(CVProfiili.offset);
	}
}
