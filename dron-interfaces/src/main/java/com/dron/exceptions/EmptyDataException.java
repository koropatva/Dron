package com.dron.exceptions;

public class EmptyDataException extends Exception {

    private static final long serialVersionUID = 1L;

    public EmptyDataException(String e) {
        super(e);
    }

    public EmptyDataException(String e, Throwable t) {
        super(e, t);
    }

}
