# Cтажировка: Программа IT-старт. 
### Настройка
Для задания параметров запуска приложения используются ресурсы в директории ***./src/main/resources/***
## Настройка БД
- Внести в ***build.gradle*** зависимость для драйвера использумеой БД, если в качестве тестовой не используется PostgreSQL
- Изменить параметры подключения к БД в ***application.properties*** файле у атрибутов с префиксом **spring.datasource.**
- В атрибуте ***app.file.path*** указать расположение локального файла, из которого будет производится импорт в БД 

## Запуск
В приложении установлены два эндпоинта для импорта данных из файла и поиска статьи по заголовку

|url|Метод|Параметры|Описание
| -----|------|------|------|
|/wiki/\<title\>|GET|pretty|Возвращает .json файл с соответствующей статьей, если таковая будет найдена. title - заголовк статьи. pretty=1 - форматированный вывод файла. 
|/wiki/import|GET|-|Производит импорт данных в бд из локального файла|

Между объектами **Article-AuxiliaryText**, а также **Article-Category** установлены отношения ***One-To-Many***. Дата и Время в БД хранится в виде атрибута типа **Timestamp**.
При отображении в .json файле даты и время представлены в unix-системе (long). Поля **сategory** и **auxiliary_text** в виде массивов.


