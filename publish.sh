ant main -buildfile lib/build.xml -lib lib/

docker build --tag switzerchees/linescript .

docker push switzerchees/linescript