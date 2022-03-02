package Model;

public class FloorPlanElementsModel {
public String	mainLabel,	subLabel,	orientation="", HorizontalWall1,	HorizontalWall2	,VerticalWall1	,VerticalWall2;

public float xAxis,	yAxis,	length	,breadth,	height,	circumference;

public String getMainLabel() {
	return mainLabel;
}

public void setMainLabel(String mainLabel) {
	this.mainLabel = mainLabel;
}

public String getSubLabel() {
	return subLabel;
}

public void setSubLabel(String subLabel) {
	this.subLabel = subLabel;
}

public String getOrientation() {
	return orientation;
}

public void setOrientation(String orientation) {
	this.orientation = orientation;
	if(this.orientation==null)
		this.orientation="";
}

public float getxAxis() {
	return xAxis;
}

public void setxAxis(float xAxis) {
	this.xAxis = xAxis;
}

public float getyAxis() {
	return yAxis;
}

public void setyAxis(float yAxis) {
	this.yAxis = yAxis;
}



public float getLength() {
	return length;
}

public void setLength(float length) {
	this.length = length;
}

public float getBreadth() {
	return breadth;
}

public void setBreadth(float breadth) {
	this.breadth = breadth;
}

public float getHeight() {
	return height;
}

public void setHeight(float height) {
	this.height = height;
}

public float getCircumference() {
	return circumference;
}

public void setCircumference(float circumference) {
	this.circumference = circumference;
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



}
