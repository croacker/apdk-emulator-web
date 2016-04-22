package ru.peak.ml.terminalexchange.exception;

/**
 *
 */
public class ApplicationException extends IllegalStateException{

    public ApplicationException(String message) {

    }

    public ApplicationException(String message, Exception e){
        super(message, e);
        initCause(e);
    }


}
