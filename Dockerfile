FROM openjdk:14-slim-buster

WORKDIR /app

COPY bin bin
COPY lib lib

CMD ["java","-cp","bin:lib/*", "ch.ffhs.pm.fac.Linescript"]