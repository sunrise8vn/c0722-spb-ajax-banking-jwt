package com.cg.controller.api;


import com.cg.exception.DataInputException;
import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.dto.DepositCreateDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/deposits")
public class DepositAPI {

    @Autowired
    private AppUtil appUtil;

    @Autowired
    private IDepositService depositService;

    @Autowired
    private ICustomerService customerService;

    @PostMapping
    public ResponseEntity<?> deposit(@RequestBody DepositCreateDTO depositCreateDTO, BindingResult bindingResult) {

        new DepositCreateDTO().validate(depositCreateDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Long customerId = Long.parseLong(depositCreateDTO.getCustomerId());

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            throw new DataInputException("Customer invalid");
        }

        Customer customer = customerOptional.get();

        BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(depositCreateDTO.getTransactionAmount()));

        try {
            customer = customerService.deposit(customer, transactionAmount);

            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        catch (Exception e) {
            throw new DataInputException("Please check data again");
        }

    }
}
