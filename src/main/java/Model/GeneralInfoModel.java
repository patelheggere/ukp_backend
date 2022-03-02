package Model;

public class GeneralInfoModel {
	public String ID,
				Reference
			 ,DistrictID
			 ,DistrictCode
			 ,TalukID
			 ,TalukCode
			 ,VillageID
			 ,VillageCode
			 ,DateOfNotification
			 ,DsrID
			 ,StructureCode
			 ,OwnerName
			 ,PlinthArea
			 ,OpenArea
			 ,TotalArea
			 ,RemarksOnStructure
			 ,AgeOfStructure
			 ,StructureTypeID
			 ,StructureTypeCode
			 ,StructureDirection
			 ,FirstFloorArea
			 ,SecondFloorArea
			 ,TotalBuiltUpArea
			 ,SiteLocationN
			 ,SiteLocationS
			 ,SiteLocationE
			 ,SiteLocationW
			 ,Surveyor
			 ,SurveyorDesignation
			 ,SurveyOffice
			 ,SurveyLocation
			 ,WitnessName
			 ,WitnessSignature
			 ,TotalAmount
			 ,Depreciation
			 ,TotalAmountAfterDep
			 ,Inactive
			 ,CreatedBy
			 ,ModifiedBy
			 ,TotalGST, GST_Value_ID;

	
	
	public GeneralInfoModel() {
		
	}

	public GeneralInfoModel(String reference, String districtID, String districtCode, String talukID, String talukCode,
			String villageID, String villageCode, String dateOfNotification, String dsrID, String structureCode,
			String ownerName, String plinthArea, String openArea, String totalArea, String remarksOnStructure,
			String ageOfStructure, String structureTypeID, String structureTypeCode, String structureDirection,
			String firstFloorArea, String secondFloorArea, String totalBuiltUpArea, String siteLocationN,
			String siteLocationS, String siteLocationE, String siteLocationW, String surveyor,
			String surveyorDesignation, String surveyOffice, String surveyLocation, String witnessName,
			String witnessSignature, String totalAmount, String depreciation, String totalAmountAfterDep,
			String inactive, String createdOn, String createdBy, String modifiedOn, String modifiedBy,
			String totalGST) {
		super();
		Reference = reference;
		DistrictID = districtID;
		DistrictCode = districtCode;
		TalukID = talukID;
		TalukCode = talukCode;
		VillageID = villageID;
		VillageCode = villageCode;
		DateOfNotification = dateOfNotification;
		DsrID = dsrID;
		StructureCode = structureCode;
		OwnerName = ownerName;
		PlinthArea = plinthArea;
		OpenArea = openArea;
		TotalArea = totalArea;
		RemarksOnStructure = remarksOnStructure;
		AgeOfStructure = ageOfStructure;
		StructureTypeID = structureTypeID;
		StructureTypeCode = structureTypeCode;
		StructureDirection = structureDirection;
		FirstFloorArea = firstFloorArea;
		SecondFloorArea = secondFloorArea;
		TotalBuiltUpArea = totalBuiltUpArea;
		SiteLocationN = siteLocationN;
		SiteLocationS = siteLocationS;
		SiteLocationE = siteLocationE;
		SiteLocationW = siteLocationW;
		Surveyor = surveyor;
		SurveyorDesignation = surveyorDesignation;
		SurveyOffice = surveyOffice;
		SurveyLocation = surveyLocation;
		WitnessName = witnessName;
		WitnessSignature = witnessSignature;
		TotalAmount = totalAmount;
		Depreciation = depreciation;
		TotalAmountAfterDep = totalAmountAfterDep;
		Inactive = inactive;
		CreatedBy = createdBy;
		ModifiedBy = modifiedBy;
		TotalGST = totalGST;
	}

	public String getReference() {
		return Reference;
	}

