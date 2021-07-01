package com.pranitpatil.repository;

public class SearchCriteria {

    private String key;
    private Object value;
    private Operation operation;

    public SearchCriteria(String key, Operation operation, Object value) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public enum Operation{
        EQUALS,
        NOT_EQUALS,
        GREATER_THAN,
        LESS_THAN,
        IS_NULL,
        IS_NOT_NULL
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public Operation getOperation() {
        return operation;
    }
}
