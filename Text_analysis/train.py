# !pip uninstall transformers sentence-transformers
# !pip install transformers==4.45.2 sentence-transformers==3.1.1
from datasets import load_dataset
from sentence_transformers import (
    SentenceTransformer,
    SentenceTransformerTrainer,
    SentenceTransformerTrainingArguments,
    SentenceTransformerModelCardData,
)
from sentence_transformers.losses import CoSENTLoss, CosineSimilarityLoss
from sentence_transformers.training_args import BatchSamplers
from sentence_transformers.evaluation import EmbeddingSimilarityEvaluator
import torch
torch.cuda.empty_cache()
from preprocessing import test_dataset, val_dataset, train_dataset

model = SentenceTransformer("ai-forever/ru-en-RoSBERTa")

loss = CosineSimilarityLoss(model)

args = SentenceTransformerTrainingArguments(
    # Required parameter:
    output_dir="\Text_analysis\models\mpnet-base-all-nli-triplet",
    # Optional training parameters:
    num_train_epochs=1,
    per_device_train_batch_size=16,
    per_device_eval_batch_size=16,
    learning_rate=2e-5,
    warmup_ratio=0.1,
    fp16=True,  # Set to False if you get an error that your GPU can't run on FP16
    bf16=False,  # Set to True if you have a GPU that supports BF16
    # Optional tracking/debugging parameters:
    eval_strategy="steps",
    eval_steps=100,
    save_strategy="steps",
    save_steps=100,
    save_total_limit=2,
    logging_steps=100,
    run_name="mpnet-base-all-nli-triplet",  # Will be used in W&B if `wandb` is installed
)
# Проверка типов данных
for i in train_dataset:
    if not isinstance(i["text1"], str) or not isinstance(i["text2"], str) or not isinstance(i["label"], float):
        raise ValueError(f"Incorrect data types in train_dataset for entry {i}")

for i in val_dataset:
    if not isinstance(i["text1"], str) or not isinstance(i["text2"], str) or not isinstance(i["label"], float):
        print(isinstance(i["text1"], str), isinstance(i["text2"], str), isinstance(i["label"], float))
        raise ValueError(f"Incorrect data types in val_dataset for entry {i}")

print(val_dataset)
print(f"Training dataset size: {len(train_dataset)}")

for i in val_dataset:
    assert isinstance(i["text1"], str), "text1 should be a string"
    assert isinstance(i["text2"], str), "text2 should be a string"
    assert isinstance(i["label"], float), "label should be a float"

# 7. Create a trainer & train
trainer = SentenceTransformerTrainer(
    model=model,
    args=args,
    train_dataset=train_dataset,
    eval_dataset=val_dataset,
    loss=loss,
    #evaluator=dev_evaluator,
)
trainer.train()

# (Optional) Evaluate the trained model on the test set
test_evaluator = EmbeddingSimilarityEvaluator(
    sentences1=test_dataset["text1"],
    sentences2=test_dataset["text2"],
    scores=test_dataset["label"],
    name="all-nli-test",
)
test_evaluator(model)

# 8. Save the trained model
model.save_pretrained("\Text_analysis\models\mpnet-base-all-nli-triplet\Final")