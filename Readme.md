# Сервис перевода денег

## Описание проекта

Разработать приложение - REST-сервис. Сервис должен предоставить интерфейс для перевода денег с одной карты на другую по заранее описанной [спецификации](https://github.com/AASukhov/MoneyService/blob/master/MoneyTransferServiceSpecification.yaml). Заранее подготовленное веб-приложение (FRONT) должно подключаться к разработанному сервису без доработок и использовать его функционал для перевода денег.

## Требования к приложению

- Сервис должен предоставлять REST интерфейс для интеграции с FRONT;
- Сервис должны реализовывать все методы перевода с одной банковской карты на другую описанные в [спецификации](https://github.com/AASukhov/MoneyService/blob/master/MoneyTransferServiceSpecification.yaml);
- Все изменения должны записываться в файл (лог переводов в произвольном формате с указанием даты, времени, карта с которой было списание, карта зачисления, сумма, комиссия, результат операции если был);

## Реализация

- Приложение разработано с использованием Spring Boot;
- Использован сборщик пакетов maven;
- Для запуска используется docker, docker-compose;
- Код размещен на github;
- Код покрыт unit тестами с использованием mockito;
- Добавлены интеграционные тесты с использованием testcontainers;

## Описание интеграции с FRONT
FRONT доступен по адресу https://github.com/serp-ya/card-transfer, можно выкачать репозиторий и запустить nodejs приложение локально
(в описании репозитория фронта добавлена информация как запустить) или использовать уже развернутое демо приложение по адресу https://serp-ya.github.io/card-transfer/ (тогда ваш api должен быть запущен по адресу http://localhost:5500/).
