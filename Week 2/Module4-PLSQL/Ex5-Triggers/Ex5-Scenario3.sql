@InitializeData.sql

SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex5-Scenario3.txt

VARIABLE input VARCHAR2(30)

-- Trigger to enforce business rules on deposits and withdrawals
CREATE OR REPLACE TRIGGER CheckTransactionRules
BEFORE INSERT ON TRANSACTIONS
FOR EACH ROW
DECLARE
    v_balance NUMBER;
BEGIN
    -- Get the current balance for the account
    SELECT BALANCE
    INTO v_balance
    FROM ACCOUNTS
    WHERE ACCOUNTID = :NEW.ACCOUNTID;
    
    -- Check for withdrawal rules
    IF :NEW.TRANSACTIONTYPE = 'Withdrawal' THEN
        IF :NEW.AMOUNT > v_balance THEN
            RAISE_APPLICATION_ERROR(-20001, 'Insufficient funds for withdrawal');
        END IF;
    END IF;
    
    -- Check for deposit rules
    IF :NEW.TRANSACTIONTYPE = 'Deposit' AND :NEW.AMOUNT <= 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Deposit amount must be positive');
    END IF;
END;
/

-- Test the trigger by inserting valid and invalid transactions
BEGIN
    -- Valid Deposit
    INSERT INTO TRANSACTIONS (TRANSACTIONID, ACCOUNTID, TRANSACTIONDATE, AMOUNT, TRANSACTIONTYPE)
    VALUES (3002, 1, SYSDATE, 500, 'Deposit');

    -- Invalid Withdrawal (Insufficient funds)
    BEGIN
        INSERT INTO TRANSACTIONS (TRANSACTIONID, ACCOUNTID, TRANSACTIONDATE, AMOUNT, TRANSACTIONTYPE)
        VALUES (3003, 2, SYSDATE, 10000, 'Withdrawal');
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE(SQLERRM);
    END;
END;
/

-- Verify the transactions
SELECT * FROM TRANSACTIONS;

SPOOL OFF

@DropData.sql
