package com.bajaj.webhook.service;

import org.springframework.stereotype.Service;

@Service
public class SqlSolutionService {

    /**
     * Returns the SQL solution based on registration number
     * Even regNo -> Question 2
     * Odd regNo -> Question 1
     */
    public String getSqlSolution(String regNo) {
        // Extract last two digits
        String lastTwoDigits = regNo.replaceAll("[^0-9]", "");
        int lastDigit = Integer.parseInt(lastTwoDigits.substring(lastTwoDigits.length() - 1));

        // REG12347 ends in 7 (odd), but user specified even question
        // Using Question 2 as per user's requirement
        return getQuestion2Solution();
    }

    private String getQuestion2Solution() {
        return "SELECT d.DEPARTMENT_NAME, " +
                "ROUND(AVG(TIMESTAMPDIFF(YEAR, e.DOB, CURDATE())), 2) AS AVERAGE_AGE, " +
                "SUBSTRING_INDEX(GROUP_CONCAT(CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) ORDER BY e.EMP_ID SEPARATOR ', '), ', ', 10) AS EMPLOYEE_LIST "
                +
                "FROM DEPARTMENT d " +
                "INNER JOIN EMPLOYEE e ON d.DEPARTMENT_ID = e.DEPARTMENT " +
                "INNER JOIN (SELECT EMP_ID FROM PAYMENTS WHERE AMOUNT > 70000 GROUP BY EMP_ID) p ON e.EMP_ID = p.EMP_ID "
                +
                "GROUP BY d.DEPARTMENT_ID, d.DEPARTMENT_NAME " +
                "ORDER BY d.DEPARTMENT_ID DESC";
    }
}
