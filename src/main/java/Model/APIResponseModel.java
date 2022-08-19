package Model;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.Rate;

import sconti.ukp.RateAbstractModel;

	public class APIResponseModel {
		String message;
		boolean status;
		int statusCode;
		String nextURL;
		String otp;
		String Username;
		String VillageID;
		String StaffID;
		String Logintime;
		int UserRole;
		String Id,	FirstName,	MiddleName,	LastName,	Level,	Email;
		List<DeptDataModel> DeptDataList;
		List<ReportModel> mReportModelList;
		List<EmpDetList> EmpRoleData;
		List<DistModel> mDistList;
		List<TalukModel> mTalukList;
		List<HobliModel> mHobliList;
		List<VillageModel> mVillageList;
		
		List<DSRMainModel> mDSRMainList;
		List<DSRSubModel> mDSRSubList;
		List<StrElementModelClass> mStrElementList;
		List<VillageModel> mUoMList;
		List<RateOfDepreciationModel> mRateOfDepr;
		List<TextEntryModel> mTextEntryList;
		GenInfoReport mGenInfoReport;
		List<FmReportByGenInfoModel> FmList;
		//List<FieldMeasurementsModel> mFieldMeasurementList;
		List<GenInfoReport> mGenInfoReportModel;
		List<UserDetailsModel> mUserList;
		List<UserRolesModel> mUserRolesList;
		List<FloorPlanElementsModel> structureElements;
		RateAbstractModel estimatedAbstractModel;
		List<SubDsrDetailsModel> subDsrDetailsWithFM;
		List<DocumentModel> mDocumentList;
		List<LatestFMModel> mFieldMeasurementList;
		List<GstValueModel> mGSTList;
		List<String> healthCheckArgs;


		
		public List<LatestFMModel> getmLatestFMModel() {
			return mFieldMeasurementList;
		}


		public void setmLatestFMModel(List<LatestFMModel> mFieldMeasurementList) {
			this.mFieldMeasurementList = mFieldMeasurementList;
		}


		public APIResponseModel() {
			super();
		}
		

		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public boolean isStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			this.status = status;
		}
		
		public String getNextURL() {
			return nextURL;
		}
		public void setNextURL(String nextURL) {
			this.nextURL = nextURL;
		}
		
		public int getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}

		public String getVillageID() {
			return VillageID;
		}

		public void setVillageID(String villageID) {
			VillageID = villageID;
		}

	

		public String getOtp() {
			return otp;
		}

		public void setOtp(String otp) {
			this.otp = otp;
		}

		public String getUsername() {
			return Username;
		}

		public void setUsername(String username) {
			Username = username;
		}

		public List<DeptDataModel> getDeptDataList() {
			return DeptDataList;
		}

		public void setDeptDataList(List<DeptDataModel> deptDataList) {
			DeptDataList = deptDataList;
		}

		public String getStaffID() {
			return StaffID;
		}

		public void setStaffID(String staffID) {
			StaffID = staffID;
		}

		

		public String getLogintime() {
			return Logintime;
		}


		public void setLogintime(String logintime) {
			Logintime = logintime;
		}


		


		

		public APIResponseModel(String message, boolean status, int statusCode, String nextURL, String otp,
				String username, String villageID, String staffID, String logintime, int userRole, String id,
				String firstName, String middleName, String lastName, String level, String email,
				List<DeptDataModel> deptDataList, List<ReportModel> mReportModelList, List<EmpDetList> empRoleData,
				List<DistModel> mDistList, List<TalukModel> mTalukList, List<HobliModel> mHobliList,
				List<VillageModel> mVillageList, List<DSRMainModel> mDSRMainList, List<DSRSubModel> mDSRSubList,
				List<StrElementModelClass> mStrElementList, List<VillageModel> mUoMList,
				List<RateOfDepreciationModel> mRateOfDepr, List<TextEntryModel> mTextEntryList,
				GenInfoReport mGenInfoReport, List<FmReportByGenInfoModel> fmList,
				List<GenInfoReport> mGenInfoReportModel, List<UserDetailsModel> mUserList,
				List<UserRolesModel> mUserRolesList, List<FloorPlanElementsModel> structureElements,
				RateAbstractModel estimatedAbstractModel, List<SubDsrDetailsModel> subDsrDetailsWithFM,
				List<DocumentModel> mDocumentList, List<LatestFMModel> mFieldMeasurementList) {
			super();
			this.message = message;
			this.status = status;
			this.statusCode = statusCode;
			this.nextURL = nextURL;
			this.otp = otp;
			Username = username;
			VillageID = villageID;
			StaffID = staffID;
			Logintime = logintime;
			UserRole = userRole;
			Id = id;
			FirstName = firstName;
			MiddleName = middleName;
			LastName = lastName;
			Level = level;
			Email = email;
			DeptDataList = deptDataList;
			this.mReportModelList = mReportModelList;
			EmpRoleData = empRoleData;
			this.mDistList = mDistList;
			this.mTalukList = mTalukList;
			this.mHobliList = mHobliList;
			this.mVillageList = mVillageList;
			this.mDSRMainList = mDSRMainList;
			this.mDSRSubList = mDSRSubList;
			this.mStrElementList = mStrElementList;
			this.mUoMList = mUoMList;
			this.mRateOfDepr = mRateOfDepr;
			this.mTextEntryList = mTextEntryList;
			this.mGenInfoReport = mGenInfoReport;
			FmList = fmList;
			this.mGenInfoReportModel = mGenInfoReportModel;
			this.mUserList = mUserList;
			this.mUserRolesList = mUserRolesList;
			this.structureElements = structureElements;
			this.estimatedAbstractModel = estimatedAbstractModel;
			this.subDsrDetailsWithFM = subDsrDetailsWithFM;
			this.mDocumentList = mDocumentList;
			this.mFieldMeasurementList = mFieldMeasurementList;
		}


		public List<ReportModel> getmReportModelList() {
			return mReportModelList;
		}


		public void setmReportModelList(List<ReportModel> mReportModelList) {
			this.mReportModelList = mReportModelList;
		}


		public List<EmpDetList> getEmpRoleData() {
			return EmpRoleData;
		}


		public void setEmpRoleData(List<EmpDetList> empRoleData) {
			EmpRoleData = empRoleData;
		}


		public List<DistModel> getmDistList() {
			return mDistList;
		}


		public void setmDistList(List<DistModel> mDistList) {
			this.mDistList = mDistList;
		}


		public List<TalukModel> getmTalukList() {
			return mTalukList;
		}


		public void setmTalukList(List<TalukModel> mTalukList) {
			this.mTalukList = mTalukList;
		}


		public List<HobliModel> getmHobliList() {
			return mHobliList;
		}


		public void setmHobliList(List<HobliModel> mHobliList) {
			this.mHobliList = mHobliList;
		}


		public List<VillageModel> getmVillageList() {
			return mVillageList;
		}


		public void setmVillageList(List<VillageModel> mVillageList) {
			this.mVillageList = mVillageList;
		}


		public int getUserRole() {
			return UserRole;
		}


		public void setUserRole(int userRole) {
			UserRole = userRole;
		}


		public String getId() {
			return Id;
		}


		public void setId(String id) {
			Id = id;
		}


		public String getFirstName() {
			return FirstName;
		}


		public void setFirstName(String firstName) {
			FirstName = firstName;
		}


		public String getMiddleName() {
			return MiddleName;
		}


		public void setMiddleName(String middleName) {
			MiddleName = middleName;
		}


		public String getLastName() {
			return LastName;
		}


		public void setLastName(String lastName) {
			LastName = lastName;
		}


		public String getLevel() {
			return Level;
		}


		public void setLevel(String level) {
			Level = level;
		}


		public String getEmail() {
			return Email;
		}


		public void setEmail(String email) {
			Email = email;
		}


		public List<DSRMainModel> getmDSRMainList() {
			return mDSRMainList;
		}


		public void setmDSRMainList(List<DSRMainModel> mDSRMainList) {
			this.mDSRMainList = mDSRMainList;
		}


		public List<DSRSubModel> getmDSRSubList() {
			return mDSRSubList;
		}


		public void setmDSRSubList(List<DSRSubModel> mDSRSubList) {
			this.mDSRSubList = mDSRSubList;
		}


		public List<StrElementModelClass> getmStrElementList() {
			return mStrElementList;
		}


		public void setmStrElementList(List<StrElementModelClass> mStrElementList) {
			this.mStrElementList = mStrElementList;
		}


		public List<VillageModel> getmUoMList() {
			return mUoMList;
		}


		public void setmUoMList(List<VillageModel> mUoMList) {
			this.mUoMList = mUoMList;
		}


		public List<RateOfDepreciationModel> getmRateOfDepr() {
			return mRateOfDepr;
		}


		public void setmRateOfDepr(List<RateOfDepreciationModel> mRateOfDepr) {
			this.mRateOfDepr = mRateOfDepr;
		}


		public List<TextEntryModel> getmTextEntryList() {
			return mTextEntryList;
		}


		public void setmTextEntryList(List<TextEntryModel> mTextEntryList) {
			this.mTextEntryList = mTextEntryList;
		}


		public GenInfoReport getmGenInfoReport() {
			return mGenInfoReport;
		}


		public void setmGenInfoReport(GenInfoReport mGenInfoReport) {
			this.mGenInfoReport = mGenInfoReport;
		}


		public List<FmReportByGenInfoModel> getFmList() {
			return FmList;
		}


		public void setFmList(List<FmReportByGenInfoModel> fmList) {
			FmList = fmList;
		}


