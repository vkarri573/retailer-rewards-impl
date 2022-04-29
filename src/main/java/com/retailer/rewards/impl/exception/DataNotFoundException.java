package com.retailer.rewards.impl.exception;

/**
 * Thrown when data not found in database.
 */
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String erroMsg) {
        super(erroMsg);
    }
}
