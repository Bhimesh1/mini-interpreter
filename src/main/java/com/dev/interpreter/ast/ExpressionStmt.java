package com.dev.interpreter.ast;

public class ExpressionStmt extends Stmt {

    private final Expr expression;

    public ExpressionStmt(Expr expression) {
        this.expression = expression;
    }

    public Expr getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
