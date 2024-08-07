@InitializeData.sql

SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex7-Scenario3.txt

VARIABLE input VARCHAR2(30)

-- Package Specification
CREATE OR REPLACE PACKAGE AccountOperations AS
    PROCEDURE OpenAccount(
        p_AccountID IN ACCOUNTS.ACCOUNTID%TYPE,
        p_CustomerID IN ACCOUNTS.CUSTOMERID%TYPE,
        p_AccountType IN ACCOUNTS.ACCOUNTTYPE%TYPE,
        p_Balance IN ACCOUNTS.BALANCE%TYPE
    );

    PROCEDURE CloseAccount(
        p_AccountID IN ACCOUNTS.ACCOUNTID%TYPE
    );

    FUNCTION GetTotalBalance(
        p_CustomerID IN ACCOUNTS.CUSTOMERID%TYPE
    ) RETURN NUMBER;
END AccountOperations;
/

-- Package Body
CREATE OR REPLACE PACKAGE BODY AccountOperations AS

    PROCEDURE OpenAccount(
        p_AccountID IN ACCOUNTS.ACCOUNTID%TYPE,
        p_CustomerID IN ACCOUNTS.CUSTOMERID%TYPE,
        p_AccountType IN ACCOUNTS.ACCOUNTTYPE%TYPE,
        p_Balance IN ACCOUNTS.BALANCE%TYPE
    ) IS
    BEGIN
        INSERT INTO ACCOUNTS (
            ACCOUNTID, CUSTOMERID, ACCOUNTTYPE, BALANCE, LASTMODIFIED
        ) VALUES (
            p_AccountID, p_CustomerID, p_AccountType, p_Balance, SYSDATE
        );
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Account ID already exists.');
    END OpenAccount;

    PROCEDURE CloseAccount(
        p_AccountID IN ACCOUNTS.ACCOUNTID%TYPE
    ) IS
    BEGIN
        DELETE FROM ACCOUNTS
        WHERE ACCOUNTID = p_AccountID;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Account ID not found.');
        END IF;
    END CloseAccount;

    FUNCTION GetTotalBalance(
        p_CustomerID IN ACCOUNTS.CUSTOMERID%TYPE
    ) RETURN NUMBER IS
        v_TotalBalance NUMBER := 0;
    BEGIN
        SELECT SUM(BALANCE) INTO v_TotalBalance
        FROM ACCOUNTS
        WHERE CUSTOMERID = p_CustomerID;

        RETURN v_TotalBalance;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('No accounts found for the customer.');
            RETURN NULL;
    END GetTotalBalance;

END AccountOperations;
/

-- Before Using the Package
SELECT * FROM Accounts;

-- Test Package Procedures and Function
BEGIN
    -- Open a new account
    AccountOperations.OpenAccount(
        p_AccountID => 4001,
        p_CustomerID => 1,
        p_AccountType => 'Savings',
        p_Balance => 5000
    );

    -- Close an account
    AccountOperations.CloseAccount(4001);

    -- Get total balance for a customer
    DECLARE
        v_TotalBalance NUMBER;
    BEGIN
        v_TotalBalance := AccountOperations.GetTotalBalance(2);
        DBMS_OUTPUT.PUT_LINE('Total balance for customer 2: ' || v_TotalBalance);
    END;
END;
/

-- After Using the Package
SELECT * FROM Accounts;

SPOOL OFF

@DropData.sql
