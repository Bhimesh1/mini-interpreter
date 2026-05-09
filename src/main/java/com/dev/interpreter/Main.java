package com.dev.interpreter;

import com.dev.interpreter.ast.Stmt;
import com.dev.interpreter.lexer.Lexer;
import com.dev.interpreter.lexer.Token;
import com.dev.interpreter.parser.Parser;
import com.dev.interpreter.runtime.Environment;
import com.dev.interpreter.runtime.Interpreter;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Read entire source program from stdin
        Scanner scanner = new Scanner(System.in);

        StringBuilder source = new StringBuilder();

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            if (line.equals("exit")) {
                break;
            }

            source.append(line).append("\n");
        }

        String program = source.toString();

        Lexer lexer = new Lexer(program);

        List<Token> tokens = lexer.tokenize();

        // Parsing
        Parser parser = new Parser(tokens);

        List<Stmt> statements = parser.parse();

        // Interpretation
        Interpreter interpreter = new Interpreter();

        interpreter.execute(statements);

        // Print variables
        Environment environment = interpreter.getEnvironment();

        for (Map.Entry<String, Object> entry :
                environment.getValues().entrySet()) {

            Object value = entry.getValue();

            // Skip functions
            if (value instanceof com.dev.interpreter.runtime.Function) {
                continue;
            }

            System.out.println(
                    entry.getKey() + ": " + value
            );
        }
    }
}