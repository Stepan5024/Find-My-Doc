FROM python:3.9-slim as builder
RUN apt-get update && apt-get install -y git && rm -rf /var/lib/apt/lists/*
RUN git clone https://github.com/something-original/doctr.git /app

WORKDIR /app
RUN git remote add upstream https://github.com/mindee/doctr.git && \
    git fetch upstream && \
    git merge upstream/main

FROM python:3.9-slim
WORKDIR /app

COPY --from=builder /app .

RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 8080

CMD ["uvicorn", "app.main:app", "--host", "127.0.0.1", "--port", "8080"]
