package PharmaceuticalApplication;

public class Pharmaceutical {
	private int pharmaceuticalID;
	private int specialRequirementID;
	private String pharmaceuticalName;
	private String Description;
	private String medicationType;
	private int recommendedDailyDose;

	public Pharmaceutical(int pharmaceuticalID, int specialRequirementID, String pharmaceuticalName, String description,
			String medicationType, int recommendedDailyDose) {
		super();
		this.pharmaceuticalID = pharmaceuticalID;
		this.specialRequirementID = specialRequirementID;
		this.pharmaceuticalName = pharmaceuticalName;
		Description = description;
		this.medicationType = medicationType;
		this.recommendedDailyDose = recommendedDailyDose;
	}

	@Override
	public String toString() {
		return "Pharmaceutical [pharmaceuticalID=" + pharmaceuticalID + ", specialRequirementID=" + specialRequirementID
				+ ", pharmaceuticalName=" + pharmaceuticalName + ", Description=" + Description + ", medicationType="
				+ medicationType + ", recommendedDailyDose=" + recommendedDailyDose + "]";
	}

	public int getPharmaceuticalID() {
		return pharmaceuticalID;
	}

	public String getPharmaceuticalName() {
		return pharmaceuticalName;
	}

	public String getDescription() {
		return Description;
	}

	public String getMedicationType() {
		return medicationType;
	}

	public int getRecommendedDailyDose() {
		return recommendedDailyDose;
	}

	public int getSpecialRequirementID() {
		return specialRequirementID;
	}


}