//		public List<FieldMeasurementsModel> getmFieldMeasurementList() {
//			return mFieldMeasurementList;
//		}
//
//
//		public void setmFieldMeasurementList(List<FieldMeasurementsModel> mFieldMeasurementList) {
//			this.mFieldMeasurementList = mFieldMeasurementList;
//		}


		public List<GenInfoReport> getmGenInfoReportModel() {
			return mGenInfoReportModel;
		}


		public void setmGenInfoReportModel(List<GenInfoReport> mGenInfoReportModel) {
			this.mGenInfoReportModel = mGenInfoReportModel;
		}


		public List<UserDetailsModel> getmUserList() {
			return mUserList;
		}


		public void setmUserList(List<UserDetailsModel> mUserList) {
			this.mUserList = mUserList;
		}


		public List<UserRolesModel> getmUserRolesList() {
			return mUserRolesList;
		}


		public void setmUserRolesList(List<UserRolesModel> mUserRolesList) {
			this.mUserRolesList = mUserRolesList;
		}


		public List<FloorPlanElementsModel> getStructureElements() {
			return structureElements;
		}


		public void setStructureElements(List<FloorPlanElementsModel> structureElements) {
			this.structureElements = structureElements;
		}


		public List<SubDsrDetailsModel> getSubDsrDetailsWithFM() {
			return subDsrDetailsWithFM;
		}


		public void setSubDsrDetailsWithFM(List<SubDsrDetailsModel> subDsrDetailsWithFM) {
			this.subDsrDetailsWithFM = subDsrDetailsWithFM;
		}


		public RateAbstractModel getEstimatedAbstractModel() {
			return estimatedAbstractModel;
		}


		public void setEstimatedAbstractModel(RateAbstractModel estimatedAbstractModel) {
			this.estimatedAbstractModel = estimatedAbstractModel;
		}


		public List<DocumentModel> getmDocumentList() {
			return mDocumentList;
		}


		public void setmDocumentList(List<DocumentModel> mDocumentList) {
			this.mDocumentList = mDocumentList;
		}


		public List<GstValueModel> getmGSTList() {
			return mGSTList;
		}


		public void setmGSTList(List<GstValueModel> mGSTList) {
			this.mGSTList = mGSTList;
		}


		public List<String> getHealthCheckArgs() {
			return healthCheckArgs;
		}


		public void setHealthCheckArgs(List<String> healthCheckArgs) {
			this.healthCheckArgs = healthCheckArgs;
		}
		
		

		
		
	}
