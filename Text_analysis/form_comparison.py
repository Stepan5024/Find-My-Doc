from typing import Dict, Any
from test import calculate_similarity


def calculate_average_value(dictionary):
    """
    Вычисляет среднее значение числовых элементов в словаре.

    Args:
        dictionary (dict): Словарь, где значения являются числами.

    Returns:
        float: Среднее значение, или None, если словарь пуст.
    """

    if not dictionary:
        return None  # Если словарь пустой, возвращаем None

    total = sum(dictionary.values())
    count = len(dictionary)
    return total / count

def compare_forms(model_path, dict1: Dict[str, Any], dict2: Dict[str, Any]) -> Dict[str, float]:
    """
    Сравнивает значения одинаковых полей в двух словарях и возвращает словарь схожестей.

    Args:
        dict1: Первый словарь.
        dict2: Второй словарь.

    Returns:
        Словарь, где ключи - поля из словарей, а значения - схожесть значений.
    """
    similarity_dict = {}
    all_keys = set(dict1.keys()).union(dict2.keys())

    for key in all_keys:
        value1 = dict1.get(key)
        value2 = dict2.get(key)
      
        if value1 is not None and value2 is not None:
            similarity = calculate_similarity(model_path, str(value1), str(value2))
        else:
            similarity = 0.0  # Если поле есть только в одном словаре, схожесть = 0

        similarity_dict[key] = similarity
    
    return similarity_dict, calculate_average_value(similarity_dict)

# Пример импользования
# if __name__ == "__main__":
#     model_path = "Text_analysis\models\mpnet-base-all-nli-triplet\Final"

#     dict1 = {"Имя": "Иван", "Фамилия": "Иванов", "Возраст": "30", "Город": "Москва"}
#     dict2 = {"Имя": "Иван", "Фамилия": "Петров", "Возраст": "35", "Город": "Санкт-Петербург"}
    
#     similarity_scores = compare_dictionaries(dict1, dict2)
#     print(*similarity_scores)
#     # Ожидаемый вывод: {"Имя": 1.000, "Фамилия": 0.767, "Возраст": 0.817, "Город": 0.761}

#     dict3 = {"Имя": "Анна", "Фамилия": "Смирнова", "Возраст": "25"}
#     dict4 = {"Имя": "Аня", "Фамилия": "Смирнова", "Пол": "Женский"}

#     similarity_scores2 = compare_dictionaries(dict3, dict4)
#     print(*similarity_scores2)
#     # Ожидаемый вывод: {"Имя": 0.5, "Фамилия": 1.0, "Возраст": 0.0, "Пол": 0.0}

#     dict5 = {"Имя": 123, "Фамилия": 456}
#     dict6 = {"Имя": "123", "Фамилия": "789"}

#     similarity_scores3 = compare_dictionaries(dict5, dict6)
#     print(*similarity_scores3)
#     # Ожидаемый вывод: {"Имя": 1.0, "Фамилия": 0.0}