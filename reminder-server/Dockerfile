FROM eclipse-temurin:17-jre-alpine

ARG JAR_FILE
ADD target/${JAR_FILE} app.jar

ENV JAVA_OPTS="-server -Xms1024m -Xmx2048m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]