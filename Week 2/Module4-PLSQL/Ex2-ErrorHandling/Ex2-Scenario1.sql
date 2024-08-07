@InitializeData.sql
SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex2-Scenario1.txt

VARIABLE input VARCHAR2(30)

-- Insert sample customers
INSERT INTO CUSTOMERS (CustomerID, Name, DOB, Balance, LastModified) 
VALUES (1001, 'John Doe', TO_DATE('1950-01-01', 'YYYY-MM-DD'), 5000, SYSDATE);

INSERT INTO CUSTOMERS (CustomerID, Name, DOB, Balance, LastModified) 
VALUES (1002, 'Jane Smith', TO_DATE('1955-01-01', 'YYYY-MM-DD'), 6000, SYSDATE);

-- Insert sample accounts with balances
INSERT INTO ACCOUNTS (AccountID, CustomerID, AccountType, Balance, LastModified) 
VALUES (3001, 1001, 'Checking', 5000, SYSDATE);

INSERT INTO ACCOUNTS (AccountID, CustomerID, AccountType, Balance, LastModified) 
VALUES (3002, 1002, 'Savings', 1000, SYSDATE);

-- Procedure for safe fund transfer
CREATE OR REPLACE PROCEDURE SafeTransferFunds (
    p_from_account_id IN NUMBER,
    p_to_account_id IN NUMBER,
    p_amount IN NUMBER
) IS
    insufficient_funds EXCEPTION;
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance FROM Accounts WHERE AccountID = p_from_account_id;
    
    IF v_balance < p_amount THEN
        RAISE insufficient_funds;
    ELSE
        UPDATE Accounts SET Balance = Balance - p_amount WHERE AccountID = p_from_account_id;
        UPDATE Accounts SET Balance = Balance + p_amount WHERE AccountID = p_to_account_id;
        DBMS_OUTPUT.PUT_LINE('Fund Transfer Successful');
        COMMIT;
    END IF;

EXCEPTION
    WHEN insufficient_funds THEN
        DBMS_OUTPUT.PUT_LINE('Error: Insufficient funds in the source account.');
        ROLLBACK;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END SafeTransferFunds;
/

-- Test the procedure
BEGIN
    SafeTransferFunds(3001, 3002, 1000); -- This should be successful
    SafeTransferFunds(3001, 3002, 6000); -- This should cause insufficient funds error
END;
/

SPOOL OFF

@DropData.sql
