package com.wheelAround.model;

public class FeatureList {
	
	private String featureName;
	private String featurePrice;
	private String featureId;
	private boolean checkBoxForVehicle = false;
	
	public boolean isCheckBoxForVehicle() {
		return checkBoxForVehicle;
	}
	public void setCheckBoxForVehicle(boolean checkBoxForVehicle) {
		this.checkBoxForVehicle = checkBoxForVehicle;
	}
	
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	public String getFeaturePrice() {
		return featurePrice;
	}
	public void setFeaturePrice(String featurePrice) {
		this.featurePrice = featurePrice;
	}
	public String getFeatureId() {
		return featureId;
	}
	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}

}
