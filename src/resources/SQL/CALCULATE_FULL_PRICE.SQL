create or replace FUNCTION CALCULATE_FULL_PRICE (priceValues IN  VEH_FEA_PRICE_VALUES, len  IN NUMBER, typeID IN VARCHAR2)
RETURN FLOAT AS
   fullPrice    FLOAT := 0.0;
BEGIN
   FOR i IN 1 .. len
   LOOP
   FOR TEMP_RECR IN (Select FEATURE_PRICE from FEATURES where FID = priceValues(i))
    LOOP
   fullPrice :=  fullPrice + TO_NUMBER(TEMP_RECR.FEATURE_PRICE, '9.9');
  dbms_output.put_line(fullPrice);
  END LOOP;
   END LOOP;
   
   FOR TEMP_RECR_TID IN (Select TYPE_PRICE from TYPE where TYPE_ID = typeID)
    LOOP
    fullPrice := fullPrice + TO_NUMBER(TEMP_RECR_TID.TYPE_PRICE, '9.9');
     END LOOP;
     
   return fullPrice;
   COMMIT;
END;