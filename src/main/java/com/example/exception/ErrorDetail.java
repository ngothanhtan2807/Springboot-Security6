package com.example.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDetail {
    private final Integer errorCode;
    private final String errorMessage;
}
