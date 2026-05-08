package com.dev.interpreter.runtime;

import com.dev.interpreter.ast.*;
import com.dev.interpreter.lexer.TokenType;

import java.util.List;

public class Interpreter {

    private final Environment environment = new Environment();

    public void execute(List<Stmt> statements) {

        for (Stmt stmt : statements) {
            execute(stmt);
        }
    }

    private void execute(Stmt stmt) {

        if (stmt instanceof AssignStmt assignStmt) {

            Object value = evaluate(assignStmt.getValue());

            environment.define(assignStmt.getName(), value);

            return;
        }

        if (stmt instanceof ExpressionStmt expressionStmt) {

            evaluate(expressionStmt.getExpression());

            return;
        }

        if (stmt instanceof IfStmt ifStmt) {

            Object condition = evaluate(ifStmt.getCondition());

            if (isTruthy(condition)) {
                execute(ifStmt.getThenBranch());
            } else {
                execute(ifStmt.getElseBranch());
            }

            return;
        }
        if (stmt instanceof BlockStmt blockStmt) {

            for (Stmt statement : blockStmt.getStatements()) {
                execute(statement);
            }

            return;
        }
        if (stmt instanceof WhileStmt whileStmt) {

            while (isTruthy(evaluate(whileStmt.getCondition()))) {
                execute(whileStmt.getBody());
            }

            return;
        }

        throw new RuntimeException("Unknown statement.");
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

    private Object evaluate(Expr expr) {

        if (expr instanceof LiteralExpr literalExpr) {
            return literalExpr.getValue();
        }

        if (expr instanceof GroupingExpr groupingExpr) {
            return evaluate(groupingExpr.getExpression());
        }

        if (expr instanceof VariableExpr variableExpr) {
            return environment.get(variableExpr.getName());
        }

        if (expr instanceof BinaryExpr binaryExpr) {
            return evaluateBinary(binaryExpr);
        }

        throw new RuntimeException("Unknown expression.");
    }

    private Object evaluateBinary(BinaryExpr expr) {

        Object left = evaluate(expr.getLeft());
        Object right = evaluate(expr.getRight());

        TokenType operator = expr.getOperator().getType();

        return switch (operator) {

            case PLUS -> asInt(left) + asInt(right);
            case MINUS -> asInt(left) - asInt(right);
            case STAR -> asInt(left) * asInt(right);
            case SLASH -> asInt(left) / asInt(right);

            case GREATER -> asInt(left) > asInt(right);
            case GREATER_EQUAL -> asInt(left) >= asInt(right);
            case LESS -> asInt(left) < asInt(right);
            case LESS_EQUAL -> asInt(left) <= asInt(right);

            case EQUAL_EQUAL -> left.equals(right);
            case BANG_EQUAL -> !left.equals(right);

            default -> throw new RuntimeException("Unsupported operator.");
        };
    }

    private int asInt(Object value) {

        if (value instanceof Integer integer) {
            return integer;
        }

        throw new RuntimeException("Expected integer.");
    }

    public Environment getEnvironment() {
        return environment;
    }
}