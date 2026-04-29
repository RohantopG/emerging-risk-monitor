from flask import Blueprint, request, jsonify
from flask_limiter.util import get_remote_address
from flask_limiter import Limiter

generate_report_bp = Blueprint("generate_report", __name__)

# ⚠️ Separate limiter instance for route-level control
limiter = Limiter(key_func=get_remote_address)


@generate_report_bp.route("/generate-report", methods=["POST"])
@limiter.limit("10 per minute")  #  STRICT LIMIT (Day 4)
def generate_report():
    data = request.get_json()

    return jsonify({
        "title": "AI Generated Report",
        "executive_summary": "Summary generated successfully",
        "data": data
    }), 200