# Lab 2 Web Server -- Project Report

## What I specified

Tasks:
1. Custom whitelabel error page 
2. Custom `/time` endpoint that returns the current time in JSON format
3. Enable HTTP/2 and TLS 

Acceptance criteria:
1. Whitelabel error page:
   - Displays a custom error page when a user navigates to a non-existent page.
   - Unit tests verify that the error page is returned for unknown paths with the correct body content.
2. Custom `/time` endpoint:
   - Returns the current server time in JSON format when accessed.
   - Unit tests verify that the `/time` endpoint returns a valid JSON response with the current time.
   - Unit tests verify that the `/time` endpoint returns the exact time when a specific time provider is used.
3. Enable HTTP/2 and TLS:
   - The server is configured to support HTTP/2 and TLS. Checked via curl commands or other means to ensure that the server is accessible over HTTPS and supports HTTP/2.

## What I changed

Created the following files:
- `src/main/resources/templates/error.html`
  - Custom error page template that is displayed when a user navigates to a non-existent page.
- `src/main/kotlin/es/unizar/webeng/lab2/TimeComponent.kt`
  - Provides a component that returns the current time in JSON format when accessed via the `/time` endpoint.
- `src/main/resources/application.yml`
  - Configuration file that enables HTTP/2 and TLS for the server.
- `src/test/resources/application.yml`
  - Test configuration file disabling SSL for unit tests to avoid certificate issues during testing.
- `src/test/kotlin/es/unizar/webeng/lab2/ErrorPageTest.kt`
  - Unit tests for the custom error page, verifying that it is returned for unknown paths with the correct body content.
- `src/test/kotlin/es/unizar/webeng/lab2/TimeControllerTest.kt`
  - Unit tests for the `/time` endpoint, verifying that it returns a valid JSON response with the current time and that it returns the exact time when a specific time provider is used.
- `openssl-localhost.cnf`
  - Configuration file for generating the self-signed certificate.
- `src/main/resources/localhost.p12`

Edited the following files:
- `build.gradle.kts`
  - Added dependency `testImplementation(kotlin("test"))` for assertNotNull method.
    - Extended explanation: Equivalent to the junit assertion `assertNotNull` already included in the starter code, but this method benefits from Kotlin's smart casting, allowing for more concise code without the need for explicit casting.
      - After using `assertNotNull(response)`, `response` is automatically treated as non-nullable, eliminating the need for the `!!` operator to cast it to a non-nullable value.

## Technical decisions

Technical decisions included in the guide:
- Used TestRestTemplate instead of MockMvc for testing ErrorPageTest to render error.html content and verify the response body.
- Added extra assertions with error details in ErrorPageTest to ensure the response body is not empty and contains the expected text.
- `/time` endpoint returns the current time in JSON format.
- Decoupling the `TimeProvider` interface from the `TimeController` to allow for easier testing and mocking of the current time.
- Added TimeControllerTest to verify the `/time` endpoint returns the correct JSON response and handles a specific time provider correctly.
- Included IP:127.0.0.1 and DNS:localhost on the certificate to fulfill moderns TLS standards

## How I verified

Once the all new functionalities were implemented, the following steps were taken to verify its correctness:
1. Run `./gradlew check` to execute all unit tests and ensure they pass successfully.
2. Run `./gradlew ktintCheck` to check for code style issues and ensure that the code adheres to the specified coding standards.
3. Use `curl` commands to verify the server's behavior:
   - For the custom error page:
     - Run `curl -v --http2 -k -H "Accept: text/html" -i https://127.0.0.1:8443/` to check that the server returns the custom error page for unknown paths.
   - For the `/time` endpoint:
     - Run `curl -v --http2 -k -i https://127.0.0.1:8443/time` to check that the server returns the current time in JSON format.
   - If curl command do not support --http2, it is possible to check in browser by navigating to the path and checking the network tab in the developer tools to verify that the server is accessible over HTTPS and supports HTTP/2 protocol.
   
Not many problems were encountered during the implementation, but some minor issues were faced:
- Working in W11, curl --http2 option was not supported, so it was necessary to check the HTTP/2 support in browser. To double verify, also booted in an Ubuntu machine and checked with curl --http2 option, which worked fine.
- Many style issues were found when running `./gradlew ktintCheck`, which some were fixed manually and other running `./gradlew ktlintFormat`,to ensure code adheres to the specified coding standards.

## AI disclosure

- **Tools / skills:**
  - GitHub Copilot (extension for IntelliJ IDEA) for code completion, suggestions, explanations, and editing.
- **Purpose:**
  - AI was used to explore different implementation options, fix errors, and improve the quality of the report. 
- **Representative prompts:**
  - "Why the dependency X is not recognized in the build.gradle.kts file?"
  - "How does the smart casting work here?"
  - "Check the redaction of the report without changing the meaning of the text."
- **Affected files/sections:**
  - All edited files were affected by AI to some extent, but the final implementation was reviewed by the author, and all suggestions were either accepted, modified, or rejected.
- **Validation steps:**
  - All suggestions were reviewed by the author and either accepted, modified, or rejected. The final implementation was tested and manually verified to ensure correctness.
- **Citations:**
  - No external sources were used for this lab.
- **Human-reviewed:**
  - All code and the report were reviewed by the author to ensure correctness and clarity. If any code was unclear or not understandable, I would have asked for clarification or rejected the suggestion.

