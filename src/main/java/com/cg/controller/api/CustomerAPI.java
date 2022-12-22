package com.cg.controller.api;


import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Customer;
import com.cg.model.dto.CustomerCreateDTO;
import com.cg.model.dto.CustomerResponseDTO;
import com.cg.model.dto.CustomerUpdateDTO;
import com.cg.model.dto.LocationRegionDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {

    @Autowired
    private AppUtil appUtil;

    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getAll() {

        List<Customer> customers = customerService.findAll();

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Customer customer = customerOptional.get();

        CustomerResponseDTO customerResponseDTO = customer.toCustomerResponseDTO();

        return new ResponseEntity<>(customerResponseDTO, HttpStatus.OK);

    }

    @GetMapping("/get-all-recipients-without-sender/{senderId}")
    public ResponseEntity<?> getAllRecipientsWithoutSender(@PathVariable Long senderId) {

        List<Customer> recipients = customerService.getAllRecipientsWithoutSender(senderId);

        return new ResponseEntity<>(recipients, HttpStatus.OK);
    }

    @GetMapping("/get-sender-info-and-recipients-without-sender/{senderId}")
    public ResponseEntity<?> getSenderInfoAndRecipientsWithoutSenderId(@PathVariable Long senderId) {

        Optional<Customer> customerOptional = customerService.findById(senderId);

        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Customer sender = customerOptional.get();

        CustomerResponseDTO senderResponseDTO = sender.toCustomerResponseDTO();

        List<Customer> recipients = customerService.getAllRecipientsWithoutSender(senderId);

        Map<String, Object> result = new HashMap<>();
        result.put("sender", senderResponseDTO);
        result.put("recipients", recipients);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> create(@RequestBody CustomerCreateDTO customerCreateDTO, BindingResult bindingResult) {

        new CustomerCreateDTO().validate(customerCreateDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Customer customer = customerCreateDTO.toCustomer();

        customerService.save(customer);

        return new ResponseEntity<>(customer.toCustomerDTO(), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> create(@PathVariable Long id, @RequestBody CustomerUpdateDTO customerUpdateDTO) {

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Customer invalid");
        }

        Customer customer = customerOptional.get();
        customer.setFullName(customerUpdateDTO.getFullName());
        customer.setEmail(customerUpdateDTO.getEmail());
        customer.setPhone(customerUpdateDTO.getPhone());
//        customer.setAddress(customerUpdateDTO.getAddress());

        customerService.save(customer);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
