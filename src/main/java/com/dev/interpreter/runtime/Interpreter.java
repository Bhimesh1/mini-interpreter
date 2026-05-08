package com.dev.interpreter.runtime;

import com.dev.interpreter.ast.*;
import com.dev.interpreter.lexer.TokenType;

public class Interpreter {

    public Object evaluate(Expr expr) {

        if (expr instanceof LiteralExpr literalExpr) {
            return literalExpr.getValue();
        }

        if (expr instanceof GroupingExpr groupingExpr) {
            return evaluate(groupingExpr.getExpression());
        }

        if (expr instanceof BinaryExpr binaryExpr) {
            return evaluateBinary(binaryExpr);
        }

        if (expr instanceof VariableExpr variableExpr) {
            throw new RuntimeException("Undefined variable: " + variableExpr.getName());
        }

        throw new RuntimeException("Unknown expression type.");
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

            default -> throw new RuntimeException("Unsupported binary operator: " + operator);
        };
    }

    private int asInt(Object value) {

        if (value instanceof Integer integer) {
            return integer;
        }

        throw new RuntimeException("Expected integer but got: " + value);
    }
}