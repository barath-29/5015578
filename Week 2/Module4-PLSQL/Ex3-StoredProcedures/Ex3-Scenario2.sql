@InitializeData.sql

SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex3-Scenario2.txt

VARIABLE input VARCHAR2(30)

-- Procedure to update employee bonus
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_department IN VARCHAR2,
    p_bonus_percentage IN NUMBER
) IS
BEGIN
    UPDATE Employees
    SET Salary = Salary + (Salary * p_bonus_percentage / 100)
    WHERE Department = p_department;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Bonus has been updated for employees in department ' || p_department || '.');
END UpdateEmployeeBonus;
/

-- Before calling procedure
SELECT * FROM Employees;

-- Test the procedure
BEGIN
    UpdateEmployeeBonus('IT', 10); -- Apply 10% bonus to employees in IT department
END;
/

-- After calling procedure
SELECT * FROM Employees;

SPOOL OFF

@DropData.sql
