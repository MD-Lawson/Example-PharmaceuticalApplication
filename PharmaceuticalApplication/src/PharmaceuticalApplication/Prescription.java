package PharmaceuticalApplication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class Prescription {
	ArrayList<PrescriptionItem> items;
	int numberOfItems;
	int totalNumberOfContainers;

	public Prescription() {
		items = new ArrayList<PrescriptionItem>();
		numberOfItems = 0;
		totalNumberOfContainers = 0;
	}

	public void addPrescriptionItem(String pharmaName, int prescribedDose, int duration, int containerSize,
			boolean overTheCounter, String description, String additionalComments) {
		//Checks if no items already exist. If true automatically adds new item
		if (items.size() == 0) {
			items.add(new PrescriptionItem(pharmaName, prescribedDose, duration, containerSize, overTheCounter,
					description, additionalComments));
		} else {
			Iterator<PrescriptionItem> it = items.iterator();
			int index = 0;
			while (it.hasNext()) {
				//This if here says that if we reach the end of the prescriptionItems list then it is a new prescription
				//So add new item
				if (index == items.size()) {
					System.out.println("Item added");
					items.add(new PrescriptionItem(pharmaName, prescribedDose, duration, containerSize, overTheCounter,
							description, additionalComments));
					break;
				} else if (items.get(index).getName().equals(pharmaName)) {
					//If item already exists then we need to update duration, dose and comments
					items.get(index).setDuration(duration);
					items.get(index).setPrescribedDailyDose(prescribedDose);
					items.get(index).setAdditionalComments(additionalComments);
					break;

				}

				index++;
			}
		}
		calculateNumberOfContainers();
	}

	public int getTotalNumberOfContainers() {
		return totalNumberOfContainers;
	}
	//Returns prescription item object
	public Object[] getPrescriptionItem(int index) {
		return items.get(index).getPrescriptionItem();
	}

	public void printPrescription() {
		for (PrescriptionItem item : items)
			System.out.println(item.toString());
	}

	public int getNumberOfItems() {
		this.numberOfItems = items.size();
		return this.numberOfItems;
	}

	public int calculateNumberOfContainers() {
		totalNumberOfContainers = 0;
		for (PrescriptionItem item : items)
			totalNumberOfContainers = totalNumberOfContainers + item.getNumberOfContainers();

		return totalNumberOfContainers;
	}

	public void clearPrescription() {
		items.clear();
		calculateNumberOfContainers();
	}

	public void removeItem(String pharmaName) {
		if (pharmaName.equals("")) {

		} else {
			Iterator<PrescriptionItem> it = items.iterator();
			int index = 0;
			while (it.hasNext()) {
				if (items.get(index).getName().equals(pharmaName)) {
					break;
				}
				index++;
			}

			items.remove(index);
			calculateNumberOfContainers();
		}
	}

	public void lowerPresribedDose(String pharmaName) {
		if (pharmaName.equals("")) {

		} else {
			Iterator<PrescriptionItem> it = items.iterator();
			int index = 0;
			while (it.hasNext()) {
				if (items.get(index).getName().equals(pharmaName)) {
					items.get(index).lowerPrescribedDose();
					break;
				}
				index++;
			}
			calculateNumberOfContainers();
		}

	}

	public String getAdditionalComments(String pharmaName) {
		Iterator<PrescriptionItem> it = items.iterator();
		int index = 0;
		while (it.hasNext()) {
			if (items.get(index).getName().equals(pharmaName)) {
				return items.get(index).getAdditionalComments();
			}
			index++;
		}

		return "";
	}

	public int setAdditionalComments(String pharmaName, String newAdditionalComments) {
		Iterator<PrescriptionItem> it = items.iterator();
		int index = 0;
		while (it.hasNext()) {
			if (items.get(index).getName().equals(pharmaName)) {
				items.get(index).setAdditionalComments(newAdditionalComments);
				break;
			}
			index++;
		}
		return index;
	}
}
