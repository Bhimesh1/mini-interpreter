package com.dev.interpreter.runtime;

import com.dev.interpreter.ast.FunctionStmt;

import java.util.List;

public class Function {

    private final FunctionStmt declaration;

    public Function(FunctionStmt declaration) {
        this.declaration = declaration;
    }

    public Object call(
            Interpreter interpreter,
            List<Object> arguments
    ) {

        Environment localEnvironment =
                new Environment(interpreter.getEnvironment());

        for (int i = 0; i < declaration.getParameters().size(); i++) {

            localEnvironment.define(
                    declaration.getParameters().get(i),
                    arguments.get(i)
            );
        }

        try {

            interpreter.executeInEnvironment(
                    declaration.getBody(),
                    localEnvironment
            );

        } catch (ReturnException returnException) {

            return returnException.getValue();
        }

        return null;
    }

    public int arity() {
        return declaration.getParameters().size();
    }

    @Override
    public String toString() {
        return "<function " +
                declaration.getName() +
                ">";
    }
}