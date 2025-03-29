package com.miroslav.acitivity_tracker.handler;

import com.miroslav.acitivity_tracker.exception.ActionNotAllowed;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashSet;
import java.util.Set;

import static com.miroslav.acitivity_tracker.handler.BusinessErrorCodes.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(ACCOUNT_LOCKED.getCode())
                                .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleException(DataIntegrityViolationException exp){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(ITEM_ALREADY_EXISTS.getCode())
                                .businessErrorDescription(ITEM_ALREADY_EXISTS.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleException(AuthorizationDeniedException exp){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(ACCESS_DENIED.getCode())
                                .businessErrorDescription(ACCESS_DENIED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ExceptionResponse> handleException(HandlerMethodValidationException exp){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(VALIDATION_FAILURE.getCode())
                                .businessErrorDescription(VALIDATION_FAILURE.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(ActionNotAllowed.class)
    public ResponseEntity<ExceptionResponse> handleException(ActionNotAllowed exp){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(ACTION_NOT_ALLOWED.getCode())
                        .businessErrorDescription(ACTION_NOT_ALLOWED.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(EntityNotFoundException exp){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(ENTITY_NOT_FOUND.getCode())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(ACCOUNT_DISABLED.getCode())
                                .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BAD_CREDENTIALS.getCode())
                                .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                                .error(BAD_CREDENTIALS.getDescription())
                                .build()
                );
    }
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp){
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(VALIDATION_FAILURE.getCode())
                                .businessErrorDescription(VALIDATION_FAILURE.getDescription())
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp){
        exp.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Internal error, contact the admin")
                                .error(exp.getMessage())
                                .build()
                );
    }
}
