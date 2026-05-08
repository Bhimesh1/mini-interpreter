package com.dev.interpreter;

import com.dev.interpreter.ast.Expr;
import com.dev.interpreter.ast.Stmt;
import com.dev.interpreter.lexer.Lexer;
import com.dev.interpreter.lexer.Token;
import com.dev.interpreter.parser.Parser;
import com.dev.interpreter.runtime.Environment;
import com.dev.interpreter.runtime.Interpreter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
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

        env.getValues().forEach((k, v) ->
                System.out.println(k + ": " + v)
        );
    }
}