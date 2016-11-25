create or replace FUNCTION CALCULATE_FULL_PRICE_JOURN(
    perHourPrice IN NUMBER,
    startDate    IN VARCHAR2 ,
    endDate      IN VARCHAR2)
  RETURN FLOAT
AS
  fullPrice FLOAT    := 0.0;
  totalSeconds FLOAT := 0.0;
  timeDiffEnd TIMESTAMP;
  timeDiffStart TIMESTAMP;
  
BEGIN
  timeDiffEnd     :=  TO_TIMESTAMP( endDate , 'MM/DD/YYYY HH24:MI:SS');-- - TO_TIMESTAMP(startDate, 'MM/DD/YYYY HH24:MI:SS');
  timeDiffStart  :=  TO_TIMESTAMP(startDate, 'MM/DD/YYYY HH24:MI:SS');
  totalSeconds := TO_NUMBER( ROUND (EXTRACT (DAY FROM (timeDiffEnd-timeDiffStart)) * 24 * 60 * 60 + EXTRACT (HOUR FROM (timeDiffEnd-timeDiffStart)) * 60 * 60 + EXTRACT (MINUTE FROM (timeDiffEnd-timeDiffStart)) * 60 + EXTRACT (SECOND FROM (timeDiffEnd-timeDiffStart))),'999999999.9');
  fullPrice    := ROUND (totalSeconds / (60 * 60), 0) * perHourPrice;
  RETURN fullPrice;
  COMMIT;
END;