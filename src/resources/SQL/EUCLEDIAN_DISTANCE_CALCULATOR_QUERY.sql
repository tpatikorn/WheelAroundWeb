SELECT g.garage_id, g.dist_in_km, g.ADDRESS, g.dist_in_miles, g.GARAGE_NAME, g.OWNED_BY
FROM ( SELECT
            g.*,
            sqrt(power((g.LATTITUDE - z.lat) * (110.574),2) + 
            power((g.longitude - z.lng) * (111.320 * cos(g.LATTITUDE)),2)) AS dist_in_km,
            0.621371*sqrt(power((g.LATTITUDE - z.lat) * (110.574),2) + 
            power((g.longitude - z.lng) * (111.320 * cos(g.LATTITUDE)),2)) AS dist_in_miles
            FROM
            GARAGE_LOCATIONS g,
            (
                SELECT
                    z.LATTITUDE AS lat,
                    z.LONGITUDE AS lng
                FROM
                    ZIP_CODE_MAPPING z
                WHERE
                    z.Zip_CODE = ? ) z ) g
	WHERE dist_in_miles <  ?