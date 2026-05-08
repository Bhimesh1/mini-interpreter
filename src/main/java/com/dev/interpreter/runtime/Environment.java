package com.dev.interpreter.runtime;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    private final Environment parent;
    private final Map<String, Object> values = new HashMap<>();

    public Environment() {
        this.parent = null;
    }

    public Environment(Environment parent) {
        this.parent = parent;
    }

    public void define(String name, Object value) {
        values.put(name, value);
    }

    public Object get(String name) {

        if (values.containsKey(name)) {
            return values.get(name);
        }

        if (parent != null) {
            return parent.get(name);
        }

        throw new RuntimeException("Undefined variable: " + name);
    }

    public Map<String, Object> getValues() {
        return values;
    }
}