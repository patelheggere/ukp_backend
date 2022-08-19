package sconti.ukp;

import java.util.List;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.google.gson.JsonElement;

import Model.APIResponseModel;

public class Impobj {
	Dbresource dbrs = new Dbresource();

	Logger logger = Logger.getLogger("Impobj");

	
	public List<String> GetDBNames(String query,  int value) {
		return dbrs.getDbs(query, value);
	}
	
	public List<String> GetTbNames(String dbname, String column, int value) {
		return dbrs.getTables(dbname, column, value);
	}
	
	public List<String> GetColumnsNames(String dbName, String tblName, int value) {
		return dbrs.getColmns(dbName, tblName, value);
	}
	
	
	
	public List<DataModel> GetData(String dbName, String tblName, String data, int value) {
		return dbrs.getData(dbName, tblName, data, value);
	}
	public String CreateDB(String data) {
		return dbrs.CreateDB(data);
	}
	
	public String getBaseURL(String dbName, int value) {
		return dbrs.getBaseURL(dbName, value);
	}
	public String healthcheck(String[] data) {
		return dbrs.healthcheck(data);
	}
	public String healthcheckV2(String[] data, String f) {
		return dbrs.healthcheckV2(data, f);
	}

	public APIResponseModel verifyUser(String uname, String pwd) {
		return dbrs.verifyUser(uname, pwd);
	}

	public APIResponseModel GetAllDistrict() {
		// TODO Auto-generated method stub
		return dbrs.GetDistricts();
	}

	public APIResponseModel GetTalukByDist(String did) {
		// TODO Auto-generated method stub
		return dbrs.GetTalukByDist(did);
	}

	public APIResponseModel GetHobliByTaluk(String tid) {
		return dbrs.GetHobliByTaluk(tid);
	}

	public APIResponseModel GetVillageByHobli(String hid) {
		return dbrs.GetVillageByByHobli(hid);
	}

	public APIResponseModel InsertDSRMst(String data) {
		return dbrs.InsertDSRMst(data);

	}
	
	public APIResponseModel InsertStrTypeMst(String data) {
		return dbrs.InsertStrTypeMst(data);

	}
	
	

	public APIResponseModel UserRegistration(String data) {
		return dbrs.InsertUSers(data);
	}

	public APIResponseModel GetVillageByTaluk(String tid) {
		return dbrs.GetVillageByTaluk(tid);
	}

	public APIResponseModel InsertDistrict(String data) {
		return dbrs.InsertDistrict(data);
	}
	
	public APIResponseModel InsertDsrMain(String data) {
		return dbrs.InsertDsrMain(data);
	}
		

	public APIResponseModel InsertDSRSub(String data) {
		return dbrs.InsertDSRSub(data);
	}
		
	
	

	
	public APIResponseModel InsertGeneralInfo(String data) {
		return dbrs.InsertGeneralInfo(data);
	}

	public APIResponseModel GetDsr() {
		return dbrs.GetDsr();
	}
	
	public APIResponseModel GetDsrOnly() {
		return dbrs.GetDsrOnly();
	}

	public APIResponseModel GetStrType() {
		return dbrs.GetStrType();
	}

	public APIResponseModel InsertStElementMst(String data) {
		return dbrs.InsertStElementMst(data);
	}

	public APIResponseModel GetStrElement() {
		return dbrs.GetStrElement();
	}
	
	public APIResponseModel GetFieldMeasuremntByGenInfoID(String genInfoID) {
		return dbrs.GetFieldMeasuremntByGenInfoID(genInfoID);
	}
	

	public APIResponseModel InsertUOM(String data) {
		return dbrs.InsertUOMMst(data);
	}

	public APIResponseModel GetUOM() {
		// TODO Auto-generated method stub
		return dbrs.GetUOM();
	}

	public APIResponseModel GetRateOfDepreciation() {
		return dbrs.GetRateOfDepreciation();
	}

	public APIResponseModel InsertStandRateDepreciation(String data) {
		return dbrs.InsertStandRateDepreciation(data);
	}
	
	public APIResponseModel importDsr(String data) {
		return dbrs.importDsr(data);
	}

	public APIResponseModel GetTextEntry(String userID) {
		// TODO Auto-generated method stub
		return dbrs.GetTextEntry(userID);
	}

