# apdk-emulator-web
Чтоб работали и run_hg(run_git примерно так же)
В файл hgrc добавить раздел:
[auth]
x.prefix = *
x.username = AlthSpb
x.password = Bac123456

после этого можно пользоваться run_hg.cmd


Дополнительно:
1.JAR-файл запускается как обычное java-приложение:
java -jar <jar-name>.jar

2. Для запуска в виде сервиса в ОС семейства Linux
- Отредактировать файл, указав значение переменных SERVICE_NAME, PATH_TO_JAR, PID_PATH_NAME и путь к каталогу с jar файлом.
- Скопировать apdk-emulator-web.sh в /etc/init.d/
- Убрать расширение
- Дать прав chmod +x apdk-emulator-web
- Попробовать запустить service apdk-emulator-web start
