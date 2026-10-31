package cv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class JcustomDialog extends JDialog {
	private static final long serialVersionUID = -2281753673029982368L;

	//
	/**
	 * https://stackoverflow.com/questions/70284166/is-there-a-way-to-add-a-keylistener-to-jdialog-joptionpane
	 * 
	 * https://coderanch.com/t/338717/java/JOptionPane-custom-buttons-close-JOptionPane
	 * 
	 * 
	 */

	private static Util util = new Util();

	public static final int DEFAULT_OPTION = -1;

	/** Type used for <code>showConfirmDialog</code>. */
	public static final int YES_NO_OPTION = 0;

	/** Type used for <code>showConfirmDialog</code>. */
	public static final int YES_NO_CANCEL_OPTION = 1;

	/** Type used for <code>showConfirmDialog</code>. */
	public static final int OK_CANCEL_OPTION = 2;

	// Return values
	//
	/** Return value if Yes chosen */
	public static final int YES_OPTION = 0;

	/** Return value if No is chosen */
	public static final int NO_OPTION = 1;

	/** Return value if Cancel is chosen */
	public static final int CANCEL_OPTION = 2;

	/** Return value if Ok is chosen */
	public static final int OK_OPTION = 0;

	//
	//
	/** used for error messages */
	public static final int ERROR_MESSAGE = 0;

	/** used for information messages */
	public static final int INFORMATION_MESSAGE = 1;

	/** used for warning messages */
	public static final int WARNING_MESSAGE = 2;

	/** used for question messages */
	public static final int QUESTION_MESSAGE = 3;

	/** no icon is used */
	public static final int PLAIN_MESSAGE = -1;

	private JFrame parentFrame;

	private String dialogTitle;

	private Object dialogMessage;
	/** Integer value which is used to take x Icon type from switch statement */
	private int iconType;
	/** Allows the usage of customoptions so aren't stuck with the default ones */
	private Object[] customOptions;
	/** Selected options default value */
	private int selectedOption = DEFAULT_OPTION;

	/** Initial button focus */
	private int initialOption;

	/**
	 * Constructor which uses custom object values from the call
	 * 
	 * @param _frame         => JFrame
	 * @param _message       => Object type of message
	 * @param _title         => Title shown on top of the dialog screen
	 * @param type           => what icon is shown on the dialog == > PLAIN_MESSAGE
	 * @param _options       => custom Object options = {"OK","YES","NO"}
	 * @param _initialOption => Defines init option to autofocus
	 */
	public JcustomDialog(JFrame _frame, Object _message, String _title, int type, Object[] _options,
			Object _initialOption) {
		super(_frame, _title, true);
		this.parentFrame = _frame;
		this.dialogTitle = _title;
		this.dialogMessage = _message;
		this.iconType = type;
		this.customOptions = _options;
		setGUI();
	}

	/**
	 * Constructor which uses int values to get already defined button label values
	 * 
	 * @param _frame      => JFrame
	 * @param _message    => Object type of message
	 * @param _title      => Title shown on dialog
	 * @param type        => Type of icon used
	 * @param _option     => Button name options
	 * @param _initOption => Initial button focus
	 */
	public JcustomDialog(JFrame _frame, Object _message, String _title, int type, int _option, int _initOption) {
		/**
		 * Treats JFrame as the parent of this JDialog to receive integer value from
		 * button presses that way
		 */
		super(_frame, _title, true);
		this.parentFrame = _frame;
		this.dialogTitle = _title;
		this.dialogMessage = _message;
		this.iconType = type;
		this.customOptions = getConfirmOption(_option);
		this.initialOption = _initOption;
		setGUI();
	}

	/**
	 * Constructor used for showMessageDialog, which doesn't use button labels or
	 * initial focus as it return only OK
	 * 
	 * @param _frame   => JFrame
	 * @param _message => Object type of message
	 * @param _title   => Title shown on dialog
	 * @param type     => Type of icon used
	 */
	public JcustomDialog(JFrame _frame, Object _message, String _title, int type) {
		super(_frame, _title, true);
		this.parentFrame = _frame;
		this.dialogMessage = _message;
		this.dialogTitle = _title;
		this.iconType = type;
		setGUI();
	}

	/**
	 * Builds the JDialog frames, which is called later on each Constructor
	 */
	private void setGUI() {
//		html allows line changes with JLabel component
		JLabel label = new JLabel("<html>" + replaceLineBreak(dialogMessage.toString()) + "</html>");
		// Defines 2 rows and 1 Component in a row, each of the component used are
		// nested inside jpanels
		JPanel parentPanel = new JPanel(new GridLayout(2, 0, 50, 0));
		parentPanel.setBackground(Color.red);
		JPanel btnPanel = new JPanel();
		JPanel labelPanel = new JPanel(new BorderLayout());
		label.setFocusable(false);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		String iconName;

		/**
		 * 
		 * Uses the value from method of type to define which icon to call from
		 * UIManager Defaults to plain
		 */
		switch (iconType) {
		case ERROR_MESSAGE:
			iconName = "error";
			break;

		case INFORMATION_MESSAGE:
			iconName = "information";
			break;

		case WARNING_MESSAGE:
			iconName = "warning";
			break;

		case QUESTION_MESSAGE:
			iconName = "question";
			break;

		default:
			iconName = "plain";
			break;
		}
		JLabel labelIcon = new JLabel();
		labelIcon.setEnabled(true);
		/**
		 * Sets empty borders to move labelIcon from touching the top and left sides of
		 * dialog panel Sets border for right to disallow label from touching right side
		 * of dialog panel
		 */
		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		labelIcon.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
		//
		Icon icon = UIManager.getIcon("OptionPane." + iconName + "Icon");
		labelIcon.setIcon(icon);
		labelPanel.add(labelIcon, BorderLayout.WEST);
		labelPanel.add(label, BorderLayout.CENTER);
		if (customOptions == null)
			customOptions = new Object[] { "Ok" };

		for (var i = 0; customOptions.length > i; i++) {
			var buttonIndex = i;
			var btn = new JButton(customOptions[i].toString());
			// btn size
			btn.setPreferredSize(new Dimension(80, 30));

			if (buttonIndex == getInitOption(initialOption)) {
				getRootPane().setDefaultButton(btn);
				util.requestFocus(btn);
			}
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// defines that selectedOption == clicked button index value and disposed the
					// dialog afterwards
					selectedOption = buttonIndex;
					dispose();
				}
			});
			util.setDialogListeners(btn);
			btnPanel.add(btn);
		}
		parentPanel.add(labelPanel);
		parentPanel.add(btnPanel);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(dialogTitle);
		add(parentPanel);
		setSize(10, 10);
		// Changes JDialog frame Minimum size
