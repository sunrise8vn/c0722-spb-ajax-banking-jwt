package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DepositCreateDTO implements Validator {

    private String customerId;
    private String transactionAmount;

    @Override
    public boolean supports(Class<?> clazz) {
        return DepositCreateDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        DepositCreateDTO depositCreateDTO = (DepositCreateDTO) target;

        String customerId = depositCreateDTO.getCustomerId();
        String transactionAmountStr = depositCreateDTO.getTransactionAmount();


        if (customerId.length() == 0) {
            errors.rejectValue("customerId", "customerId.length", "Customer Id not empty");
        }

        if (!customerId.matches("^\\d+$")) {
            errors.rejectValue("customerId", "customerId.matches", "Customer Id is accept only number");
        }

        if (transactionAmountStr.length() == 0) {
            errors.rejectValue("transactionAmount", "transactionAmount.length", "Transaction Amount not empty");
        }

        if (!transactionAmountStr.matches("^\\d+$")) {
            errors.rejectValue("transactionAmount", "transactionAmount.matches", "Transaction Amount is accept only number");
            return;
        }

        double transactionAmount = Double.parseDouble(transactionAmountStr);

        if (transactionAmount % 10 > 0) {
            errors.rejectValue("transactionAmount", "transactionAmount.percent", "Transaction Amount accept only even number");

        }

    }
}
