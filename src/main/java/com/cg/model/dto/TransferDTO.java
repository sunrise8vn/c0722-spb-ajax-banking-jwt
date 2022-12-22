package com.cg.model.dto;

import com.cg.model.Transfer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransferDTO {

    private Long id;
    private CustomerDTO sender;
    private CustomerDTO recipient;
    private BigDecimal transferAmount;
    private Long fees;
    private BigDecimal feesAmount;
    private BigDecimal transactionAmount;

    public Transfer toTransfer() {
        return new Transfer()
                .setId(id)
                .setSender(sender.toCustomer())
                .setRecipient(recipient.toCustomer())
                .setTransferAmount(transferAmount)
                .setFees(fees)
                .setFeesAmount(feesAmount)
                .setTransactionAmount(transactionAmount);
    }
}
