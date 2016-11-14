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
	public List<String> getFeatureList() throws SQLException;
}
