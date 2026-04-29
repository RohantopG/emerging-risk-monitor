from flask import Flask, jsonify
from routes.describe import describe_bp
from routes.recommend import recommend_bp
from routes.generate_report import generate_report_bp
import logging

app = Flask(__name__)

# Logging setup
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - [AI-SERVICE] %(message)s"
)

# Register blueprints
app.register_blueprint(describe_bp)
app.register_blueprint(recommend_bp)
app.register_blueprint(generate_report_bp)

# Health check
@app.route("/health", methods=["GET"])
def health():
    return jsonify({
        "status": "ok",
        "service": "ai-service"
    }), 200


# Root route
@app.route("/", methods=["GET"])
def home():
    return jsonify({
        "message": "AI Service is running",
        "endpoints": [
            "/health",
            "/describe",
            "/recommend",
            "/generate-report"
        ]
    }), 200


# Global error handler
@app.errorhandler(Exception)
def handle_exception(e):
    logging.error(f"Unhandled error: {str(e)}")
    return jsonify({
        "status": "error",
        "message": "Internal server error"
    }), 500


if __name__ == "__main__":
    app.run(debug=True)