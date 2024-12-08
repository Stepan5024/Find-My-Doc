import csv
# %pip install datasets
from datasets import Dataset
import os
from scipy.spatial import distance

#validation data
val_scores = {}
# Code to use for google collab
# with (open("/content/drive/MyDrive/text_analysis/datasets/_validation/similarity_scores.csv", "r")) as file :
#     csv_val_sc_reader = csv.reader(file)
#     for row in csv_val_sc_reader :
#         val_scores[str(row[0]) + "," + str(row[1])] = row[2]
# Code to use otherwise
with (open("Text_analysis\datasets\_validation\similarity_scores.csv", "r")) as file :
    csv_val_sc_reader = csv.reader(file)
    for row in csv_val_sc_reader :
        val_scores[str(row[0]) + "," + str(row[1])] = row[2]

#print(val_scores)

val_text1s = []
val_text2s = []
val_scores2 = []

for texts in val_scores :
    #print(texts)
    val_scores2.append(float(val_scores[texts]))
    # print(val_scores[texts])
    texts_list = texts.split(",")
    text1 = str(texts_list[0]) + ".txt"
    text2 = str(texts_list[1]) + ".txt"
    # Code to use for google collab
    # with (open("/content/drive/MyDrive/text_analysis/datasets/_validation/documents" + "/" + text1, "r")) as text1_contents :
    #     val_text1s.append(text1_contents.read())
    #     # print(text1_contents.read())
    # with (open("/content/drive/MyDrive/text_analysis/datasets/_validation/documents" + "/" + text2, "r")) as text2_contents :
    #     val_text2s.append(text2_contents.read())
    #     # print(text2_contents.read())
    # Code to use otherwise
    with (open("Text_analysis\datasets\_validation\documents" + "/" + text1, "r")) as text1_contents :
        val_text1s.append(text1_contents.read())
    with (open("Text_analysis\datasets\_validation\documents" + "/" + text2, "r")) as text2_contents :
        val_text2s.append(text2_contents.read())

val_dataset = Dataset.from_dict({
    "text1": val_text1s,
    "text2": val_text2s,
    "label": val_scores2
})

#test data
test_scores = {}
# Code to use for google collab
# with (open("/content/drive/MyDrive/text_analysis/datasets/_test/similarity_scores.csv", "r")) as file :
#     csv_test_sc_reader = csv.reader(file)
#     for row in csv_test_sc_reader :
#         test_scores[str(row[0]) + "," + str(row[1])] = row[2]
# Code to use otherwise
with (open("Text_analysis\datasets\_test\similarity_scores.csv", "r")) as file :
    csv_test_sc_reader = csv.reader(file)
    for row in csv_test_sc_reader :
        test_scores[str(row[0]) + "," + str(row[1])] = row[2]

#print(test_scores)

test_text1s = []
test_text2s = []
test_scores2 = []

for texts in test_scores :
    #print(texts)
    test_scores2.append(float(test_scores[texts]))
    texts_list = texts.split(",")
    text1 = str(texts_list[0]) + ".txt"
    text2 = str(texts_list[1]) + ".txt"
    # Code to use for google collab
    # with (open("/content/drive/MyDrive/text_analysis/datasets/_test/documents" + "/" + text1, "r")) as text1_contents :
    #     test_text1s.append(text1_contents.read())
    # with (open("/content/drive/MyDrive/text_analysis/datasets/_test/documents" + "/" + text2, "r")) as text2_contents :
    #     test_text2s.append(text2_contents.read())
    # Code to use otherwise
    with (open("Text_analysis\datasets\_test\documents" + "/" + text1, "r")) as text1_contents :
        test_text1s.append(text1_contents.read())
    with (open("Text_analysis\datasets\_test\documents" + "/" + text2, "r")) as text2_contents :
        test_text2s.append(text2_contents.read())

test_dataset = Dataset.from_dict({
    "text1": test_text1s,
    "text2": test_text2s,
    "label": test_scores2
})

#train data
train_scores = []
train_texts = []
train_text1s = []
train_text2s = []

#preparing sentences
# Code to use for google collab
# for filename in os.listdir(os.getcwd() + "/drive/MyDrive/text_analysis/datasets/_text") :
#   #  if filename == "1953_L_1.txt" :
#   with open(os.path.join(os.getcwd() + "/drive/MyDrive/text_analysis/datasets/_text", filename), 'r') as f: # open in readonly mode
#         train_texts.append(f.read())
# Code to use otherwise
for filename in os.listdir(os.getcwd() + "\Text_analysis\datasets\_text") :
  #  if filename == "1953_L_1.txt" :
  with open(os.path.join(os.getcwd() + "\Text_analysis\datasets\_text", filename), 'r') as f: # open in readonly mode
        train_texts.append(f.read())

for i in range(len(train_texts)) :
    sentences = train_texts[i].split("\n")
    for j in range(len(sentences)) :
        sentences[j] = sentences[j].split("\t")[0]
    train_texts[i] = sentences

new_train_texts = []
for i in train_texts :
    for j in i :
        if j != "" :
            new_train_texts.append(j)
train_texts = new_train_texts

for i in range(len(train_texts)) :
    if i < len(train_texts)/2 :
        train_text1s.append(train_texts[i])
    else :
        train_text2s.append(train_texts[i])

#preparing the embeddings
# Code to use for google collab
# for filename in os.listdir(os.getcwd() + "/drive/MyDrive/text_analysis/datasets/pretrained_embeddings") :
#   #  if filename == "1953_L_1.txt" :
#   with open(os.path.join(os.getcwd() + "/drive/MyDrive/text_analysis/datasets/pretrained_embeddings", filename), 'r') as ff: # open in readonly mode
#         train_scores.append(ff.read())
# Code to use otherwise
for filename in os.listdir(os.getcwd() + "\Text_analysis\datasets\pretrained_embeddings") :
  #  if filename == "1953_L_1.txt" :
  with open(os.path.join(os.getcwd() + "\Text_analysis\datasets\pretrained_embeddings", filename), 'r') as ff: # open in readonly mode
        train_scores.append(ff.read())

for i in range(len(train_scores)) :
    sentences = train_scores[i].split("\n")
    for j in range(len(sentences)) :
        sentences[j] = sentences[j].split("\t")[0]
    train_scores[i] = sentences

new_train_scores = []
for i in train_scores :
    for j in i :
        if j != "" :
            new_train_scores.append(j)
train_scores = new_train_scores
# print(train_scores)

train_scores1 = []
train_scores2 = []

for i in range(len(train_scores)) :
    if i < len(train_scores)/2 :
        train_scores1.append(train_scores[i])
    else :
        train_scores2.append(train_scores[i])

actual_train_scores = []

for i in range(len(train_scores1)) :
    v1 = train_scores1[i].split(" ")
    v2 = train_scores2[i].split(" ")
    for x in range(len(v1)) :
        v1[x] = float(v1[x])
    for y in range(len(v2)) :
        v2[y] = float(v2[y])
    #print(v1, v2)
    actual_train_scores.append(distance.cosine(v1, v2))

print(len(actual_train_scores))

train_dataset = Dataset.from_dict({
    "text1": train_text1s,
    "text2": train_text2s,
    "label": actual_train_scores
})