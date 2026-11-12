
package Task.Management.API.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles invalid input
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.badRequest().body(
                Map.of(
                        "status", 400,
                        "error", "Bad Request",
                        "message", message
                )
        );
    }

    // Handles tasks or users not found
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleNotFound(RuntimeException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "status", 404,
                        "error", "Not Found",
                        "message", ex.getMessage()
                )
        );
    }

    // Handles duplicate email or other illegal arguments
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleBadRequest(IllegalArgumentException ex) {

        return ResponseEntity.badRequest().body(
                Map.of(
                        "status", 400,
                        "error", "Bad Request",
                        "message", ex.getMessage()
                )
        );
    }
}
