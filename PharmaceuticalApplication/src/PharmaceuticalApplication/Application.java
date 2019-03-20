package PharmaceuticalApplication;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.mysql.cj.jdbc.MysqlDataSource;

public class Application {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultset = null;
	private ApplicationUI frame;
	private Prescription prescription;
	private String comboBoxSelected = null;
	private int spnPrescribeValue;
	private int spnDurationValue;
	private int specialID;
	private String additionalComments = "";
//	private ArrayList<Pharmaceutical> pharmaceuticals;
	private HashMap<String, Pharmaceutical> pharmaceuticalsHash;

	public Application() {
//		pharmaceuticals = new ArrayList<>();
		pharmaceuticalsHash = new HashMap<>();
		prescription = new Prescription();
		spnPrescribeValue = 1;
		spnDurationValue = 1;

		// Creates a new ApplicationUI
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ApplicationUI();

					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		try {
			// Connects to SQL Database
			MysqlDataSource pharmaceuticalDS = new MysqlDataSource();
			pharmaceuticalDS.setURL("jdbc:mysql://localhost:3306/doc_u_medx");
			pharmaceuticalDS.setUser("root");
			pharmaceuticalDS.setPassword("root");
			/* Connector - Java - 8.0.11 */
			connect = pharmaceuticalDS.getConnection();
			// Create new query and get info from pharmaceutical tables
			statement = connect.createStatement();
			resultset = statement.executeQuery("SELECT * FROM pharmaceuticals");
			while (resultset.next()) {
				// This loads the data from the database into the program, so I do not have to
				// make continuous database calls

				Pharmaceutical newPharma = new Pharmaceutical(resultset.getInt(1), resultset.getInt(2),
						resultset.getString(3), resultset.getString(4), resultset.getString(5), resultset.getInt(6));
				pharmaceuticalsHash.put(resultset.getString(3), newPharma);
				//Creating ArrayList but I have since refactored to HashMaps instead
//				pharmaceuticals.add(new Pharmaceutical(resultset.getInt(1), resultset.getInt(2), resultset.getString(3),
//						resultset.getString(4), resultset.getString(5), resultset.getInt(6)));
			}
			statement.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		// Calls method to populate UI combobox with pharmaceutical names
		setProductNames();
		// Create and set action listeners
		setActionListeners();

	}

	/*
	 * Calls Method on UI to populate the Pharmaceutical Names Combobox with names
	 * from Database
	 */
	public void setProductNames() {
		// Commented block of code below was what I did originally. When the application
		// was loaded I selected all
		// the pharmaceutical names from database and populated the combobox on the UI
//		try {
//			statement = connect.createStatement();
//			resultset = statement.executeQuery("SELECT PharmaceuticalName FROM pharmaceuticals");
//			while (resultset.next()) {
//				frame.setPharmaNames(resultset.getString(1));
//			}
//
//			statement.close();
//		} catch (Exception e) {
//			System.out.println(e);
//		}

		// Now I have simply loaded all the database into a database model class called
		// Pharmaceutical. No more calls to
		// the database should be required.
		
		//Mappings for ArrayList but I have since refactored to HashMaps instead.
//		for (Pharmaceutical pharmaceutical : pharmaceuticals) {
//			frame.setPharmaNames(pharmaceutical.getPharmaceuticalName());
//		}
		
		Map<String, Pharmaceutical> map = pharmaceuticalsHash;
		for (Pharmaceutical pharmaceutical: map.values()){
			frame.setPharmaNames(pharmaceutical.getPharmaceuticalName());
		}
	}

	public void setActionListeners() {
		// Create Action Listeners
		// I've done it this way to keep UI separate from logic
		ActionListener cbxPharmaNameAL = new ActionListener() {
			public void actionPerformed(ActionEvent evnt) {
				// Required to confirm which option was selected
				comboBoxSelected = (String) ((JComboBox) evnt.getSource()).getSelectedItem();
				System.out.println(comboBoxSelected);
				if (comboBoxSelected.equals("Select Item")) {

				} else {
					specialID = pharmaceuticalsHash.get(comboBoxSelected).getSpecialRequirementID();
					frame.uiData(getRecDailyDose((String) comboBoxSelected), getDescription(specialID));
				}
			}
		};
		ChangeListener spnPrescribedAL = new ChangeListener() {
			public void stateChanged(ChangeEvent evnt) {
				// Sets value for prescribed dose when spinner changes
				spnPrescribeValue = (int) ((JSpinner) evnt.getSource()).getValue();
			}
		};
		ChangeListener spnDurationAL = new ChangeListener() {
			public void stateChanged(ChangeEvent evnt) {
				// Sets value for duration when spinner changes
				spnDurationValue = (int) ((JSpinner) evnt.getSource()).getValue();
			}
		};
		ActionListener btnAddAL = new ActionListener() {
			public void actionPerformed(ActionEvent evnt) {
				//If statement here is validation of selected item.
				if (comboBoxSelected == null || comboBoxSelected.equals("Select Item")) {
					frame.displaySelectItemMsg();
				} else {
					additionalComments = frame.getComments();
					addItemToPerscription();
					frame.setNumberOfItems(prescription.getNumberOfItems());
					frame.setNumberOfContainers(prescription.getTotalNumberOfContainers());
				}
			}
		};
		ActionListener btnRemoveAL = new ActionListener() {
			public void actionPerformed(ActionEvent evnt) {
				if (prescription.getNumberOfItems() > 0) {
					// Calls methjod that removes selected row
					prescription.removeItem(frame.removeItemRow());
					frame.setNumberOfItems(prescription.getNumberOfItems());
					frame.setNumberOfContainers(prescription.getTotalNumberOfContainers());
				}
			}
		};
		ActionListener btnClearAL = new ActionListener() {
			public void actionPerformed(ActionEvent evnt) {
				// Calls method that removes items from prescription
				prescription.clearPrescription();
				// Calls methods that remove items from JTable and update UI values
				frame.clearPrescriptionTable();
				frame.setNumberOfItems(prescription.getNumberOfItems());
				frame.setNumberOfContainers(prescription.getTotalNumberOfContainers());
			}
		};
		ActionListener btnExitAL = new ActionListener() {
			public void actionPerformed(ActionEvent evnt) {
				System.exit(0);
			}
		};
		PopupMenuListener popupMenu = new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				frame.selectRowWhenRightClick();
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub

			}
		};
		ActionListener popLowerDose = new ActionListener() {
			public void actionPerformed(ActionEvent evnt) {
				//System.out.println("Lower Dose");
				prescription.lowerPresribedDose(frame.lowerItemRow());
				frame.setNumberOfItems(prescription.getNumberOfItems());
				frame.setNumberOfContainers(prescription.getTotalNumberOfContainers());
			}
		};
		ActionListener popEditComments = new ActionListener() {
			public void actionPerformed(ActionEvent evnt) {
				// Get already existing comments for selected item
				String additionalComments = prescription.getAdditionalComments(frame.getSelectedRowPharmaName());
				// Get new comments from input dialogue
				String newComments = frame.editItemRowComments(additionalComments);
				// Updates prescription item and returns index of updated item
				int index = prescription.setAdditionalComments(frame.getSelectedRowPharmaName(), newComments);
				frame.updatePrescriptionItem(prescription.getPrescriptionItem(index), index);

			}
		};
		frame.addActionListeners(cbxPharmaNameAL, btnAddAL, spnPrescribedAL, spnDurationAL, btnRemoveAL, btnClearAL,
				btnExitAL, popupMenu, popLowerDose, popEditComments);
	}

	public String getDescription(int specialRequirementsID) {
		String description = "";
		String specialRequirements = "";

		// The code below was run in order to set the string to go in the description
		// box on the UI
		// Previously I was running multiple queries in order to get the description and
		// special requirements from the database
		// This is now no longer necessary

//		try {
//			statement = connect.createStatement();
//			resultset = statement.executeQuery(
//					"SELECT Description FROM pharmaceuticals WHERE PharmaceuticalName = \"" + pharmaName + "\"");
//			while (resultset.next()) {
//				description = resultset.getString(1);
//			}
//			resultset = statement
//					.executeQuery("SELECT SpecialRequirementID FROM pharmaceuticals WHERE PharmaceuticalName = \""
//							+ pharmaName + "\"");
//			while (resultset.next()) {
//				switch (resultset.getInt(1)) {
//				case 1:
//					specialRequirements = "; Comes in 100ml Bottle";
//					break;
//				case 2:
//					specialRequirements = "; Comes in box of 62";
//					break;
//				case 3:
//					specialRequirements = "; Comes in 75ml Tube; MUST BE STORED IN FRIDGE";
//					break;
//				case 4:
//					specialRequirements = "; Comes in 75ml Tube";
//					break;
//				case 5:
//					specialRequirements = "; Comes in box of 30";
//					break;
//				case 6:
//					specialRequirements = "; Comes in 21ml phial; MUST BE STORED IN FRIDGE";
//					break;
//				default:
//					specialRequirements = "";
//					break;
//				}
//			}
//
//			statement.close();
//
//		} catch (Exception e) {
//			System.out.println(e);
//		}

		// Instead of complex querying I can simply access the database model class.
		System.out.println(specialRequirementsID);
		description = pharmaceuticalsHash.get(comboBoxSelected).getDescription();
		switch (specialRequirementsID) {
		case 1:
			specialRequirements = ";\nComes in 100ml Bottle";
			break;
		case 2:
			specialRequirements = ";\nComes in box of 62";
			break;
		case 3:
			specialRequirements = ";\nComes in 75ml Tube;\nMUST BE STORED IN FRIDGE";
			break;
		case 4:
			specialRequirements = ";\nComes in 75ml Tube";
			break;
		case 5:
			specialRequirements = ";\nComes in box of 30";
			break;
		case 6:
			specialRequirements = ";\nComes in 21ml phial;\nMUST BE STORED IN FRIDGE";
			break;
		default:
			specialRequirements = "";
			break;
		}

		description = description + specialRequirements;

		return description;
	}

	public int getRecDailyDose(String pharmaName) {
		int dailyDose = 1;
		// Gets recommended daily dose from hashmap of pharmaceuticals
		dailyDose = pharmaceuticalsHash.get(pharmaName).getRecommendedDailyDose();
		return dailyDose;
	}

	// Add item to prescription class and show on UI
	public void addItemToPerscription() {
		int containerSize = 1;
		int initialPrescriptionSize = prescription.getNumberOfItems();
		specialID = 1;
		String description = "";
		boolean overTheCounter = false;
		// Old code before i included hashmaps
//		for (Pharmaceutical pharmaceutical : pharmaceuticals) {
//			if (pharmaceutical.getPharmaceuticalName().equals(comboBoxSelected))
//				specialID = pharmaceutical.getSpecialRequirementID();
//		}
		// Much simpler and easier to read when using hashmaps
		specialID = pharmaceuticalsHash.get(comboBoxSelected).getSpecialRequirementID();
		
		description = getDescription(specialID);
		description = description + ";\n";
		// Neccessary to retrieve details from special requirements table. Special
		// Requirements needs its own data model
		// but I have left this in to demonstrate ease of use when mapping database to
		// model
		try {
			statement = connect.createStatement();
			resultset = statement.executeQuery(
					"SELECT * FROM " + "specialrequirements WHERE SpecialRequirementID = \"" + specialID + "\"");
			while (resultset.next()) {
				containerSize = resultset.getInt(2);
				if (resultset.getInt(5) == 1) {
					overTheCounter = true;
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		// Calls add on prescription. Most of the validation of existing items is done
		// in the Prescription Class
		prescription.addPrescriptionItem(comboBoxSelected, spnPrescribeValue, spnDurationValue, containerSize,
				overTheCounter, description, additionalComments);
		// if checks to see if there are actually items that have been added to
		// prescription
		if (prescription.getNumberOfItems() > 0) {
			for (int i = 0; i < prescription.getNumberOfItems(); i++) {
				Object[] tempItem = prescription.getPrescriptionItem(i);
				int size = prescription.getNumberOfItems();
				// Check to see if size of prescription has changed
				// If so, new item has been added to prescription so update JTable.
				if (initialPrescriptionSize < size) {
					frame.addNewPrescriptionItem(prescription.getPrescriptionItem(size - 1));
					break;
				} else {
					// If size has not been changed, simply update existing item on jTable.
					if (tempItem[0].equals(comboBoxSelected)) {
						frame.updatePrescriptionItem(tempItem, i);
					}
				}
			}
		}
		// Reset values after item has been added
		spnPrescribeValue = 1;
		spnDurationValue = 1;

	}
}
