package com.wheelAround.model.dao;

import java.util.Date;
import java.util.List;

import com.wheelAround.model.FeatureList;
import com.wheelAround.model.VehicleTpeListBean;
import com.wheelAround.model.VehiclesListBean;

public class LoginBean {
	private String username;
	private String password;
	private String cid;
	private VehiclesListBean vlBean;
	private double pricePerHour;
	
	public double getPricePerHour() {
		return pricePerHour;
	}
	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}
	public VehiclesListBean getVlBean() {
		return vlBean;
	}
	public void setVlBean(VehiclesListBean vlBean) {
		this.vlBean = vlBean;
	}
	private String rid;
	private String startDate;
	private String endDate;
	
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	private String zipCode;
	private int distance;
	
	private List<VehicleTpeListBean> vTypeBean;
	private List<VehiclesListBean> vAndPriceListBean;
	
	private List<FeatureList> featureList;
	
	public List<FeatureList> getFeatureList() {
		return featureList;
	}
	public void setFeatureList(List<FeatureList> featureList) {
		this.featureList = featureList;
	}
	public List<VehiclesListBean> getvAndPriceListBean() {
		return vAndPriceListBean;
	}
	public void setvAndPriceListBean(List<VehiclesListBean> vAndPriceListBean) {
		this.vAndPriceListBean = vAndPriceListBean;
	}
	public List<VehicleTpeListBean> getvTypeBean() {
		return vTypeBean;
	}
	public void setvTypeBean(List<VehicleTpeListBean> vTypeBean) {
		this.vTypeBean = vTypeBean;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
