package com.dev.interpreter.ast;

public class VariableExpr extends Expr {

    private final String name;

    public VariableExpr(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
