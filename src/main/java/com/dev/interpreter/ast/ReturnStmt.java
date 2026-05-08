package com.dev.interpreter.ast;

public class ReturnStmt extends Stmt {

    private final Expr value;

    public ReturnStmt(Expr value) {
        this.value = value;
    }

    public Expr getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "return " + value;
    }
}