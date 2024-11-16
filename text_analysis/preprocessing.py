import csv  
from datasets import Dataset  
  
#validation data  
val_scores = {}  
with (open("text_analysis/datasets/_validation/similarity_scores.csv", "r")) as file:  
    csv_val_sc_reader = csv.reader(file)  
    for row in csv_val_sc_reader:  
        val_scores[str(row[0]) + "," + str(row[1])] = row[2]  
  
#print(val_scores)  
  
val_text1s = []
val_text2s = []
val_scores = []

for texts in val_scores :   
    #print(texts)
    val_scores.append(val_scores[texts])
    texts_list = texts.split(",")
    text1 = str(texts_list[0]) + ".txt"
    text2 = str(texts_list[1]) + ".txt"
    with (open("text_analysis/datasets/_validation/documents" + "/" + text1, "r")) as text1_contents:  
        val_text1s.append(text1_contents.read())
    with (open("text_analysis/datasets/_validation/documents" + "/" + text2, "r")) as text2_contents:  
        val_text2s.append(text2_contents.read())

val_dataset = Dataset.from_dict({
    "text1": val_text1s,
    "text2": val_text2s,
    "score": val_scores
})  
  
#test data  
test_scores = {}  
with (open("text_analysis/datasets/_test/similarity_scores.csv", "r")) as file:  
    csv_test_sc_reader = csv.reader(file)  
    for row in csv_test_sc_reader:  
        test_scores[str(row[0]) + "," + str(row[1])] = row[2]  
  
#print(test_scores)  
  
test_text1s = []
test_text2s = []
test_scores = []

for texts in test_scores :   
    #print(texts)
    test_scores.append(test_scores[texts])
    texts_list = texts.split(",")
    text1 = str(texts_list[0]) + ".txt"
    text2 = str(texts_list[1]) + ".txt"
    with (open("text_analysis/datasets/_test/documents" + "/" + text1, "r")) as text1_contents:  
        test_text1s.append(text1_contents.read())
    with (open("text_analysis/datasets/_test/documents" + "/" + text2, "r")) as text2_contents:  
        test_text2s.append(text2_contents.read())

test_dataset = Dataset.from_dict({
    "text1": test_text1s,
    "text2": test_text2s,
    "score": test_scores
})  