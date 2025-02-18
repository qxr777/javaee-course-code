package edu.whut.cs.javaee.spring.ai.sql;

public class SqlGenerationException extends RuntimeException {
    public SqlGenerationException(String response) {
        super(response);
    }
}
