package Model;

public class StrElementModelClass implements Comparable<StrElementModelClass>{
	public String
	ID,
	 Name,			
     Code,			
     Description,	
     LabelPrefix,	
     HeighDepthRequired, 
     LengthRequired,		
     BreadthWidthThicknessRequried, 
     DiameterRequired,		 
     PlanRequired,			
     CrossSectionRequired,	
     CircumferenceRequired,	
     OrientationRequired,	
     XRequired,				
     YRequired,				
     QuantityRequired,		
     UomID,					
     UomCode,				
     AreaFormula,			
     VolumeFormula,			
     Inactive,		
     CreatedBy,
     ModifiedBy ;
	
	public StrElementModelClass() {
		super();
	}

	public StrElementModelClass(String name, String code, String description, String labelPrefix,
			String heighDepthRequired, String lengthRequired, String breadthWidthThicknessRequried,
			String diameterRequired, String planRequired, String crossSectionRequired, String circumferenceRequired,
			String orientationRequired, String xRequired, String yRequired, String quantityRequired, String uomID,
			String uomCode, String areaFormula, String volumeFormula, String inactive, String createdBy,
			String modifiedBy) {
		super();
		Name = name;
		Code = code;
		Description = description;
		LabelPrefix = labelPrefix;
		HeighDepthRequired = heighDepthRequired;
		LengthRequired = lengthRequired;
		BreadthWidthThicknessRequried = breadthWidthThicknessRequried;
		DiameterRequired = diameterRequired;
		PlanRequired = planRequired;
		CrossSectionRequired = crossSectionRequired;
		CircumferenceRequired = circumferenceRequired;
		OrientationRequired = orientationRequired;
		XRequired = xRequired;
		YRequired = yRequired;
		QuantityRequired = quantityRequired;
		UomID = uomID;
		UomCode = uomCode;
		AreaFormula = areaFormula;
		VolumeFormula = volumeFormula;
		Inactive = inactive;
		CreatedBy = createdBy;
		ModifiedBy = modifiedBy;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
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

	public String getLabelPrefix() {
		return LabelPrefix;
	}

	public void setLabelPrefix(String labelPrefix) {
		LabelPrefix = labelPrefix;
	}

	public String getHeighDepthRequired() {
		return HeighDepthRequired;
	}

	public void setHeighDepthRequired(String heighDepthRequired) {
		HeighDepthRequired = heighDepthRequired;
	}

	public String getLengthRequired() {
		return LengthRequired;
	}

	public void setLengthRequired(String lengthRequired) {
		LengthRequired = lengthRequired;
	}

	public String getBreadthWidthThicknessRequried() {
		return BreadthWidthThicknessRequried;
	}

	public void setBreadthWidthThicknessRequried(String breadthWidthThicknessRequried) {
		BreadthWidthThicknessRequried = breadthWidthThicknessRequried;
	}

	public String getDiameterRequired() {
		return DiameterRequired;
	}

	public void setDiameterRequired(String diameterRequired) {
		DiameterRequired = diameterRequired;
	}

	public String getPlanRequired() {
		return PlanRequired;
	}

	public void setPlanRequired(String planRequired) {
		PlanRequired = planRequired;
	}

	public String getCrossSectionRequired() {
		return CrossSectionRequired;
	}

	public void setCrossSectionRequired(String crossSectionRequired) {
		CrossSectionRequired = crossSectionRequired;
	}

	public String getCircumferenceRequired() {
		return CircumferenceRequired;
	}

	public void setCircumferenceRequired(String circumferenceRequired) {
		CircumferenceRequired = circumferenceRequired;
	}

	public String getOrientationRequired() {
		return OrientationRequired;
	}

	public void setOrientationRequired(String orientationRequired) {
		OrientationRequired = orientationRequired;
	}

	public String getXRequired() {
		return XRequired;
	}

	public void setXRequired(String xRequired) {
		XRequired = xRequired;
	}

	public String getYRequired() {
		return YRequired;
	}

	public void setYRequired(String yRequired) {
		YRequired = yRequired;
	}

	public String getQuantityRequired() {
		return QuantityRequired;
	}

	public void setQuantityRequired(String quantityRequired) {
		QuantityRequired = quantityRequired;
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

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	 @Override
	    public int compareTo(StrElementModelClass o) {
	        // usually toString should not be used,
	        // instead one of the attributes or more in a comparator chain
	        return this.getLabelPrefix().compareTo(o.getLabelPrefix());
	    }
	
	
}

