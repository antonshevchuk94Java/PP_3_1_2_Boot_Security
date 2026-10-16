package ru.kata.spring.boot_security.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kata.spring.boot_security.demo.controller.AdminRestController;
import ru.kata.spring.boot_security.demo.controller.UserRestController;

import java.util.Map;

@RestControllerAdvice(assignableTypes = {AdminRestController.class, UserRestController.class})
public class ApiExceptionHandler {

    @ExceptionHandler(DuplicateLoginException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateLogin(DuplicateLoginException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(UserDeleteException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserDeleteException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
    }
}