import re
from flask import request, jsonify


def validate_request():
    if request.method == "POST":
        data = request.get_json()

        #  Empty input check
        if not data:
            return jsonify({"error": "Empty input"}), 400

        text = str(data).lower()

        #  SQL Injection detection
        sql_patterns = ["drop", "select", "insert", "delete", "--", ";"]
        if any(pattern in text for pattern in sql_patterns):
            return jsonify({"error": "SQL Injection detected"}), 400

        # Prompt Injection detection
        if "ignore previous instructions" in text:
            return jsonify({"error": "Prompt Injection detected"}), 400

    return None