# Сканер штрихкодов

Простейшая реализация сканера штрихкодов.
Он позволяет используя API получать данные о конкретном штрихкоде, если тот присутствует в базе данных.

## Возможности

- Авторизация пользователей(дефолтные креды admin admin)
- Сканирование штрихкода с обработкой возникающих ошибок
- Вывод информации о товаре с возможностью вернутся назад
- Прсмотр истории последних 10 сканирований товара

### Планы развития проекта
- [x] Создать базовое приложение
- [x] Добавить возможность сканирования штрихкодов
- [x] Добавить возможность просмотра истории
- [ ] Добавить Minio для отображения картинки товара