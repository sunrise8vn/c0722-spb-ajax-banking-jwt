package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.LocationRegion;
import com.cg.model.Transfer;
import com.cg.model.dto.TransferDTO;
import com.cg.model.dto.TransferResponseDTO;
import com.cg.repository.CustomerRepository;
import com.cg.repository.DepositRepository;
import com.cg.repository.LocationRegionRepository;
import com.cg.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLike(String fullName, String email, String phone) {
        return customerRepository.findAllByFullNameLikeOrEmailLikeOrPhoneLike(fullName, email, phone);
    }

    @Override
    public List<Customer> getAllRecipientsWithoutSender(Long senderId) {
        return customerRepository.findAllByIdNot(senderId);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public Customer deposit(Customer customer, BigDecimal transactionAmount) {
        incrementBalance(customer.getId(), transactionAmount);

        Deposit deposit = new Deposit();
        deposit.setId(null);
        deposit.setCustomer(customer);
        deposit.setTransactionAmount(transactionAmount);
        depositRepository.save(deposit);

        Optional<Customer> customerOptional = customerRepository.findById(customer.getId());

        return customerOptional.get();
    }

    @Override
    public TransferResponseDTO transfer(TransferDTO transferDTO) {
        customerRepository.decrementBalance(transferDTO.getSender().getId(), transferDTO.getTransactionAmount());
        customerRepository.incrementBalance(transferDTO.getRecipient().getId(), transferDTO.getTransferAmount());

        Transfer transfer = transferDTO.toTransfer();
        transfer.setId(null);
        transferRepository.save(transfer);

        Optional<Customer> senderOptional = customerRepository.findById(transferDTO.getSender().getId());
        Optional<Customer> recipientOptional = customerRepository.findById(transferDTO.getRecipient().getId());

        TransferResponseDTO transferResponseDTO = new TransferResponseDTO();
        transferResponseDTO.setSender(senderOptional.get().toCustomerDTO());
        transferResponseDTO.setRecipient(recipientOptional.get().toCustomerDTO());

        return transferResponseDTO;
    }

    @Override
    public void incrementBalance(Long customerId, BigDecimal transactionAmount) {
        customerRepository.incrementBalance(customerId, transactionAmount);
    }

    @Override
    public void decrementBalance(Long customerId, BigDecimal transactionAmount) {
        customerRepository.decrementBalance(customerId, transactionAmount);
    }

    @Override
    public Customer save(Customer customer) {

        LocationRegion locationRegion = customer.getLocationRegion();
        locationRegionRepository.save(locationRegion);

        customer.setId(null);
        customer.setBalance(BigDecimal.valueOf(0L));
        customer.setLocationRegion(locationRegion);

        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
