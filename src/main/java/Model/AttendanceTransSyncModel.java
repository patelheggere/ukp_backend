package Model;

public class AttendanceTransSyncModel {
	
	public String  MobileNumber,ActionTime,ActionType,MacAddress,DeviceName,DeviceVersion,ApplnVer;
	/*public String DeptID
	,StaffID
	,InTime
	,OutTime
	;*/

	public String getMobileNumber() {
		return MobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		MobileNumber = mobileNumber;
	}

	public String getActionTime() {
		return ActionTime;
	}

	public void setActionTime(String actionTime) {
		ActionTime = actionTime;
	}

	public String getActionType() {
		return ActionType;
	}

	public void setActionType(String actionType) {
		ActionType = actionType;
	}

	public String getMacAddress() {
		return MacAddress;
	}

	public void setMacAddress(String macAddress) {
		MacAddress = macAddress;
	}

	public String getDeviceName() {
		return DeviceName;
	}

	public void setDeviceName(String deviceName) {
		DeviceName = deviceName;
	}

	public String getDeviceVersion() {
		return DeviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		DeviceVersion = deviceVersion;
	}

	public String getApplnVer() {
		return ApplnVer;
	}

	public void setApplnVer(String applnVer) {
		ApplnVer = applnVer;
	}
	
	
}
