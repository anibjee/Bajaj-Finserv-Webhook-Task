# Bajaj Finserv Webhook Challenge - Submission Details

## Submission Information

**Submission Form**: https://forms.office.com/r/WFzAwgbNQb

## Required Information for Submission

### 1. GitHub Repository
**URL**: https://github.com/anibjee/Bajaj-Finserv-Webhook-Task

### 2. JAR File Download Link
**Direct Download**: https://github.com/anibjee/Bajaj-Finserv-Webhook-Task/raw/main/target/webhook-challenge-1.0.0.jar

### 3. Personal Details
- **Name**: Aniruddha Bhattacharjee
- **Registration Number**: 22BCE10820
- **Email**: aniruddhabhattacharjee2022@vitbhopal.ac.in

## What's Included in the Repository

✅ **Source Code**
- Complete Spring Boot application
- All Java source files
- Configuration files (pom.xml, application.properties)
- Maven wrapper for easy building

✅ **JAR File**
- Location: `target/webhook-challenge-1.0.0.jar`
- Size: ~17 MB (fat JAR with all dependencies)
- Ready to run with: `java -jar webhook-challenge-1.0.0.jar`

✅ **Documentation**
- README.md with complete instructions
- Build and run instructions
- SQL solution explanation

## SQL Solution (Question 2)

**Problem**: Calculate average age of employees earning > ₹70,000 per department, with a list of up to 10 employee names.

**Query**:
```sql
SELECT d.DEPARTMENT_NAME, 
       ROUND(AVG(TIMESTAMPDIFF(YEAR, e.DOB, CURDATE())), 2) AS AVERAGE_AGE, 
       SUBSTRING_INDEX(GROUP_CONCAT(CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) ORDER BY e.EMP_ID SEPARATOR ', '), ', ', 10) AS EMPLOYEE_LIST 
FROM DEPARTMENT d 
INNER JOIN EMPLOYEE e ON d.DEPARTMENT_ID = e.DEPARTMENT 
INNER JOIN (SELECT EMP_ID FROM PAYMENTS WHERE AMOUNT > 70000 GROUP BY EMP_ID) p ON e.EMP_ID = p.EMP_ID 
GROUP BY d.DEPARTMENT_ID, d.DEPARTMENT_NAME 
ORDER BY d.DEPARTMENT_ID DESC
```

## Verification

✅ **Application Tested Successfully**
- Webhook generated successfully
- Access token received
- SQL solution submitted with JWT authentication
- **API Response**: "Webhook processed successfully"

## Next Steps

1. **Push code to GitHub** (if not already done):
   ```bash
   cd "c:\Users\Lenovo\Downloads\Bajaj Finserv Task 2"
   git add .
   git commit -m "Update README with GitHub links"
   git push origin main
   ```

2. **Verify JAR file is in repository**:
   - Check that `target/webhook-challenge-1.0.0.jar` is committed
   - Or create a GitHub Release and upload the JAR

3. **Submit the form** at: https://forms.office.com/r/WFzAwgbNQb
   - Include GitHub repository URL
   - Include direct JAR download link

## Important Notes

- Registration number **22BCE10820** ends in **20** (even) → Question 2 assigned ✅
- Application runs automatically on startup (no controller needed) ✅
- Uses RestTemplate for HTTP requests ✅
- JWT token used in Authorization header ✅
- All requirements met ✅

---

**Ready for submission!** 🚀
