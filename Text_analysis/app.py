from flask import Flask, request, jsonify
from test import calculate_similarity

app = Flask(__name__)



@app.route("/similarity", methods=["POST"])
def send():
    sentences = request.get_json()
    model_path = "Text_analysis\models\mpnet-base-all-nli-triplet\Final"  # Replace with your model path

    sentence1 = sentences["sen1"]
    sentence2 = sentences["sen2"]

    similarity = calculate_similarity(model_path, sentence1, sentence2)

    if similarity is not None:
        return jsonify({"similarity": similarity}), 200
    else:
        return jsonify({"reason": "Similarity calculation failed."}), 400



if __name__ == "__main__":
    # запускаем сервер
    app.run(debug=True, port=8085)