package com.dev.interpreter.lexer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LexerTest {

    @Test
    void testSimpleExpression() {

        Lexer lexer = new Lexer("x = 2 + 3");

        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
        assertEquals(TokenType.ASSIGN, tokens.get(1).getType());
        assertEquals(TokenType.NUMBER, tokens.get(2).getType());
        assertEquals(TokenType.PLUS, tokens.get(3).getType());
        assertEquals(TokenType.NUMBER, tokens.get(4).getType());
        assertEquals(TokenType.EOF, tokens.get(5).getType());
    }

    @Test
    void testKeywords() {

        Lexer lexer = new Lexer("if true then return");

        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.IF, tokens.get(0).getType());
        assertEquals(TokenType.TRUE, tokens.get(1).getType());
        assertEquals(TokenType.THEN, tokens.get(2).getType());
        assertEquals(TokenType.RETURN, tokens.get(3).getType());
    }
}