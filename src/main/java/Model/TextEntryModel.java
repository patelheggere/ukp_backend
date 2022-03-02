package Model;

public class TextEntryModel {
	public String ID
		      ,GeneralInfoID
		      ,SerialNo
		      ,AreaName
		      ,Horizontal
		      ,Vertical
		      ,HorizontalWall1
		      ,HorizontalWall2
		      ,VerticalWall1
		      ,VerticalWall2
		      ,Inactive
		      ,CreatedBy;

	
	public TextEntryModel(String iD, String generalInfoID, String serialNo, String areaName, String horizontal,
			String vertical, String horizontalWall1, String horizontalWall2, String verticalWall1, String verticalWall2,
			String inactive, String createdBy) {
		super();
		ID = iD;
		GeneralInfoID = generalInfoID;
		SerialNo = serialNo;
		AreaName = areaName;
		Horizontal = horizontal;
		Vertical = vertical;
		HorizontalWall1 = horizontalWall1;
		HorizontalWall2 = horizontalWall2;
		VerticalWall1 = verticalWall1;
		VerticalWall2 = verticalWall2;
		Inactive = inactive;
		CreatedBy = createdBy;
	}

	public TextEntryModel() {
		// TODO Auto-generated constructor stub
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getGeneralInfoID() {
		return GeneralInfoID;
	}

	public void setGeneralInfoID(String generalInfoID) {
		GeneralInfoID = generalInfoID;
	}

	public String getSerialNo() {
		return SerialNo;
	}

	public void setSerialNo(String serialNo) {
		SerialNo = serialNo;
	}

	public String getAreaName() {
		return AreaName;
	}

	public void setAreaName(String areaName) {
		AreaName = areaName;
	}

	public String getHorizontal() {
		return Horizontal;
	}

	public void setHorizontal(String horizontal) {
		Horizontal = horizontal;
	}

	public String getVertical() {
		return Vertical;
	}

	public void setVertical(String vertical) {
		Vertical = vertical;
	}

	public String getHorizontalWall1() {
		return HorizontalWall1;
	}

	public void setHorizontalWall1(String horizontalWall1) {
		HorizontalWall1 = horizontalWall1;
	}

	public String getHorizontalWall2() {
		return HorizontalWall2;
	}

	public void setHorizontalWall2(String horizontalWall2) {
		HorizontalWall2 = horizontalWall2;
	}

	public String getVerticalWall1() {
		return VerticalWall1;
	}

	public void setVerticalWall1(String verticalWall1) {
		VerticalWall1 = verticalWall1;
	}

	public String getVerticalWall2() {
		return VerticalWall2;
	}

	public void setVerticalWall2(String verticalWall2) {
		VerticalWall2 = verticalWall2;
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
