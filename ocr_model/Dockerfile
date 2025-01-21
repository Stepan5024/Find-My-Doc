FROM python:3.10-slim as builder

RUN apt-get update && apt-get install -y git && rm -rf /var/lib/apt/lists/*

RUN git clone --depth 1 https://github.com/something-original/doctr.git /app/doctr

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

ENV PYTHONPATH=/app

EXPOSE 8080
ENTRYPOINT ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8080"]