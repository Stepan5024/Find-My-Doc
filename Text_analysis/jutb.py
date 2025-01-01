import re

def extract_words_from_string(text):
  """
  Извлекает слова из строки, удаляя лишние символы.

  Args:
      text: Строка для обработки.

  Returns:
      Список (массив) слов из строки.
  """
  # 1. Заменяем все не-буквенно-цифровые символы и пробельные символы на пробелы.
  text = re.sub(r'[^a-zA-Zа-яА-Я0-9]+', ' ', text) # [^a-zA-Zа-яА-Я0-9] - все символы, кроме букв и цифр. + означает 1 или более таких символов
  # 2. Разбиваем строку на слова по пробелам и удаляем пустые слова
  words = [word for word in text.split() if word]
  return words

# Примеры использования
string_example = "Это, пример; строки! для. разбиения на слова."
words = extract_words_from_string(string_example)
print(words)
# Вывод: ['Это', 'пример', 'строки', 'для', 'разбиения', 'на', 'слова']

string_example = "Еще   один,    пример   с;    лишними. пробелами!"
words = extract_words_from_string(string_example)
print(words)
# Вывод: ['Еще', 'один', 'пример', 'с', 'лишними', 'пробелами']

string_example = "123 строка с цифрами и  символами! @#$%^"
words = extract_words_from_string(string_example)
print(words)
# Вывод: ['123', 'строка', 'с', 'цифрами', 'и', 'символами']

string_example = ""
words = extract_words_from_string(string_example)
print(words)
# Вывод: []