# Mini Interpreter

A small interpreter for a custom programming language built in Java.

The project includes:

* Lexical analysis (Lexer)
* Recursive descent parser
* Abstract Syntax Tree (AST)
* Runtime interpreter
* Functions and recursion
* Control flow statements

The interpreter reads a source program from standard input, executes it, and prints all variables to standard output.

---

# Features

* Variable assignments
* Arithmetic expressions
* Comparison operators
* Boolean values
* if / else statements
* while loops
* Functions
* Function calls
* Return statements
* Recursive functions

---

# Project Structure

```
src/main/java/com/dev/interpreter
│
├── ast/          # AST node classes
├── lexer/        # Lexer and token definitions
├── parser/       # Recursive descent parser
├── runtime/      # Interpreter runtime
└── Main.java     # Program entry point
```

---

# Architecture

The interpreter follows a traditional interpreter pipeline:

```
Source Code
   ↓
Lexer
   ↓
Tokens
   ↓
Parser
   ↓
Abstract Syntax Tree (AST)
   ↓
Interpreter
   ↓
Execution Result
```

---

# Supported Syntax

## Variable Assignment

```
x = 10
y = x + 5
```

Expected output:

```
x: 10
y: 15
```

---

## Arithmetic Expressions

```
x = 2 + 3 * 4
y = (x + 2) * 2
```

Expected output:

```
x: 14
y: 32
```

---

## Comparison Operators

Supported operators:

```
>
>=
<
<=
==
!=
```

Example:

```
x = 10
y = 20

a = x < y
b = x == y
c = x != y
```

Expected output:

```
x: 10
y: 20
a: true
b: false
c: true
```

---

## If Statements

```
x = 20
if x > 10 then y = 100 else y = 0
```

Expected output:

```
x: 20
y: 100
```

---

## While Loops

```
x = 0
y = 0

while x < 3 do
if x == 1 then y = 10 else y = y + 1,
x = x + 1
```

Expected output:

```
x: 3
y: 11
```

---

## Functions

```
fun add(a, b) {
    return a + b
}

result = add(10, 20)
```

Expected output:

```
result: 30
```

---

## Recursive Functions

```
fun fact(n) {
    if n <= 0 then return 1 else return n * fact(n - 1)
}

result = fact(5)
```

Expected output:

```
result: 120
```

---

## Fibonacci Example

```
fun fib(n) {
    if n <= 1 then return n else return fib(n - 1) + fib(n - 2)
}

result = fib(6)
```

Expected output:

```
result: 8
```

---

# How to Run

## Requirements

* Java 17+
* Gradle
* IntelliJ IDEA (recommended)

---

## Run Tests

```
./gradlew test
```

Windows:

```
gradlew.bat test
```

---

## Run the Interpreter

```
./gradlew run
```

Windows:

```
gradlew.bat run
```

---

# Interactive Input

The interpreter reads source code from standard input.

Type your program and finish with:

```
exit
```

Example:

```
fun fact(n) {
    if n <= 0 then return 1 else return n * fact(n - 1)
}

result = fact(5)

exit
```

Expected output:

```
result: 120
```

---

# Running in IntelliJ IDEA

1. Open the project
2. Open `Main.java`
3. Click the Run button
4. Type your program into the console
5. Type `exit`

---

# Testing

The project includes JUnit tests for:

* Variable assignments
* Arithmetic expressions
* If statements
* While loops
* Function calls
* Recursive functions

Run tests:

```
./gradlew test
```

---

# Technologies Used

* Java
* Gradle
* JUnit 5
* IntelliJ IDEA
