@InitializeData.sql

SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex7-Scenario2.txt

VARIABLE input VARCHAR2(30)


-- Package Specification
CREATE OR REPLACE PACKAGE EmployeeManagement AS
    PROCEDURE HireEmployee(
        p_EmployeeID IN EMPLOYEES.EMPLOYEEID%TYPE,
        p_Name IN EMPLOYEES.NAME%TYPE,
        p_Position IN EMPLOYEES.POSITION%TYPE,
        p_Salary IN EMPLOYEES.SALARY%TYPE,
        p_Department IN EMPLOYEES.DEPARTMENT%TYPE
    );

    PROCEDURE UpdateEmployee(
        p_EmployeeID IN EMPLOYEES.EMPLOYEEID%TYPE,
        p_Name IN EMPLOYEES.NAME%TYPE,
        p_Position IN EMPLOYEES.POSITION%TYPE,
        p_Salary IN EMPLOYEES.SALARY%TYPE,
        p_Department IN EMPLOYEES.DEPARTMENT%TYPE
    );

    FUNCTION CalculateAnnualSalary(
        p_EmployeeID IN EMPLOYEES.EMPLOYEEID%TYPE
    ) RETURN NUMBER;
END EmployeeManagement;
/

-- Package Body
CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS

    PROCEDURE HireEmployee(
        p_EmployeeID IN EMPLOYEES.EMPLOYEEID%TYPE,
        p_Name IN EMPLOYEES.NAME%TYPE,
        p_Position IN EMPLOYEES.POSITION%TYPE,
        p_Salary IN EMPLOYEES.SALARY%TYPE,
        p_Department IN EMPLOYEES.DEPARTMENT%TYPE
    ) IS
    BEGIN
        INSERT INTO EMPLOYEES (
            EMPLOYEEID, NAME, POSITION, SALARY, DEPARTMENT, HIREDATE
        ) VALUES (
            p_EmployeeID, p_Name, p_Position, p_Salary, p_Department, SYSDATE
        );
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Employee ID already exists.');
    END HireEmployee;

    PROCEDURE UpdateEmployee(
        p_EmployeeID IN EMPLOYEES.EMPLOYEEID%TYPE,
        p_Name IN EMPLOYEES.NAME%TYPE,
        p_Position IN EMPLOYEES.POSITION%TYPE,
        p_Salary IN EMPLOYEES.SALARY%TYPE,
        p_Department IN EMPLOYEES.DEPARTMENT%TYPE
    ) IS
    BEGIN
        UPDATE EMPLOYEES
        SET NAME = p_Name, POSITION = p_Position, SALARY = p_Salary, DEPARTMENT = p_Department
        WHERE EMPLOYEEID = p_EmployeeID;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Employee ID not found.');
        END IF;
    END UpdateEmployee;

    FUNCTION CalculateAnnualSalary(
        p_EmployeeID IN EMPLOYEES.EMPLOYEEID%TYPE
    ) RETURN NUMBER IS
        v_Salary EMPLOYEES.SALARY%TYPE;
        v_AnnualSalary NUMBER;
    BEGIN
        SELECT SALARY INTO v_Salary
        FROM EMPLOYEES
        WHERE EMPLOYEEID = p_EmployeeID;

        v_AnnualSalary := v_Salary * 12;
        RETURN v_AnnualSalary;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('Employee ID not found.');
            RETURN NULL;
    END CalculateAnnualSalary;

END EmployeeManagement;
/

-- Before Using the Package
SELECT * FROM Employees;

-- Test Package Procedures and Function
BEGIN
    -- Hire a new employee
    EmployeeManagement.HireEmployee(
        p_EmployeeID => 5,
        p_Name => 'Rick Brown',
        p_Position => 'Developer',
        p_Salary => 7000,
        p_Department => 'IT'
    );

    -- Update employee details
    EmployeeManagement.UpdateEmployee(
        p_EmployeeID => 2,
        p_Name => 'Dan Smith',
        p_Position => 'Senior Developer',
        p_Salary => 8000,
        p_Department => 'IT'
    );

    -- Calculate annual salary
    DECLARE
        v_AnnualSalary NUMBER;
    BEGIN
        v_AnnualSalary := EmployeeManagement.CalculateAnnualSalary(3001);
        DBMS_OUTPUT.PUT_LINE('Annual salary for employee 3001: ' || v_AnnualSalary);
    END;
END;
/

-- After Using the Package
SELECT * FROM Employees;

SPOOL OFF

@DropData.sql
