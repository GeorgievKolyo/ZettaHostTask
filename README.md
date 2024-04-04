Java Assignment

You will develop a simple foreign exchange application which is one of the most common services used in financial applications. Requirements are as follows:<br />

Mandatory Functional Requirements:<br />

Exchange Rate Endpoint:<br />
• Input: A pair of currency codes (e.g., USD to EUR).<br />
• Output: The current exchange rate between the two currencies.<br />
Currency Conversion Endpoint:<br />
• Input: An amount in the source currency, source currency code, and target currency code.<br />
• Output: The converted amount in the target currency and a unique transaction identifier.<br />
Conversion History Endpoint:<br />
• Input: A transaction identifier or a transaction date for filtering purposes (at least one must be provided).<br />
• Output: A paginated list of currency conversions filtered by the provided criteria.<br />
External Exchange Rate Integration:<br />
• The application must utilize an external service provider for fetching exchange rates and optionally for performing the currency conversion calculations.<br />
Error Handling:<br />
• Errors must be handled gracefully, providing meaningful error messages and specific error codes.<br />

Mandatory Technical Requirements:<br />

Self-Contained Application:<br />
• The application should require no additional setup or configuration to run.<br />
RESTful API Design:<br />
• Develop the service endpoints following REST principles using Spring Boot.<br />
Build & Dependency Management:<br />
• Use appropriate tools for building the project and managing dependencies.<br />
Use of Design Patterns:<br />
• Implement design patterns appropriately to enhance code quality.<br />
Code Structure:<br />
• The code should be organized to reflect a clear separation of concerns.<br />
Unit Testing:<br />
• Include unit tests to ensure the reliability and robustness of the application.<br />
API Documentation:<br />
• Provide complete and accurate documentation for the API, including request and response examples.<br />
Docker:<br />
• Containerize the application with Docker to ensure consistency across different environments.<br />
Proper Use of a Git Repository:<br />
• Maintain the code in a Git repository with a clear history of commits, adhering to best practices for version control.<br />

Optional Features:<br />

API Documentation Tooling:<br />
• Utilize tools like Swagger or OpenAPI for generating interactive API documentation.<br />
Caching:<br />
• Apply caching strategies to improve performance, particularly for exchange rate data.<br />
Rate Limiting:<br />
• Implement rate limiting on the APIs to prevent abuse.<br />

Hints and Suggestions:<br />

FX Rate Service Providers:<br />
• You may select any service provider for exchange rates such as currencylayer.com or fixer.io, considering the limitations of their free offerings.<br />
Spring Boot Resources:<br />
• Refer to Spring Boot's official guides for quick reference and tutorials.<br />
In-Memory Database:<br />
• An in-memory database can be employed for demonstration purposes and ease of setup.<br />

The detailed API documentation is crucial for users to understand how to interact with
the service effectively. Ensure that the Git repository reflects a professional approach to
software development with meaningful commit messages, feature branches, and pull
requests where applicable.<br />

Time for completion 5-7 working days<br />
