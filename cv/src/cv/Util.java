package cv;

import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
// import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class Util {
	// Defines enter variable into keystroke of enter
	private KeyStroke enterKey = KeyStroke.getKeyStroke("ENTER");

	/**
	 * 
	 * @param str              => String looked for
	 * @param str2             => String Replaced with
	 * @param appearanceAmount => which index is looked for
	 * @return
	 */
	public int getIndexOf(String str, String str2, int appearanceAmount) {
		int count = 0;
		int pos = 0;

		for (int i = 0; i < str.length(); ++i) {

			if (str.substring(i, i + 1).equals(str2)) {
				++count;
			}

			if (count == appearanceAmount) {
				pos = i;
				break;
			}
		}
		return pos;
	}

	/**
	 * 
	 * @param <T>  Dynamic type of component, Meaning it can be any type from
	 *             Component.Class
	 * @param f    => Parameter of Frame
	 * @param type => Parameter used to define wanted class output Jbutton.class as
	 *             example
	 * @return => returns output list items
	 * 
	 * 
	 *         to call this use => List<{Component}> x =
	 *         returnFrameComponents(Frame, {Wanted Component}.class)
	 */
	public <T extends Component> List<T> returnFrameComponents(JFrame f, Class<T> type) {
		List<T> output = new ArrayList<T>();
		Component[] Components = f.getContentPane().getComponents();
		// x.isInstance == Instanceof y
		for (Component c : Components)
			if (type.isInstance(c))
				output.add(type.cast(c));
		return output;
	}

	/**
	 * Adds button listeners to buttons inside dialog without needing frame
	 * 
	 * @param b => button in JCustomDialog
	 */
	public void setDialogListeners(JButton b) {
		addLeftRightToTraversalKeys(b);
		addUpDownToTraversalKeys(b);
		addFocusListener(b);
		b.setFocusPainted(false);
		b.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Component comp = e.getComponent();
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					((JButton) comp).doClick();
			}
		});
	}

	/**
	 * Adds Listeners to Components
	 * 
	 * @param ff => JComponents parent frame
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public void setKeyListener(JFrame ff) {
		// Defines values types for returnFrameComponents Generic list types and class
		// types
		List<JButton> b = returnFrameComponents(ff, JButton.class);
		List<JTextArea> ta = returnFrameComponents(ff, JTextArea.class);
		List<JTextField> tf = returnFrameComponents(ff, JTextField.class);
		List<JRadioButton> rb = returnFrameComponents(ff, JRadioButton.class);
		List<JCheckBox> cbx = returnFrameComponents(ff, JCheckBox.class);
		List<JDialog> jd = returnFrameComponents(ff, JDialog.class);
		List<JComboBox> cb = returnFrameComponents(ff, JComboBox.class);
		List<JScrollPane> sp = returnFrameComponents(ff, JScrollPane.class);
//		System.out.println("Focus border is created in Util.addFocusListener(JFrame, JComponent) method");

		for (JDialog _jd : jd) {
			_jd.setFocusable(true);
			addUpDownToTraversalKeys(_jd);
			addLeftRightToTraversalKeys(_jd);
			_jd.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyChar() == KeyEvent.VK_ENTER)
						((JRadioButton) e.getComponent()).doClick();
				}
			});
		}

		for (JComboBox<?> _cb : cb) {
			addFocusListener(ff, _cb);
			_cb.setFocusable(true);
			addLeftRightToTraversalKeys(_cb);
			// Gets inputmap, when combobox is focused
			InputMap imap = _cb.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			// Gets actionmap which contains behavior
			@SuppressWarnings("unused")
			ActionMap amap = _cb.getActionMap();
			// Finds the actionkey assigned to space
			var spaceKey = imap.get(KeyStroke.getKeyStroke("SPACE"));
			// Defines "ENTER" to use same action key as "SPACE"
			if (spaceKey != null)
				imap.put(enterKey, spaceKey);
			else
				imap.put(enterKey, "togglePopup");
		}

		for (JButton _b : b) {
			addLeftRightToTraversalKeys(_b);
			addUpDownToTraversalKeys(_b);
			addFocusListener(ff, _b);
			_b.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					Component comp = e.getComponent();
					if (e.getKeyChar() == KeyEvent.VK_ENTER)
						((JButton) comp).doClick();
				}
			});
		}

		for (JTextField _tf : tf) {
			addFocusListener(ff, _tf);
			_tf.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_TAB)
						_tf.transferFocusBackward();
				}
			});
		}

		for (JTextArea _ta : ta) {
			_ta.setFocusable(true);
			addFocusListener(ff, _ta);
			_ta.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_TAB) {
						if (e.getModifiersEx() > 0)
							_ta.transferFocusBackward();
						else
							_ta.transferFocus();
						e.consume();
					}
				}
			});
		}

		for (JScrollPane _sp : sp) {
			_sp.setFocusable(true);
			addFocusListener(ff, _sp);
			_sp.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_TAB) {
						if (e.getModifiersEx() > 0)
							_sp.transferFocusBackward();
						else
							_sp.transferFocus();
						e.consume();
					}
				}
			});
		}

		for (JRadioButton _rb : rb) {
			_rb.setFocusable(true);
			addLeftRightToTraversalKeys(_rb);
			addUpDownToTraversalKeys(_rb);
			addFocusListener(ff, _rb);
			_rb.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyChar() == KeyEvent.VK_ENTER)
						((JRadioButton) e.getComponent()).doClick();
				}
			});
		}

		for (JCheckBox _cbx : cbx) {
			_cbx.setFocusable(true);
			addLeftRightToTraversalKeys(_cbx);
			addUpDownToTraversalKeys(_cbx);
			addFocusListener(ff, _cbx);
			_cbx.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyChar() == KeyEvent.VK_ENTER)
						((JCheckBox) e.getComponent()).doClick();
				}
			});
		}
	}
	// Source - https://stackoverflow.com/a/8255423
	// Posted by Nyerguds, modified by community. See post 'Timeline' for change
	// history
	// Retrieved 2026-03-09, License - CC BY-SA 3.0

	/**
	 * Allos traveling thru components with up and down arrow keys
	 * 
	 * @param c => Component
	 */
	protected static void addUpDownToTraversalKeys(Component c) {
		addTraversalKeys(c, KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, KeyEvent.VK_DOWN);
		addTraversalKeys(c, KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, KeyEvent.VK_UP);
	}

	/**
	 * Allows traveling thru components with left and right keys
	 * 
	 * @param c
	 */
	public static void addLeftRightToTraversalKeys(Component c) {
		addTraversalKeys(c, KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, KeyEvent.VK_RIGHT);
		addTraversalKeys(c, KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, KeyEvent.VK_LEFT);
	}

	protected static void addTraversalKeys(Component c, int keysetId, int... keyCodes) {
		HashSet<AWTKeyStroke> newKeys = new HashSet<AWTKeyStroke>(c.getFocusTraversalKeys(keysetId));
		for (int keyCode : keyCodes)
			newKeys.add(AWTKeyStroke.getAWTKeyStroke(keyCode, 0));
		c.setFocusTraversalKeys(keysetId, newKeys);
	}

	/**
	 * allows to request focus after jdialog is initialized so it can define initial
	 * focus button
	 * 
	 * @param c => component
	 */
	public void requestFocus(Component c) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				c.requestFocus();
			}
		});
	}

	/**
	 * Changes borders on component upon gaining or losing focus
	 * 
	 * @param ff   => JFrame
	 * @param type => Component
	 */
	public void addFocusListener(JFrame ff, JComponent type) {
		type.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				type.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLUE));
				ff.repaint();
				ff.validate();
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (type instanceof JComboBox)
					type.setBorder(null);
				else
					type.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				ff.repaint();
				ff.validate();
			}
		});
	}

	/**
	 * Used in JCustomDialog class to focus borders
	 * 
	 * @param type => Component
	 */
	public void addFocusListener(JComponent type) {
		type.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				type.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLUE));
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (type instanceof JComboBox)
					type.setBorder(null);
				else
					type.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
		});
	}
}
