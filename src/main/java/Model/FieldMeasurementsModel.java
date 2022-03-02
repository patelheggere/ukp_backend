package Model;

public class FieldMeasurementsModel {
	public String ID
	           ,GeneralInfoID
	           ,StructureElementID
	           ,SerialNo
	           ,ItemDescription
	           ,MainLabel
	           ,SubLabel
	           ,O
	           ,X
	           ,Y
	           ,L
	           ,B
	           ,H
	           ,C
	           ,Q
	           ,D
	           ,DsrSubID
	           ,UomID
	           ,UomCode
	           ,TotalArea
	           ,TotalVolume
	           ,DSRArea
	           ,DSRVolume
	           ,TotalAreaCalculated
	           ,TotalVolumeCalculated
	           ,CopyFromID
	           ,Inactive
	           ,CreatedBy
	           ,ModifiedBy
	           ,DsrOrder
	           ,Code, 
	           AreaFormula, 
	           VolumeFormula, 
	           deductionArea, paintDeductionArea;

	
	public FieldMeasurementsModel() {
		
	}

	

	public FieldMeasurementsModel(String iD, String generalInfoID, String structureElementID, String serialNo,
			String itemDescription, String mainLabel, String subLabel, String o, String x, String y, String l, String b,
			String h, String c, String q, String d, String dsrSubID, String uomID, String uomCode, String totalArea,
			String totalVolume, String dSRArea, String dSRVolume, String totalAreaCalculated,
			String totalVolumeCalculated, String copyFromID, String inactive, String createdBy, String modifiedBy,
			String dsrOrder) {
		super();
		ID = iD;
		GeneralInfoID = generalInfoID;
		StructureElementID = structureElementID;
		SerialNo = serialNo;
		ItemDescription = itemDescription;
		MainLabel = mainLabel;
		SubLabel = subLabel;
		O = o;
		X = x;
		Y = y;
		L = l;
		B = b;
		H = h;
		C = c;
		Q = q;
		D = d;
		DsrSubID = dsrSubID;
		UomID = uomID;
		UomCode = uomCode;
		TotalArea = totalArea;
		TotalVolume = totalVolume;
		DSRArea = dSRArea;
		DSRVolume = dSRVolume;
		TotalAreaCalculated = totalAreaCalculated;
		TotalVolumeCalculated = totalVolumeCalculated;
		CopyFromID = copyFromID;
		Inactive = inactive;
		CreatedBy = createdBy;
		ModifiedBy = modifiedBy;
		DsrOrder = dsrOrder;
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

	public String getStructureElementID() {
		return StructureElementID;
	}

	public void setStructureElementID(String structureElementID) {
		StructureElementID = structureElementID;
	}

	public String getSerialNo() {
		return SerialNo;
	}

	public void setSerialNo(String serialNo) {
		SerialNo = serialNo;
	}

	public String getItemDescription() {
		return ItemDescription;
	}

	public void setItemDescription(String itemDescription) {
		ItemDescription = itemDescription;
	}

	public String getMainLabel() {
		return MainLabel;
	}

	public void setMainLabel(String mainLabel) {
		MainLabel = mainLabel;
	}

	public String getSubLabel() {
		return SubLabel;
	}

	public void setSubLabel(String subLabel) {
		SubLabel = subLabel;
	}

	public String getO() {
		return O;
	}

	public void setO(String o) {
		O = o;
	}

	public String getX() {
		return X;
	}

	public void setX(String x) {
		X = x;
	}

	public String getY() {
		return Y;
	}

	public void setY(String y) {
		Y = y;
	}

	public String getL() {
		return L;
	}

	public void setL(String l) {
		L = l;
	}

	public String getB() {
		return B;
	}

	public void setB(String b) {
		B = b;
	}

	public String getH() {
		return H;
	}

	public void setH(String h) {
		H = h;
	}

	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
	}

	public String getQ() {
		return Q;
	}

	public void setQ(String q) {
		Q = q;
	}

	public String getD() {
		return D;
	}

	public void setD(String d) {
		D = d;
	}

	public String getDsrSubID() {
		return DsrSubID;
	}

	public void setDsrSubID(String dsrSubID) {
		DsrSubID = dsrSubID;
	}

	public String getUomID() {
		return UomID;
	}

	public void setUomID(String uomID) {
		UomID = uomID;
	}

	public String getUomCode() {
		return UomCode;
	}

	public void setUomCode(String uomCode) {
		UomCode = uomCode;
	}

	public String getTotalArea() {
		return TotalArea;
	}

	public void setTotalArea(String totalArea) {
		TotalArea = totalArea;
	}

	public String getTotalVolume() {
		return TotalVolume;
	}

	public void setTotalVolume(String totalVolume) {
		TotalVolume = totalVolume;
	}

	public String getDSRArea() {
		return DSRArea;
	}

	public void setDSRArea(String dSRArea) {
		DSRArea = dSRArea;
	}

	public String getDSRVolume() {
		return DSRVolume;
	}

	public void setDSRVolume(String dSRVolume) {
		DSRVolume = dSRVolume;
	}

	public String getTotalAreaCalculated() {
		return TotalAreaCalculated;
	}

	public void setTotalAreaCalculated(String totalAreaCalculated) {
		TotalAreaCalculated = totalAreaCalculated;
	}

	public String getTotalVolumeCalculated() {
		return TotalVolumeCalculated;
	}

	public void setTotalVolumeCalculated(String totalVolumeCalculated) {
		TotalVolumeCalculated = totalVolumeCalculated;
	}

	public String getCopyFromID() {
		return CopyFromID;
	}

	public void setCopyFromID(String copyFromID) {
		CopyFromID = copyFromID;
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

	public String getDsrOrder() {
		return DsrOrder;
	}

	public void setDsrOrder(String dsrOrder) {
		DsrOrder = dsrOrder;
	}



	public String getCode() {
		return Code;
	}



	public void setCode(String code) {
		Code = code;
	}



	public String getAreaFormula() {
		return AreaFormula;
	}



	public void setAreaFormula(String areaFormula) {
		AreaFormula = areaFormula;
	}



	public String getVolumeFormula() {
		return VolumeFormula;
	}



	public void setVolumeFormula(String volumeFormula) {
		VolumeFormula = volumeFormula;
	}



	public String getDeductionArea() {
		return deductionArea;
	}



	public void setDeductionArea(String deductionArea) {
		this.deductionArea = deductionArea;
	}



	public String getPaintDeductionArea() {
		return paintDeductionArea;
	}



	public void setPaintDeductionArea(String paintDeductionArea) {
		this.paintDeductionArea = paintDeductionArea;
	} 
	
	
	
	
}
