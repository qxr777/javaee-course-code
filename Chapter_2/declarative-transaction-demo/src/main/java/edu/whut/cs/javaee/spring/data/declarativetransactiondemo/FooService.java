package edu.whut.cs.javaee.spring.data.declarativetransactiondemo;

public interface FooService {
    void insertRecord();
    void insertThenRollback() throws RollbackException;
    void invokeInsertThenRollback() throws RollbackException;
}
