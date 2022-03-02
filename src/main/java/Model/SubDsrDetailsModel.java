package Model;

import java.util.List;

public class SubDsrDetailsModel {
	public String DsrMainId,	SubDSrCode,	SubDsrDescription,	SubDsrRate,	DsrMainDescription;
	public double mTotalQuantity, mAmount;
	public List<FieldMeasurementsModel> mFmList;
	public String getDsrMainId() {
		return DsrMainId;
	}
	public void setDsrMainId(String dsrMainId) {
		DsrMainId = dsrMainId;
	}
	public String getSubDSrCode() {
		return SubDSrCode;
	}
	public void setSubDSrCode(String subDSrCode) {
		SubDSrCode = subDSrCode;
	}
	public String getSubDsrDescription() {
		return SubDsrDescription;
	}
	public void setSubDsrDescription(String subDsrDescription) {
		SubDsrDescription = subDsrDescription;
	}
	public String getSubDsrRate() {
		return SubDsrRate;
	}
	public void setSubDsrRate(String subDsrRate) {
		SubDsrRate = subDsrRate;
	}
	public String getDsrMainDescription() {
		return DsrMainDescription;
	}
	public void setDsrMainDescription(String dsrMainDescription) {
		DsrMainDescription = dsrMainDescription;
	}
	public double getmTotalQuantity() {
		return mTotalQuantity;
	}
	public void setmTotalQuantity(double mTotalQuantity) {
		this.mTotalQuantity = mTotalQuantity;
	}
	public double getmAmount() {
		return mAmount;
	}
	public void setmAmount(double mAmount) {
		this.mAmount = mAmount;
	}
	public List<FieldMeasurementsModel> getmFmList() {
		return mFmList;
	}
	public void setmFmList(List<FieldMeasurementsModel> mFmList) {
		this.mFmList = mFmList;
	}
	
	
}
