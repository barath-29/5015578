@InitializeData.sql

SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex6-Scenario2.txt

VARIABLE input VARCHAR2(30)

-- PL/SQL block to apply an annual fee to all accounts
DECLARE
    CURSOR c_accounts IS
        SELECT ACCOUNTID, BALANCE
        FROM ACCOUNTS;
    
    v_fee NUMBER := 50; -- Example annual fee amount
BEGIN
    FOR r IN c_accounts LOOP
        UPDATE ACCOUNTS
        SET BALANCE = BALANCE - v_fee
        WHERE ACCOUNTID = r.ACCOUNTID;
        
        DBMS_OUTPUT.PUT_LINE('Account ID: ' || r.ACCOUNTID || ' - Fee applied. New balance: ' || (r.BALANCE - v_fee));
    END LOOP;
END;
/

SPOOL OFF

@DropData.sql
