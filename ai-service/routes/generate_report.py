from flask import Blueprint, request, jsonify

# Create blueprint
generate_report_bp = Blueprint("generate_report", __name__)


@generate_report_bp.route("/generate-report", methods=["POST"])
def generate_report():

    data = request.get_json()

    # Input validation
    if not data or "text" not in data:
        return jsonify({"error": "Invalid input"}), 400

    text = data["text"]

    # Structured response (as per PDF requirement)
    report = {
        "title": "Risk Analysis Report",
        "executive_summary": f"This report summarizes risks related to: {text}",
        "overview": f"The scenario involves potential risks in: {text}",
        "top_items": [
            f"Risk identified in: {text}",
            "Possible system impact",
            "Requires monitoring and mitigation"
        ],
        "recommendations": [
            "Implement monitoring mechanisms",
            "Strengthen security controls",
            "Conduct regular audits"
        ]
    }

    return jsonify({
        "status": "success",
        "report": report
    }), 200