package Model;

public class TalukModel {
public 	String 
	ID
	           ,Code
	           ,Name
	           ,DistrictID
	           ,DistrictCode
	           ,Inactive
	           ,CreatedBy
	           ,ModifiedBy;
	
	public TalukModel() {
		super();
	}

	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getDistrictID() {
		return DistrictID;
	}

	public void setDistrictID(String districtID) {
		DistrictID = districtID;
	}

	public String getDistrictCode() {
		return DistrictCode;
	}

	public void setDistrictCode(String districtCode) {
		DistrictCode = districtCode;
	}


	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
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


	public String getModifiedBy() {
		return ModifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}

	
	
	
	
}
