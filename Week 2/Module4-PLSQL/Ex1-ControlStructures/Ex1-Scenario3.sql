@InitializeData.sql

SET ECHO ON

SET SERVEROUTPUT on SIZE UNLIMITED

SPOOL output-Ex1-Scenario3.txt

VARIABLE input VARCHAR2(30)

-- Scenario 3

-- Insert sample customer
INSERT INTO CUSTOMERS (
    CUSTOMERID,
    NAME,
    DOB,
    BALANCE,
    LASTMODIFIED
) VALUES (
    1005,
    'Charlie Davis',
    TO_DATE('1990-01-01', 'YYYY-MM-DD'),
    7000,
    SYSDATE
);

-- Insert loan for the customer with end date within the next 30 days
INSERT INTO LOANS (
    LOANID,
    CUSTOMERID,
    LOANAMOUNT,
    INTERESTRATE,
    STARTDATE,
    ENDDATE
) VALUES (
    2003,
    1005,
    20000,
    4,
    SYSDATE - 300,
    SYSDATE + 10
);

BEGIN
    FOR LOAN_REC IN (
        SELECT
            L.LOANID,
            L.CUSTOMERID,
            C.NAME,
            L.ENDDATE
        FROM
            LOANS     L
            JOIN CUSTOMERS C
            ON L.CUSTOMERID = C.CUSTOMERID
        WHERE
            L.ENDDATE BETWEEN SYSDATE AND SYSDATE + 30
    ) LOOP
        DBMS_OUTPUT.PUT_LINE('Reminder: Loan ID '
                             || LOAN_REC.LOANID
                             || ' for customer '
                             || LOAN_REC.NAME
                             || ' is due on '
                             || TO_CHAR(LOAN_REC.ENDDATE, 'YYYY-MM-DD'));
    END LOOP;
END;
/

SPOOL OFF

@DropData.sql