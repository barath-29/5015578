@InitializeData.sql

SET ECHO ON;

SET SERVEROUTPUT ON SIZE UNLIMITED;

-- Start spooling output to a file
SPOOL output-Ex1-Scenario2.txt;

VARIABLE input VARCHAR2(30);

-- Scenario 2

-- Insert sample customers with varying balances
INSERT INTO CUSTOMERS (
    CUSTOMERID,
    NAME,
    DOB,
    BALANCE,
    LASTMODIFIED
) VALUES (
    1003,
    'Kyle',
    TO_DATE('1980-01-01', 'YYYY-MM-DD'),
    15000,
    SYSDATE
);

INSERT INTO CUSTOMERS (
    CUSTOMERID,
    NAME,
    DOB,
    BALANCE,
    LASTMODIFIED
) VALUES (
    1004,
    'Zach',
    TO_DATE('1985-01-01', 'YYYY-MM-DD'),
    8000,
    SYSDATE
);

ALTER TABLE CUSTOMERS
    ADD (
        ISVIP VARCHAR2(5)
    );

BEGIN
    FOR CUSTOMER_REC IN (
        SELECT
            CUSTOMERID,
            BALANCE
        FROM
            CUSTOMERS
    ) LOOP
        IF CUSTOMER_REC.BALANCE > 10000 THEN
            UPDATE CUSTOMERS
            SET
                ISVIP='TRUE'
            WHERE
                CUSTOMERID = CUSTOMER_REC.CUSTOMERID;
        ELSE
            UPDATE CUSTOMERS
            SET
                ISVIP='FALSE'
            WHERE
                CUSTOMERID = CUSTOMER_REC.CUSTOMERID;
        END IF;
    END LOOP;
END;
/

-- Select from customers to see the changes
SELECT
    *
FROM
    CUSTOMERS;

-- Stop spooling
SPOOL OFF

@DropData.sql