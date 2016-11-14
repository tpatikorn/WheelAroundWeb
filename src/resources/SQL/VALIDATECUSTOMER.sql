create or replace FUNCTION validateCustomer(userName in varchar2, passwrd in varchar2)

  return VARCHAR2
  
  AS
  
  customerId VARCHAR2(50);
  customerValidCount NUMBER;
  
BEGIN

for c in (SELECT CID INTO customerId
    FROM CUSTOMERS
    where CUSTOMERS.USERNAME=userName)
    
    loop

    customerId := c.CID;
    
    SELECT COUNT(*) INTO 
    customerValidCount
    FROM AUTHENTICATION
    where  
    AUTHENTICATION.CID=customerId and
    AUTHENTICATION.HASHED_PW=passwrd;

    end loop;
    
  if customerValidCount = 1 then
    return 'Login successful!';
  else
    return 'Login Failed. Please try again.';
  end if;
end;