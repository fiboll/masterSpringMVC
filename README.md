build: gradle build
run: java -jar build/libs/masterSpringMVC-0.0.1-SNAPSHOT.jar
run production: java -jar -Dspring.profiles.active=prod build/libs/masterSpringMVC-0.0.1-SNAPSHOT.jar
