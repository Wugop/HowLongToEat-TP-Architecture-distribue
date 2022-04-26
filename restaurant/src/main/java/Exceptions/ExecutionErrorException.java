package Exceptions;

import org.springframework.http.HttpStatus;

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
