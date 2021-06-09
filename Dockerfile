FROM openjdk:15-slim-buster

WORKDIR /app

COPY bin bin
COPY lib lib
COPY init.ls init.ls

ENTRYPOINT ["java","-cp","bin:lib/*", "ch.ffhs.pm.fac.Linescript"]