package cv;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class CVLuoPDF {
	private String savePath = "";
	private final int FA_LEFT = 0;
	private final int FA_CENTER = 1;
	private final int FA_RIGHT = 2;
	private final int FA_TOP = 0;
	private final int FA_MIDDLE = 1;
	private final int FA_BOTTOM = 2;
	private int[] textAlign = { FA_LEFT, FA_TOP };
	private float profileSize = 1;
	private float[] offsets = { 0, 0 };
	private float textYScale = 0;
	private Path PDF = Paths.get("test.pdf");
	private Map<String, String> info = new HashMap<String, String>();
	private static Map<String, Color[]> colorMap = new HashMap<String, Color[]>();
	private String borderColor = "red";
	private ArrayList<String> infoKoulutus = new ArrayList<>();
	private ArrayList<String> infoKielet = new ArrayList<>();
	private ArrayList<String> infoTaidot = new ArrayList<>();
	private ArrayList<String> infoTyokokemus = new ArrayList<>();
	private ArrayList<String> infoTyokokemus2 = new ArrayList<>();
	private ArrayList<String> infoKoulutusMuu = new ArrayList<>();
	private ArrayList<String> infoTyokortti = new ArrayList<>();
	private ArrayList<String> infoSuosittelijat = new ArrayList<>();
	
	CVLuoPDF(String borderColor, String savePath) {
		super();
		this.borderColor = borderColor;
		if (!savePath.endsWith("\\") && !savePath.endsWith("/"))
			savePath += "\\";
		this.savePath = savePath;
		colorMap.put("red", 	new Color[] { new Color(0xC02040), new Color(0xE8A0B0) });
		colorMap.put("blue", 	new Color[] { new Color(0x3040B0), new Color(0x98A0D8) });
		colorMap.put("green", 	new Color[] { new Color(0x287028), new Color(0xB0D8A8) });
	//	colorMap.put("yellow", 	new Color[] { new Color(0xF8C040), new Color(0xF8E0A0) });
		colorMap.put("gray", 	new Color[] { new Color(0x506070), new Color(0xB0B8C0) });
		colorMap.put("black", 	new Color[] { new Color(0x000000), new Color(0x607080) });
		colorMap.put("purple", 	new Color[] { new Color(0x9820E0), new Color(0xD098F0) });
		colorMap.put("magenta", new Color[] { new Color(0xB000B0), new Color(0xF088D8) });
	//	colorMap.put("pink", 	new Color[] { new Color(0xF070C0), new Color(0xF8B8E0) });
		colorMap.put("maroon", 	new Color[] { new Color(0x601010), new Color(0xB06868) });
	//	colorMap.put("orange", 	new Color[] { new Color(0xF05F10), new Color(0xF8B088) });
		colorMap.put("spesia", 	new Color[] { new Color(0x5BD1D7), new Color(0x5BD1D7) });
	//	colorMap.put("white", 	new Color[] { new Color(0xFFFFFF), new Color(0xFFFFFF) });
	//	colorMap.put("custom", 	new Color[] { null, 			   null 			   });
	}
	
	// taustaväri
	public static Color[] getColor(String color) {
		color = color.toLowerCase();
		if (!colorMap.containsKey(color))
			color = getRandomColorString();
		return colorMap.get(color);
	}
	
	public static String getRandomColorString() {
		Random random = new Random();
		String[] keys = new String[colorMap.size()];
		keys = colorMap.keySet().toArray(keys);
		String rColor;
		while(true) {
			rColor = keys[random.nextInt(colorMap.size())];
			if (!rColor.equals("spesia"))
				break;
		}
		return rColor;
	}
	
	public Color[] getCurrentColor() {
		return getColor(borderColor);
	}
	
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	
	public void luo() {
		// hakee tekstitiedoston tiedot infomuttujiin
		loadTextFiles();
		Color color1 = getColor(borderColor)[0];
		Color color2 = getColor(borderColor)[1];
		try {
			// luo uuden pdf tiedoston
			PDDocument d = new PDDocument();
			// luo uuden sivun
			PDPage page = new PDPage();
			// lisää sivun pdf tiedostoon
			d.addPage(page);
			// ww ja hh ovat pdf sivun leveys ja korkeus
			float ww = page.getMediaBox().getWidth();
			float hh = page.getMediaBox().getHeight();
			// sallii eri datamuotojen lisäämisen pdf tiedostoon
			PDPageContentStream cs = new PDPageContentStream(d, page);
			// Värillisen osion leveys
			float bWidth = (ww / 3);
			
			if (borderColor != "white") {
				// Laittaa reunan värin ja piirtää sen
				cs.setNonStrokingColor(color1);
				cs.addRect(0, 0, bWidth, hh);
				cs.fill();
			} else {
				cs.setStrokingColor(0);
				drawLine(cs, bWidth, hh, bWidth, 0);
			}
			/*
			 * cs.setNonStrokingColor(colorMap.get(borderColor)[1]); cs.moveTo(ww, 0);
			 * cs.curveTo(ww, 0, ww - 20, hh - (hh / 3), ww / 2, hh); cs.lineTo(ww, hh);
			 * cs.fill(); //
			 */
			
			// hakee kuvan, jos on olemassa. muuten laittaa Profiilikuvaksi Profiili.png
			var img = info.get("Kuva");
			
			PDImageXObject pfpImage = null;
			// Tarkistaa löytyykö käyttäjän antama kuva kovalevyltä
			if (img != null && !img.isBlank())
			{
				if (Files.exists(Paths.get(img))) {
					// Jos kuva löytyy annetusta polusta ladataan se normaalisti tiedostona
					pfpImage = PDImageXObject.createFromFile(img, d);
				} else {
					pfpImage = getResource(d, "/Images/Profiili.png");
				}
			}
//			if (!Files.exists(Paths.get(img)) || img.isBlank() || img.isEmpty())
//				img = getResource("/Images/Profiili.png");
			// Lataa kuvan polusta käytettäväksi PDF tiedostoon
			
//			PDImageXObject pfpImage = PDImageXObject.createFromFile(img, d);
			
			if (pfpImage != null)
			{
				// kuvan korkeus verrattuna kuvan pituuteen
				float pfpHeight = ((float) pfpImage.getHeight() / (float) pfpImage.getWidth());
				// kuvan koko skaalattuna värillisen reunan leveyteen
				float width = (bWidth * profileSize);
				float height = ((bWidth * pfpHeight) * profileSize);
				// asettaa katkoviivojen paksuuden
				cs.setLineWidth(1.5f);
				// asettaa tekstin värin valkoiseksi värillisellä taustalla
				cs.setNonStrokingColor(color2);
				// luo alueen mihin profiili kuva piirretään
				PDRectangle cropBox = page.getCropBox();
				// määrittää alueen sijainnin
				cropBox.setLowerLeftY(hh - bWidth);
				// asettaa alueen korkeuden
				cropBox.setUpperRightY(cropBox.getLowerLeftY() + bWidth);
				// määrittää koon
				var circleSize = bWidth / 2.575f;
				cs.saveGraphicsState();
				// jos kuvalla on läpinäkyvätausta sen näkee
				addCircle(cs, cropBox.getLowerLeftX() + bWidth / 2, cropBox.getLowerLeftY() + bWidth / 2, circleSize);
				cs.fill();
				// kuvan rajaus
				addCircle(cs, cropBox.getLowerLeftX() + bWidth / 2, cropBox.getLowerLeftY() + bWidth / 2, circleSize);
				cs.clip();
				cs.stroke();
				// x y = kuvan sijainti alueella. s = värillisen reunan keskikohta
				var x = bWidth / 2 - width / 2;
				var y = cropBox.getLowerLeftY() + bWidth / 2 - height / 2;
				var s = bWidth / 2;
				// piirtää kuvan
				cs.drawImage(pfpImage, x + offsets[0] * s, y - offsets[1] * s, width, height);
				cs.restoreGraphicsState();
				// kuvan valkoinen ulkoreuna
				cs.setStrokingColor(borderColor == "white" ? 0 : 1);
				addCircle(cs, cropBox.getLowerLeftX() + bWidth / 2, cropBox.getLowerLeftY() + bWidth / 2, circleSize);
				cs.stroke();
			}
			else
			{
				// asettaa katkoviivojen paksuuden
				cs.setLineWidth(1.5f);
				// asettaa tekstin värin valkoiseksi värillisellä taustalla
				cs.setNonStrokingColor(color2);
				// kuvan valkoinen ulkoreuna
				cs.setStrokingColor(borderColor == "white" ? 0 : 1);
			}
			// asettaa fontin
			PDType1Font fontA = new PDType1Font(FontName.HELVETICA_BOLD);
			PDType1Font fontB = new PDType1Font(FontName.HELVETICA);
			cs.setNonStrokingColor(borderColor == "white" ? 0 : 1);
			// drawSetTextAlign(FA_CENTER, FA_TOP);
			// drawTextPos(cs, bWidth / 2, hh - bWidth - 5, info.get("etunimi"), fontA, 20);
			// drawTextPos(cs, bWidth / 2, hh - bWidth - 35, info.get("sukunimi"), fontA,
			// 20);
			
			// Määrittää tekstin sijainnin ja välien koon
			float yAdd = 0, yOffset = 0;
			float yy = (pfpImage != null) ? (hh - bWidth - 15) : (hh - 58);
			drawSetTextAlign(FA_LEFT, FA_TOP);
			drawTextPos(cs, 20, yy, "YHTEYSTIEDOT", fontA, 14);
			drawLine(cs, 20, yy - 10, bWidth - 20, yy - 10);
			
			PDImageXObject image;
			
			drawTextPos(cs, 40, yy - 25, info.get("phone"), fontB, 10);
			image = getResource(d, "/Images/YhteystiedotPieni2.png");
			cs.drawImage(image, 20, yy - 27.5f, textYScale, textYScale);
			yAdd += textYScale + 5;

			drawTextPos(cs, 40, yy - 40, info.get("email"), fontB, 10);
			image = getResource(d, "/Images/Email2.png");
			cs.drawImage(image, 20, yy - 42.5f, textYScale, textYScale);
			yAdd += textYScale + 5;
			
			yOffset = 0;
			if (!info.get("city").isBlank()) {
				yOffset += 15;
				drawTextPos(cs, 40, yy - 40 - yOffset, info.get("city"), fontB, 10);
				image = getResource(d, "/Images/kaupunki.png");
				cs.drawImage(image, 20, yy - 42.5f - yOffset, textYScale, textYScale);
				yAdd += textYScale + 5;
			}
			if (!info.get("website").isBlank()) {
				yOffset += 15;
				drawTextPos(cs, 40, yy - 40 - yOffset, info.get("website"), fontB, 10);
				image = getResource(d, "/Images/Nettisivu2.png");
				cs.drawImage(image, 20, yy - 42.5f - yOffset, textYScale, textYScale);
				yAdd += textYScale + 5;
			}
			
			if (infoKielet.size() > 0) {
				yy -= yAdd + 30;
				drawTextPos(cs, 20, yy, "KIELET", fontA, 14);
				drawLine(cs, 20, yy - 10, bWidth - 20, yy - 10);
				
				yAdd = 0;
				for (var i = 0; i < infoKielet.size(); i++) {
					ArrayList<String> l = stringSplit(infoKielet.get(i), '§');
					drawTextPos(cs, 20, yy - 25 - (i * 15), l.get(0), fontB, 10);
					drawTextPos(cs, bWidth / 2, yy - 25 - (i * 15), l.get(1).toUpperCase(), fontA, 10);
					yAdd += textYScale + 5;
				}
			}
			
			if (infoTaidot.size() > 0) {
				yy -= yAdd + 30;
				drawTextPos(cs, 20, yy, "TAIDOT", fontA, 14);
				drawLine(cs, 20, yy - 10, bWidth - 20, yy - 10);
				
				yAdd = 0;
				yOffset = 0;
				for (var i = 0; i < infoTaidot.size(); i++) {
					drawTextPosExt(cs, 20, yy - 30 - yOffset, infoTaidot.get(i), fontB, 10, bWidth - 20);
					yAdd += textYScale;
					yOffset = yAdd;
				}
			}
			
			if (infoSuosittelijat.size() > 0) {
				yy -= yAdd + 45;
				drawTextPos(cs, 20, yy, "SUOSITTELIJAT", fontA, 14);
				drawLine(cs, 20, yy - 10, bWidth - 20, yy - 10);
				
				yAdd = 0;
				yOffset = 0;
				for (var i = 0; i < infoSuosittelijat.size(); i += 2) {
					drawTextPosExt(cs, 20, yy - 30 - yOffset, infoSuosittelijat.get(i), fontA, 10, bWidth);
					yAdd += textYScale + 5;
					drawTextPosExt(cs, 20, yy - 30 - yOffset - (textYScale + 5), infoSuosittelijat.get(i + 1), fontB,
							10, bWidth);
					yAdd += textYScale + 5;
					yOffset = yAdd + 5;
				}
			}
			
			cs.setNonStrokingColor(0);
			cs.setStrokingColor(0);
			drawTextPos(cs, bWidth + 20, hh - 30, info.get("etunimi") + " " + info.get("sukunimi"), fontA, 20);
			yy = hh - 35 - textYScale;
			drawTextPos(cs, bWidth + 20, yy, "PROFIILI", fontA, 14);
			drawLine(cs, bWidth + 20, yy - 10, ww - 20, yy - 10);
			drawTextPosExt(cs, bWidth + 20, yy - 30, info.get("Profiili"), fontB, 10, ww - bWidth - 40);
			yy -= textYScale;
			
			yAdd = 0;
			yOffset = 0;
			if (infoKoulutus.size() > 0) {
				yy -= yAdd + 50;
				drawTextPos(cs, bWidth + 20, yy, "KOULUTUS", fontA, 14);
				drawLine(cs, bWidth + 20, yy - 10, ww - 20, yy - 10);
				
				yAdd = 0;
				yOffset = 0;
				for (var i = 0; i < infoKoulutus.size(); i++) {
					ArrayList<String> l = stringSplit(infoKoulutus.get(i), '§');
					drawTextPos(cs, bWidth + 20, yy - 30 - yOffset, l.get(1), fontA, 12);
					yAdd += textYScale;
					drawTextPos(cs, bWidth + 20, yy - 30 - yOffset - textYScale, l.get(0), fontA, 12);
					yAdd += textYScale;
					cs.setNonStrokingColor(0.25f);
					String date = l.get(2) + " - " + l.get(3);
					drawTextPos(cs, bWidth + 20, yy - 60 - yOffset, date, fontB, 10);
					yAdd += textYScale + 10;
					cs.setNonStrokingColor(0);
					yOffset = yAdd;
				}
			}
			
			if (infoTyokokemus.size() > 0) {
				yy -= yAdd + 40;
				drawTextPos(cs, bWidth + 20, yy, "TYÖKOKEMUS", fontA, 14);
				drawLine(cs, bWidth + 20, yy - 10, ww - 20, yy - 10);
				
				yAdd = 0;
				yOffset = 0;
				for (var i = 0; i < infoTyokokemus.size(); i++) {
					ArrayList<String> l = stringSplit(infoTyokokemus.get(i), '§');
					drawTextPos(cs, bWidth + 20, yy - 30 - yOffset, l.get(0), fontA, 12);
					yAdd += textYScale;
					drawTextPos(cs, bWidth + 20, yy - 30 - yOffset - textYScale, l.get(1), fontA, 12);
					yAdd += textYScale;
					cs.setNonStrokingColor(0.25f);
					String date = l.get(2) + " - " + l.get(3);
					drawTextPos(cs, bWidth + 20, yy - 60 - yOffset, date, fontB, 10);
					yAdd += textYScale + 5;
					cs.setNonStrokingColor(0);
					if (!infoTyokokemus2.get(i).isBlank() && !infoTyokokemus2.get(i).isEmpty()) {
						drawTextPosExt(cs, bWidth + 20, yy - 70 - yOffset, infoTyokokemus2.get(i), fontB, 10, ww - bWidth - 40);
						yAdd += textYScale + 5;
					}
					yOffset = yAdd;
				}
			}
			
			if (infoKoulutusMuu.size() > 0 || infoTyokortti.size() > 0) {
				yy -= yAdd + 40;
				drawTextPos(cs, bWidth + 20, yy, "MUU KOULUTUS / TYÖKORTIT", fontA, 14);
				drawLine(cs, bWidth + 20, yy - 10, ww - 20, yy - 10);
				yAdd = 0;
				yOffset = 0;
			}
			
			if (infoKoulutusMuu.size() > 0) {
				for (var i = 0; i < infoKoulutusMuu.size(); i++) {
					ArrayList<String> l = stringSplit(infoKoulutusMuu.get(i), '§');
					drawTextPosExt(cs, bWidth + 20, yy - 30 - yOffset, l.get(0), fontB, 10, ww - bWidth - 40);
					yAdd += textYScale + 2.5;
					yOffset = yAdd;
				}
			}
			
			if (infoTyokortti.size() > 0) {
				for (var i = 0; i < infoTyokortti.size(); i++) {
					ArrayList<String> l = stringSplit(infoTyokortti.get(i), '§');
					drawTextPosExt(cs, bWidth + 20, yy - 30 - yOffset, l.get(0), fontB, 10, ww - bWidth - 40);
					yAdd += textYScale + 2.5;
					yOffset = yAdd;
				}
			}
			// sulkee datamuotojen lisäämisen
			cs.close();
			// PDF tiedoston tallentamis valintaikkuna
			UIManager.put("FileChooser.cancelButtonText", "Peruuta");
			JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			fc.setSelectedFile(
					new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + "\\cv.pdf"));
			fc.setAcceptAllFileFilterUsed(false);
			fc.addChoosableFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
			fc.setDialogTitle("Luo PDF");
			fc.setApproveButtonText("tallenna");
			int ret = fc.showDialog(null, "Tallenna");
			if (ret == JFileChooser.APPROVE_OPTION) {
				var path = fc.getSelectedFile().getAbsolutePath();
				if (!path.toLowerCase().endsWith(".pdf"))
					path += ".pdf";
				PDF = Paths.get(path);
				Files.deleteIfExists(PDF);
				d.save(PDF.toString());
				JOptionPane.showMessageDialog(null, "Tiedosto tallennettu kohteeseen " + PDF);
			}
			d.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// lisää tekstin pdf tiedostoon
	private void drawTextPos(PDPageContentStream cs, float x, float y, String text, PDType1Font font, float fontSize)
			throws IOException {
		if (text == null)
			text = "";
		cs.beginText();
		float xoffset = 0;
		float yoffset = 0;
		switch (textAlign[0]) {
		case FA_CENTER:
			xoffset = (font.getStringWidth(text) / 1000 * fontSize) / 2;
			break;
		case FA_RIGHT:
			xoffset = font.getStringWidth(text) / 1000 * fontSize;
			break;
		}
		switch (textAlign[1]) {
		case FA_MIDDLE:
			yoffset = (font.getBoundingBox().getHeight() / 1000 * fontSize) / 2;
			break;
		case FA_BOTTOM:
			yoffset = font.getBoundingBox().getHeight() / 1000 * fontSize;
			break;
		}
		cs.newLineAtOffset(x - xoffset, y - yoffset);
		cs.setLeading((fontSize * 1.20f));
		cs.setFont(font, fontSize);
		cs.showText(text);
		cs.endText();
		textYScale = (fontSize * 1.20f);
	}
	
	// lisää tekstin ja tarkistaa rivinvaihdot
	private void drawTextPosExt(PDPageContentStream cs, float x, float y, String text, PDType1Font font, float fontSize,
			float maxWidth) throws IOException {
		if (text == null)
			text = "";
		text += " ";
		String tempStr = "";
		String prevStr = "";
		int prevPos = 0;
		float yscale = y;
		for (int i = 0; i < text.length(); i++) {
			tempStr += text.charAt(i) == '\n' ? ' ' : text.charAt(i);
			if ((maxWidth >= 0 && (text.charAt(i) == ' ')
					&& Math.floor(font.getStringWidth(tempStr) / 1000 * fontSize) > maxWidth + 5)
					|| text.charAt(i) == '\n') {
				drawTextPos(cs, x, y, text.charAt(i) == '\n' ? tempStr : prevStr, font, fontSize);
				y -= (fontSize * 1.25f);
				tempStr = "";
				prevStr = "";
				if (text.charAt(i) == ' ')
					i = prevPos;
				continue;
			}
			if (text.charAt(i) == ' ') {
				prevStr = tempStr;
				prevPos = i;
			}
		}
		drawTextPos(cs, x, y, prevStr, font, fontSize);
		y -= (fontSize * 1.25f);
		textYScale = yscale - y;
	}
	
	// Erottaa merkkijonon erillisiksi sanoiksi annetun merkin mukaan
	private ArrayList<String> stringSplit(String str, char a) {
		str = str.replaceAll("\n", " ");
		String[] strArray = str.split("" + a, -1);
		ArrayList<String> strList = new ArrayList<>();
		for(var i = 0; i < strArray.length; i++)
			strList.add(strArray[i]);
		return strList;
	}
	
	// määrittää tekstin sijainnin
	private void drawSetTextAlign(int h, int v) {
		textAlign[0] = h;
		textAlign[1] = v;
	}
	
	// piirtää viivan kahden pisteen välille
	private void drawLine(PDPageContentStream cs, float x, float y, float x2, float y2) throws IOException {
		cs.moveTo(x, y);
		cs.lineTo(x2, y2);
		cs.stroke();
	}
	
	// https://stackoverflow.com/questions/42811353/
	// lisää ympyrän
	private void addCircle(PDPageContentStream cs, float cx, float cy, float r) throws IOException {
		final float k = 0.552284749831f;
		cs.moveTo(cx - r, cy);
		cs.curveTo(cx - r, cy + k * r, cx - k * r, cy + r, cx, cy + r);
		cs.curveTo(cx + k * r, cy + r, cx + r, cy + k * r, cx + r, cy);
		cs.curveTo(cx + r, cy - k * r, cx + k * r, cy - r, cx, cy - r);
		cs.curveTo(cx - k * r, cy - r, cx - r, cy - k * r, cx - r, cy);
		cs.closePath();
	}
	
	private PDImageXObject getResource(PDDocument d, String p) throws IOException {
		/*
		 * Ladataan oletuskuva suoraan sovelluksen sourcesta Ohjelman paketoimisessa exe
		 * tiedostoksi tulee oletuskuva mukaan ohjelman binaariin getResourceAsStream
		 * avaa resurrsivirran .exe:n sisään ja lataa kuvan tavuina
		 * 
		 */
		try (java.io.InputStream is = getClass().getResourceAsStream(p)) {
			if (is == null)
				throw new java.io.FileNotFoundException("Default image missing from source");

			// Lukee source kuvan tiedot suoraan tavutaulukoksi muistiin
			byte[] imageBytes = is.readAllBytes();
			// Luo pdfimage kuva objektin suoraan muistissa olevissa tavuista tiedosto polun
			// sijaan
			String[] name = p.split("/");
			return PDImageXObject.createFromByteArray(d, imageBytes, name[name.length - 1]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// poistaa viimeisen merkin merkkijonosta
	private String stringFix(String str) {
		if (str.length() == 0) return str;
		return str.substring(0, str.length() - 1);
	}

	public Map<String, String> getInfo() {
		return info;
	}

	public Map<String, ArrayList<String>> getInfoLists() {
		Map<String, ArrayList<String>> m = new HashMap<>();
		m.put("koulutus", 		infoKoulutus);
		m.put("kielet", 		infoKielet);
		m.put("taidot", 		infoTaidot);
		m.put("tyokokemus", 	infoTyokokemus);
		m.put("tyokokemus2", 	infoTyokokemus2);
		m.put("koulutusMuu", 	infoKoulutusMuu);
		m.put("tyokortti", 		infoTyokortti);
		m.put("suosittelijat", 	infoSuosittelijat);
		return m;
	}
	
	// ladataan tekstitiedostot %appdata% kansiosta ja poistaa ylimääräisen tekstin.
	// tallentaa tiedot infomuuttujiin, joilla saadaan tekstit PDF tiedostoon
	public void loadTextFiles() {
		try {
			infoKoulutus.clear();
			infoKielet.clear();
			infoTaidot.clear();
			infoTyokokemus.clear();
			infoTyokokemus2.clear();
			infoKoulutusMuu.clear();
			infoTyokortti.clear();
			infoSuosittelijat.clear();
			String[] files = { "Kuva", "Profiili", "Yhteystiedot", "Kieli", "Taidot", "Tyokokemus", "Koulutus",
					"KoulutusMuu", "Suosittelija" };
			for (var i = 0; i < files.length; i++) {
				if (!Files.exists(Paths.get(savePath + files[i] + ".txt")))
					continue;
				
				try (BufferedReader br = new BufferedReader(new FileReader(savePath + files[i] + ".txt"))) {
					String line;
					String strFinal = "";
					if (files[i].equals("Kuva")) {
						if ((line = br.readLine()) != null)
							strFinal = line;
						
						info.put("Kuva", strFinal);
						System.out.println(info.get("Kuva"));
						if ((line = br.readLine()) != null)
						{
							profileSize = Float.parseFloat(line);
							info.put("imageSize", line);
						}
						if ((line = br.readLine()) != null)
						{
							offsets[0] = Float.parseFloat(line);
							info.put("imageXOff", line);
						}
						if ((line = br.readLine()) != null)
						{
							offsets[1] = Float.parseFloat(line);
							info.put("imageYOff", line);
						}
						continue;
					} else if (files[i].equals("Yhteystiedot")) {
						var first = true;
						var isCity = false;
						var isWebsite = false;
						while ((line = br.readLine()) != null) {
							if (first) {
								line = line.replace("Nimi: ", "");
								ArrayList<String> l = stringSplit(line, ' ');
								info.put("etunimi", l.get(0));
								info.put("sukunimi", l.get(1));
								first = false;
							} else {
								isCity = line.startsWith("Kaupunki: ");
								isWebsite = line.startsWith("Nettisivut: ");
								line = line.replace("Puhelinnumero: ", "");
								line = line.replace("Sähköposti: ", "");
								line = line.replace("Kaupunki: ", "");
								line = line.replace("Nettisivut: ", "");
								strFinal += line + "§";
							}
						}
						ArrayList<String> l = stringSplit(stringFix(strFinal), '§');
						if (l.size() < 2) continue;
						info.put("phone", l.get(0));
						info.put("email", l.get(1));
						if (l.size() > 2 && l.size() < 4) {
							var index = 2;
							info.put("city", !isCity ? "" : l.get(index));
							if (isCity)
								index++;
							info.put("website", !isWebsite ? "" : l.get(index));
						} else if (l.size() == 4) {
							info.put("city", l.get(2));
							info.put("website", l.get(3));
						} else {
							info.put("city", "");
							info.put("website", "");
						}
					} else if (files[i].equals("Koulutus")) {
						while ((line = br.readLine()) != null) {
							line = line.replace("Oppilaitos: ", "");
							line = line.replace("Tutkinto: ", "");
							line = line.replace("Aloitus pvm: ", "");
							line = line.replace("Lopetus pvm: ", "");
							if (!line.contains("jatkuva"))
								strFinal += line + "§";
							else
								strFinal += "J" + line.substring(1, line.length()) + "§";
							if (line.isEmpty() || line.isBlank()) {
								ArrayList<String> l = stringSplit(strFinal, '§');
								infoKoulutus.add(l.get(0) + "§" + l.get(1) + "§" + l.get(2) + "§" + l.get(3));
								strFinal = "";
							}
						}
					} else if (files[i].equals("KoulutusMuu")) {
						var kortti = false;
						while ((line = br.readLine()) != null) {
							if (line.startsWith("Muu koulutus: ", 0)) {
								line = line.replace("Muu koulutus: ", "");
								kortti = false;
							} else if (line.startsWith("Työkortti: ", 0)) {
								line = line.replace("Työkortti: ", "");
								kortti = true;
							}
							strFinal += line + " ";
							if (line.isEmpty() || line.isBlank()) {
								if (!kortti)
									infoKoulutusMuu.add(strFinal);
								else
									infoTyokortti.add(strFinal);
								strFinal = "";
							}
						}
					} else if (files[i].equals("Kieli")) {
						while ((line = br.readLine()) != null) {
							line = line.replace("Kieli: ", "");
							line = line.replace("Kielitaito: ", "");
							strFinal += line + "§";
							if (line.isEmpty() || line.isBlank()) {
								infoKielet.add(strFinal);
								strFinal = "";
							}
						}
					} else if (files[i].equals("Taidot")) {
						while ((line = br.readLine()) != null) {
							line = line.replace("Muut taidot: ", "");
							strFinal += line + "\n";
							if (line.isEmpty() || line.isBlank()) {
								infoTaidot.add(strFinal.substring(0, strFinal.length() - 2));
								strFinal = "";
							}
						}
						if (strFinal.length() > 0) {
							infoTaidot.add(strFinal.substring(0, strFinal.length() - 1));
						}
					} else if (files[i].equals("Tyokokemus")) {
						boolean multiLine = false;
						while ((line = br.readLine()) != null) {
							if (line.startsWith("Lisätiedot työstä: ")) {
								multiLine = true;
							} else if (line.startsWith("Aloitus pvm: ")) {
								strFinal = stringFix(strFinal) + "§";
								multiLine = false;
							}
							line = line.replace("Työnimike: ", "");
							line = line.replace("Työnantaja: ", "");
							line = line.replace("Lisätiedot työstä: ", "");
							line = line.replace("Aloitus pvm: ", "");
							var jatkuva = line.startsWith("Lopetus pvm: ") && line.contains("jatkuva");
							line = line.replace("Lopetus pvm: ", "");
							if (!jatkuva)
								strFinal += line + (multiLine ? "\n" : "§");
							else
								strFinal += "J" + line.substring(1, line.length()) + "§";
							if (line.isEmpty() || line.isBlank()) {
								if (multiLine)
									strFinal = stringFix(strFinal);
								ArrayList<String> l = stringSplit(strFinal, '§');
								infoTyokokemus.add(l.get(0) + "§" + l.get(1) + "§" + l.get(3) + "§" + l.get(4));
								infoTyokokemus2.add(l.get(2));
								strFinal = "";
							}
						}
					} else if (files[i].equals("Suosittelija")) {
						while ((line = br.readLine()) != null) {
							line = line.replace("Yhteyshenkilö: ", "");
							line = line.replace("Yhteyshenkilön titteli: ", "");
							line = line.replace("Yrityksen nimi: ", "");
							line = line.replace("Puhelinnumero: ", "");
							line = line.replace("Sähköposti: ", "");
							strFinal += line + "§";
							if (line.isEmpty() || line.isBlank()) {
								ArrayList<String> l = stringSplit(strFinal, '§');
								infoSuosittelijat.add(l.get(0) + "\n" + l.get(1) + "\n" + l.get(2));
								infoSuosittelijat.add(l.get(3) + "\n" + l.get(4));
								strFinal = "";
							}
						}
					} else {
						while ((line = br.readLine()) != null)
							strFinal += line + "\n";
						info.put(files[i], stringFix(strFinal));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
