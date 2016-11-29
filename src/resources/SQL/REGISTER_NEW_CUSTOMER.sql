create or replace PROCEDURE REGISTER_NEW_CUSTOMER
(      customer_fname IN VARCHAR2,
       customer_lname IN VARCHAR2,
       customer_email IN VARCHAR2,
       customer_phoneNumber IN VARCHAR2,
       customer_username IN VARCHAR2,
       customer_license IN VARCHAR2,
       customer_password IN VARCHAR2,
       customer_cid IN VARCHAR2
)
IS
BEGIN
    INSERT INTO CUSTOMERS VALUES(customer_fname,customer_email,customer_username,customer_license,customer_cid,customer_lname,customer_phoneNumber);
    INSERT INTO AUTHENTICATION VALUES(customer_password,customer_cid);
COMMIT;
END;