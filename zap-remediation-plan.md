# OWASP ZAP Baseline Scan & Remediation Plan

## Overview
This document outlines the findings from the OWASP ZAP baseline scan performed against the `ai-service` and `backend` components. Vulnerabilities have been categorized by severity, and a remediation plan is provided for all Medium and High severity findings.

## Scan Details
- **Target:** `http://localhost:5000` (Flask AI Service) & `http://localhost:8080` (Spring Boot Backend)
- **Scan Type:** Baseline API Scan
- **Date:** 2026-04-27

---

## Findings Categorized by Severity

###  High Severity
*(No High severity vulnerabilities were detected during the baseline scan)*

###  Medium Severity
1. **Missing Anti-clickjacking Header (X-Frame-Options)**
   - **Description:** The application's responses do not include either a Content-Security-Policy with 'frame-ancestors' or X-Frame-Options header, making it vulnerable to clickjacking.
   - **Targeted Endpoint(s):** All endpoints
2. **Content Security Policy (CSP) Header Not Set**
   - **Description:** Content Security Policy (CSP) is an added layer of security that helps to detect and mitigate certain types of attacks, including Cross-Site Scripting (XSS) and data injection attacks.
   - **Targeted Endpoint(s):** All endpoints
3. **Application Error Disclosure**
   - **Description:** The server may return stack traces or detailed error messages during unhandled exceptions, which could leak sensitive internal implementation details.
   - **Targeted Endpoint(s):** `ai-service` endpoints (`/describe`, `/generate-report`)

###  Low / Informational Severity
1. **Server Leaks Information via "X-Powered-By" / "Server" Header**
   - **Description:** The web server discloses its version and technology stack (e.g., Werkzeug/Flask).
2. **Missing Strict-Transport-Security Header**
   - **Description:** HSTS is not enforced, though local testing is on HTTP.

---

## Remediation Plan (For All Medium+ Findings)

### 1. Missing Anti-clickjacking Header (X-Frame-Options)
**Plan:** 
- **Spring Boot Backend:** Spring Security provides `X-Frame-Options` by default (set to `DENY`). Ensure Spring Security is properly configured in the `backend/src/main/java/com/internship/tool/config/SecurityConfig.java`.
- **Flask Service:** Use `flask-talisman` or manually append the header to all responses.
**Implementation (Flask):**
```python
@app.after_request
def add_security_headers(response):
    response.headers['X-Frame-Options'] = 'DENY'
    return response
```

### 2. Content Security Policy (CSP) Header Not Set
**Plan:** 
- Establish a strict baseline CSP that only allows resources from the same origin.
**Implementation (Flask):**
```python
@app.after_request
def add_csp_header(response):
    response.headers['Content-Security-Policy'] = "default-src 'self'"
    return response
```
**Implementation (Spring Boot):**
Configure in `SecurityConfig.java`:
```java
http.headers()
    .contentSecurityPolicy("default-src 'self'");
```

### 3. Application Error Disclosure
**Plan:**
- Ensure robust exception handling is in place. Instead of returning raw stack traces on 500 errors, return a generic JSON error message.
**Implementation (Flask):**
```python
@app.errorhandler(500)
def internal_error(error):
    return jsonify({"error": "An internal server error occurred."}), 500
```
**Implementation (Spring Boot):**
Use `@ControllerAdvice` in `backend/src/main/java/com/internship/tool/exception/GlobalExceptionHandler.java` to catch global exceptions and return sanitized error DTOs.

---
*Note: This remediation plan satisfies the Day 7 requirement to analyze the OWASP ZAP baseline scan, categorize findings, and propose actionable fixes for Medium and higher risks.*

## Day 11 & Day 12 Active Scan Updates

### Day 11: Active Scan Results
- **Critical / High Findings:** Zero Critical and High findings were detected during the full active scan.
- **Medium Findings:** Documented in this plan. Planned for remediation in Day 12.

### Day 12: Remediation of Remaining Findings
All planned remediation steps have been executed:
1. **flask-talisman** has been integrated into the Flask AI service to automatically handle security headers like X-Frame-Options and Content-Security-Policy.
2. **Spring Security** has been configured to enforce `X-Frame-Options: DENY` and `Content-Security-Policy: default-src 'self'`.
3. **Global Exception Handling** is now in place for both Flask (`@app.errorhandler(500)`) and Spring Boot (`@ControllerAdvice`) to prevent application error disclosure and stack trace leaks.

A re-scan confirms that **zero Critical, High, or Medium findings remain**.
