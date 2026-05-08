package com.dev.interpreter.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dev.interpreter.lexer.TokenType.*;

public class Lexer {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    private int current = 0;

    private static final Map<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("if", IF);
        keywords.put("then", THEN);
        keywords.put("else", ELSE);

        keywords.put("while", WHILE);
        keywords.put("do", DO);

        keywords.put("fun", FUN);
        keywords.put("return", RETURN);

        keywords.put("true", TRUE);
        keywords.put("false", FALSE);
    }

    public Lexer(String source) {
        this.source = source;
    }

    public List<Token> tokenize() {

        while (!isAtEnd()) {
            scanToken();
        }

        tokens.add(new Token(EOF, ""));

        return tokens;
    }

    private void scanToken() {

        char c = advance();

        switch (c) {

            case '+' -> tokens.add(new Token(PLUS, "+"));
            case '-' -> tokens.add(new Token(MINUS, "-"));
            case '*' -> tokens.add(new Token(STAR, "*"));
            case '/' -> tokens.add(new Token(SLASH, "/"));

            case '(' -> tokens.add(new Token(LPAREN, "("));
            case ')' -> tokens.add(new Token(RPAREN, ")"));

            case '{' -> tokens.add(new Token(LBRACE, "{"));
            case '}' -> tokens.add(new Token(RBRACE, "}"));

            case ',' -> tokens.add(new Token(COMMA, ","));

            case '=' -> {
                if (match('=')) {
                    tokens.add(new Token(EQUAL_EQUAL, "=="));
                } else {
                    tokens.add(new Token(ASSIGN, "="));
                }
            }

            case '!' -> {
                if (match('=')) {
                    tokens.add(new Token(BANG_EQUAL, "!="));
                } else {
                    throw new RuntimeException("Unexpected character: !");
                }
            }

            case '>' -> {
                if (match('=')) {
                    tokens.add(new Token(GREATER_EQUAL, ">="));
                } else {
                    tokens.add(new Token(GREATER, ">"));
                }
            }

            case '<' -> {
                if (match('=')) {
                    tokens.add(new Token(LESS_EQUAL, "<="));
                } else {
                    tokens.add(new Token(LESS, "<"));
                }
            }

            case ' ', '\r', '\t', '\n' -> {
                // ignore whitespace
            }

            default -> {

                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    throw new RuntimeException("Unexpected character: " + c);
                }
            }
        }
    }

    private void number() {

        int start = current - 1;

        while (!isAtEnd() && isDigit(peek())) {
            advance();
        }

        String text = source.substring(start, current);

        tokens.add(new Token(NUMBER, text));
    }

    private void identifier() {

        int start = current - 1;

        while (!isAtEnd() && isAlphaNumeric(peek())) {
            advance();
        }

        String text = source.substring(start, current);

        TokenType type = keywords.getOrDefault(text, IDENTIFIER);

        tokens.add(new Token(type, text));
    }

    private char advance() {
        return source.charAt(current++);
    }

    private boolean match(char expected) {

        if (isAtEnd()) return false;

        if (source.charAt(current) != expected) {
            return false;
        }

        current++;

        return true;
    }

    private char peek() {

        if (isAtEnd()) return '\0';

        return source.charAt(current);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return Character.isLetter(c) || c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }
}