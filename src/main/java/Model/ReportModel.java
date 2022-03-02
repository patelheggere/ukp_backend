package Model;

public class ReportModel {
	public String SyncDate,	MobileNumber,	ActionTime	,ActionType, LoginType ;

	public String getSyncDate() {
		return SyncDate;
	}

	public void setSyncDate(String syncDate) {
		SyncDate = syncDate;
	}

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

	public String getLoginType() {
		return LoginType;
	}

	public void setLoginType(String loginType) {
		LoginType = loginType;
	}
				
	
}
