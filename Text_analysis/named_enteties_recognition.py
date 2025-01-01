import re
from test import calculate_similarity


def extract_form_data_fuzzy(form_string, field_names, similarity_threshold=0.7):
    """Извлекает данные из строки формы, используя нечеткое сравнение имен полей.

      Args:
        form_string: Строка формы без форматирования.
        field_names: Список названий полей (строка).
        similarity_threshold: Порог похожести.

      Returns:
        Словарь {поле: значение, ...} или пустой словарь, если не удалось извлечь данные.
    """
    model_path = "Text_analysis/models/mpnet-base-all-nli-triplet/Final"
    field_names = re.sub(r'[^a-zA-Zа-яА-Я0-9]+', ' ', field_names)
    field_names = [word for word in field_names.split() if word]
    form_data = {}
    form_string = form_string.strip()
    form_string += ' '
    words = form_string.split()
    processed_words = set()

    for field_name in field_names:
        best_match = None
        best_similarity = 0.0
        best_start_index = -1

        # 1. Ищем точное совпадение
        for i, word in enumerate(words):
            if word == field_name and word not in processed_words:
                best_match = word
                best_similarity = 1.0
                best_start_index = i
                break

        if best_match is not None:
            start_index = best_start_index + 1
            next_field_start_index = -1
            for next_field in field_names:
                if next_field == field_name:
                    continue
                try:
                    next_field_index = words.index(next_field, start_index)
                    next_field_start_index = next_field_index
                    break
                except ValueError:
                    pass

            if next_field_start_index != -1:
                end_index = next_field_start_index
            else:
                end_index = len(words)  
            
            value = " ".join(words[start_index:end_index]).strip()
            form_data[field_name] = value
            processed_words.add(best_match)
        else:
            # 2. Если точного совпадения нет, ищем наиболее похожее
            for i, word in enumerate(words):
                if word in processed_words:
                  continue
                similarity = calculate_similarity(model_path, field_name, word)
                if similarity > best_similarity:
                    best_similarity = similarity
                    best_match = word
                    best_start_index = i
            
            if best_similarity >= similarity_threshold:
                start_index = best_start_index + 1
                next_field_start_index = -1
               
                #Сначала пробуем найти точное совпадение для следующего поля
                for next_field in field_names:
                    if next_field == field_name:
                        continue
                    try:
                        next_field_index = words.index(next_field, start_index)
                        next_field_start_index = next_field_index
                        break
                    except ValueError:
                        pass
               
                #Если точного совпадения нет, то пытаемся найти по похожести
                if next_field_start_index == -1:
                    for next_field in field_names:
                        if next_field == field_name:
                            continue
                        for k in range(len(words)):
                            if words[k] not in processed_words and calculate_similarity(model_path, next_field, words[k]) >= similarity_threshold:
                                next_field_start_index = k
                                break
                        if next_field_start_index != -1:
                             break
                             
                if next_field_start_index != -1:
                    end_index = next_field_start_index
                else:
                    end_index = len(words)
                   
                print(start_index, end_index)
                print(words)
                value = " ".join(words[start_index:end_index]).strip()
                form_data[field_name] = value
                processed_words.add(best_match)
    return form_data


# Пример использования
form_string = "Имя Иван Фамилия Иванов Возраст 30 Город Москва"
field_names = "Имя, Фамилия, Возраст, Город"
extracted_data = extract_form_data_fuzzy(form_string, field_names)
print(extracted_data)
# Вывод: {'Имя': 'Иван', 'Фамилия': 'Иванов', 'Возраст': '30', 'Город': 'Москва'}

# Пример использования
form_string = "Имя Иван Фомилия Иванов Во3раст 30 Город Москва"
field_names = "Имя, Фамилия, Возраст, Город"
extracted_data = extract_form_data_fuzzy(form_string, field_names)
print(extracted_data)
# Вывод: {'Имя': 'Иван', 'Фамилия': 'Иванов', 'Возраст': '30', 'Город': 'Москва'}