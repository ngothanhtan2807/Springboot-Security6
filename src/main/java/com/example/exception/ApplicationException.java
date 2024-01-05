package com.example.exception;

public class ApplicationException extends RuntimeException{
    protected final transient ErrorDetail errorDetail;

    public ApplicationException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorDetail = ErrorDetail.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }
    public ApplicationException(int errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorDetail = ErrorDetail.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }
}
