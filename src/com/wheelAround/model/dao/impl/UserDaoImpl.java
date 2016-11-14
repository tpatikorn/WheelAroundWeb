package com.wheelAround.model.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.wheelAround.mapper.VehicleMapper;
import com.wheelAround.mapper.VehicleTypeMapper;
import com.wheelAround.model.VehicleTpeListBean;
import com.wheelAround.model.VehicleTypes;
import com.wheelAround.model.Vehicles;
import com.wheelAround.model.dao.RegistrationBean;
import com.wheelAround.model.dao.UserDao;



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
		
		callStmt = dataSource.getConnection().prepareCall("{call REGISTER_NEW_CUSTOMER (?,?,?,?,?,?,?,?)}");
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
	    String sql = "select * from VEHICLES where GARAGE_ID in (:garageIds) and TYPE_ID in (:typeIds)";
	    
	    Map<String, List<String>> fullMap = new HashMap<String, List<String>>();
	    fullMap.put("garageIds", garageIds);
	    fullMap.put("typeIds", typeIds);
	    
	    List<Vehicles> result = jdbcTemplate.query(sql, fullMap, new VehicleMapper());
	   
	    for(Vehicles v : result){
	    	String finalValues = v.getModelName() + "~~" + mapping2.get(v.getTypeId()) + "~~" + mapping.get(v.getGarageId());
	    	finalValuesMap.put(v.getVID()+"@@"+v.getGarageId()+"@@"+v.getTypeId(), finalValues);
	    	
	    }
	    
		return finalValuesMap;
		
	}

	
	private List<String> getGarageIdsForAvailableDistanceandZip(String zipCode, int distance) {
		try {
			String s            = new String();
	        StringBuffer sb = new StringBuffer();
	        List<String> availableGarageId = new ArrayList<>();	
	        File file = new File("C:/Neeraj/Projects/WAWorkspace/wheelAroundWeb/src/resources/SQL/EUCLEDIAN_DISTANCE_CALCULATOR_QUERY.sql");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			String selectSQL = "SELECT g.garage_id, g.dist_in_km, g.ADDRESS, g.dist_in_miles, g.GARAGE_NAME, g.OWNED_BY FROM ( SELECT g.*, sqrt(power((g.LATTITUDE - z.lat) * (110.574),2) + power((g.longitude - z.lng) * "
					+ "(111.320 * cos(g.LATTITUDE)),2)) AS dist_in_km, 0.621371*sqrt(power((g.LATTITUDE - z.lat) * (110.574),2) + "
					+ "power((g.longitude - z.lng) * (111.320 * cos(g.LATTITUDE)),2)) AS dist_in_miles FROM GARAGE_LOCATIONS g, (SELECT z.LATTITUDE AS lat,z.LONGITUDE AS lng FROM ZIP_CODE_MAPPING z WHERE z.Zip_CODE = ? ) z )"
					+ "g WHERE dist_in_miles < ?";
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
		callStmt = dataSource.getConnection().prepareCall("{?=call VALIDATECUSTOMER (?,?)}");
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
		String selectSQL = "SELECT * from TYPE";
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
	public List<String> getFeatureList() throws SQLException {
		List<String> featureList = new ArrayList<String>();
		 String sqlFeatures = "select * from FEATURES";
		    
		    PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlFeatures);
			ResultSet rsfeatures = preparedStatement.executeQuery();
			while (rsfeatures.next()) {
				String feature = rsfeatures.getString("FEATURE_NAME") +"~~"+ rsfeatures.getString("FID")+"~~"+ rsfeatures.getString("FEATURE_PRICE");	
				featureList.add(feature);
			}
		return featureList;
	}
}