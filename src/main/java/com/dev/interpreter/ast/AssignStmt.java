package com.dev.interpreter.ast;

public class AssignStmt extends Stmt {

    private final String name;
    private final Expr value;

    public AssignStmt(String name, Expr value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Expr getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + " = " + value;
    }

}
