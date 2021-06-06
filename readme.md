<p align="center">
  <img width="200" height="200" src="logo.png">
</p>

> Linescript is a simple interpreted script language based on JFlex and CUP powered by Java. The focus is on fast and short code. All Statements are represented in one line. ~~no multiline statements and unnecessary brackets~~. The syntax is inspired by python, means simple and intuitive.

## ✨ Getting Started

### 🔥 Demo / Try it out

If you want to test the language you can easily do that by just execute the official docker image.

```
docker run -it switzerchees/linescript:latest
```

### ♾️ Dependencies

* \>= java 14 (major version 58).
* JFlex to create the token scanner.
* Java CUP: stands for Construction of Useful Parsers and is an LALR parser generator for Java.

### 💽 Installing

* Pull this repository
* Execute the comple task in VSCode or execute the build.xml manually.
    - ``ant main -buildfile lib/build.xml -lib lib/``
* Run the application locally with the java CLI.
    - Linux/Mac: ``java -cp "bin:lib/*" ch.ffhs.pm.fac.Linescript``
    - Windows: ``java -cp "bin;lib/*" ch.ffhs.pm.fac.Linescript``
* Pack the application into a docker container with the **DOCKERFILE**.
    - ``docker build --tag <name-of-image> .``

## 📜 License

This project is licensed under the Apache Version 2.0 License - see the LICENSE file for details.

> ©2021 SwitzerChees. Made with ❤️ and a lot of ☕.