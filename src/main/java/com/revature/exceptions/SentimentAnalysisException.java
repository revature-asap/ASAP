package com.revature.exceptions;

/**
 * Exception for all exceptions relating to the Sentiment Analysis functionality
 */
public class SentimentAnalysisException extends RuntimeException {

    public SentimentAnalysisException(String message){

        super(message);
    }
}
