package com.wheelAround.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wheelAround.model.Vehicles;

public class VehicleMapper implements RowMapper {

	@Override
	public Vehicles mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Vehicles veh = new Vehicles();
		
		veh.setTypeId(rs.getString("TYPE_ID"));  
		veh.setVID(rs.getString("VID"));  
		veh.setMaintainance_by(rs.getString("MAINTENANCE_BY"));  
		veh.setOwnerId(rs.getString("OWNER_ID"));  
		veh.setGarageId(rs.getString("GARAGE_ID"));  
		veh.setModelName(rs.getString("MODEL_NAME"));  
		  
		return veh;
	}

}
