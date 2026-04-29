from flask import Flask, jsonify
from flask_limiter import Limiter
from flask_limiter.util import get_remote_address

from services.sanitizer import validate_request

# Import routes
from routes.describe import describe_bp
from routes.generate_report import generate_report_bp


app = Flask(__name__)

#GLOBAL RATE LIMITER 
limiter = Limiter(
    key_func=get_remote_address,
    default_limits=["30 per minute"]
)
limiter.init_app(app)


# ✅ REGISTER SANITIZATION (Day 3 + Day 5)
@app.before_request
def before_request():
    response = validate_request()
    if response:
        return response


app.register_blueprint(describe_bp)
app.register_blueprint(generate_report_bp)


@app.route("/health", methods=["GET"])
def health():
    return {"status": "ok"}, 200


#HANDLE RATE LIMIT ERROR
@app.errorhandler(429)
def rate_limit_exceeded(e):
    return jsonify({
        "error": "Too many requests",
        "retry_after": str(e.description)
    }), 429


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)