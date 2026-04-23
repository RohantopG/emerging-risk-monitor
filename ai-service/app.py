from flask import Flask
from services.sanitizer import validate_request

app = Flask(__name__)

#  Register middleware globally
@app.before_request
def before_request():
    response = validate_request()
    if response:
        return response


# Sample route (keep your existing routes)
@app.route("/health", methods=["GET"])
def health():
    return {"status": "ok"}, 200


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)