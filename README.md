LibararyManagementAPITest_RestAssured
This project provides automated API tests for an Libarary Management system using Java, RestAssured, TestNG
Key Methods and Technologies Used
RestAssured: For making HTTP requests and validating API responses.
TestNG: For organizing and running test cases.
Maven: For dependency management and build automation.
Example Test Workflow
Setup: Initialize RestAssured base URI and authentication in a base test class.
Test Execution: Use TestNG to run test methods that:
Send HTTP requests (GET, POST, PUT, DELETE) to API endpoints.
Validate status codes, response bodies, and headers.
Use assertions to check business logic.
**Reporting:Pending ** Generate Allure reports after test execution for detailed insights.
How to Run
Install dependencies:
mvn clean install
Run tests:
mvn test
Generate Allure report:
mvn allure:report
Open the report:
Open target/site/allure-maven-plugin/index.html in your browser, or
Run mvn allure:serve to view the report locally.
can apply futher for Extension
Allure: For generating detailed and interactive test reports.
BDD style Scenarios
Add CI/CD integration for automated testing.
Parameterize tests for broader coverage.
Integrate with API contract validation tools.
Add performance and security tests.
For more details, refer to the code comments and structure in the repository.

