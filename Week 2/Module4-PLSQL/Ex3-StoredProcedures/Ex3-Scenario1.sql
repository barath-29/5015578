@InitializeData.sql

SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex3-Scenario1.txt

VARIABLE input VARCHAR2(30)

-- Procedure to process monthly interest for savings accounts
CREATE OR REPLACE PROCEDURE PROCESSMONTHLYINTEREST IS
BEGIN
    UPDATE ACCOUNTS
    SET
        BALANCE = BALANCE + (
            BALANCE * 0.01
        )
    WHERE
        ACCOUNTTYPE = 'Savings';
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Monthly interest has been applied to all savings accounts.');
END PROCESSMONTHLYINTEREST;
/

-- Test the procedure
BEGIN
    PROCESSMONTHLYINTEREST;
END;
/

SELECT
    *
FROM
    ACCOUNTS;

SPOOL OFF

@DropData.sql