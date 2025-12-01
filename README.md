# Spring Boot Webhook SQL Challenge

A Spring Boot application that integrates with the Bajaj Finserv hiring API to solve a SQL challenge.

## Overview

This application:
1. Generates a webhook by sending user registration details to the API
2. Receives a webhook URL and JWT access token
3. Solves the assigned SQL problem (Question 2 for even registration numbers)
4. Submits the SQL solution to the webhook URL with JWT authentication

## SQL Problem (Question 2)

**Problem Statement**: For every department, calculate the average age of individuals with salaries exceeding ₹70,000, and produce a concatenated string containing at most 10 of their names.

**Output Format**:
- `DEPARTMENT_NAME`: The name of the department
- `AVERAGE_AGE`: The average age of employees earning more than ₹70,000
- `EMPLOYEE_LIST`: Comma-separated list of up to 10 employee names (FIRST_NAME LAST_NAME)
- Ordered by department ID in descending order

## Technologies Used

- Java 17
- Spring Boot 3.2.0
- Maven
- RestTemplate for HTTP requests
- Lombok for cleaner code

## Project Structure

```
src/
├── main/
│   ├── java/com/bajaj/webhook/
│   │   ├── WebhookApplication.java          # Main application class
│   │   ├── config/
│   │   │   └── RestTemplateConfig.java      # RestTemplate configuration
│   │   ├── model/
│   │   │   ├── WebhookRequest.java          # Request DTO for webhook generation
│   │   │   ├── WebhookResponse.java         # Response DTO from webhook API
│   │   │   └── SolutionRequest.java         # Request DTO for solution submission
│   │   ├── service/
│   │   │   ├── WebhookService.java          # Service for API interactions
│   │   │   └── SqlSolutionService.java      # Service for SQL solution
│   │   └── runner/
│   │       └── WebhookRunner.java           # CommandLineRunner for startup execution
│   └── resources/
│       └── application.properties            # Application configuration
└── pom.xml                                   # Maven dependencies
```

## Build Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the JAR

```bash
cd "c:\Users\Lenovo\Downloads\Bajaj Finserv Task 2"
mvn clean package
```

The JAR file will be created at: `target/webhook-challenge-1.0.0.jar`

## Run Instructions

### Run the JAR

```bash
java -jar target/webhook-challenge-1.0.0.jar
```

The application will:
1. Start up and execute the webhook flow automatically
2. Log all steps to the console
3. Display the final response from the API
4. Shut down after completion

### Expected Output

```
=================================================
Starting Webhook SQL Challenge
=================================================
Step 1: Generating webhook...
Webhook generated successfully
Step 2: Preparing SQL solution...
SQL Query prepared successfully
Step 3: Submitting solution to webhook...
Solution submitted successfully
=================================================
Challenge completed successfully!
Final Response: [API Response]
=================================================
```

## Configuration

User details can be modified in `src/main/resources/application.properties`:

```properties
user.name=John Doe
user.regNo=REG12347
user.email=john@example.com
```

## JAR Download

> [!IMPORTANT]
> **Next Steps**: After creating your GitHub repository, update this section with your actual links.

**GitHub Repository**: https://github.com/anibjee/Bajaj-Finserv-Webhook-Task

**Direct JAR Download**: https://github.com/anibjee/Bajaj-Finserv-Webhook-Task/raw/main/target/webhook-challenge-1.0.0.jar

### How to Set Up GitHub Repository

1. **Initialize Git** (if not already done):
   ```bash
   cd "c:\Users\Lenovo\Downloads\Bajaj Finserv Task 2"
   git init
   git add .
   git commit -m "Initial commit: Spring Boot Webhook SQL Challenge"
   ```

2. **Create a new repository** on GitHub (make it public)

3. **Push your code**:
   ```bash
   git remote add origin https://github.com/anibjee/Bajaj-Finserv-Webhook-Task.git
   git branch -M main
   git push -u origin main
   ```

4. **Upload JAR file**:
   - Option 1: Include in repository (already in `target/` directory)
   - Option 2: Create a GitHub Release and upload the JAR file

5. **Get the raw download link**:
   - For in-repo: `https://github.com/anibjee/Bajaj-Finserv-Webhook-Task/raw/main/target/webhook-challenge-1.0.0.jar`
   - For release: Go to Releases → Copy download link

6. **Update this README** with your actual GitHub username and links

## API Endpoints Used

1. **Generate Webhook**
   - URL: `https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA`
   - Method: POST
   - Body: `{ "name": "...", "regNo": "...", "email": "..." }`

2. **Submit Solution**
   - URL: `https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA`
   - Method: POST
   - Headers: `Authorization: <accessToken>`
   - Body: `{ "finalQuery": "..." }`

## SQL Solution

The application uses the following SQL query for Question 2:

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

## Author

**Aniruddha Bhattacharjee**
- Registration Number: 22BCE10820
- Email: aniruddhabhattacharjee2022@vitbhopal.ac.in

## License

This project is created for the Bajaj Finserv hiring challenge.
