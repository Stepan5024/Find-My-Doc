Для запуска микросервиса нужно в папке Text_analysis создать папку models и разархивировать туда чекпоинты модели, которые можно скачать тут: https://drive.google.com/file/d/1XY1mmccYddXR-buqy4uHVlHPrSU_8PJq/view?usp=sharing
Мискросевис запускается локально на порту 8085. 

Путь /similarity/words (метод POST) принимает на вход JSON формата: 
{
    "sen1": "court",
    "sen2": "court"
}
И возвращает ответ формата:
{"similarity": similarity} и код 200

Путь /similarity/forms (метод POST) принимает на вход JSON формата: 
{
    "form1": {"Поле 1": "Значение 1", "Поле 2": "Значение 2"},
    "form2": {"Поле 1": "Значение 1", "Поле 2": "Значение 2"}
}
И возвращает ответ формата:
{"forms_similarity": forms_similarity, "overall_forms_similarity": overall_similarity} и код 200
Если в какой-то из форма есть поля которых нет в другой, то схожесть значений этих полей будет 0.0