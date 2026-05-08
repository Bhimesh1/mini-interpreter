package com.dev.interpreter;

import com.dev.interpreter.lexer.Lexer;
import com.dev.interpreter.lexer.Token;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String source = "x = (2 + 3) * 4";

        Lexer lexer = new Lexer(source);

        List<Token> tokens = lexer.tokenize();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}