	public void setReference(String reference) {
		Reference = reference;
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

	public String getTalukID() {
		return TalukID;
	}

	public void setTalukID(String talukID) {
		TalukID = talukID;
	}

	public String getTalukCode() {
		return TalukCode;
	}

	public void setTalukCode(String talukCode) {
		TalukCode = talukCode;
	}

	public String getVillageID() {
		return VillageID;
	}

	public void setVillageID(String villageID) {
		VillageID = villageID;
	}

	public String getVillageCode() {
		return VillageCode;
	}

	public void setVillageCode(String villageCode) {
		VillageCode = villageCode;
	}

	public String getDateOfNotification() {
		return DateOfNotification;
	}

	public void setDateOfNotification(String dateOfNotification) {
		DateOfNotification = dateOfNotification;
	}

	public String getDsrID() {
		return DsrID;
	}

	public void setDsrID(String dsrID) {
		DsrID = dsrID;
	}

	public String getStructureCode() {
		return StructureCode;
	}

	public void setStructureCode(String structureCode) {
		StructureCode = structureCode;
	}

	public String getOwnerName() {
		return OwnerName;
	}

	public void setOwnerName(String ownerName) {
		OwnerName = ownerName;
	}

	public String getPlinthArea() {
		return PlinthArea;
	}

	public void setPlinthArea(String plinthArea) {
		PlinthArea = plinthArea;
	}

	public String getOpenArea() {
		return OpenArea;
	}

	public void setOpenArea(String openArea) {
		OpenArea = openArea;
	}

	public String getTotalArea() {
		return TotalArea;
	}

	public void setTotalArea(String totalArea) {
		TotalArea = totalArea;
	}

	public String getRemarksOnStructure() {
		return RemarksOnStructure;
	}

	public void setRemarksOnStructure(String remarksOnStructure) {
		RemarksOnStructure = remarksOnStructure;
	}

	public String getAgeOfStructure() {
		return AgeOfStructure;
	}

	public void setAgeOfStructure(String ageOfStructure) {
		AgeOfStructure = ageOfStructure;
	}

	public String getStructureTypeID() {
		return StructureTypeID;
	}

	public void setStructureTypeID(String structureTypeID) {
		StructureTypeID = structureTypeID;
	}

	public String getStructureTypeCode() {
		return StructureTypeCode;
	}

	public void setStructureTypeCode(String structureTypeCode) {
		StructureTypeCode = structureTypeCode;
	}

	public String getStructureDirection() {
		return StructureDirection;
	}

	public void setStructureDirection(String structureDirection) {
		StructureDirection = structureDirection;
	}

	public String getFirstFloorArea() {
		return FirstFloorArea;
	}

	public void setFirstFloorArea(String firstFloorArea) {
		FirstFloorArea = firstFloorArea;
	}

	public String getSecondFloorArea() {
		return SecondFloorArea;
	}

	public void setSecondFloorArea(String secondFloorArea) {
		SecondFloorArea = secondFloorArea;
	}

	public String getTotalBuiltUpArea() {
		return TotalBuiltUpArea;
	}

	public void setTotalBuiltUpArea(String totalBuiltUpArea) {
		TotalBuiltUpArea = totalBuiltUpArea;
	}

	public String getSiteLocationN() {
		return SiteLocationN;
	}

	public void setSiteLocationN(String siteLocationN) {
		SiteLocationN = siteLocationN;
	}

	public String getSiteLocationS() {
		return SiteLocationS;
	}

	public void setSiteLocationS(String siteLocationS) {
		SiteLocationS = siteLocationS;
	}

	public String getSiteLocationE() {
		return SiteLocationE;
	}

	public void setSiteLocationE(String siteLocationE) {
		SiteLocationE = siteLocationE;
	}

	public String getSiteLocationW() {
		return SiteLocationW;
	}

	public void setSiteLocationW(String siteLocationW) {
		SiteLocationW = siteLocationW;
	}

	public String getSurveyor() {
		return Surveyor;
	}

	public void setSurveyor(String surveyor) {
		Surveyor = surveyor;
	}

	public String getSurveyorDesignation() {
		return SurveyorDesignation;
	}

	public void setSurveyorDesignation(String surveyorDesignation) {
		SurveyorDesignation = surveyorDesignation;
	}

	public String getSurveyOffice() {
		return SurveyOffice;
	}

	public void setSurveyOffice(String surveyOffice) {
		SurveyOffice = surveyOffice;
	}

	public String getSurveyLocation() {
		return SurveyLocation;
	}

	public void setSurveyLocation(String surveyLocation) {
		SurveyLocation = surveyLocation;
	}

	public String getWitnessName() {
		return WitnessName;
	}

	public void setWitnessName(String witnessName) {
		WitnessName = witnessName;
	}

	public String getWitnessSignature() {
		return WitnessSignature;
	}

	public void setWitnessSignature(String witnessSignature) {
		WitnessSignature = witnessSignature;
	}

	public String getTotalAmount() {
		return TotalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		TotalAmount = totalAmount;
	}

	public String getDepreciation() {
		return Depreciation;
	}

	public void setDepreciation(String depreciation) {
		Depreciation = depreciation;
	}

	public String getTotalAmountAfterDep() {
		return TotalAmountAfterDep;
	}

	public void setTotalAmountAfterDep(String totalAmountAfterDep) {
		TotalAmountAfterDep = totalAmountAfterDep;
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

	public String getTotalGST() {
		return TotalGST;
	}

	public void setTotalGST(String totalGST) {
		TotalGST = totalGST;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getGST_Value_ID() {
		return GST_Value_ID;
	}

	public void setGST_Value_ID(String gST_Value_ID) {
		GST_Value_ID = gST_Value_ID;
	}
	
	
	
}
