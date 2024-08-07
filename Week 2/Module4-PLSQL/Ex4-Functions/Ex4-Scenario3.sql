@InitializeData.sql

SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex4-Scenario3.txt

VARIABLE input VARCHAR2(30)

-- Function to check if a customer has sufficient balance
CREATE OR REPLACE FUNCTION HasSufficientBalance (
    p_account_id NUMBER,
    p_amount NUMBER
) RETURN BOOLEAN IS
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance FROM Accounts WHERE AccountID = p_account_id;

    RETURN v_balance >= p_amount;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN FALSE;
END HasSufficientBalance;
/

SELECT * FROM Accounts;

-- Test the function
DECLARE
    v_sufficient BOOLEAN;
BEGIN
    v_sufficient := HasSufficientBalance(1, 2000); -- Check if account 1 has at least 2000
    DBMS_OUTPUT.PUT_LINE('Sufficient Balance: ' || CASE WHEN v_sufficient THEN 'YES' ELSE 'NO' END);

    v_sufficient := HasSufficientBalance(2, 500); -- Check if account 2 has at least 500
    DBMS_OUTPUT.PUT_LINE('Sufficient Balance: ' || CASE WHEN v_sufficient THEN 'YES' ELSE 'NO' END);
END;
/

SPOOL OFF

@DropData.sql
