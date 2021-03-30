FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
ARG DB_URL
ARG DB_PASSWORD
ARG DB_USERNAME
ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY
ARG EMAIL_USERNAME
ARG EMAIL_PASSWORD
ARG REDDIT_PUBLIC
ARG REDDIT_PRIVATE
ARG REDDIT_USERNAME
ARG REDDIT_PASSWORD


ENV db_url=$DB_URL
ENV db_username=$DB_USERNAME
ENV db_password=$DB_PASSWORD
ENV AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID
ENV AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY
ENV email_username=$EMAIL_USERNAME
ENV email_password=$EMAIL_PASSWORD
ENV reddit_public=$REDDIT_PUBLIC
ENV reddit_private=$REDDIT_PRIVATE
ENV reddit_username=$REDDIT_USERNAME
ENV reddit_password=$REDDIT_PASSWORD

COPY ${JAR_FILE} app.jar
WORKDIR /home/docker/data
RUN chmod +x /app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/app.jar"]