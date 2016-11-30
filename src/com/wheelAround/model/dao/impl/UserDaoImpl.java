package com.wheelAround.model.dao.impl;

import java.security.MessageDigest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.wheelAround.mapper.FeatureMapper;
import com.wheelAround.mapper.RatingsListBeanMapper;
import com.wheelAround.mapper.RatingsMapper;
import com.wheelAround.mapper.SQLQUERYCONSTANTS;
import com.wheelAround.mapper.VehicleMapper;
import com.wheelAround.model.FeatureList;
import com.wheelAround.model.Ratings;
import com.wheelAround.model.RatingsListBean;
import com.wheelAround.model.VehicleTpeListBean;
import com.wheelAround.model.Vehicles;
import com.wheelAround.model.dao.RegistrationBean;
import com.wheelAround.model.dao.UserDao;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;



@Component
public class UserDaoImpl implements UserDao {
	
	@Autowired private DataSource dataSource ;
	
	
	CallableStatement callStmt;

	public DataSource getDataSource()
	{
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	public boolean newCustomerRegistration(RegistrationBean regBean) throws SQLException{
		
		callStmt = dataSource.getConnection().prepareCall(SQLQUERYCONSTANTS.REGISTER_NEW_CUSTOMER);
		callStmt.setString(1, regBean.getFname());
		callStmt.setString(2, regBean.getLname());
		callStmt.setString(3, regBean.getEmail());
		callStmt.setString(4, regBean.getPhoneNumber());
		callStmt.setString(5, regBean.getUserName());
		callStmt.setString(6, regBean.getLicencse());
		try {
			callStmt.setString(7, hashGenerateCIDAndPasswordForUser(regBean.getPassword()));
			callStmt.setString(8, hashGenerateCIDAndPasswordForUser(regBean.getLicencse()));
		} catch (Exception e) {
			System.out.println("Error while hashing the fields");
		}

		callStmt.execute();
		return true;
		
		
	}
	
	@Override
	public double calculateFinalAmount(List<String> features,String typeID) throws SQLException{
	
		Connection con = dataSource.getConnection();
		ArrayDescriptor des = ArrayDescriptor.createDescriptor(SQLQUERYCONSTANTS.VEH_FEA_PRICE_VALUES, con);
		String[] fea = features.toArray(new String[features.size()]);
		ARRAY array_to_pass = new ARRAY(des,con,fea);
		
		callStmt = dataSource.getConnection().prepareCall(SQLQUERYCONSTANTS.CAL_FULL_PRICE);
		callStmt.setArray(2, array_to_pass);
		callStmt.setInt(3, features.size());
		callStmt.setString(4, typeID);
		callStmt.registerOutParameter(1, Types.NUMERIC);
		callStmt.execute();
		
		double results = callStmt.getFloat(1);
		results =Double.parseDouble(new DecimalFormat("##.#").format(results));
		
		return results;
		
	}
	
	public Map<String,String> getMapOfAllPossibleVehicleForGivenZipAndDistance(String zipCode,int distance,List<VehicleTpeListBean> vtYpeBeanList){
		Map<String,String> finalValuesMap = new HashMap<String,String>();
		List<String> garageIdsAndName  = getGarageIdsForAvailableDistanceandZip(zipCode,distance);
		List<String> garageIds = new ArrayList<String>();
		List<String> typeIds = new ArrayList<String>();
		
		Map<String,String> mapping2 = new HashMap<String,String>();
		
		for(VehicleTpeListBean vty : vtYpeBeanList){
			if(vty.getTypeName() != null){
				mapping2.put(vty.getTypeId(), vty.getTypeName()+"~~"+vty.getTypePrice());
				typeIds.add(vty.getTypeId());
			}
		}
		
		Map<String,String> mapping = new HashMap<String,String>();
		
		
		for(String garageId : garageIdsAndName){
			
			garageIds.add(garageId.split("~~")[0]);
			
			mapping.put(garageId.split("~~")[0], garageId.split("~~")[2] +"~~" + garageId.split("~~")[1]);
		}
		
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    String sql = SQLQUERYCONSTANTS.VEHICLE_SELECTION;
	    
	    Map<String, List<String>> fullMap = new HashMap<String, List<String>>();
	    fullMap.put("garageIds", garageIds);
	    fullMap.put("typeIds", typeIds);
	    
	    List<Vehicles> result = jdbcTemplate.query(sql, fullMap, new VehicleMapper());
	   
	    for(Vehicles v : result){
	    	String finalValues = v.getModelName() + "~~" + mapping2.get(v.getTypeId())+ "~~" +mapping.get(v.getGarageId());
	    	finalValuesMap.put(v.getVID()+"@@"+v.getGarageId()+"@@"+v.getTypeId(), finalValues);
	    	
	    }
	    
		return finalValuesMap;
		
	}

	
	private List<String> getGarageIdsForAvailableDistanceandZip(String zipCode, int distance) {
		try {
			List<String> availableGarageId = new ArrayList<String>();	
			String selectSQL = SQLQUERYCONSTANTS.EUCLEDIAN_DIS_CAL;
			PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(selectSQL);
			preparedStatement.setString(1, zipCode);
			preparedStatement.setInt(2, distance);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String garageValues = rs.getString("GARAGE_ID") +"~~"+ rs.getString("ADDRESS")+"~~"+ rs.getString("GARAGE_NAME")+"~~"+ rs.getString("OWNED_BY");
				availableGarageId.add(garageValues);	
			}
			return availableGarageId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String hashGenerateCIDAndPasswordForUser(String data) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(data.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	public String isValidUser(String username, String password) throws Exception
	{
		callStmt = dataSource.getConnection().prepareCall(SQLQUERYCONSTANTS.VALIDATE_CUSTOMER);
		callStmt.setString(2, username);
		String hashedPwd= hashGenerateCIDAndPasswordForUser(password);
		callStmt.setString(3, hashedPwd);
		callStmt.registerOutParameter(1, Types.VARCHAR);
		callStmt.execute();
		
		String results = callStmt.getString(1);
		
		if(!results.equals("Login Failed. Please try again.")){
			return results;
		}else{
			return null;
		}
	}


	@Override
	public Map<String, String> getListOfAvailableVehicleTypes() throws SQLException {
		String selectSQL = SQLQUERYCONSTANTS.TYPE_SELECTION;
		Map<String,String> finalValuesMap = new HashMap<String,String>();
		PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(selectSQL);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			String garageValues = rs.getString("TYPE_ID") +"~~"+ rs.getString("TYPE_NAME")+"~~"+ rs.getString("TYPE_PRICE");
			finalValuesMap.put(rs.getString("TYPE_ID"),garageValues);	
		}
		return finalValuesMap;
	}

	@Override
	public List<FeatureList> getFeatureList(String vid) throws SQLException {
		List<String> featureList = new ArrayList<String>();
		List<String> featureListMapping = new ArrayList<String>();
		 String sqlFeaturesMapping = SQLQUERYCONSTANTS.VEHICLE_MAPPING_SELECTION;
		    
		 	PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlFeaturesMapping);
			preparedStatement.setString(1, vid);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				String feature = rs.getString("FID");	
				featureListMapping.add(feature);
			}
			
			NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			String sqlFeatures = SQLQUERYCONSTANTS.FEATURE_SELECTION;
			Map<String, List<String>> fullMap = new HashMap<String, List<String>>();
		    fullMap.put("fids", featureListMapping);
	
		    List<FeatureList> result = jdbcTemplate.query(sqlFeatures, fullMap, new FeatureMapper());
		    
			
		return result;
	}
	
