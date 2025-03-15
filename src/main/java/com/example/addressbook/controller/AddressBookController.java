package com.example.addressbook.controller;

import com.example.addressbook.dto.AddressDTO;
import com.example.addressbook.service.AddressBookService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping
    public List<AddressDTO> getAllAddresses() {
        return addressBookService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        return addressBookService.getAddressById(id);
    }

    @PostMapping
    public AddressDTO addAddress(@RequestBody AddressDTO addressDTO) {
        return addressBookService.addAddress(addressDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        return addressBookService.updateAddress(id, addressDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        return addressBookService.deleteAddress(id);
    }
}
