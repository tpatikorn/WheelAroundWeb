package com.wheelAround.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.wheelAround.model.FeatureList;
import com.wheelAround.model.VehicleTpeListBean;

public interface UserDao {
	
	public String isValidUser(String username, String password) throws  Exception;
	public boolean newCustomerRegistration(RegistrationBean regValues) throws SQLException;
	public Map<String,String> getMapOfAllPossibleVehicleForGivenZipAndDistance(String zipCode,int distance, List<VehicleTpeListBean> list);
	
	public Map<String,String> getListOfAvailableVehicleTypes() throws SQLException;
	public List<FeatureList> getFeatureList(String vid) throws SQLException;
	public double calculateFinalAmount(List<String> features, String typeID) throws SQLException;
	public String reserveVehicleForCustomer(String cid, String vid, String startDate, String endDate,
			double amountPerHour) throws SQLException;
	

}
