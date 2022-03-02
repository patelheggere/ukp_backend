package sconti.ukp;

public class RateAbstractModel {
	public double EstimatedAmount = 0;
	public double gst=0.12;
	public double dep = 0.25;
	public double payableAmount = 0;
	public double getEstimatedAmount() {
		return EstimatedAmount;
	}
	public void setEstimatedAmount(double estimatedAmount) {
		EstimatedAmount = estimatedAmount;
	}
	public double getGst() {
		return gst;
	}
	public void setGst(double gst) {
		this.gst = gst;
	}
	public double getDep() {
		return dep;
	}
	public void setDep(double dep) {
		this.dep = dep;
	}
	public double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(double payableAmount) {
		this.payableAmount = payableAmount;
	}
	
	
}
