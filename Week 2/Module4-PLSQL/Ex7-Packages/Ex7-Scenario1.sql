@InitializeData.sql

SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex7-Scenario1.txt

VARIABLE input VARCHAR2(30)

-- Package Specification
CREATE OR REPLACE PACKAGE CustomerManagement AS
    PROCEDURE AddCustomer(
        p_CustomerID IN CUSTOMERS.CUSTOMERID%TYPE,
        p_Name IN CUSTOMERS.NAME%TYPE,
        p_DOB IN CUSTOMERS.DOB%TYPE,
        p_Balance IN CUSTOMERS.BALANCE%TYPE
    );

    PROCEDURE UpdateCustomer(
        p_CustomerID IN CUSTOMERS.CUSTOMERID%TYPE,
        p_Name IN CUSTOMERS.NAME%TYPE,
        p_Balance IN CUSTOMERS.BALANCE%TYPE
    );

    FUNCTION GetCustomerBalance(
        p_CustomerID IN CUSTOMERS.CUSTOMERID%TYPE
    ) RETURN CUSTOMERS.BALANCE%TYPE;
END CustomerManagement;
/

-- Package Body
CREATE OR REPLACE PACKAGE BODY CustomerManagement AS

    PROCEDURE AddCustomer(
        p_CustomerID IN CUSTOMERS.CUSTOMERID%TYPE,
        p_Name IN CUSTOMERS.NAME%TYPE,
        p_DOB IN CUSTOMERS.DOB%TYPE,
        p_Balance IN CUSTOMERS.BALANCE%TYPE
    ) IS
    BEGIN
        INSERT INTO CUSTOMERS (
            CUSTOMERID, NAME, DOB, BALANCE, LASTMODIFIED
        ) VALUES (
            p_CustomerID, p_Name, p_DOB, p_Balance, SYSDATE
        );
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Customer ID already exists.');
    END AddCustomer;

    PROCEDURE UpdateCustomer(
        p_CustomerID IN CUSTOMERS.CUSTOMERID%TYPE,
        p_Name IN CUSTOMERS.NAME%TYPE,
        p_Balance IN CUSTOMERS.BALANCE%TYPE
    ) IS
    BEGIN
        UPDATE CUSTOMERS
        SET NAME = p_Name, BALANCE = p_Balance, LASTMODIFIED = SYSDATE
        WHERE CUSTOMERID = p_CustomerID;
        
        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Customer ID not found.');
        END IF;
    END UpdateCustomer;

    FUNCTION GetCustomerBalance(
        p_CustomerID IN CUSTOMERS.CUSTOMERID%TYPE
    ) RETURN CUSTOMERS.BALANCE%TYPE IS
        v_Balance CUSTOMERS.BALANCE%TYPE;
    BEGIN
        SELECT BALANCE INTO v_Balance
        FROM CUSTOMERS
        WHERE CUSTOMERID = p_CustomerID;

        RETURN v_Balance;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('Customer ID not found.');
            RETURN NULL;
    END GetCustomerBalance;

END CustomerManagement;
/

-- Before Using the Package
SELECT * FROM Customers;

-- Test Package Procedures and Function
BEGIN
    -- Add a new customer
    CustomerManagement.AddCustomer(
        p_CustomerID => 2001,
        p_Name => 'Alice Johnson',
        p_DOB => TO_DATE('1985-06-15', 'YYYY-MM-DD'),
        p_Balance => 3000
    );

    -- Update an existing customer
    CustomerManagement.UpdateCustomer(
        p_CustomerID => 2001,
        p_Name => 'Alice Thompson',
        p_Balance => 3500
    );

    -- Get customer balance
    DECLARE
        v_Balance CUSTOMERS.BALANCE%TYPE;
    BEGIN
        v_Balance := CustomerManagement.GetCustomerBalance(2001);
        DBMS_OUTPUT.PUT_LINE('Balance for customer 2001: ' || v_Balance);
    END;
END;
/

-- After Using the Package
SELECT * FROM Customers;

SPOOL OFF

@DropData.sql
