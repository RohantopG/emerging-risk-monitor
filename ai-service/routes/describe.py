from flask import Blueprint, request, jsonify
import time
import logging

describe_bp = Blueprint("describe", __name__)

# Simple in-memory rate limiting
request_log = {}

RATE_LIMIT = 5       # requests
TIME_WINDOW = 60     # seconds

def is_rate_limited(ip):
    current_time = time.time()
    if ip not in request_log:
        request_log[ip] = []

    # Remove old requests
    request_log[ip] = [
        t for t in request_log[ip]
        if current_time - t < TIME_WINDOW
    ]

    if len(request_log[ip]) >= RATE_LIMIT:
        return True

    request_log[ip].append(current_time)
    return False


@describe_bp.route("/describe", methods=["POST"])
def describe():
    client_ip = request.remote_addr

    # Rate limit check
    if is_rate_limited(client_ip):
        return jsonify({
            "error": "Too many requests. Try again later."
        }), 429

    data = request.get_json()

    # Validation
    if not data or "text" not in data:
        return jsonify({"error": "Invalid input"}), 400

    text = data["text"]

    if not isinstance(text, str) or len(text.strip()) == 0:
        return jsonify({"error": "Text must be non-empty"}), 400

    if len(text) > 500:
        return jsonify({"error": "Input too long"}), 400

    # Prompt injection protection
    blocked_words = ["ignore previous", "system prompt", "override", "bypass"]

    for word in blocked_words:
        if word in text.lower():
            logging.warning(f"Blocked malicious input: {text}")
            return jsonify({"error": "Unsafe input detected"}), 400

    # Load prompt
    try:
        with open("prompts/describe.txt", "r") as f:
            prompt_template = f.read()
    except Exception as e:
        logging.error(f"Prompt file error: {str(e)}")
        prompt_template = "Describe the following input: {input}"

    final_prompt = prompt_template.replace("{input}", text)

    logging.info(f"Processed request from {client_ip}")

    return jsonify({
        "status": "success",
        "input": text,
        "generated_prompt": final_prompt,
        "description": f"Processed safely: {text}"
    }), 200