	@Override
	public List<Ratings> getRatings(String vid) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sqlRatings = SQLQUERYCONSTANTS.RATINGS_SELECTION;
		List<Ratings> ratings = jdbcTemplate.query(sqlRatings, new Object[] {vid}, new RatingsMapper());
		for(Ratings rating: ratings) {
			System.out.println(rating.getRatingId());
		}
		return ratings;
	}
	
	@Override
	public List<RatingsListBean> getRatingsListBean(String vid) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sqlRatings = SQLQUERYCONSTANTS.RATINGS_SELECTION_FOR_UI;
		List<RatingsListBean> ratings = jdbcTemplate.query(sqlRatings, new Object[] {vid}, new RatingsListBeanMapper());
		for(RatingsListBean rating: ratings) {
			System.out.println(rating.getRatingId());
		}
		return ratings;
	}
	
	@Override
	public String reserveVehicleForCustomer(String cid, String vid, String startDate, String endDate,
			double amountPerHour) throws SQLException {
		
		callStmt = dataSource.getConnection().prepareCall(SQLQUERYCONSTANTS.CAL_FULL_PRICE_JOURN);
		callStmt.setDouble(2, amountPerHour);
		callStmt.setString(3, startDate);
		callStmt.setString(4, endDate);
		callStmt.registerOutParameter(1, Types.NUMERIC);
		callStmt.execute();

		double results = callStmt.getFloat(1);
		results = Double.parseDouble(new DecimalFormat("##.##").format(results));
	
		String keyCode = insertEntryTOResrvationsTable(cid,vid,startDate,endDate);

		return String.valueOf(results)+"@@@"+keyCode;

	}

	private String insertEntryTOResrvationsTable(String cid, String vid, String startDate, String endDate) throws SQLException {
		
		callStmt = dataSource.getConnection().prepareCall(SQLQUERYCONSTANTS.INSERT_RESERV_ENTRY);
		callStmt.setString(1, cid);
		callStmt.setString(2, vid);
		callStmt.setString(3, startDate);
		callStmt.setString(4, endDate);
		callStmt.registerOutParameter(5, Types.VARCHAR);
		callStmt.execute();
		
		return callStmt.getString(5);
	}

	@Override
	public String inserRatings(String vid, String rating, String cid, String rating_time, String comment)
			throws SQLException {
		callStmt = dataSource.getConnection().prepareCall(SQLQUERYCONSTANTS.INSERT_RATINGS);
		callStmt.setString(1, vid);
		callStmt.setString(2, rating);
		callStmt.setString(3, cid);
		callStmt.setString(4, rating_time);
		callStmt.setString(5, comment);
		callStmt.execute();
		return "okay";
	}
}