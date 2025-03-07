package com.example.addressbook.service;

import com.example.addressbook.dto.AddressDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class AddressBookService {

    private final List<AddressDTO> addressList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    // Get all addresses
    public List<AddressDTO> getAllAddresses() {
        return addressList;
    }

    // Get address by ID
    public ResponseEntity<AddressDTO> getAddressById(Long id) {
        return addressList.stream()
                .filter(address -> address.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new address
    public AddressDTO addAddress(AddressDTO addressDTO) {
        addressDTO.setId(idCounter.getAndIncrement());
        addressList.add(addressDTO);
        return addressDTO;
    }

    // Update an address
    public ResponseEntity<AddressDTO> updateAddress(Long id, AddressDTO updatedDTO) {
        for (AddressDTO address : addressList) {
            if (address.getId().equals(id)) {
                address.setName(updatedDTO.getName());
                address.setPhoneNumber(updatedDTO.getPhoneNumber());
                address.setEmail(updatedDTO.getEmail());
                return ResponseEntity.ok(address);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Delete an address
    public ResponseEntity<Void> deleteAddress(Long id) {
        boolean removed = addressList.removeIf(address -> address.getId().equals(id));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
