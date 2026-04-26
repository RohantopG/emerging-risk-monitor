from flask import Flask, jsonify
from routes.describe import describe_bp
import logging

app = Flask(__name__)

# Logging setup
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s"
)

# Register blueprint
app.register_blueprint(describe_bp)

# Health check
@app.route("/health")
def health():
    return jsonify({"status": "ok"}), 200

# Global error handler
@app.errorhandler(Exception)
def handle_exception(e):
    logging.error(f"Unhandled error: {str(e)}")
    return jsonify({
        "error": "Internal server error"
    }), 500

if __name__ == "__main__":
    app.run(debug=True)