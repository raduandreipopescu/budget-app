package course18.homework.budgetapp.controller;

import course18.homework.budgetapp.exception.TransactionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleTransactionNotFoundException(TransactionNotFoundException transactionNotFoundException) {
        return new ErrorResponse(transactionNotFoundException.getMessage());
    }

    record ErrorResponse(String message) {
    }
}
