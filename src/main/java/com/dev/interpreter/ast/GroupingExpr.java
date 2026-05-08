package com.dev.interpreter.ast;

public class GroupingExpr extends Expr {

    private final Expr expression;

    public GroupingExpr(Expr expression) {
        this.expression = expression;
    }

    public Expr getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "(" + expression + ")";
    }
}
