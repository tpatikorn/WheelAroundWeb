package com.wheelAround.mapper;

public class SQLQUERYCONSTANTS {
	
public static final String REGISTER_NEW_CUSTOMER = "{call REGISTER_NEW_CUSTOMER (?,?,?,?,?,?,?,?)}";

public static final String VEH_FEA_PRICE_VALUES = "VEH_FEA_PRICE_VALUES";

public static final String CAL_FULL_PRICE = "{?=call CALCULATE_FULL_PRICE (?,?,?)}";

public static final String VALIDATE_CUSTOMER = "{?=call VALIDATECUSTOMER (?,?)}";

public static final String CAL_FULL_PRICE_JOURN = "{?=call CALCULATE_FULL_PRICE_JOURN (?,?,?)}";

public static final String INSERT_RESERV_ENTRY = "{call INSERT_RESERV_ENTRY (?,?,?,?,?)}";

public static final String INSERT_RATINGS = "{call INSERT_RATINGS (?,?,?,?,?)}";

public static final String VEHICLE_SELECTION = "select * from VEHICLES where GARAGE_ID in (:garageIds) and TYPE_ID in (:typeIds)";

public static final String EUCLEDIAN_DIS_CAL = "SELECT g.garage_id, g.dist_in_km, g.ADDRESS, g.dist_in_miles, g.GARAGE_NAME, g.OWNED_BY FROM ( SELECT g.*, sqrt(power((g.LATTITUDE - z.lat) * (110.574),2) + power((g.longitude - z.lng) * "
					+ "(111.320 * cos(g.LATTITUDE)),2)) AS dist_in_km, 0.621371*sqrt(power((g.LATTITUDE - z.lat) * (110.574),2) + "
					+ "power((g.longitude - z.lng) * (111.320 * cos(g.LATTITUDE)),2)) AS dist_in_miles FROM GARAGE_LOCATIONS g, (SELECT z.LATTITUDE AS lat,z.LONGITUDE AS lng FROM ZIP_CODE_MAPPING z WHERE z.Zip_CODE = ? ) z )"
					+ "g WHERE dist_in_miles < ?";

public static final String TYPE_SELECTION = "SELECT * from TYPE";

public static final String VEHICLE_MAPPING_SELECTION = "select * from VEHICLE_FEATURE_MAPPING where VID  = ?";

public static final String RATINGS_SELECTION = "select * from RATINGS where VID  = ?";

public static final String RATINGS_SELECTION_FOR_UI = "select RATINGS.*, CUSTOMERS.FIRST_NAME, CUSTOMERS.LAST_NAME, CUSTOMERS.USERNAME from RATINGS inner join CUSTOMERS on RATINGS.CID = CUSTOMERS.CID where VID  = ?";

public static final String FEATURE_SELECTION = "select * from FEATURES where FID in (:fids)";

}
