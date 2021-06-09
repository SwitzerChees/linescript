<p align="center">
  <img width="200" height="200" src="logo.png">
</p>

> Linescript is a simple interpreted script language based on JFlex and CUP powered by Java. The focus is on fast and short code. All Statements are represented in one line. ~~no multiline statements and unnecessary brackets~~. The syntax is inspired by python, means simple and intuitive.

## ✨ Getting Started

### 🔥 Demo / Try it out

If you want to test the language you can easily do that by just execute the official docker image.
```bash
# Interactive Mode
docker run -it switzerchees/linescript:latest

# Script Interpreter Mode executes the example.ls file
docker run -it -v "$PWD/example.ls":/app/example.ls switzerchees/linescript:latest example.ls
```
### ⚡ Interactive Mode 🚨 HIGLIGHTS🚨!!!

* \<tab\> Autocompletion (Variables and Functions)
* Syntax Highlighting
* Statement History
### ♾️ Dependencies

* \>= java 15 (major version 59).
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


## 🌐 The Language

### 🔢 Data Types
The datatypes are implicit converted into the following datatypes.

| Name        | Java Type           |  Description |
| ------------- |:-------------:| -----:| 
| String      | string | A normal string all UTF-8 chars are allowed except "|
| Number      | BigDecimal | Int and Decimal numbers are allowed they are combined in the number datatype |
| Boolean | boolean | True or False as values are allowed |

Some examples of different valid assign statements with the datatypes.
```python
# Numbers
my_number = 12
my_number = 12.
my_number = 12.2345

# Strings
my_string = "Linescript"
my_string = "This is a longer string with 🤩 inside!!"

# String Interpolation
price = 12.50
my_string = "The price of the product is: ${price}"

# Boolean
my_true = True
my_false = False
```

### 🧮 Arithemtic Operations
The following section describes all allowed arithmetic operations in the language.

```python
# Addition
1 + 1
# Subtraction
2 - 2
# Multiplication
3 * 3
# Division
4 / 4
# Power
5 ^ 5
# Mod
6 % 6
# Point before Line
1 + 1 * 2
2 * 1 + 1
# Power before Point
2 ^ 2 * 3
3 * 2 ^ 2
```

### ⬅️ Assign Statements
The following section describes all allowed assign statements.

```python
# Assign
my_number = 10
my_string = "Linescript"
my_boolean = True

#IMPORTANT: The following statements only working with numbers obviously.
# Addition Assign
my_number += 10
# Subtraction Assign
my_number -= 10
# Multiplication Assign
my_number *= 10
# Division Assign
my_number /= 10
```

### ↔️ Conditional Statements
The following section describes all allowed conditional statements.

```python
# Equal
10 == 10
"Linescript" == "Linescript"
# Not Equal
10 != 10
"Linescript" != "Linescript"
# Less Than
5 < 10
"abc" < "bcd"
# Greater
5 > 10
"abc" > "bcd"
# Less Equal
10 <= 10
"abc" <= "abc"
# Greater Equal
10 >= 10
"abc" >= "abc"

# Variables to compare
small_number = 10
big_number = 100
is_bigger = small_number < big_number
```

### 👌 If-Else Statements

```python
# If Statement
if 1 < 2: "Yes"
# If-Else Statement
if 2 < 2: "Yes" else: "No"
# If-Else with return Value
res = if 1 == 1: "Yes" else: "No"

#If-Else Statement combined with Assign Statement
if 1 == 2: res = "Yes" else: res = "No"
```

### 🧰 Functions

```python
# Sum Function
sum = lambda a,b: a + b
sum(1, 2)

# Integrated Exit Function
exit()

# Integrated Print Function
name = "John Doe"
print("Hello", name)

# Recursive Fibonacci Number Function
fibo = lambda n: if n <= 1: n else: fibo(n - 1) + fibo(n - 2)
# Calculates the first 20 numbers of the Fibonacci sequence
i = 0
end = 20
while i <= end: "${i}th Fibonacci number", fibo(i), i += 1
```

## 📜 License

This project is licensed under the Apache Version 2.0 License - see the LICENSE file for details.

> ©2021 SwitzerChees. Made with ❤️ and a lot of ☕.