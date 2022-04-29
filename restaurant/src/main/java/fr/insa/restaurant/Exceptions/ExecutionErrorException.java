package fr.insa.restaurant.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExecutionErrorException extends Exception {

    public HttpStatus httpsCodeStatus;

    public ExecutionErrorException(String error, HttpStatus httpsCodeStatus) {
        super(error);
        this.httpsCodeStatus = httpsCodeStatus;
    }

    public HttpStatus getHttpsCodeStatus() {
        return httpsCodeStatus;
    }
}
