package PharmaceuticalApplication;

public class PrescriptionItem {

	private int pharmaceuticalID;
	private int specialRequirementID;
	private String name;
	private String description;
	private String additionalComments;
	private boolean availableOverTheCounter;
	private int containerSize;
	private int numberOfContainers;
	private int prescribedDailyDose;
	private int duration;


	public PrescriptionItem(String name, int prescribedDailyDose, int duration,
			 int containerSize, boolean availableOverTheCounter, String description, String additonalComments) {
		super();
		this.name = name;
		this.description = description;
		this.additionalComments = additonalComments;
		this.availableOverTheCounter = availableOverTheCounter;
		this.containerSize = containerSize;
		this.prescribedDailyDose = prescribedDailyDose;
		this.duration = duration;
		setNumberOfContainers();
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAvailableOverTheCounter() {
		return availableOverTheCounter;
	}

	public void setAvailableOverTheCounter(boolean availableOverTheCounter) {
		this.availableOverTheCounter = availableOverTheCounter;
	}

	public int getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(int containerSize) {
		this.containerSize = containerSize;
	}

	public int getPrescribedDailyDose() {
		return prescribedDailyDose;
	}

	public void setPrescribedDailyDose(int prescribedDailyDose) {
		this.prescribedDailyDose = prescribedDailyDose;
		setNumberOfContainers();
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = this.duration + duration;
		setNumberOfContainers();
	}
	
	public String getAdditionalComments() {
		return additionalComments;
	}
	
	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}


	public int getNumberOfContainers() {
		return numberOfContainers;
	}

	private void setNumberOfContainers() {
		numberOfContainers = (int)Math.ceil(((double)(prescribedDailyDose*duration)/containerSize));
	}

	public String toString() {
		return ("Name: " + name + "Duration: " + duration
				+ "Daily Dose: " + prescribedDailyDose
				+ "Number Of Containers: " + numberOfContainers
				+ "Over the Counter: " + availableOverTheCounter
				+ "Comments: " + description+additionalComments);

	}

	public Object[] getPrescriptionItem() {
		return new Object[] {name, duration,prescribedDailyDose,
				numberOfContainers, availableOverTheCounter, (description+additionalComments) };
	}

	public void lowerPrescribedDose() {
		this.prescribedDailyDose = this.prescribedDailyDose - 1;
	}
}
