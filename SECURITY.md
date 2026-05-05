# Emerging Risk Monitor - Security Documentation (SECURITY.md)

**[FINAL VERSION FOR DEMO DAY]**

This document tracks the security architecture, risk scenarios, and mitigation strategies for the Emerging Risk Monitor capstone project. It is complete, accurate, and approved for Demo Day distribution.


## 1. OWASP Top 10 Risks (Day 1 Task)

### Broken Access Control  
An attacker accesses the API endpoints without a valid JWT token, attempting to read or modify emerged risks.  
Mitigation plan: Implement JWT authentication on the Spring Boot backend using Spring Security. Reject unauthenticated API requests with 401.

---

### Injection (SQL/Prompt)  
An attacker injects malicious SQL statements into a form field, or overrides the structural AI prompt.  
Mitigation plan: Use parameterized queries via JPA/Hibernate in Java. Implement input sanitisation middleware in Flask to strip tags.

---

### Insecure Design  
The system lacks proper rate limits, allowing an attacker to launch a DoS attack to exhaust Groq AI credits.  
Mitigation plan: Implement IP-based rate limiting (via `flask-limiter`) across all AI service endpoints.

---

### Security Misconfiguration  
Unnecessary ports are exposed publicly, or default credentials are left in the Docker Compose setup.  
Mitigation plan: Restrict exposed ports in Docker Compose. Use `.env` files for secrets and ensure `.env` is inside `.gitignore`.

---

### Vulnerable and Outdated Components  
The system runs on outdated dependencies (e.g., outdated Flask or Spring Boot versions) with known CVEs.  
Mitigation plan: Define exact version numbers in `pom.xml` and `requirements.txt`. Periodically scan with OWASP ZAP.

---

### Identification and Authentication Failures  
Weak passwords or missing authentication checks allow attackers to log in as valid users.  
Mitigation plan: Enforce strong password policies, use secure JWT handling, and validate authentication for every protected API.

---

### Software and Data Integrity Failures  
Application code, dependencies, or data may be tampered with during updates or communication.  
Mitigation plan: Use trusted sources for dependencies, verify integrity, and validate all incoming data before processing.

---

### Security Logging and Monitoring Failures  
Suspicious activities are not detected due to missing or weak logging.  
Mitigation plan: Add proper logging for important actions and monitor logs regularly to detect unusual behavior.

---

### Server-Side Request Forgery (SSRF)  
The application makes unintended requests to internal or external systems based on user input.  
Mitigation plan: Validate all external requests and never allow direct use of user input in server-side calls.

---

### Cryptographic Failures  
Sensitive data is stored or transmitted without proper protection.  
Mitigation plan: Use HTTPS for all communication and encrypt sensitive data. Avoid storing secrets in plain text.



## 2. Tool-Specific Security Threats (Day 2 Task)

### Prompt Injection / Jailbreaks  
A user passes instructions like “Ignore previous instructions” in the input sent to the AI model.  
Damage potential: High — This can lead to unsafe outputs or exposure of internal prompt logic.  
Mitigation plan: Add Flask middleware to check all incoming inputs, detect such patterns, and reject the request with a 400 error.

---

### Rate Limit Exhaustion  
A user sends repeated requests to heavy endpoints like `/generate-report` from the same IP.  
Damage potential: High — This can quickly use up API credits and slow down the system.  
Mitigation plan: Apply strict rate limits using `flask-limiter`, such as 10 requests per minute for heavy APIs and 30 requests per minute for others.

---

### Reflected Cross-Site Scripting (XSS)  
A user submits `<script>` tags in input fields which later get displayed in the frontend.  
Damage potential: Medium — This can execute malicious scripts in the browser and affect users.  
Mitigation plan: Remove HTML tags on the backend and ensure proper output handling in the React frontend.

---

### Internal API Exposure  
The Flask AI service is accessed directly from outside on port 5000, bypassing the main backend.  
Damage potential: Medium — This skips authentication and core business logic.  
Mitigation plan: Keep the Flask service internal within Docker and only expose the Spring Boot backend (port 8080).

---

### Sensitive Data Exposure (PII)  
Users enter personal data into the AI prompt, which gets sent to external AI services.  
Damage potential: High — This can lead to privacy and compliance issues.  
Mitigation plan: Validate inputs and ensure no personal data is sent to the AI model. Restrict usage to relevant risk-related data only.

## 3. Executive Summary (Day 14)

The Emerging Risk Monitor application has undergone a comprehensive security hardening and testing process. We have systematically addressed the OWASP Top 10 vulnerabilities, applied strict rate limiting to protect AI API quotas, implemented robust JWT authentication, and sanitized inputs against prompt injection and XSS. An active OWASP ZAP scan was conducted, resulting in the successful remediation of all detected Medium findings (via Spring Security configurations and `flask-talisman`). No Critical or High vulnerabilities remain. The system is now deemed secure for production deployment, provided that the residual risks are continuously monitored.

## 4. Tests Conducted and Verified (Day 13 & 14)

The following full-stack security tests were conducted and verified successfully:
- **401 Unauthorized:** API access without a valid JWT token is successfully rejected with a 401 status.
- **403 Forbidden:** API access with a valid token but an incorrect role is rejected with a 403 status.
- **XSS in Input Field:** Malicious `<script>` tags injected into form fields are sanitized and rendered harmless.
- **429 Too Many Requests:** Exceeding the allowed API rate limits triggers a 429 status, successfully preventing rate limit exhaustion and protecting AI service credits.

## 5. Findings Fixed (Day 14)

All findings from the baseline and active OWASP ZAP scans have been addressed:
- **Missing Anti-clickjacking Header:** Fixed by enforcing `X-Frame-Options: DENY`.
- **CSP Header Not Set:** Fixed by enforcing a strict `default-src 'self'` Content Security Policy.
- **Application Error Disclosure:** Fixed by implementing global exception handlers that return generic 500 error messages instead of raw stack traces.

## 6. Residual Risks (Day 14)

- **Zero-day AI vulnerabilities:** Prompt injection techniques evolve rapidly. While current mitigation blocks known jailbreaks, new techniques may bypass existing filters.
- **Third-Party API Dependency:** Dependency on Groq API implies that if their service is compromised or experiences downtime, our AI functionalities will be impacted.

## 7. Final Security Checklist (Day 15)

- [x] JWT Authentication & Authorization implemented and verified.
- [x] Rate limiting enforced on all AI endpoints.
- [x] Input sanitization (XSS and Prompt Injection) active.
- [x] Missing security headers (CSP, X-Frame-Options) added via `flask-talisman` and Spring Security.
- [x] Exception handling prevents stack trace leaks.
- [x] PII Audit completed; no sensitive data is leaked to logs or external APIs.
- [x] OWASP ZAP Active Scan confirms zero Critical/High/Medium vulnerabilities.

## 8. Team Sign-Off (Day 14 & Day 15)

All 6 team members have reviewed the security architecture, test results, and residual risks, and provide their sign-off for deployment:

1. **[Signed]** - Lead Developer
2. **[Signed]** - AI Engineer
3. **[Signed]** - Backend Specialist
4. **[Signed]** - Frontend Developer
5. **[Signed]** - QA/Security Analyst
6. **[Signed]** - Project Manager

*Final version committed and approved for production.*