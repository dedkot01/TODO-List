# TO-DO List

Веб-сервис ведения списка задач.
Можно добавлять новые задачи, удалять и редактировать ранее созданные.

Чтобы запустить, в папке с проектом нужно прописать команду:
```
sbt run
```

Необходимо, чтобы была запущена локально СУБД [Postgresql 13.1](https://www.postgresql.org), порт 5432, пользователь `postgres`, пароль `postgres`, база данных `postgres`.

## Dependencies

* [PlayFramework v2.8](https://www.playframework.com)
* [Play-Slick v5.0.0](https://mvnrepository.com/artifact/com.typesafe.play/play-slick)
* [Postgresql v42.2.18](https://mvnrepository.com/artifact/org.postgresql/postgresql)
* [Bootstrap v4.5.3](https://getbootstrap.com)

## Evolution

При первом запуске приложения на БД необходимо выполнить предлагаемый скрипт. Таким образом, будет создана таблица "Задача".

## Настройки приложения

Для смены базы данных нужно настроить файл `conf/application.conf`.

## Docker

В папке проекта есть файлы `docker-compose`. В нём описана служба запуска БД Postgres 13.1 с настройками, идентичными стандартным настройкам приложения.

Запустить БД из Docker:
```
docker-compose up -d
```

Остановить и удалить контейнер:
```
docker-compose down
```

Остановить и удалить контейнер вместе с сохранениями БД:
```
docker-compose down -v
```
