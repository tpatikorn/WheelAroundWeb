package com.wheelAround.model;

public class VehiclesListBean {
	private String modelName;
	private String modelType;
	private String garageName;
	private String garageLocation;
	private String basePrice;
	private String keysForVehicles;
	private boolean checkBoxForVehicle = false;
	
	public boolean isCheckBoxForVehicle() {
		return checkBoxForVehicle;
	}
	public void setCheckBoxForVehicle(boolean checkBoxForVehicle) {
		this.checkBoxForVehicle = checkBoxForVehicle;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getGarageName() {
		return garageName;
	}
	public void setGarageName(String garageName) {
		this.garageName = garageName;
	}
	public String getGarageLocation() {
		return garageLocation;
	}
	public void setGarageLocation(String garageLocation) {
		this.garageLocation = garageLocation;
	}
	public String getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}
	public String getKeysForVehicles() {
		return keysForVehicles;
	}
	public void setKeysForVehicles(String keysForVehicles) {
		this.keysForVehicles = keysForVehicles;
	}

}
