@InitializeData.sql

SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex3-Scenario3.txt

VARIABLE input VARCHAR2(30)

-- Procedure to transfer funds between accounts
CREATE OR REPLACE PROCEDURE TransferFunds (
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
        COMMIT;
    END IF;

EXCEPTION
    WHEN insufficient_funds THEN
        DBMS_OUTPUT.PUT_LINE('Error: Insufficient funds in the source account.');
        ROLLBACK;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END TransferFunds;
/

-- Before the procedure call
SELECT * FROM Accounts;

-- Test the procedure
BEGIN
    TransferFunds(1, 2, 800); -- Transfer 800 from account 1 to account 2
END;
/

-- After the procedure call
SELECT * FROM Accounts;

SPOOL OFF

@DropData.sql
