SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex4-Scenario1.txt

VARIABLE input VARCHAR2(30)

-- Function to calculate the age of a customer
CREATE OR REPLACE FUNCTION CalculateAge (
    p_dob DATE
) RETURN NUMBER IS
    v_age NUMBER;
BEGIN
    v_age := TRUNC(MONTHS_BETWEEN(SYSDATE, p_dob) / 12);
    RETURN v_age;
END CalculateAge;
/

-- Test the function
DECLARE
    v_age NUMBER;
BEGIN
    v_age := CalculateAge(TO_DATE('1990-01-01', 'YYYY-MM-DD'));
    DBMS_OUTPUT.PUT_LINE('Age: ' || v_age);
END;
/

SPOOL OFF

