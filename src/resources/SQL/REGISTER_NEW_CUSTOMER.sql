create or replace PROCEDURE REGISTER_NEW_CUSTOMER
(      customer_name IN VARCHAR2,
       customer_email IN VARCHAR2,
       customer_dob IN VARCHAR2,
       customer_username IN VARCHAR2,
       customer_license IN VARCHAR2,
       customer_password IN VARCHAR2,
       customer_cid IN VARCHAR2
)
IS
BEGIN
    INSERT INTO CUSTOMERS VALUES(customer_name,customer_email,customer_dob,customer_username,customer_license,customer_cid);
    INSERT INTO AUTHENTICATION VALUES(customer_password,customer_cid);
COMMIT;
END;