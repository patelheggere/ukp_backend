package Model;

public class DistModel {
	String dist_id, dist_name, dist_name_kn, DistCode, DistName, ID, Code, Name, creadted_by, ModifiedBy;

	public DistModel(String dist_id, String dist_name, String dist_name_kn) {
		this.dist_id = dist_id;
		this.dist_name = dist_name;
		this.dist_name_kn = dist_name_kn;
	}

	public DistModel() {
	}

	public String getDist_id() {
		return dist_id;
	}

	public void setDist_id(String dist_id) {
		this.dist_id = dist_id;
	}

	public String getDist_name() {
		return dist_name;
	}

	public void setDist_name(String dist_name) {
		this.dist_name = dist_name;
	}

	public String getDist_name_kn() {
		return dist_name_kn;
	}

	public void setDist_name_kn(String dist_name_kn) {
		this.dist_name_kn = dist_name_kn;
	}

	public String getDistCode() {
		return DistCode;
	}

	public void setDistCode(String distCode) {
		DistCode = distCode;
	}

	public String getDistName() {
		return DistName;
	}

	public void setDistName(String distName) {
		DistName = distName;
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

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getCreadted_by() {
		return creadted_by;
	}

	public void setCreadted_by(String creadted_by) {
		this.creadted_by = creadted_by;
	}

	public String getModifiedBy() {
		return ModifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	
}
