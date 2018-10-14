FROM maven:3.3-jdk-8-onbuild as build

FROM java:8
COPY --from=build /usr/src/app/target/bieber-tweets-1.0.0-SNAPSHOT.jar /usr/local/app/bieber-tweets.jar
CMD ["java","-jar","/usr/local/app/bieber-tweets.jar"]