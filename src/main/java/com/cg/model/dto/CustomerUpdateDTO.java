package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerUpdateDTO {

    @NotEmpty(message = "Full Name is required")
    private String fullName;

    private String email;
    private String phone;
    private String address;

}
