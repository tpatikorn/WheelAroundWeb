package com.wheelAround.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wheelAround.model.FeatureList;

public class FeatureMapper implements RowMapper {

	@Override
	public FeatureList mapRow(ResultSet rs, int arg1) throws SQLException {
		
		FeatureList veh = new FeatureList();
		
		veh.setFeatureName(rs.getString("FEATURE_NAME"));  
		veh.setFeatureId(rs.getString("FID"));  
		veh.setFeaturePrice(rs.getString("FEATURE_PRICE"));  

		return veh;
	}
}
