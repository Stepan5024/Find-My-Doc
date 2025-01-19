import requests

from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from model import load_model, predict_text
from database import save_image, get_image

app = FastAPI()

model = load_model("doctr_parseq")

class ImageRequest(BaseModel):
    original: str
    copy: str

class TextResponse(BaseModel):
    sen1: str
    sen2: str

@app.post("/recognize-text/", response_model=TextResponse)
async def recognize_text(request: ImageRequest):
    original_image = get_image(request.original)
    copy_image = get_image(request.copy)

    if original_image is None:
        original_image = download_image(request.original)
        save_image(request.original, original_image)

    if copy_image is None:
        copy_image = download_image(request.copy)
        save_image(request.copy, copy_image)

    text1 = predict_text(model, original_image)
    text2 = predict_text(model, copy_image)

    return {"sen1": text1, "sen2": text2}

def download_image(url: str) -> bytes:
    response = requests.get(url)
    if response.status_code == 200:
        return response.content
    else:
        raise HTTPException(status_code=400, detail="Failed to download image")
