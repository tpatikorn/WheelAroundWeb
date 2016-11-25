create or replace PROCEDURE INSERT_RESERV_ENTRY(
       in_CID IN RESERVATIONS.CID%TYPE,
       in_VID IN RESERVATIONS.VID%TYPE,
		   in_startDate IN RESERVATIONS.RESERVATION_START%TYPE,
       in_endDate IN RESERVATIONS.RESERVATION_END%TYPE,
       out_key OUT VARCHAR
)
IS

carKeyCode VARCHAR(500);

BEGIN
    
    for i in 1..25 loop
        carKeyCode := carKeyCode || dbms_random.string(
            case when dbms_random.value(0, 1) < 0.5 then 'l' else 'x' end, 1);
    end loop;
     out_key := carKeyCode ;
    INSERT INTO RESERVATIONS VALUES(in_CID,in_VID,in_startDate,in_endDate,carKeyCode,RESERVATION_SEQUENCE.NEXTVAL);
  
COMMIT;
END;