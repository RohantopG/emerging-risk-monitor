from flask import Flask, jsonify, request
from datetime import datetime
from talisman import Talisman

app = Flask(__name__)
Talisman(app, content_security_policy={"default-src": "'self'"}, force_https=False)


# -------------------------
# Health API
# -------------------------
start_time = datetime.utcnow()

def get_uptime():
    return str(int((datetime.utcnow() - start_time).total_seconds())) + " seconds"

@app.route('/health', methods=['GET'])
def health():
    return jsonify({
        "status": "running",
        "model": "llama3",
        "uptime": get_uptime()
    })


# -------------------------
# Describe API (ADD HERE)
# -------------------------
@app.route('/describe', methods=['POST'])
def describe():
    try:
        # Handle JSON input
        if request.is_json:
            data = request.get_json()
            text = data.get("text", "")
        else:
            text = request.get_data(as_text=True)

        if not text:
            return jsonify({"error": "No input provided"}), 400

        response = f"AI Analysis for: {text}"

        return jsonify({
            "result": response
        }), 200

    except Exception as e:
        return jsonify({
            "error": "An internal server error occurred."
        }), 500

@app.errorhandler(500)
def internal_error(error):
    return jsonify({"error": "An internal server error occurred."}), 500


# -------------------------
# Security headers (Day 8)
# -------------------------
@app.route('/')
def home():
    return "AI Service is running"

@app.route('/describe', methods=['POST'])

@app.after_request
def add_headers(response):
    response.headers["X-Content-Type-Options"] = "nosniff"
    response.headers["X-Frame-Options"] = "DENY"
    return response


# -------------------------
# Run server
# -------------------------
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)