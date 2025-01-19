from pymongo import MongoClient
from bson.binary import Binary
from typing import Optional

client = MongoClient("mongodb://mongo:27017/")
db = client["image_db"]
collection = db["images"]

def save_image(filename: str, image_bytes: bytes):
    collection.insert_one({"filename": filename, "image": Binary(image_bytes)})

def get_image(filename: str) -> Optional[bytes]:
    result = collection.find_one({"filename": filename})
    if result:
        return result["image"]
    return None
