package com.dev.interpreter.ast;

import java.util.List;

public class FunctionStmt extends Stmt {

    private final String name;
    private final List<String> parameters;
    private final Stmt body;

    public FunctionStmt(
            String name,
            List<String> parameters,
            Stmt body
    ) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public Stmt getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "fun " + name + parameters + " " + body;
    }
}