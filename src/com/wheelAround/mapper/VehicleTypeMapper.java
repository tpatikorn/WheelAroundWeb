package com.wheelAround.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wheelAround.model.VehicleTypes;

public class VehicleTypeMapper implements RowMapper {

	@Override
	public VehicleTypes mapRow(ResultSet rs, int arg1) throws SQLException {
		VehicleTypes vType  = new VehicleTypes();
		
		vType.setTypeId(rs.getString("TYPE_ID"));  
		vType.setTypeName(rs.getString("TYPE_NAME"));  
		vType.setTypePrice(rs.getString("TYPE_PRICE"));  
		
		return vType;
	
	}

}
