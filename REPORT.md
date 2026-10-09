# Lab 2 Web Server -- Project Report

## What I specified

Tasks:
1. Custom Whitelabel error page
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
   - The server is configured to support HTTP/2 and TLS. This was checked via curl commands or other means to ensure that the server is accessible over HTTPS and supports HTTP/2.

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
  - Self-signed certificate used by the server for TLS.

Edited the following files:
- `build.gradle.kts`
  - Added dependency `testImplementation(kotlin("test"))` for the `assertNotNull` method.
    - Extended explanation: This is equivalent to the JUnit assertion `assertNotNull`, which was already included in the starter code, but this method benefits from Kotlin's smart casting. This allows for more concise code without explicit casting.
      - After using `assertNotNull(response)`, `response` is automatically treated as non-nullable, eliminating the need for the `!!` operator.

## Technical decisions

The technical decisions included in the implementation were:
- Used TestRestTemplate instead of MockMvc for testing ErrorPageTest to render error.html content and verify the response body.
- Added extra assertions with error details in ErrorPageTest to ensure the response body is not empty and contains the expected text.
- `/time` endpoint returns the current time in JSON format.
- Decoupled the `TimeProvider` interface from the `TimeController` to allow for easier testing and mocking of the current time.
- Added TimeControllerTest to verify the `/time` endpoint returns the correct JSON response and handles a specific time provider correctly.
- Included IP:127.0.0.1 and DNS:localhost in the certificate to fulfill modern TLS standards.

## How I verified

Once all the new functionalities were implemented, the following steps were taken to verify their correctness:
1. Run `./gradlew check` to execute all unit tests and ensure they pass successfully.
2. Run `./gradlew ktintCheck` to check for code style issues and ensure that the code adheres to the specified coding standards.
3. Use `curl` commands to verify the server's behavior:
   - For the custom error page:
     - Run `curl -v --http2 -k -H "Accept: text/html" -i https://127.0.0.1:8443/` to check that the server returns the custom error page for unknown paths.
   - For the `/time` endpoint:
     - Run `curl -v --http2 -k -i https://127.0.0.1:8443/time` to check that the server returns the current time in JSON format.
   - If the curl command does not support `--http2`, it is possible to check in a browser by navigating to the path and checking the Network tab in the developer tools to verify that the server is accessible over HTTPS and supports the HTTP/2 protocol.
   
Not many problems were encountered during the implementation, but some minor issues were faced:
- While working in Windows 11, the `curl --http2` option was not supported, so it was necessary to check HTTP/2 support in a browser. To verify this further, the server was also started on an Ubuntu machine and checked with the `curl --http2` option, which worked correctly.
- Many style issues were found when running `./gradlew ktintCheck`. Some were fixed manually, while others were fixed by running `./gradlew ktlintFormat` to ensure that the code adheres to the specified coding standards.

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
