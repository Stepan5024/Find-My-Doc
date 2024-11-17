from tqdm import tqdm

with open('./1.txt', 'r', encoding='utf-8') as labels_txt, open('./labels.json', 'w', encoding='utf-8') as labels_json:
    labels_json.write('{'+'\n')
    for line in tqdm(labels_txt.readlines()):
        splitted_line = line.split('`')
        img_name = splitted_line[0][2:]
        img_value = splitted_line[1][:-1] if splitted_line[1][-1] == '\n' else splitted_line[1]
        comma = ',' if splitted_line[1][-1] == '\n' else ""
        labels_json.write(f"\"{img_name}\": \"{img_value}\"{comma}")
        labels_json.write('\n')
    labels_json.write('}')