	public APIResponseModel InsertTextEntry(String data) {
		// TODO Auto-generated method stub
		return dbrs.InsertTextEntry(data);
	}

	public APIResponseModel InsertFieldMeaurements(String data) {
		// TODO Auto-generated method stub
		return dbrs.InsertFieldMeaurements(data);
	}

	public APIResponseModel InsertTaluk(String data) {
		return dbrs.InsertTaluk(data);
	}

	public APIResponseModel InsertVillage(String data) {
		return dbrs.InsertVillage(data);
	}

	public APIResponseModel GetReport(String GenInfoID) {
		return dbrs.GetReport(GenInfoID);

	}
	public APIResponseModel GetReportWithElements(String GenInfoID) {
		return dbrs.GetReportWithElements(GenInfoID);

	}
	
	public APIResponseModel GetFloorElements(String GenInfoID) {
		return dbrs.GetFloorElements(GenInfoID);

	}
	
	public APIResponseModel DeleteData(String GenInfoID, int type) {
		return dbrs.DeleteData(GenInfoID, type);

	}

	public APIResponseModel GetAllGenInfo() {
		return dbrs.GetAllGenInfo();
	}

	public APIResponseModel GetAllUser() {
		return dbrs.GetAllUser();
	}

	public APIResponseModel GetAllRoles() {
		// TODO Auto-generated method stub
		return dbrs.GetAllRoles();
	}

	public APIResponseModel GetDsrCodeNames() {
		return dbrs.GetDsrCodeNames();
	}

	public APIResponseModel USP_GetMainDsrByDSRID(String dsrID) {
		return dbrs.USP_GetMainDsrByDSRID(dsrID);
	}

	public APIResponseModel USP_GetSubDsrByDSRMainID(String dsrID) {
		return dbrs.USP_GetSubDsrByDSRMainID(dsrID);
	}

	public APIResponseModel GetMainDsrByDSRNonDSR(String label, String dsrID) {
		// TODO Auto-generated method stub
		return dbrs.GetMainDsrByDSRNonDSR(label, dsrID);
	}

	public APIResponseModel GetAllGenInfoV2(String userid) {
		return dbrs.GetAllGenInfoV2(userid);
	}

	
	public APIResponseModel GetTextEntryByGenInfoID(String genInfoID) {
		// TODO Auto-generated method stub
	 		return dbrs.GetTextEntryByGenInfoID(genInfoID);
	}

	public APIResponseModel UpdatePassword(String data) {
		return dbrs.UpdatePassword(data);
	}

	public APIResponseModel UpdateDistrict(String data) {
		return dbrs.UpdateDistrict(data);
	}

	public APIResponseModel UpdateGenInfo(String data) {
		return dbrs.UpdateGenInfo(data);
	}

	public APIResponseModel GetReportWithElementsDsrDetails(String genInfoID) {
		// TODO Auto-generated method stub
		return dbrs.GetFieldMeasuremntByGenInfoIDWithDetails(genInfoID);
	}

	public APIResponseModel updateFileUploaded(String genInfoID, String fileType, String uploadedBy, FormDataContentDisposition fileDetail) {
		// TODO Auto-generated method stub
		return dbrs.updateFileUploaded(genInfoID, fileType, uploadedBy, fileDetail);
	}

	public APIResponseModel GetDocumentList(String iD, int type) {
		// TODO Auto-generated method stub
		return dbrs.GetDocumentList(iD, type);
	}
	
	public APIResponseModel  UpdateFieldMeasurments(String data) {
		return dbrs.UpdateFieldMeasurments(data);
	}

	public APIResponseModel UpdateTextEntry(String data) {
		// TODO Auto-generated method stub
		return dbrs.UpdateTextEntry(data);
	}

	public APIResponseModel UpdateDsrMain(String data) {
		// TODO Auto-generated method stub
		return dbrs.UpdateDsrMain(data);
	}

	public APIResponseModel UpdateDsrSub(String data) {
		// TODO Auto-generated method stub
		return dbrs.UpdateDsrSub(data);
	}

	public APIResponseModel GetGSTValues() {
		return dbrs.GetGSTValues();

	}

	public APIResponseModel InsertGST(String data) {
		return dbrs.InsertGST(data);
	}

	public APIResponseModel UpdateGST(String data) {
		return dbrs.UpdateGST(data);
	}
	
}
