package com.cloudtechies.txnvalidator.exception;

import com.cloudtechies.txnvalidator.enums.TransactionFieldsProps;
import com.cloudtechies.txnvalidator.enums.TransactionStatus;
import com.cloudtechies.txnvalidator.model.TransactionError;
import com.cloudtechies.txnvalidator.model.TransactionReport;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class DataValidationExceptionHandler {

    //Handle Validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<TransactionError> transactionErrors = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();
        bindingResult.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            String rejectedField=TransactionFieldsProps.getTransactionFieldMapping(fieldName);
            TransactionError transactionError=TransactionError.builder().
                    rejectedField(rejectedField).
                    rejectedReason(errorMessage).build();
        });

        TransactionReport transactionReport = (TransactionReport) bindingResult.getTarget();
        transactionReport.setTxnStatus(TransactionStatus.RJCT);
        transactionReport.setTxnErrors(transactionErrors);
    }
}
