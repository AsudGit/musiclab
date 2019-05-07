package com.lhs.musiclab.exception;

public class NoFoundException extends Exception {
    private static final long serialVersionUID = -5955607821816077172L;

    public NoFoundException(String errorInfo) {
        super(errorInfo);
    }
}
