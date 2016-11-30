package com.wheelAround.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wheelAround.model.dao.LoginBean;
import com.wheelAround.model.dao.RegistrationBean;
import com.wheelAround.model.dao.UserDao;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired private UserDao userDao;

	public UserDao getUserDao()
	{
		return this.userDao;
	}

	public void setUserDao(UserDao userDao)
	{
		this.userDao = userDao;
	}

	
	public String isValidUser(String username, String password) throws Exception
	{
		return userDao.isValidUser(username, password);
	}

	@Override
	public boolean registerNewUser(RegistrationBean regValues) throws SQLException {
		
		return userDao.newCustomerRegistration(regValues);
	}

	@Override
	public Map<String, String> getMapOfAllPossibleVehicleForGivenZipAndDistance(LoginBean bean) {
		return userDao.getMapOfAllPossibleVehicleForGivenZipAndDistance(bean.getZipCode(), bean.getDistance(),bean.getvTypeBean());
	}

	@Override
	public Map<String, String> getListOfAvailableVehicleTypes() throws SQLException {
		return userDao.getListOfAvailableVehicleTypes();
	}

	@Override
	public List<FeatureList> getFeatureList(String vid) throws SQLException {
		return userDao.getFeatureList(vid);
	}
	
	@Override
	public List<Ratings> getRatings(String vid) throws SQLException {
		return userDao.getRatings(vid);
	}


	@Override
	public double calculateFinalAmount(List<String> features, String typeID) throws SQLException {
		return userDao.calculateFinalAmount(features, typeID);
	}

	@Override
	public String reserveVehicleForCustomer(String cid, String vid, String startDate, String endDate,
			double amountPerHour) throws SQLException {
		return userDao.reserveVehicleForCustomer(cid,vid,startDate,endDate,amountPerHour );
	}

	@Override
	public List<RatingsListBean> getRatingsListBean(String vid) throws SQLException {
		return userDao.getRatingsListBean(vid);
	}

	@Override
	public String inserRatings(String vid, String rating, String cid, String rating_time, String comment)
			throws SQLException {
		return userDao.inserRatings(vid, cid, rating, comment, rating_time);
	}
}
