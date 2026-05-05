# Security Talking Points

- **JWT Authentication & Access Control:** We implemented a robust JWT-based authentication system across the Spring Boot backend to strictly enforce role-based access control, effectively mitigating Broken Access Control risks and ensuring only authorized personnel can access sensitive risk data.
- **API Rate Limiting:** To protect our application against Denial of Service (DoS) attacks and to prevent the exhaustion of our Groq AI API credits, we applied strict IP-based rate limiting across all AI service endpoints.
- **Input Sanitization & Injection Prevention:** We successfully integrated comprehensive input sanitization middleware to neutralize Cross-Site Scripting (XSS) and mitigate AI Prompt Injection attacks, guaranteeing the integrity of our data and AI responses.
- **OWASP ZAP Results & Header Security:** Following comprehensive baseline and active OWASP ZAP scans, we remediated all findings by integrating `flask-talisman` and Spring Security to enforce strict HTTP headers (like CSP and X-Frame-Options), achieving a clean scan with zero remaining Critical, High, or Medium vulnerabilities.
