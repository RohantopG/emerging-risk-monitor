from flask import Blueprint, request, jsonify
import os

recommend_bp = Blueprint("recommend", __name__)

def load_prompt():
    try:
        base_dir = os.path.dirname(os.path.abspath(__file__))
        prompt_path = os.path.join(base_dir, "../prompts/recommend.txt")

        with open(prompt_path, "r") as file:
            return file.read()
    except Exception:
        return None


@recommend_bp.route("/recommend", methods=["POST"])
def recommend():

    data = request.get_json()

    if not data or "text" not in data:
        return jsonify({"error": "Invalid input"}), 400

    text = data["text"]

    # Load prompt
    prompt_template = load_prompt()

    if not prompt_template:
        return jsonify({"error": "Prompt file not found"}), 500

    final_prompt = prompt_template.replace("{text}", text)

    # Dummy response (PDF compliant)
    recommendations = [
        {
            "action_type": "Monitor",
            "description": f"Monitor risk related to: {text}",
            "priority": "HIGH"
        },
        {
            "action_type": "Mitigate",
            "description": f"Take mitigation steps for: {text}",
            "priority": "MEDIUM"
        }
    ]

    return jsonify({
        "status": "success",
        "prompt_used": final_prompt,   # 👈 useful for debugging/demo
        "recommendations": recommendations
    }), 200