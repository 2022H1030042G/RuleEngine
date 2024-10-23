# Rule Engine Application

# Overview: 
Uses an Abstract Syntax Tree (AST) to represent conditional rules, allowing dynamic creation, combination, and evaluation of rules.
# Features
Rule Creation: Dynamically create rules from a rule string and generate AST.
Rule Combination: Combine multiple rules into a single AST efficiently.
Rule Evaluation: Evaluate user data against the generated rules (AST).

# Technologies Used
Java 17
Spring Boot 3.x
Jackson for JSON processing
Maven for build and dependency management
H2 Database (or any preferred database)
JUnit and MockMvc for testing

# Prerequisites
Before running this application, make sure you have the following installed on your system:
JDK 17 or higher
Maven 3.x or higher
IntelliJ IDEA or any IDE of your choice with Spring Boot support

# Steps to Set Up and Run the Project in IntelliJ IDEA
# 1. Clone the Repository
git clone https://github.com/2022H1030042G/RuleEngine.git

cd ruleengine

# 2. Open the Project in IntelliJ IDEA
Open IntelliJ IDEA.
Select File > Open and navigate to the rule-engine project directory.
IntelliJ will automatically detect and import the Maven project. Wait for it to download dependencies.

# 3. Build and Run the Application
In the Project Explorer, navigate to src/main/java/com/example/ruleengine/RuleEngineApplication.java.
Right-click on RuleEngineApplication.java and choose Run 'RuleEngineApplication'.
Alternatively, you can build and run the application using the following Maven command:

mvn clean install

mvn spring-boot:run

# 4. Test the Application
You can test the application using Postman or any other REST client.

Example Requests:

# Create a Rule:

POST http://localhost:8080/api/rules/create

Content-Type: application/json

Body:

"((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 50000 OR experience > 5)"

# Combine Rules:

POST http://localhost:8080/api/rules/combine

Content-Type: application/json

Body:
[
    "((age > 30 AND department = 'Sales'))",
    "(salary > 50000 OR experience > 5)"
]

# Evaluate Rule:

POST http://localhost:8080/api/rules/evaluate

Content-Type: application/json

Body:
{
  "jsonData": {
    "age": 35,
    "department": "Sales",
    "salary": 60000,
    "experience": 3
  },
  "ast": {
    "type": "operator",
    "value": "AND",
    "left": {
      "type": "operator",
      "value": "OR",
      "left": {
        "type": "operator",
        "value": "AND",
        "left": {
          "type": "operand",
          "value": "age > 30"
        },
        "right": {
          "type": "operand",
          "value": "department = 'Sales'"
        }
      },
      "right": {
        "type": "operator",
        "value": "AND",
        "left": {
          "type": "operand",
          "value": "age < 25"
        },
        "right": {
          "type": "operand",
          "value": "department = 'Marketing'"
        }
      }
    },
    "right": {
      "type": "operator",
      "value": "OR",
      "left": {
        "type": "operand",
        "value": "salary > 50000"
      },
      "right": {
        "type": "operand",
        "value": "experience > 5"
      }
    }
  }
}

# 5. Running Unit Tests
You can run unit tests for the project to ensure everything is working correctly:

Open the Project Explorer in IntelliJ IDEA.
Navigate to src/test/java/com/example/ruleengine/.

Right-click on the RuleControllerTests.java and select Run 'RuleControllerTests' to run the unit tests.
Alternatively, you can run tests from the command line using Maven:

mvn test

# Configuration
The application.properties file contains configuration settings for the database and other environment-specific settings.
To access the H2 database console, go to:

http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb, Username: sa, Password: password
