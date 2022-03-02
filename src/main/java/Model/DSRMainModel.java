package Model;

public class DSRMainModel implements Comparable<DSRMainModel> {
	public String ID
		      ,DsrID
		      ,Code
		      ,Description
		      ,IsDsr
		      ,CreatedBy
		      ,ModifiedBy
		      ,DsrOrder
		      ,Inactive;

	public DSRMainModel() {
		super();
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getDsrID() {
		return DsrID;
	}

	public void setDsrID(String dsrID) {
		DsrID = dsrID;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getIsDsr() {
		return IsDsr;
	}

	public void setIsDsr(String isDsr) {
		IsDsr = isDsr;
	}

	public String getInactive() {
		return Inactive;
	}

	public void setInactive(String inactive) {
		Inactive = inactive;
	}

	public String getDsrOrder() {
		return DsrOrder;
	}

	public void setDsrOrder(String dsrOrder) {
		DsrOrder = dsrOrder;
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
	
	 @Override
	    public int compareTo(DSRMainModel o) {
	        // usually toString should not be used,
	        // instead one of the attributes or more in a comparator chain
		 int one = Integer.parseInt(this.getCode());
		 int two = Integer.parseInt(o.getCode());
	        return this.getCode().compareTo(o.getCode());
	    }
	
}
