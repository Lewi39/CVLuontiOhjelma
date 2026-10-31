package cv;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Shape;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CVEsikatselu
{
	private JFrame frame;

	private static JPanel panelDraw = null;

	private static CVLuoPDF pdf = new CVLuoPDF(CVAlkusivu.borderColor, CVAlkusivu.savePath);

	private static BufferedImage imgProfile = null;

	private float imgXOff = 0.0F;

	private float imgYOff = 0.0F;

	private float imgSize = 0.0F;

	public static float previewScale = 1.5F;

	private int stringHeight = 0;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{

				try
				{
					CVEsikatselu window = new CVEsikatselu();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public CVEsikatselu()
	{

		try
		{
			initialize();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

	private void initialize() throws Throwable
	{
		pdf.loadTextFiles();
		final Map <String, String> info = pdf.getInfo();
		final Map <String, ArrayList <String>> infoLists = pdf.getInfoLists();

		try
		{
			String path = (String) info.get("Kuva");

			if (!path.isEmpty() && !path.isBlank())
			{

				if (Files.exists(Paths.get(path), new LinkOption[0]))
				{
					imgProfile = ImageIO.read(new File(path));
				}
				else
				{

					try
					{
						InputStream is = getClass().getResourceAsStream("/Images/Profiili.png");

						try
						{

							if (is == null)
							{ throw new FileNotFoundException("Default image missing from source"); }
							byte[] imageBytes = is.readAllBytes();
							ByteArrayInputStream stream = new ByteArrayInputStream(imageBytes);
							imgProfile = ImageIO.read(stream);
						}
						finally
						{

							if (is != null)
							{ is.close(); }
						}
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				CVAlkusivu.esikatselu = false;
			}
		});
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.getContentPane().setLayout((LayoutManager) null);
		frame.setResizable(false);
		frame.setSize(scaledInt(508.0F), scaledInt(716.0F));
		frame.setLocationRelativeTo((Component) null);
		panelDraw = new JPanel()
		{
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				float ww = (float) getWidth();
				float hh = (float) getHeight();
				float bWidth = ww / 3.0F;
				Color[] col = CVEsikatselu.pdf.getCurrentColor();
				g2.setColor(col[0]);
				g2.fillRect(0, 0, (int) bWidth, (int) hh);
				g2.setColor(Color.WHITE);

				if (CVEsikatselu.imgProfile != null)
				{
//					imgXOff = Float.parseFloat((String) info.get("imageXOff"));
//					imgYOff = Float.parseFloat((String) info.get("imageYOff"));
//					imgSize = Float.parseFloat((String) info.get("imageSize"));
					imgXOff = info.get("imageXOff") != null ? Float.parseFloat(info.get("imageXOff")) : 0.0F;
					imgYOff = info.get("imageYOff") != null ? Float.parseFloat(info.get("imageYOff")) : 0.0F;
					imgSize = info.get("imageSize") != null ? Float.parseFloat(info.get("imageSize")) : 1.0F;
					int cPos = (int) (bWidth / 2.0F);
					int cSize = (int) (bWidth / 2.575F);
					int offset = scaledInt(10.0F);
					Ellipse2D clip = new Ellipse2D.Float((float) (cPos - cSize), (float) (cPos - cSize + offset),
					        (float) (cSize * 2), (float) (cSize * 2));
					cSize = (int) ((double) (bWidth / 2.575F) * 1.025);
					g2.fillOval(cPos - cSize, cPos - cSize + offset, cSize * 2, cSize * 2);
					Shape clipReset = g2.getClip();
					g2.setClip(clip);
					g2.setColor(col[1]);
					g2.fillRect(0, 0, (int) bWidth, (int) bWidth);
					float height = (float) CVEsikatselu.imgProfile.getHeight()
					        / (float) CVEsikatselu.imgProfile.getWidth();
					int iw = (int) (bWidth * imgSize);
					int ih = (int) (bWidth * height * imgSize);
					int x = (int) (bWidth / 2.0F - (float) (iw / 2));
					int y = (int) (bWidth / 2.0F - (float) (ih / 2));
					int xoff = (int) (imgXOff * (bWidth / 2.0F));
					int yoff = (int) (imgYOff * (bWidth / 2.0F));
					g2.drawImage(CVEsikatselu.imgProfile, x + xoff, y + yoff + offset, iw, ih, (ImageObserver) null);
					g2.setClip(clipReset);
				}
				float yAdd = 0.0F;
				float yOffset = 0.0F;
				float yy = CVEsikatselu.imgProfile != null ? bWidth + scaledFloat(15.0F) : scaledFloat(38.0F);
				g2.setColor(Color.WHITE);
				setFontA(g2, 10);
				g2.drawString("YHTEYSTIEDOT", scaledFloat(15.0F), yy);
				g2.drawLine(scaledInt(15.0F), (int) (yy + scaledFloat(5.0F)), (int) (bWidth - scaledFloat(15.0F)),
				        (int) (yy + scaledFloat(5.0F)));
				setFontB(g2, 8);
				BufferedImage image;
				g2.drawString(info.containsKey("phone") ? (String) info.get("phone") : "Puhelin", scaledFloat(30.0F),
				        yy + scaledFloat(20.0F));
				image = getResource("/Images/YhteystiedotPieni2.png");
				g2.drawImage(image, scaledInt(15.0F), (int) (yy + scaledInt(12.5F)), (int) stringHeight,
				        (int) stringHeight, null);
				yAdd += (float) stringHeight;
				g2.drawString(info.containsKey("email") ? (String) info.get("email") : "Sähköposti", scaledFloat(30.0F),
				        yy + scaledFloat(30.0F));
				image = getResource("/Images/Email2.png");
				g2.drawImage(image, scaledInt(15.0F), (int) (yy + scaledInt(22.5F)), (int) stringHeight,
				        (int) stringHeight, null);
				yAdd += (float) stringHeight;
				yOffset = 0.0F;

				if (info.containsKey("phone") && info.containsKey("email"))
				{

					if (!((String) info.get("city")).isBlank())
					{
						yOffset += scaledFloat(10.0F);
						g2.drawString((String) info.get("city"), scaledFloat(30.0F), yy + scaledFloat(30.0F) + yOffset);
						image = getResource("/Images/kaupunki.png");
						g2.drawImage(image, scaledInt(15.0F), (int) (yy + scaledInt(22.5F) + yOffset),
						        (int) stringHeight, (int) stringHeight, null);
						yAdd += (float) stringHeight;
					}

					if (!((String) info.get("website")).isBlank())
					{
						yOffset += scaledFloat(10.0F);
						g2.drawString((String) info.get("website"), scaledFloat(30.0F),
						        yy + scaledFloat(30.0F) + yOffset);
						image = getResource("/Images/Nettisivu2.png");
						g2.drawImage(image, scaledInt(15.0F), (int) (yy + scaledInt(22.5F) + yOffset),
						        (int) stringHeight, (int) stringHeight, null);
						yAdd += (float) stringHeight;
					}
				}
				ArrayList <String> list = infoLists.get("kielet");

				if (list.size() > 0)
				{
					yy += yAdd + scaledFloat(30.0F);
					setFontA(g2, 10);
					g2.drawString("KIELET", scaledFloat(15.0F), yy);
					g2.drawLine(scaledInt(15.0F), (int) (yy + scaledFloat(5.0F)), (int) (bWidth - scaledFloat(15.0F)),
					        (int) (yy + scaledFloat(5.0F)));
					yAdd = 0.0F;

					for (int i = 0; i < list.size(); ++i)
					{
						String[] l = (list.get(i)).split("§");
						setFontB(g2, 8);
						g2.drawString(l[0], scaledFloat(15.0F),
						        yy + scaledFloat(20.0F) + (float) i * scaledFloat(10.0F));
						setFontA(g2, 8);
						g2.drawString(l[1].toUpperCase(), bWidth / 2.0F,
						        yy + scaledFloat(20.0F) + (float) i * scaledFloat(10.0F));
						yAdd += (float) stringHeight;
					}
				}
				list = infoLists.get("taidot");

				if (list.size() > 0)
				{
					yy += yAdd + scaledFloat(30.0F);
					setFontA(g2, 10);
					g2.drawString("TAIDOT", scaledFloat(15.0F), yy);
					g2.drawLine(scaledInt(15.0F), (int) (yy + scaledFloat(5.0F)), (int) (bWidth - scaledFloat(15.0F)),
					        (int) (yy + scaledFloat(5.0F)));
					yAdd = 0.0F;
					yOffset = 0.0F;

					for (int i = 0; i < list.size(); ++i)
					{
						setFontB(g2, 8);
						drawStringExt(g2, (String) list.get(i), scaledFloat(15.0F), yy + scaledFloat(20.0F) + yOffset,
						        bWidth - scaledFloat(15.0F));
						yAdd += (float) stringHeight;
						yOffset = yAdd;
					}
				}
				list = infoLists.get("suosittelijat");

				if (list.size() > 0)
				{
					yy += yAdd + scaledFloat(30.0F);
					setFontA(g2, 10);
					g2.drawString("SUOSITTELIJAT", scaledFloat(15.0F), yy);
					g2.drawLine(scaledInt(15.0F), (int) (yy + scaledFloat(5.0F)), (int) (bWidth - scaledFloat(15.0F)),
					        (int) (yy + scaledFloat(5.0F)));
					yAdd = 0.0F;
					yOffset = 0.0F;

//					for (int i = 0; i < list.size(); i += 2)
//					{
//						setFontA(g2, 8);
//						drawStringExt(g2, list.get(i), scaledFloat(15.0F), yy + scaledFloat(20.0F) + yOffset,
//						        bWidth - scaledFloat(15.0F));
//						yAdd += (float) stringHeight;
//						setFontB(g2, 8);
//						drawStringExt(g2, (String) list.get(i + 1), scaledFloat(15.0F),
//						        yy + scaledFloat(20.0F) + yOffset + (float) stringHeight + scaledFloat(20.0F),
//						        bWidth - scaledFloat(15.0F));
//						yAdd += (float) stringHeight;
//						yOffset = yAdd;
//					}
					for(int i = 0; i < list.size(); i += 2) {
					    setFontA(g2, 8);
					    drawStringExt(g2, list.get(i), scaledFloat(15.0F), yy + scaledFloat(20.0F) + yOffset, bWidth - scaledFloat(15.0F));
					    yAdd += (float)stringHeight;

					    if (i + 1 < list.size()) {
					        setFontB(g2, 8);
					        drawStringExt(g2, (String)list.get(i + 1), scaledFloat(15.0F), yy + scaledFloat(20.0F) + yOffset + (float)stringHeight + scaledFloat(20.0F), bWidth - scaledFloat(15.0F));
					        yAdd += (float)stringHeight;
					    }
					    yOffset = yAdd;
					}
				}
				g2.setColor(Color.BLACK);
				setFontA(g2, 15);

				if (info.containsKey("etunimi") && info.containsKey("sukunimi"))
				{
					g2.drawString(info.get("etunimi") + " " + info.get("sukunimi"), bWidth + scaledFloat(15.0F),
					        scaledFloat(20.0F));
				}
				else
				{
					g2.drawString("Etunimi Sukunimi", bWidth + scaledFloat(15.0F), scaledFloat(20.0F));
				}
				yy = scaledFloat(20.0F) + (float) stringHeight;
				setFontA(g2, 10);
				g2.drawString("PROFIILI", bWidth + scaledFloat(15.0F), yy);
				g2.drawLine((int) (bWidth + (float) scaledInt(15.0F)), (int) (yy + scaledFloat(5.0F)),
				        (int) (ww - scaledFloat(15.0F)), (int) (yy + scaledFloat(5.0F)));
				setFontB(g2, 8);

				if (info.containsKey("Profiili"))
				{
					drawStringExt(g2, (String) info.get("Profiili"), bWidth + scaledFloat(15.0F),
					        yy + scaledFloat(15.0F), ww - bWidth - scaledFloat(20.0F));
				}
				yy += (float) stringHeight;
				yAdd = 0.0F;
				yOffset = 0.0F;
				list = infoLists.get("koulutus");

				if (list.size() > 0)
				{
					yy += yAdd + scaledFloat(30.0F);
					setFontA(g2, 10);
					g2.drawString("KOULUTUS", bWidth + scaledFloat(15.0F), yy);
					g2.drawLine((int) (bWidth + (float) scaledInt(15.0F)), (int) (yy + scaledFloat(5.0F)),
					        (int) (ww - scaledFloat(15.0F)), (int) (yy + scaledFloat(5.0F)));
					yAdd = 0.0F;
					yOffset = 0.0F;

					for (int i = 0; i < list.size(); ++i)
					{
						String[] l = ((String) list.get(i)).split("§");
						setFontA(g2, 8);
						g2.drawString(l[1], bWidth + scaledFloat(15.0F), yy + scaledFloat(20.0F) + yOffset);
						yAdd += (float) stringHeight;
						g2.drawString(l[0], bWidth + scaledFloat(15.0F),
						        yy + scaledFloat(20.0F) + yOffset + (float) stringHeight);
						yAdd += (float) stringHeight;
						g2.setColor(Color.DARK_GRAY);
						setFontB(g2, 8);
						String date = l[2] + " - " + l[3];
						g2.drawString(date, bWidth + scaledFloat(15.0F), yy + scaledFloat(40.0F) + yOffset);
						yAdd += (float) stringHeight + scaledFloat(5.0F);
						g2.setColor(Color.BLACK);
						yOffset = yAdd;
					}
				}
				list = infoLists.get("tyokokemus");

				if (list.size() > 0)
				{
					ArrayList <String> list2 = infoLists.get("tyokokemus2");
					yy += yAdd + scaledFloat(30.0F);
					setFontA(g2, 10);
					g2.drawString("TYÖKOKEMUS", bWidth + scaledFloat(15.0F), yy);
					g2.drawLine((int) (bWidth + (float) scaledInt(15.0F)), (int) (yy + scaledFloat(5.0F)),
					        (int) (ww - scaledFloat(15.0F)), (int) (yy + scaledFloat(5.0F)));
					yAdd = 0.0F;
					yOffset = 0.0F;

					for (int i = 0; i < list.size(); ++i)
					{
						String[] l = ((String) list.get(i)).split("§");
						setFontA(g2, 8);
						g2.drawString(l[1], bWidth + scaledFloat(15.0F), yy + scaledFloat(20.0F) + yOffset);
						yAdd += (float) stringHeight;
						g2.drawString(l[0], bWidth + scaledFloat(15.0F),
						        yy + scaledFloat(20.0F) + yOffset + (float) stringHeight);
						yAdd += (float) stringHeight;
						g2.setColor(Color.DARK_GRAY);
						setFontB(g2, 8);
						String date = l[2] + " - " + l[3];
						g2.drawString(date, bWidth + scaledFloat(15.0F), yy + scaledFloat(40.0F) + yOffset);
						yAdd += (float) stringHeight + scaledFloat(5.0F);
						g2.setColor(Color.BLACK);

						if (!((String) list2.get(i)).isBlank() && !((String) list2.get(i)).isEmpty())
						{
							drawStringExt(g2, (String) list2.get(i), bWidth + scaledFloat(15.0F),
							        yy + scaledFloat(50.0F) + yOffset, ww - bWidth - scaledFloat(20.0F));
							yAdd += (float) stringHeight;
						}
						yOffset = yAdd;
					}
				}

				if ((infoLists.get("koulutusMuu")).size() > 0 || (infoLists.get("tyokortti")).size() > 0)
				{
					yy += yAdd + scaledFloat(30.0F);
					setFontA(g2, 10);
					g2.drawString("MUU KOULUTUS / TYÖKORTIT", bWidth + scaledFloat(15.0F), yy);
					g2.drawLine((int) (bWidth + (float) scaledInt(15.0F)), (int) (yy + scaledFloat(5.0F)),
					        (int) (ww - scaledFloat(15.0F)), (int) (yy + scaledFloat(5.0F)));
					yAdd = 0.0F;
					yOffset = 0.0F;
				}
				setFontB(g2, 8);
				list = infoLists.get("koulutusMuu");

				if (list.size() > 0)
				{

					for (int i = 0; i < list.size(); ++i)
					{
						drawStringExt(g2, (String) list.get(i), bWidth + scaledFloat(15.0F),
						        yy + scaledFloat(15.0F) + yOffset, ww - bWidth - scaledFloat(20.0F));
						yAdd += (float) stringHeight;
						yOffset = yAdd;
					}
				}
				list = infoLists.get("tyokortti");

				if (list.size() > 0)
				{

					for (int i = 0; i < list.size(); ++i)
					{
						drawStringExt(g2, (String) list.get(i), bWidth + scaledFloat(15.0F),
						        yy + scaledFloat(15.0F) + yOffset, ww - bWidth - scaledFloat(25.0F));
						yAdd += (float) stringHeight;
						yOffset = yAdd;
					}
				}
				g2.dispose();
			}
		};
		panelDraw.setBackground(new Color(255, 255, 255));
		panelDraw.setBounds(0, 0, frame.getWidth() - 16, frame.getHeight() - 39);
		panelDraw.repaint();
		frame.getContentPane().add(panelDraw);
	}

	private void setFontA(Graphics2D g2, int size)
	{
		g2.setFont(new Font("Arial", 1, scaledInt((float) size)));
		FontMetrics font = g2.getFontMetrics(g2.getFont());
		stringHeight = font.getHeight();
	}

	private void setFontB(Graphics2D g2, int size)
	{
		g2.setFont(new Font("Arial", 0, scaledInt((float) size)));
		FontMetrics font = g2.getFontMetrics(g2.getFont());
		stringHeight = font.getHeight();
	}

	private int scaledInt(float num)
	{
		return (int) (num * previewScale);
	}

	private float scaledFloat(float num)
	{
		return num * previewScale;
	}

	public static void PreviewRefresh(String borderColor)
	{

		if (panelDraw != null)
		{
			pdf.loadTextFiles();

			if (borderColor != null)
			{ pdf.setBorderColor(borderColor); }

			try
			{
				String path = (String) pdf.getInfo().get("Kuva");

				if (!path.isEmpty() && !path.isBlank())
				{

					if (imgProfile != null)
					{ imgProfile.flush(); }
					imgProfile = ImageIO.read(new File(path));
				}
				else
				{

					if (imgProfile != null)
					{ imgProfile.flush(); }
					imgProfile = null;
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			panelDraw.repaint();
		}
	}

	private void drawStringExt(Graphics2D g2, String text, float x, float y, float maxWidth)
	{
		FontMetrics font = g2.getFontMetrics(g2.getFont());

		if (text == null)
		{ text = ""; }
		text = text + " ";
		String tempStr = "";
		String prevStr = "";
		int prevPos = 0;
		float yscale = y;

		for (int i = 0; i < text.length(); ++i)
		{
			tempStr = tempStr + (text.charAt(i) == '\n' ? ' ' : text.charAt(i));

			if ((!(maxWidth >= 0.0F) || text.charAt(i) != ' ' || !((float) font.stringWidth(tempStr) > maxWidth - 2.0F))
			        && text.charAt(i) != '\n')
			{

				if (text.charAt(i) == ' ')
				{ prevStr = tempStr; prevPos = i; }
			}
			else
			{
				g2.drawString(text.charAt(i) == '\n' ? tempStr : prevStr, x, y);
				y += (float) stringHeight;
				tempStr = "";
//				prevStr = "";

				if (text.charAt(i) == ' ' && prevPos > 0)
				{ i = prevPos; prevPos = 0; }
			}
		}
		g2.drawString(prevStr, x, y);
		y += (float) stringHeight;
		stringHeight = (int) (y - yscale);
	}

	private BufferedImage getResource(String p)
	{

		/*
		 * Ladataan oletuskuva suoraan sovelluksen sourcesta Ohjelman paketoimisessa exe
		 * tiedostoksi tulee oletuskuva mukaan ohjelman binaariin getResourceAsStream
		 * avaa resurrsivirran .exe:n sisään ja lataa kuvan tavuina
		 * 
		 */
		try (java.io.InputStream is = getClass().getResourceAsStream(p))
		{
			if (is == null) throw new java.io.FileNotFoundException("Default image missing from source");
			// Lukee source kuvan tiedot suoraan tavutaulukoksi muistiin
			byte[] imageBytes = is.readAllBytes();
			ByteArrayInputStream stream = new ByteArrayInputStream(imageBytes);
			return ImageIO.read(stream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
