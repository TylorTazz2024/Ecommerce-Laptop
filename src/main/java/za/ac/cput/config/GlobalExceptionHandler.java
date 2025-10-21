package za.ac.cput.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Cannot delete resource due to related data. Remove or reassign related records first.";
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error", "DATA_INTEGRITY_VIOLATION",
                        "message", message
                ));
    }
}
