package fr.insa.restaurant.repositories;

import fr.insa.restaurant.Exceptions.ExecutionErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class CommonRessource {

    @ExceptionHandler(ExecutionErrorException.class)
    public ResponseEntity<String> handleModelNotValidException(ExecutionErrorException ex) {
        return ResponseEntity
                .status(ex.getHttpsCodeStatus())
                .body(ex.getMessage());
    }
}

