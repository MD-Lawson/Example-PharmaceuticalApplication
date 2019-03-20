package PharmaceuticalApplication;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.Rectangle;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ApplicationUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtRecDaily;
	private JTable prescriptionTable;
	private JTextField txtNumPrescriptions;
	private JTextField txtNumContainers;
	private JComboBox cbxPharmaName;
	private JTextArea txtrDescription;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnClear;
	private JButton btnExit;
	private JSpinner spnPrescribed;
	private JSpinner spnDuration;
	private JCheckBox chckbxOkToExceed;
	private JCheckBox chckbxAddComment;
	private JPopupMenu popTable;
	private JMenuItem lowerPrescribedDose;
	private JMenuItem editComments;
	private int prescribedMultiplier;

	/**
	 * Create the frame.
	 */
	public ApplicationUI() {
		setResizable(false);

		// Most of this is initialisation of UI.
		prescribedMultiplier = 1;

		setTitle("Doc-U-Med Prescription Creator");
		setMinimumSize(new Dimension(1000, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPharmaceuticalName = new JLabel("Pharmaceutical Name");
		lblPharmaceuticalName.setBounds(47, 43, 137, 14);
		contentPane.add(lblPharmaceuticalName);

		txtrDescription = new JTextArea();
		txtrDescription.setEditable(false);
		txtrDescription.setRows(4);
		txtrDescription.setBounds(479, 57, 179, 49);
		txtrDescription.setLineWrap(true);
		contentPane.add(txtrDescription);

		cbxPharmaName = new JComboBox();
		cbxPharmaName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// This action changes the prescribed maximum prescribed dose value depending on
				// the multiplier
				int maximum = Integer.parseInt(txtRecDaily.getText()) * prescribedMultiplier;
				spnPrescribed.setModel(new SpinnerNumberModel(1, 1, maximum, 1));
			}
		});
		cbxPharmaName.setModel(new DefaultComboBoxModel(new String[] { "Select Item" }));
		cbxPharmaName.setBounds(47, 59, 126, 20);
		contentPane.add(cbxPharmaName);

		JLabel lblRecDailyDose = new JLabel("Rec Daily Dose");
		lblRecDailyDose.setBounds(204, 43, 95, 14);
		contentPane.add(lblRecDailyDose);

		txtRecDaily = new JTextField();
		txtRecDaily.setEditable(false);
		txtRecDaily.setBounds(204, 59, 86, 20);
		contentPane.add(txtRecDaily);
		txtRecDaily.setColumns(10);

		JLabel lblPrescribedDaily = new JLabel("Prescribed Daily Dose");
		lblPrescribedDaily.setBounds(309, 43, 137, 14);
		contentPane.add(lblPrescribedDaily);

		spnPrescribed = new JSpinner();
		spnPrescribed.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spnPrescribed.setBounds(308, 59, 138, 20);
		contentPane.add(spnPrescribed);

		chckbxOkToExceed = new JCheckBox("OK to exceed Daily Dose");
		chckbxOkToExceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evnt) {
				// This action changes the multiplier to either 2 or 1, this allows maximum
				// prescribed dose to be set as
				// described in additional functionality of spec
				int currentValue = (int)spnPrescribed.getValue();
				if (chckbxOkToExceed.isSelected()) {
					prescribedMultiplier = 2;
					int maximum = Integer.parseInt(txtRecDaily.getText()) * prescribedMultiplier;
					spnPrescribed.setModel(new SpinnerNumberModel(1, 1, maximum, 1));
				} else {
					prescribedMultiplier = 1;
					int maximum = Integer.parseInt(txtRecDaily.getText()) * prescribedMultiplier;
					spnPrescribed.setModel(new SpinnerNumberModel(1, 1, maximum, 1));
				}
				spnPrescribed.setValue(currentValue);
			}
		});
		chckbxOkToExceed.setBounds(304, 83, 164, 23);
		contentPane.add(chckbxOkToExceed);

		JLabel lblDescriptionSpecial = new JLabel("Description & special details");
		lblDescriptionSpecial.setBounds(479, 43, 164, 14);
		contentPane.add(lblDescriptionSpecial);

		JLabel lblDuration = new JLabel("Duration");
		lblDuration.setBounds(668, 43, 57, 14);
		contentPane.add(lblDuration);

		spnDuration = new JSpinner();
		spnDuration.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spnDuration.setBounds(668, 59, 57, 20);
		contentPane.add(spnDuration);

		chckbxAddComment = new JCheckBox("Add Comment");
		chckbxAddComment.setBounds(731, 59, 106, 23);
		contentPane.add(chckbxAddComment);

		btnAdd = new JButton("Add");
		btnAdd.setBounds(885, 59, 89, 23);
		contentPane.add(btnAdd);

		JScrollPane prescriptionScrollPane = new JScrollPane();
		prescriptionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		prescriptionScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		prescriptionScrollPane.setBounds(56, 136, 781, 292);
		contentPane.add(prescriptionScrollPane);

		prescriptionTable = new JTable();
		prescriptionTable.setBounds(new Rectangle(0, 0, 1000, 0));
		prescriptionScrollPane.setViewportView(prescriptionTable);
		prescriptionTable.setShowVerticalLines(false);
		prescriptionTable.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		prescriptionTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Product Name", "Duration",
				"Prescribed Daily Dose", "Number of Containers", "OTC", "Comments" }) {
			Class[] columnTypes = new Class[] { String.class, Integer.class, Integer.class, Integer.class,
					Boolean.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		prescriptionTable.getColumnModel().getColumn(0).setResizable(false);
		prescriptionTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		prescriptionTable.getColumnModel().getColumn(1).setResizable(false);
		prescriptionTable.getColumnModel().getColumn(1).setPreferredWidth(80);
		prescriptionTable.getColumnModel().getColumn(2).setResizable(false);
		prescriptionTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		prescriptionTable.getColumnModel().getColumn(3).setResizable(false);
		prescriptionTable.getColumnModel().getColumn(3).setPreferredWidth(130);
		prescriptionTable.getColumnModel().getColumn(4).setResizable(false);
		prescriptionTable.getColumnModel().getColumn(4).setPreferredWidth(32);
		prescriptionTable.getColumnModel().getColumn(5).setResizable(false);
		prescriptionTable.getColumnModel().getColumn(5).setPreferredWidth(300);
		// The wrapper below ensures that text is wrapped in the comments box and
		// doesn't stay in one line
		prescriptionTable.getColumnModel().getColumn(5).setCellRenderer(new WordWrapCellRenderer());

		popTable = new JPopupMenu();
		lowerPrescribedDose = new JMenuItem("Lower the prescribed daily dose");
		editComments = new JMenuItem("Edit Comments");
		popTable.add(lowerPrescribedDose);
		popTable.add(editComments);
		prescriptionTable.setComponentPopupMenu(popTable);

		btnRemove = new JButton("Remove");
		btnRemove.setBounds(885, 165, 89, 23);
		contentPane.add(btnRemove);

		btnClear = new JButton("Clear");
		btnClear.setBounds(885, 190, 89, 23);
		contentPane.add(btnClear);

		JLabel lblTotalPrescription = new JLabel("Total Number of Prescription Items:");
		lblTotalPrescription.setBounds(56, 516, 210, 14);
		contentPane.add(lblTotalPrescription);

		txtNumPrescriptions = new JTextField();
		txtNumPrescriptions.setText("0");
		txtNumPrescriptions.setEditable(false);
		txtNumPrescriptions.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtNumPrescriptions.setBounds(264, 513, 86, 20);
		contentPane.add(txtNumPrescriptions);
		txtNumPrescriptions.setColumns(10);

		JLabel lblNumContainers = new JLabel("Total Number of Containers:");
		lblNumContainers.setBounds(604, 516, 166, 14);
		contentPane.add(lblNumContainers);

		txtNumContainers = new JTextField();
		txtNumContainers.setText("0");
		txtNumContainers.setEditable(false);
		txtNumContainers.setBounds(771, 513, 95, 20);
		contentPane.add(txtNumContainers);
		txtNumContainers.setColumns(10);

		btnExit = new JButton("Exit");
		btnExit.setBounds(885, 512, 89, 23);
		contentPane.add(btnExit);
	}

	// This function adds the action listeners defined in Application Class to the
	// respective UI elements
	public void addActionListeners(ActionListener cbxPharmaNameAL, ActionListener btnAddAL,
			ChangeListener spnPrescribedAL, ChangeListener spnDurationAL, ActionListener btnRemoveAL,
			ActionListener btnClearAL, ActionListener btnExitAL, PopupMenuListener popupTable,
			ActionListener popLowerDose, ActionListener popEditComments) {
		cbxPharmaName.addActionListener(cbxPharmaNameAL);
		btnAdd.addActionListener(btnAddAL);
		spnPrescribed.addChangeListener(spnPrescribedAL);
		spnDuration.addChangeListener(spnDurationAL);
		btnRemove.addActionListener(btnRemoveAL);
		btnClear.addActionListener(btnClearAL);
		btnExit.addActionListener(btnExitAL);
		popTable.addPopupMenuListener(popupTable);
		lowerPrescribedDose.addActionListener(popLowerDose);
		editComments.addActionListener(popEditComments);
	}

	// Adds the pharmaceuitcal items to the comboBox
	public void setPharmaNames(String newName) {
		cbxPharmaName.addItem(newName);
	}

	// Updates rec daily dose and description
	public void uiData(int recDailyDose, String description) {
		txtRecDaily.setText(Integer.toString(recDailyDose));
		txtrDescription.setText(description);
	}
	
	public void displaySelectItemMsg() {
		JOptionPane.showMessageDialog(null, "Please Select a pharmaceutical");
	}

	// Adds data to the JTable
	public void addNewPrescriptionItem(Object[] newItem) {
		DefaultTableModel model = (DefaultTableModel) prescriptionTable.getModel();
		model.addRow(newItem);

		// Reset values once added
		spnPrescribed.setValue(1);
		spnDuration.setValue(1);
	}

	// Updates data on JTable from item passed into method
	public void updatePrescriptionItem(Object[] updateItem, int index) {
		DefaultTableModel model = (DefaultTableModel) prescriptionTable.getModel();
		model.setValueAt(updateItem[1], index, 1);
		model.setValueAt(updateItem[3], index, 3);
		model.setValueAt(updateItem[5], index, 5);

		if ((int) updateItem[2] > (int) model.getValueAt(index, 2)) {
			model.setValueAt(updateItem[2], index, 2);
		}

		spnPrescribed.setValue(1);
		spnDuration.setValue(1);
	}

	// Updates number of containers text box
	public void setNumberOfContainers(int numberOfContainers) {
		txtNumContainers.setText(Integer.toString(numberOfContainers));
	}

	// Updates number of items text box
	public void setNumberOfItems(int numberOfItems) {
		txtNumPrescriptions.setText(Integer.toString(numberOfItems));
	}

	// Gets the Name of the pharmaceutical from currently selected row
	public String getSelectedRowPharmaName() {
		int selectedRow = getSelectedRow();
		if (selectedRow < 0) {
			return "";
		} else {
			String pharma = (String) prescriptionTable.getValueAt(selectedRow, 0);
			return pharma;
		}
	}

	// Gets the index of the currently selected row
	private int getSelectedRow() {
		return prescriptionTable.getSelectedRow();
	}

	// Removes all items from JTable
	public void clearPrescriptionTable() {
		DefaultTableModel model = (DefaultTableModel) prescriptionTable.getModel();
		model.setRowCount(0);
	}

	// Removes selected item from JTable and returns the pharmaceutical name of that
	// item
	public String removeItemRow() {
		DefaultTableModel model = (DefaultTableModel) prescriptionTable.getModel();
		String pharmaName = getSelectedRowPharmaName();
		if (getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "Cannot remove as no item is selected");
		} else {
			model.removeRow(getSelectedRow());
		}
		return pharmaName;
	}

	// Lowers the prescription value of selected row on table
	public String lowerItemRow() {
		DefaultTableModel model = (DefaultTableModel) prescriptionTable.getModel();
		int newPrescribedValue = (int) model.getValueAt(getSelectedRow(), 2) - 1;
		if (newPrescribedValue < 1) {
			return "";
		} else {
			model.setValueAt(newPrescribedValue, getSelectedRow(), 2);
			return getSelectedRowPharmaName();
		}

	}

	// Edits the comments of selected row on table from JPopupMenu Edit action
	public String editItemRowComments(String comments) {
		String newComments = JOptionPane.showInputDialog(null, "Edit Comments: ", comments);
		if (newComments == null) {
			return "";
		}
		if (newComments.length() > 0) {
			// Need to ensure comments are properly formatted
			newComments = newComments.replaceAll("\\s", "\n");
			String lastLetter = newComments.substring(newComments.length() - 1);
			if (lastLetter.equals(";")) {
				newComments = newComments + "\n";
			} else if (lastLetter.equals("\n") || lastLetter.equals(" ")) {

			} else {
				newComments = newComments + ";\n";
			}
		}
		return newComments;
	}

	// Displays dialog input box for comments to be added
	public String getComments() {
		if (chckbxAddComment.isSelected() || chckbxOkToExceed.isSelected()) {
			String additionalComments = JOptionPane.showInputDialog("Please input additonal comments: ");
			// If handles when dialog box is cancelled
			if (additionalComments == null) {
				return "";
			}
			// Checks if ; is last letter and adds it if not
			String lastLetter = additionalComments.substring(additionalComments.length() - 1);
			if (lastLetter.equals(";")) {
				additionalComments = additionalComments + "\n";
			} else if (lastLetter.equals("\n")) {

			} else {
				additionalComments = additionalComments + ";\n";
			}
			return additionalComments;
		} else {
			return "";
		}
	}
	//This method selects a JTable row when the right click is within its bounds
	//The specific method is taken from https://stackoverflow.com/questions/16743427/jtable-right-click-popup-menu
	//Submitted by user Guillaume Polet https://stackoverflow.com/users/928711/guillaume-polet
	//Built upon by me to work with my application.
	public void selectRowWhenRightClick() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				int rowAtPoint = prescriptionTable
						.rowAtPoint(SwingUtilities.convertPoint(popTable, new Point(0, 0), prescriptionTable));
				if (rowAtPoint > -1) {
					prescriptionTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
				}
			}
		});
	}
}
