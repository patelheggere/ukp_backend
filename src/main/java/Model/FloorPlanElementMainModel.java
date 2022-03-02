package Model;

import java.util.List;

public class FloorPlanElementMainModel {
	public FloorPlanElementsModel mainLabel;
	public List<FloorPlanElementsModel> floorPlanElementList;
	public List<FloorPlanElementsModel> getFloorPlanElementList() {
		return floorPlanElementList;
	}
	public void setFloorPlanElementList(List<FloorPlanElementsModel> floorPlanElementList) {
		this.floorPlanElementList = floorPlanElementList;
	}
	public FloorPlanElementsModel getMainLabel() {
		return mainLabel;
	}
	public void setMainLabel(FloorPlanElementsModel mainLabel) {
		this.mainLabel = mainLabel;
	}
	
}
