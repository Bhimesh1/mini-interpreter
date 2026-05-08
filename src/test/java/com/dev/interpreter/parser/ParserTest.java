package com.dev.interpreter.parser;

import com.dev.interpreter.ast.Stmt;
import com.dev.interpreter.lexer.Lexer;
import com.dev.interpreter.lexer.Token;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    @Test
    void testAssignmentStatement() {

        Lexer lexer = new Lexer("x = 2 + 3 * 4");

        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);

        List<Stmt> statements = parser.parse();

        assertEquals(1, statements.size());

        System.out.println(statements.get(0));
    }

    @Test
    void testFunctionDeclaration() {

        String source = """
            fun add(a, b) { return a + b }
            """;

        Lexer lexer = new Lexer(source);

        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);

        List<Stmt> statements = parser.parse();

        assertEquals(1, statements.size());

        System.out.println(statements.get(0));
    }
}