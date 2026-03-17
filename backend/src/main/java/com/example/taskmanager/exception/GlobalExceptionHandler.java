package com.example.taskmanager.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// @ControllerAdvice = diese Klasse gilt fuer ALLE Controller gleichzeitig
// Statt in jedem Controller Fehler zu behandeln, machen wir es hier zentral
@ControllerAdvice
public class GlobalExceptionHandler {

    // Wird aufgerufen wenn @Valid einen Fehler findet
    // MethodArgumentNotValidException enthaelt alle Fehlermeldungen
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        // Map: Feldname -> Fehlermeldung
        // z.B. "username" -> "Username darf nicht leer sein"
        Map<String, String> errors = new HashMap<>();

        // getBindingResult() gibt alle Validierungsfehler zurueck
        // getFieldErrors() gibt nur Fehler auf bestimmten Feldern
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            // getField() = Name des Feldes (z.B. "username")
            // getDefaultMessage() = unser message= Text aus der Annotation
            errors.put(error.getField(), error.getDefaultMessage());
        }

        // HTTP 400 Bad Request = der Client hat ungueltige Daten geschickt
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFound.class)
    public ResponseEntity<String> handleTaskNotFound(TaskNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectNotFound.class)
    public ResponseEntity<String> handleProjectNotFound(ProjectNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}