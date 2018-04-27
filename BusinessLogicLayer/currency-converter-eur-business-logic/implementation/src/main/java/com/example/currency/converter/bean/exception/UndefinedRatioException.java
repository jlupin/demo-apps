// BusinessLogicLayer/currency-converter-business-logic/microservice/src/main/java/com/example/currency/converter/bean/exception/UndefinedRatioException.java
package com.example.currency.converter.bean.exception;

/**
 * @author Piotr Heilman
 */
public class UndefinedRatioException extends Exception {
    public UndefinedRatioException() {
    }

    public UndefinedRatioException(String message) {
        super(message);
    }
}
