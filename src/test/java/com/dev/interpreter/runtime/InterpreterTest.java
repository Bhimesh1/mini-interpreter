package com.dev.interpreter.runtime;

import com.dev.interpreter.ast.Expr;
import com.dev.interpreter.lexer.Lexer;
import com.dev.interpreter.lexer.Token;
import com.dev.interpreter.parser.Parser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterpreterTest {

    private Object runExpression(String source) {
        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        Expr expr = parser.parse();

        Interpreter interpreter = new Interpreter();
        return interpreter.evaluate(expr);
    }

    @Test
    void testArithmeticExpression() {
        assertEquals(14, runExpression("2 + 3 * 4"));
    }

    @Test
    void testParenthesizedExpression() {
        assertEquals(20, runExpression("(2 + 3) * 4"));
    }

    @Test
    void testComparisonExpression() {
        assertEquals(true, runExpression("10 > 3"));
    }

    @Test
    void testEqualityExpression() {
        assertEquals(true, runExpression("5 == 5"));
    }
}