//		setMinimumSize(new Dimension(380, 100));
		pack();
		// needs jframe to set location relative to the jframe
		setLocationRelativeTo(parentFrame);
	}

	/**
	 * 
	 * @param options => Integer value used to determine returned object
	 * @return
	 */
	private Object[] getConfirmOption(int options) {

		switch (options) {
		case YES_NO_OPTION:
			return customOptions = new Object[] { "Kyllä", "Ei" };

		case YES_NO_CANCEL_OPTION:
			return customOptions = new Object[] { "Kyllä", "Ei", "Peruuta" };

		case OK_CANCEL_OPTION:
			return customOptions = new Object[] { "Ok", "Peruuta" };

		default:
			return customOptions = new Object[] { "Ok" };
		}
	}
	/*
	 * If JButton label option equals to OK_CANCEL_OPTION
	 * returns value 1 to autofocus cancel option
	 */
	private int getInitOption(int initOption) {

		if (initialOption == OK_CANCEL_OPTION) {
			return 1;
		}
		return 0;
	}

	/**
	 * Changes \n on dialog messages to <br>
	 * which allows to JLabel to go into multiple lines for working as line break
	 * 
	 * @param message
	 * @return => returns the message with \n replaced with <br>
	 */
	private static String replaceLineBreak(String message) {
		if (message.contains("\n"))
			message = message.replaceAll("\n", "<br />");
		return message;
	}

	/** Returns clicked button index */
	public int getSelectedOption() {
		return selectedOption;
	}

	/**
	 * 
	 * @param _parentFrame => Frame
	 * @param _message     => Label message
	 * @param _title       => JDialog title
	 * @param type         => Icon type
	 * @param _option      => Custom Object[]
	 * @param _initOption  => Init custom Object[]
	 * @return
	 */
	public static int showOptionDialog(JFrame _parentFrame, Object _message, String _title, int type, Object[] _option,
			Object _initOption) {
		JcustomDialog dialog = new JcustomDialog(_parentFrame, _message.toString(), _title, type, _option, _initOption);
		dialog.setVisible(true);
		return dialog.getSelectedOption();
	}

	/**
	 * 
	 * @param _parentFrame => Frame
	 * @param _message     => Label message
	 * @param _title       => JDialog title
	 * @param type         => Icon type
	 * @param _option      => Integer Value of an option
	 * @param _initOption  => Integer Value of an Initial option
	 * @return
	 */
	public static int showOptionDialog(JFrame _parentFrame, String _message, String _title, int type, int _option,
			int _initOption) {
		JcustomDialog dialog = new JcustomDialog(_parentFrame, _message.toString(), _title, type, _option, _initOption);
		dialog.setVisible(true);
		return dialog.getSelectedOption();
	}

	/**
	 * @param _parentFrame => Frame
	 * @param _message     => Message shown on label
	 * @param _title       => JDialog title
	 * @param type         => Icon type
	 * @return
	 */
	public static int showMessageDialog(JFrame _parentFrame, Object _message, String _title, int type) {
		JcustomDialog dialog = new JcustomDialog(_parentFrame, _message.toString(), _title, type);
		dialog.setVisible(true);
		return dialog.getSelectedOption();
	}

	/**
	 * @param _parentFrame => Frame
	 * @param _message     => Message shown on label
	 * @param _title       => JDialog title
	 * @param type         => Icon type
	 * @param _option      => btn options
	 * @param _initOption  => Initial btn option (auto focused)
	 * @return
	 */
	public static int showMessageDialog(JFrame _parentFrame, Object _message, String _title, int type, int _option,
			int _initOption) {
		JcustomDialog dialog = new JcustomDialog(_parentFrame, _message.toString(), _title, type, _option, _initOption);
		dialog.setVisible(true);
		return dialog.getSelectedOption();
	}

	/**
	 * @param _parentFrame => Frame
	 * @param _message     => Message shown on label
	 * @param _title       => JDialog title
	 * @param type         => Icon type
	 * @param _option      => btn options
	 * @param _initOption  => Initial btn option (auto focused)
	 * @return
	 */
	public static int showConfirmDialog(JFrame _parentFrame, Object _message, String _title, int type, int _option,
			int _init) {
		JcustomDialog dialog = new JcustomDialog(_parentFrame, _message.toString(), _title, type, _option, _init);
		dialog.setVisible(true);
		return dialog.getSelectedOption();
	}
}
