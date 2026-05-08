package com.dev.interpreter.lexer;

public enum TokenType {

    // Single-character tokens
    PLUS,
    MINUS,
    STAR,
    SLASH,

    LPAREN,
    RPAREN,

    LBRACE,
    RBRACE,

    COMMA,

    ASSIGN,

    // Comparison operators
    GREATER,
    GREATER_EQUAL,

    LESS,
    LESS_EQUAL,

    EQUAL_EQUAL,
    BANG_EQUAL,

    // Literals
    IDENTIFIER,
    NUMBER,

    // Keywords
    IF,
    THEN,
    ELSE,

    WHILE,
    DO,

    FUN,
    RETURN,

    TRUE,
    FALSE,

    // Special
    EOF



}
