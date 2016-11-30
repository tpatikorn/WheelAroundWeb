package com.wheelAround.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wheelAround.model.dao.LoginBean;
import com.wheelAround.model.dao.RegistrationBean;

@Component
public class LoginDelegate
{

	@Autowired private UserService userService;

	public UserService getUserService()
	{
		return this.userService;
	}

	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

	public String isValidUser(String username, String password) throws Exception
	{
	    return userService.isValidUser(username, password);
	}
	
	public boolean registerNewUser(RegistrationBean regValues) throws SQLException
	{
	    return userService.registerNewUser(regValues);
	}
	
	public Map<String, String> getValuesList(LoginBean beanValues) throws SQLException
	{
	    return userService.getMapOfAllPossibleVehicleForGivenZipAndDistance(beanValues);
	}
	
	public Map<String, String> getTypeList() throws SQLException
	{
	    return userService.getListOfAvailableVehicleTypes();
	}
	
	public List<FeatureList> getFeatureList(String vid) throws SQLException
	{
	    return userService.getFeatureList(vid);
	}
	
	public List<Ratings> getRatings(String vid) throws SQLException
	{
	    return userService.getRatings(vid);
	}
	
	public List<RatingsListBean> getRatingsListBean(String vid) throws SQLException
	{
	    return userService.getRatingsListBean(vid);
	}
	
	public double calculateFinalAmount(List<String> fids, String typeId) throws SQLException
	{
	    return userService.calculateFinalAmount(fids, typeId);
	}
	
	public String reserveVehicleForCustomer(String cid, String vid,String startDate,String endDate,double amountPerHour ) throws SQLException
	{
	    return userService.reserveVehicleForCustomer(cid,vid,startDate,endDate,amountPerHour );
	}
	
	public String inserRatings(String vid, String rating, String cid, String rating_time, String comment)
			throws SQLException {
		return userService.inserRatings(vid, rating, cid, rating_time, comment);
	}
}
