package com.dev.interpreter.runtime;

import com.dev.interpreter.ast.*;
import com.dev.interpreter.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Interpreter {

    private Environment environment = new Environment();

    public void execute(List<Stmt> statements) {

        for (Stmt stmt : statements) {
            execute(stmt);
        }
    }

    void executeInEnvironment(
            Stmt stmt,
            Environment newEnvironment
    ) {

        Environment previous = environment;

        try {

            environment = newEnvironment;

            execute(stmt);

        } finally {

            environment = previous;
        }
    }

    private void execute(Stmt stmt) {

        // FUNCTION DECLARATION
        if (stmt instanceof FunctionStmt functionStmt) {

            environment.define(
                    functionStmt.getName(),
                    new Function(functionStmt)
            );

            return;
        }

        // RETURN
        if (stmt instanceof ReturnStmt returnStmt) {

            Object value =
                    evaluate(returnStmt.getValue());

            throw new ReturnException(value);
        }

        // ASSIGNMENT
        if (stmt instanceof AssignStmt assignStmt) {

            Object value =
                    evaluate(assignStmt.getValue());

            environment.define(
                    assignStmt.getName(),
                    value
            );

            return;
        }

        // EXPRESSION STATEMENT
        if (stmt instanceof ExpressionStmt expressionStmt) {

            evaluate(expressionStmt.getExpression());

            return;
        }

        // IF
        if (stmt instanceof IfStmt ifStmt) {

            Object condition =
                    evaluate(ifStmt.getCondition());

            if (isTruthy(condition)) {

                execute(ifStmt.getThenBranch());

            } else {

                execute(ifStmt.getElseBranch());
            }

            return;
        }

        // BLOCK
        if (stmt instanceof BlockStmt blockStmt) {

            for (Stmt statement :
                    blockStmt.getStatements()) {

                execute(statement);
            }

            return;
        }

        // WHILE
        if (stmt instanceof WhileStmt whileStmt) {

            while (
                    isTruthy(
                            evaluate(
                                    whileStmt.getCondition()
                            )
                    )
            ) {

                execute(whileStmt.getBody());
            }

            return;
        }

        throw new RuntimeException(
                "Unknown statement."
        );
    }

    private Object evaluate(Expr expr) {

        // LITERAL
        if (expr instanceof LiteralExpr literalExpr) {
            return literalExpr.getValue();
        }

        // GROUPING
        if (expr instanceof GroupingExpr groupingExpr) {

            return evaluate(
                    groupingExpr.getExpression()
            );
        }

        // VARIABLE
        if (expr instanceof VariableExpr variableExpr) {

            return environment.get(
                    variableExpr.getName()
            );
        }

        // BINARY
        if (expr instanceof BinaryExpr binaryExpr) {

            return evaluateBinary(binaryExpr);
        }

        // FUNCTION CALL
        if (expr instanceof CallExpr callExpr) {

            Object callee =
                    evaluate(callExpr.getCallee());

            if (!(callee instanceof Function function)) {

                throw new RuntimeException(
                        "Can only call functions."
                );
            }

            List<Object> arguments =
                    new ArrayList<>();

            for (Expr argument :
                    callExpr.getArguments()) {

                arguments.add(
                        evaluate(argument)
                );
            }

            if (arguments.size() != function.arity()) {

                throw new RuntimeException(
                        "Expected " +
                                function.arity() +
                                " arguments but got " +
                                arguments.size()
                );
            }

            return function.call(
                    this,
                    arguments
            );
        }

        throw new RuntimeException(
                "Unknown expression."
        );
    }

    private Object evaluateBinary(BinaryExpr expr) {

        Object left =
                evaluate(expr.getLeft());

        Object right =
                evaluate(expr.getRight());

        TokenType operator =
                expr.getOperator().getType();

        return switch (operator) {

            case PLUS ->
                    asInt(left) + asInt(right);

            case MINUS ->
                    asInt(left) - asInt(right);

            case STAR ->
                    asInt(left) * asInt(right);

            case SLASH ->
                    asInt(left) / asInt(right);

            case GREATER ->
                    asInt(left) > asInt(right);

            case GREATER_EQUAL ->
                    asInt(left) >= asInt(right);

            case LESS ->
                    asInt(left) < asInt(right);

            case LESS_EQUAL ->
                    asInt(left) <= asInt(right);

            case EQUAL_EQUAL ->
                    left.equals(right);

            case BANG_EQUAL ->
                    !left.equals(right);

            default ->
                    throw new RuntimeException(
                            "Unsupported operator."
                    );
        };
    }

    private int asInt(Object value) {

        if (value instanceof Integer integer) {
            return integer;
        }

        throw new RuntimeException(
                "Expected integer."
        );
    }

    private boolean isTruthy(Object value) {

        if (value instanceof Boolean bool) {
            return bool;
        }

        if (value instanceof Integer integer) {
            return integer != 0;
        }

        return value != null;
    }

    public Environment getEnvironment() {
        return environment;
    }
}