package com.cg.model.dto;

import com.cg.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerCreateDTO implements Validator {

    private Long id;

    @NotEmpty(message = "Full Name is required")
    private String fullName;

    private String email;
    private String phone;

    @Valid
    private LocationRegionDTO locationRegion;

    //    @Pattern(regexp = "^\\d+$", message = "Balance is accept only number")
    private String balance;

    public Customer toCustomer() {
        return new Customer()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegion())
                .setBalance(BigDecimal.valueOf(Long.parseLong(balance)));
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerCreateDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CustomerCreateDTO customerCreateDTO = (CustomerCreateDTO) target;

        String email = customerCreateDTO.getEmail();

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,6}$")) {
            errors.rejectValue("email", "email.matches", "Email not valid");
            return;
        }

    }
}
