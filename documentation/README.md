# Дипломный проект по профессии «Тестировщик»
## План автоматизации и отчетные документы по итогам тестирования:

[1. План автоматизации](https://github.com/Anna-grr/Diploma/blob/master/documentation/Plan.md)

## Процедура запуска автотестов:
### MySQL
1. Открыть проект в IntelliJ IDEA.
2. Запустить Docker Desktop.
3. Открыть терминал в корне проекта и запустить контейнеры командой
   `docker-compose up`.
4. Открыть второй терминал и запустить приложение **aqa-shop.jar** командой  
   `java -jar ./artifacts/aqa-shop.jar -Dspring.datasource.url=jdbc:mysql://localhost:3306/app`.
5. В отдельном терминале запустить тесты командой  
   `./gradlew clean test -DdbUrl=jdbc:mysql://localhost:3306/app`.
6. Сгенерировать отчёт Allure: `./gradlew allureServe`.
7. Остановить приложение: перейти в терминал, в котором оно запущено, и нажать **CTRL + C**.
8. Остановить контейнеры: `docker-compose down`.

### PostgreSQL
1. Открыть проект в IntelliJ IDEA.
2. Запустить Docker Desktop.
3. Открыть терминал в корне проекта и запустить контейнеры командой
   `docker-compose up`.
4. Открыть второй терминал и запустить приложение **aqa-shop.jar** командой  
   `java -jar ./artifacts/aqa-shop.jar -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app`.
5. В отдельном терминале запустить тесты командой  
   `./gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5432/app`.
6. Сгенерировать отчёт Allure: `./gradlew allureServe`.
7. Остановить приложение: перейти в терминал, в котором оно запущено, и нажать **CTRL + C**.
8. Остановить контейнеры: `docker-compose down`.