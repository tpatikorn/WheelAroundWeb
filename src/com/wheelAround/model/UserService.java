package com.wheelAround.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.wheelAround.model.dao.LoginBean;
import com.wheelAround.model.dao.RegistrationBean;

/**
 * @author CENTAUR
 *
 */
public interface UserService
{
	public String isValidUser(String username, String password) throws SQLException, Exception;
	
	public boolean registerNewUser(RegistrationBean regValues) throws SQLException;
	public Map<String,String> getListOfAvailableVehicleTypes() throws SQLException;
	public Map<String,String> getMapOfAllPossibleVehicleForGivenZipAndDistance(LoginBean bean);
	

	public List<FeatureList> getFeatureList(String vid) throws SQLException;
	public double calculateFinalAmount(List<String> fids,String typeID) throws SQLException;

	public String reserveVehicleForCustomer(String cid, String vid, String startDate, String endDate, double amountPerHour) throws SQLException;

	public List<Ratings> getRatings(String vid) throws SQLException;

	public List<RatingsListBean> getRatingsListBean(String vid) throws SQLException;
	
	public String inserRatings(String vid, String rating, String cid, String rating_time, String comment)
			throws SQLException;
}
