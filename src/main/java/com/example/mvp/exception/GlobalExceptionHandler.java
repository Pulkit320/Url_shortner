package com.example.mvp.exception;

import com.example.mvp.dto.apiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<apiErrorResponse> handleValidationException(
            MethodArgumentNotValidException e){
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .filter(error->"NotBlank".equals(error.getCode()))
                .map(error->error.getDefaultMessage())
                .findFirst()
                .orElseGet(()->e.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error->error.getDefaultMessage())
                        .findFirst()
                        .orElse("Invalid request")
                );

        apiErrorResponse errorResponse = new apiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message
        );

        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<apiErrorResponse> handlResponseStatusException(
            ResponseStatusException e
    ){
        HttpStatus status = HttpStatus.valueOf(e.getStatusCode().value());
        apiErrorResponse errorResponse = new apiErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                e.getReason()!=null?e.getReason():status.getReasonPhrase()
        );
        return ResponseEntity.status(status).body(errorResponse);
    }
}
