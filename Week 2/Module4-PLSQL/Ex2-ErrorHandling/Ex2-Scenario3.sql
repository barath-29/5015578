@InitializeData.sql

SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex2-Scenario3.txt

VARIABLE input VARCHAR2(30)

-- Procedure to add a new customer
CREATE OR REPLACE PROCEDURE AddNewCustomer (
    p_customer_id IN NUMBER,
    p_name IN VARCHAR2,
    p_dob IN DATE,
    p_balance IN NUMBER
) IS
    customer_exists EXCEPTION;
BEGIN
    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (p_customer_id, p_name, p_dob, p_balance, SYSDATE);

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Customer successfully added');

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Error: Customer with ID ' || p_customer_id || ' already exists.');
        ROLLBACK;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END AddNewCustomer;
/

-- Test the procedure
BEGIN
    AddNewCustomer(1006, 'David Wright', TO_DATE('1980-02-15', 'YYYY-MM-DD'), 8000); -- This should be successful
    AddNewCustomer(1006, 'Eve Adams', TO_DATE('1985-03-10', 'YYYY-MM-DD'), 9000); -- This should cause duplicate customer error
END;
/

SPOOL OFF

@DropData.sql
