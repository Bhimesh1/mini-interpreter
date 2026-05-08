package com.dev.interpreter.parser;

import com.dev.interpreter.ast.Expr;
import com.dev.interpreter.lexer.Lexer;
import com.dev.interpreter.lexer.Token;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParserTest {

    @Test
    void testSimpleExpression() {

        Lexer lexer = new Lexer("2 + 3 * 4");

        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);

        Expr expr = parser.parse();

        assertNotNull(expr);

        System.out.println(expr);
    }
}