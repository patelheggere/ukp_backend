package Model;

public class RateOfDepreciationModel {
	public String ID,	NoOfYear,	AtOnePercent,	AtOneAndHalfPercent,	AtTwoPercent,	AtFourPercent,	Description,	Inactive,
	CreatedBy;

	
	public RateOfDepreciationModel(String iD, String noOfYear, String atOnePercent, String atOneAndHalfPercent,
			String atTwoPercent, String atFourPercent, String description, String inactive, String createdOn,
			String createdBy, String modifiedOn, String modifiedBy) {
		super();
		ID = iD;
		NoOfYear = noOfYear;
		AtOnePercent = atOnePercent;
		AtOneAndHalfPercent = atOneAndHalfPercent;
		AtTwoPercent = atTwoPercent;
		AtFourPercent = atFourPercent;
		Description = description;
		Inactive = inactive;
	
	}

	public RateOfDepreciationModel() {
		// TODO Auto-generated constructor stub
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getNoOfYear() {
		return NoOfYear;
	}

	public void setNoOfYear(String noOfYear) {
		NoOfYear = noOfYear;
	}

	public String getAtOnePercent() {
		return AtOnePercent;
	}

	public void setAtOnePercent(String atOnePercent) {
		AtOnePercent = atOnePercent;
	}

	public String getAtOneAndHalfPercent() {
		return AtOneAndHalfPercent;
	}

	public void setAtOneAndHalfPercent(String atOneAndHalfPercent) {
		AtOneAndHalfPercent = atOneAndHalfPercent;
	}

	public String getAtTwoPercent() {
		return AtTwoPercent;
	}

	public void setAtTwoPercent(String atTwoPercent) {
		AtTwoPercent = atTwoPercent;
	}

	public String getAtFourPercent() {
		return AtFourPercent;
	}

	public void setAtFourPercent(String atFourPercent) {
		AtFourPercent = atFourPercent;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getInactive() {
		return Inactive;
	}

	public void setInactive(String inactive) {
		Inactive = inactive;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	
}
