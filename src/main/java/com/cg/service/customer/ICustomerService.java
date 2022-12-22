package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.dto.TransferDTO;
import com.cg.model.dto.TransferResponseDTO;
import com.cg.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {

    List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLike(String fullName, String email, String phone);

    List<Customer> getAllRecipientsWithoutSender(Long senderId);

    void incrementBalance(Long customerId, BigDecimal transactionAmount);

    void decrementBalance(Long customerId, BigDecimal transactionAmount);

    Customer deposit(Customer customer, BigDecimal transactionAmount);

    TransferResponseDTO transfer(TransferDTO transferDTO);
}
