package com.dev.interpreter;

import com.dev.interpreter.ast.Expr;
import com.dev.interpreter.lexer.Lexer;
import com.dev.interpreter.lexer.Token;
import com.dev.interpreter.parser.Parser;
import com.dev.interpreter.runtime.Interpreter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String source = "(2 + 3) * 4";

        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        Expr expr = parser.parse();

        Interpreter interpreter = new Interpreter();
        Object result = interpreter.evaluate(expr);

        System.out.println(result);
    }
}