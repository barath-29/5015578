@InitializeData.sql

SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex5-Scenario1.txt

VARIABLE input VARCHAR2(30)

-- Trigger to automatically update the LastModified date when a customer's record is updated
CREATE OR REPLACE TRIGGER UpdateCustomerLastModified
BEFORE UPDATE ON CUSTOMERS
FOR EACH ROW
BEGIN
    :NEW.LastModified := SYSDATE;
END;
/

-- Before Updation
SELECT * FROM CUSTOMERS;

-- Test the trigger by updating a customer record
UPDATE CUSTOMERS
SET NAME = 'Richard Nomad'
WHERE CUSTOMERID = 1;

-- Verify the change
SELECT * FROM CUSTOMERS;

SPOOL OFF

@DropData.sql
