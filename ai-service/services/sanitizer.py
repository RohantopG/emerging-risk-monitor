import re
from flask import request, jsonify

# Regex to remove HTML tags
HTML_TAG_PATTERN = re.compile(r"<.*?>")

# Prompt injection patterns
INJECTION_PATTERNS = [
    r"ignore previous instructions",
    r"system prompt",
    r"you are chatgpt",
    r"act as",
    r"pretend to",
    r"bypass",
    r"jailbreak",
    r"developer mode",
    r"override",
    r"reset instructions"
]


def sanitize_input(value: str) -> str:
    """Remove HTML tags and trim spaces"""
    if not isinstance(value, str):
        return value

    value = re.sub(HTML_TAG_PATTERN, "", value)
    return value.strip()


def detect_injection(value: str) -> bool:
    """Detect prompt injection attempts"""
    value = value.lower()

    for pattern in INJECTION_PATTERNS:
        if re.search(pattern, value):
            return True
    return False


def validate_request():
    """Global request validation middleware"""

    if request.method in ["POST", "PUT"]:
        data = request.get_json(silent=True)

        if data is None:
            return jsonify({
                "error": "Invalid or missing JSON body"
            }), 400

        sanitized_data = {}

        for key, value in data.items():

            if isinstance(value, str):

                # Detect prompt injection
                if detect_injection(value):
                    return jsonify({
                        "error": f"Prompt injection detected in field '{key}'"
                    }), 400

                # Sanitize input
                sanitized_data[key] = sanitize_input(value)

            else:
                sanitized_data[key] = value

        # Replace request JSON safely
        request._cached_json = sanitized_data