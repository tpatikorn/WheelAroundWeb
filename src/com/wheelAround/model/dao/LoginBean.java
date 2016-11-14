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
	
	
	private String rid;
	private Date startDate;
	private Date endDate;
	
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
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
