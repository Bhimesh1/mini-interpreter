package com.dev.interpreter.runtime;

import com.dev.interpreter.ast.Stmt;
import com.dev.interpreter.lexer.Lexer;
import com.dev.interpreter.lexer.Token;
import com.dev.interpreter.parser.Parser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterpreterTest {

    @Test
    void testVariableAssignments() {

        String source = """
                x = 2
                y = x + 3
                """;

        Lexer lexer = new Lexer(source);

        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);

        List<Stmt> statements = parser.parse();

        Interpreter interpreter = new Interpreter();

        interpreter.execute(statements);

        Environment env = interpreter.getEnvironment();

        assertEquals(2, env.get("x"));
        assertEquals(5, env.get("y"));
    }

    @Test
    void testIfStatement() {

        String source = """
            x = 20
            if x > 10 then y = 100 else y = 0
            """;

        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        Interpreter interpreter = new Interpreter();
        interpreter.execute(statements);

        Environment env = interpreter.getEnvironment();

        assertEquals(20, env.get("x"));
        assertEquals(100, env.get("y"));
    }

    @Test
    void testWhileLoop() {


        String source = """
            x = 0
            y = 0
            while x < 3 do if x == 1 then y = 10 else y = y + 1, x = x + 1
            """;

        Lexer lexer = new Lexer(source);

        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);

        List<Stmt> statements = parser.parse();

        Interpreter interpreter = new Interpreter();


        interpreter.execute(statements);

        Environment env = interpreter.getEnvironment();

        assertEquals(3, env.get("x"));
        assertEquals(11, env.get("y"));


    }

    @Test
    void testFunctionCall() {

        String source = """
            fun add(a, b) { return a + b }
            four = add(2, 2)
            """;

        Lexer lexer = new Lexer(source);

        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);

        List<Stmt> statements = parser.parse();

        Interpreter interpreter = new Interpreter();

        interpreter.execute(statements);

        Environment env = interpreter.getEnvironment();

        assertEquals(4, env.get("four"));
    }

    @Test
    void testRecursiveFactorial() {

        String source = """
            fun fact_rec(n) {
                if n <= 0 then return 1
                else return n * fact_rec(n - 1)
            }

            a = fact_rec(5)
            """;

        Lexer lexer = new Lexer(source);

        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);

        List<Stmt> statements = parser.parse();

        Interpreter interpreter = new Interpreter();

        interpreter.execute(statements);

        Environment env = interpreter.getEnvironment();

        assertEquals(120, env.get("a"));
    }
}