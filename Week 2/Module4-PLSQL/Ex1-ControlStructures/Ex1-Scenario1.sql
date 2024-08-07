@InitializeData.sql

SET ECHO ON;

SET SERVEROUTPUT ON SIZE UNLIMITED;

-- Start spooling output to a file
SPOOL output-Ex1-Scenario1.txt;

-- Declare a variable for input
VARIABLE input VARCHAR2(30);

-- Scenario 1

-- Insert sample customers older than 60 years
INSERT INTO CUSTOMERS (
    CUSTOMERID,
    NAME,
    DOB,
    BALANCE,
    LASTMODIFIED
) VALUES (
    1001,
    'John Doe',
    TO_DATE('1950-01-01', 'YYYY-MM-DD'),
    5000,
    SYSDATE
);

INSERT INTO CUSTOMERS (
    CUSTOMERID,
    NAME,
    DOB,
    BALANCE,
    LASTMODIFIED
) VALUES (
    1002,
    'Jane Smith',
    TO_DATE('1955-01-01', 'YYYY-MM-DD'),
    6000,
    SYSDATE
);

-- Insert loans for these customers
INSERT INTO LOANS (
    LOANID,
    CUSTOMERID,
    LOANAMOUNT,
    INTERESTRATE,
    STARTDATE,
    ENDDATE
) VALUES (
    2001,
    1001,
    10000,
    5,
    SYSDATE - 100,
    SYSDATE + 365
);

INSERT INTO LOANS (
    LOANID,
    CUSTOMERID,
    LOANAMOUNT,
    INTERESTRATE,
    STARTDATE,
    ENDDATE
) VALUES (
    2002,
    1002,
    15000,
    5,
    SYSDATE - 200,
    SYSDATE + 365
);

-- Update the interest rate for customers older than 60
BEGIN
    FOR CUSTOMER_REC IN (
        SELECT
            CUSTOMERID,
            TRUNC(MONTHS_BETWEEN(SYSDATE, DOB) / 12) AS AGE
        FROM
            CUSTOMERS
    ) LOOP
        IF CUSTOMER_REC.AGE > 60 THEN
            UPDATE LOANS
            SET
                INTERESTRATE = INTERESTRATE - 1
            WHERE
                CUSTOMERID = CUSTOMER_REC.CUSTOMERID;
        END IF;
    END LOOP;
END;
/

-- Select from loans to see the changes
SELECT
    *
FROM
    LOANS;


-- Stop spooling
SPOOL OFF;

@DropData.sql
