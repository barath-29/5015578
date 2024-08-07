SET ECHO ON

SET SERVEROUTPUT ON SIZE UNLIMITED

SPOOL output-Ex4-Scenario2.txt

VARIABLE input VARCHAR2(30)

-- Function to calculate the monthly installment for a loan
CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment (
    p_loan_amount NUMBER,
    p_interest_rate NUMBER,
    p_duration_years NUMBER
) RETURN NUMBER IS
    v_monthly_installment NUMBER;
    v_monthly_rate NUMBER;
    v_total_months NUMBER;
BEGIN
    v_monthly_rate := p_interest_rate / 12 / 100;
    v_total_months := p_duration_years * 12;

    IF v_monthly_rate > 0 THEN
        v_monthly_installment := p_loan_amount * (v_monthly_rate * POWER(1 + v_monthly_rate, v_total_months)) / (POWER(1 + v_monthly_rate, v_total_months) - 1);
    ELSE
        v_monthly_installment := p_loan_amount / v_total_months;
    END IF;

    RETURN v_monthly_installment;
END CalculateMonthlyInstallment;
/

-- Test the function
DECLARE
    v_installment NUMBER;
BEGIN
    v_installment := CalculateMonthlyInstallment(10000, 5, 10); -- Loan amount: 10000, Interest rate: 5%, Duration: 10 years
    DBMS_OUTPUT.PUT_LINE('Monthly Installment: ' || v_installment);
END;
/

SPOOL OFF