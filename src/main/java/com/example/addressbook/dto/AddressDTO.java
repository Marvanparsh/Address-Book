package com.example.addressbook.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
}
