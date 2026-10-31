package cv;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
// import javax.swing.JComboBox;
import javax.swing.JFrame;

import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class borderChange
{
	/**
	 * Changes border & focuspaint values of jframe child components
	 */
	public void addBorder(JFrame f)
	{
		Util util = new Util();
		Border bBlack;
		bBlack = BorderFactory.createLineBorder(Color.black);
		List <JButton> b = util.returnFrameComponents(f, JButton.class);
		List <JTextArea> ta = util.returnFrameComponents(f, JTextArea.class);
		List <JTextField> tf = util.returnFrameComponents(f, JTextField.class);
		List <JRadioButton> rb = util.returnFrameComponents(f, JRadioButton.class);
		List <JCheckBox> cbx = util.returnFrameComponents(f, JCheckBox.class);
		

		try
		{
			for (JButton _b : b)
			{
				_b.setBorder(bBlack);
				_b.setFocusPainted(false);
				_b.addMouseListener(new MouseAdapter()
				{
					public void mouseEntered(MouseEvent e)
					{
						_b.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(200, 200, 255)));
					}

					public void mouseExited(MouseEvent e)
					{
						_b.setBorder(BorderFactory.createLineBorder(Color.black));
					}
				});
				_b.addFocusListener(new FocusAdapter()
				{
					public void focusGained(FocusEvent e)
					{
						_b.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(200, 200, 255)));
					}

					public void focusLost(FocusEvent e)
					{
						_b.setBorder(BorderFactory.createLineBorder(Color.black));
					}
				});
			}

			// JComboBox
			// if(c instanceof JComboBox) if(c != null)
			// {
			// ((JComboBox<?>)c).setBorder(bBlack);
			//
			// }
			// JCheckbox
			for (JCheckBox _cbx : cbx)
			{
				_cbx.setBorder(bBlack);
				_cbx.setFocusPainted(false);
			}
			// JTextArea
			for (JTextArea _ta : ta) _ta.setBorder(bBlack);
			// JTextField
			for (JTextField _tf : tf) _tf.setBorder(bBlack);

			// JRadioButton
			for (JRadioButton _rb : rb)
			{
				_rb.setBorder(bBlack);
				_rb.setFocusPainted(false);
			}
			// Updates frame afterwards
			f.repaint();
			f.validate();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
