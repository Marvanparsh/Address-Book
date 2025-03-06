package com.example.addressbook.service;

import com.example.addressbook.model.Address;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressBookService {
    private final List<Address> addressList = new ArrayList<>();
    private Long idCounter = 1L;

    public List<Address> getAllAddresses() {
        return addressList;
    }

    public Address addAddress(Address address) {
        address.setId(idCounter++);
        addressList.add(address);
        return address;
    }
}
