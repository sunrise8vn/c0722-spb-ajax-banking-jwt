package com.cg.controller.api;


import com.cg.exception.DataInputException;
import com.cg.model.Customer;
import com.cg.model.Transfer;
import com.cg.model.dto.TransferCreateDTO;
import com.cg.model.dto.TransferDTO;
import com.cg.model.dto.TransferResponseDTO;
import com.cg.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/transfers")
public class TransferAPI {

    @Autowired
    private ICustomerService customerService;

    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody TransferCreateDTO transferCreateDTO) {

        Long senderId = transferCreateDTO.getSenderId();
        Long recipientId = transferCreateDTO.getRecipientId();
        BigDecimal transferAmount = transferCreateDTO.getTransferAmount();

        Optional<Customer> senderOptional = customerService.findById(senderId);

        if (!senderOptional.isPresent()) {
            throw new DataInputException("Sender information not valid");
        }

        Optional<Customer> recipientOptional = customerService.findById(recipientId);

        if (!recipientOptional.isPresent() || senderId.equals(recipientId)) {
            throw new DataInputException("Recipient information not valid");
        }

        long fees = 10L;
        BigDecimal feesAmount = transferAmount.multiply(BigDecimal.valueOf(fees).divide(BigDecimal.valueOf(100L)));
        BigDecimal transactionAmount = transferAmount.add(feesAmount);
        long transactionAmountLong = transactionAmount.longValue();

        if (transactionAmountLong % 10 > 0) {
            throw new DataInputException("Transaction amount must be divisible by 10");
        }

        BigDecimal senderCurrentBalance = senderOptional.get().getBalance();

        if (senderCurrentBalance.compareTo(transactionAmount) < 0) {
            throw new DataInputException("Sender balance not enough money");
        }

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSender(senderOptional.get().toCustomerDTO());
        transferDTO.setRecipient(recipientOptional.get().toCustomerDTO());
        transferDTO.setTransferAmount(transferAmount);
        transferDTO.setFees(fees);
        transferDTO.setFeesAmount(feesAmount);
        transferDTO.setTransactionAmount(transactionAmount);

        TransferResponseDTO transferResponseDTO = customerService.transfer(transferDTO);

        return new ResponseEntity<>(transferResponseDTO, HttpStatus.OK);
    }
}
