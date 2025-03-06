package com.example.addressbook.controller;

import com.example.addressbook.model.Address;
import com.example.addressbook.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressBookService.getAllAddresses();
    }

    @PostMapping
    public Address addAddress(@RequestBody Address address) {
        return addressBookService.addAddress(address);
    }
}
