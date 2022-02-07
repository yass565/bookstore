FROM openjdk:17
VOLUME /tmp
ADD /target/bookstore-*.jar bookstore.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/bookstore.jar"]
