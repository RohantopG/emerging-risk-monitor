from flask import Blueprint, request, jsonify

describe_bp = Blueprint("describe", __name__)


@describe_bp.route("/describe", methods=["POST"])
def describe():
    data = request.get_json()

    return jsonify({
        "description": "Generated description",
        "input": data
    }), 200