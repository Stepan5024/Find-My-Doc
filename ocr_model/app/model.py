import torch
import os
import subprocess

from pathlib import Path
from ..doctr.models.detection.predictor import DetectionPredictor
from ..doctr.models.recognition.predictor import RecognitionPredictor
from ..doctr.models.preprocessor import PreProcessor
from ..doctr.models import db_resnet50, parseq, master, crnn_mobilenet_v3_small, ocr_predictor
from ..doctr.io import DocumentFile
from ..doctr.datasets.vocabs import VOCABS
from utils import check_file_type


def load_model(model_name: str):

    current_path = Path(__file__).resolve().parent
    models_dir = "models"
    models_path = os.path.join(current_path, models_dir)

    if not os.path.exists(models_path):
        os.makedirs(models_path)

    model_url = f'https://huggingface.co/smthrgnl/{model_name}/resolve/main/{model_name}.pt'
    subprocess.run(["wget", model_url, "-O", models_path], check=True)
    model_path = os.path.join(models_path, model_name)
    
    detection_model = db_resnet50(pretrained=True, pretrained_backbone=False)
    if model_name == 'ocr_crnn_mobilenet_v3_small':
        recognition_model = crnn_mobilenet_v3_small(pretrained=False, pretrained_backbone=False, vocab=VOCABS['russian'])
    if model_name == 'doctr_parseq':
        recognition_model = parseq(pretrained=False, pretrained_backbone=False, vocab=VOCABS['russian'])
    if model_name == 'doctr_master':
        recognition_model = master(pretrained=False, pretrained_backbone=False, vocab=VOCABS['russian'])

    recognition_params = torch.load(model_path, map_location="cuda:0", weights_only=False)
    recognition_model.load_state_dict(recognition_params, strict=False)
    ocr_model = ocr_predictor(detection_model, recognition_model)
    
    return ocr_model


def predict_text(ocr_model, file_path) -> str | None:

    file_type = check_file_type(file_path)
    if file_type == 'pdf':
        document = DocumentFile.from_pdf(file_path)
    elif file_type == 'image':
        document = DocumentFile.from_image(file_path)
    else:
        return None

    text = ocr_model(document).render()
    return text
