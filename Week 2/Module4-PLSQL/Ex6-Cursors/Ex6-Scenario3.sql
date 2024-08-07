@InitializeData.sql

SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex6-Scenario3.txt

VARIABLE input VARCHAR2(30)

-- PL/SQL block to update interest rates for all loans based on a new policy
DECLARE
    CURSOR c_loans IS
        SELECT LOANID, INTERESTRATE
        FROM LOANS;
    
    v_new_interest_rate NUMBER := 3; -- Example new interest rate
BEGIN
    FOR r IN c_loans LOOP
        UPDATE LOANS
        SET INTERESTRATE = v_new_interest_rate
        WHERE LOANID = r.LOANID;
        
        DBMS_OUTPUT.PUT_LINE('Loan ID: ' || r.LOANID || ' - Interest rate updated to: ' || v_new_interest_rate);
    END LOOP;
END;
/

SPOOL OFF

@DropData.sql
