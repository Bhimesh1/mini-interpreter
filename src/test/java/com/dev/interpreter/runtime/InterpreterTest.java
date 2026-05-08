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
}