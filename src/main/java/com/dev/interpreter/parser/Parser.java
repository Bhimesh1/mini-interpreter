package com.dev.interpreter.parser;

import com.dev.interpreter.ast.*;
import com.dev.interpreter.lexer.Token;
import com.dev.interpreter.lexer.TokenType;

import java.util.List;

import static com.dev.interpreter.lexer.TokenType.*;

public class Parser {

    private final List<Token> tokens;

    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expr parse() {
        return expression();
    }

    private Expr expression() {
        return equality();
    }

    private Expr equality() {

        Expr expr = comparison();

        while (match(EQUAL_EQUAL, BANG_EQUAL)) {

            Token operator = previous();

            Expr right = comparison();

            expr = new BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr comparison() {

        Expr expr = term();

        while (match(
                GREATER,
                GREATER_EQUAL,
                LESS,
                LESS_EQUAL
        )) {

            Token operator = previous();

            Expr right = term();

            expr = new BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr term() {

        Expr expr = factor();

        while (match(PLUS, MINUS)) {

            Token operator = previous();

            Expr right = factor();

            expr = new BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr factor() {

        Expr expr = primary();

        while (match(STAR, SLASH)) {

            Token operator = previous();

            Expr right = primary();

            expr = new BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr primary() {

        if (match(NUMBER)) {
            return new LiteralExpr(
                    Integer.parseInt(previous().getLexeme())
            );
        }

        if (match(TRUE)) {
            return new LiteralExpr(true);
        }

        if (match(FALSE)) {
            return new LiteralExpr(false);
        }

        if (match(IDENTIFIER)) {
            return new VariableExpr(previous().getLexeme());
        }

        if (match(LPAREN)) {

            Expr expr = expression();

            consume(RPAREN, "Expected ')' after expression.");

            return new GroupingExpr(expr);
        }

        throw error("Expected expression.");
    }

    private boolean match(TokenType... types) {

        for (TokenType type : types) {

            if (check(type)) {

                advance();

                return true;
            }
        }

        return false;
    }

    private boolean check(TokenType type) {

        if (isAtEnd()) return false;

        return peek().getType() == type;
    }

    private Token advance() {

        if (!isAtEnd()) current++;

        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private Token consume(TokenType type, String message) {

        if (check(type)) return advance();

        throw error(message);
    }

    private RuntimeException error(String message) {
        return new RuntimeException(message);
    }
}
