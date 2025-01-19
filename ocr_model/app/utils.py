import magic

def check_file_type(file_path) -> str:
    
    mime = magic.Magic(mime=True)
    file_type = mime.from_file(file_path)
    if file_type == "application/pdf":
        return "pdf"
    elif file_type.startswith("image/"):
        return "image"
    else:
        return "wrong format"
