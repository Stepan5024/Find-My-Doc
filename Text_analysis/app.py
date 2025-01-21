from flask import Flask, request, jsonify
from test import calculate_similarity
from form_comparison import compare_forms

app = Flask(__name__)



@app.route("/similarity/words", methods=["POST"])
def words_sim():
    sentences = request.get_json()
    model_path = "Text_analysis\models\mpnet-base-all-nli-triplet\Final"  # Replace with your model path

    sentence1 = sentences["sen1"]
    sentence2 = sentences["sen2"]

    similarity = calculate_similarity(model_path, sentence1, sentence2)

    if similarity is not None:
        return jsonify({"similarity": similarity}), 200
    else:
        return jsonify({"reason": "Similarity calculation failed."}), 400

@app.route("/similarity/forms", methods=["POST"])
def forms_sim():
    forms = request.get_json()
    model_path = "Text_analysis\models\mpnet-base-all-nli-triplet\Final"  # Replace with your model path

    sentence1 = forms["form1"]
    sentence2 = forms["form2"]

    forms_similarity, overall_similarity = compare_forms(model_path, sentence1, sentence2)

    if overall_similarity is not None:
        return jsonify({"forms_similarity": forms_similarity, "overall_forms_similarity": overall_similarity}), 200
    else:
        return jsonify({"reason": "Similarity calculation failed."}), 400



if __name__ == "__main__":
    # запускаем сервер
    app.run(debug=True, port=8085)