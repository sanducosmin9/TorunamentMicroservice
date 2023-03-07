FROM openjdk:19
ADD target/TournamentMicroservice-0.0.1-SNAPSHOT.jar dockerapp.jar
ENV POSTGRES_DB=tournament-db
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
EXPOSE 8080
ENTRYPOINT ["java","-jar","dockerapp.jar"]