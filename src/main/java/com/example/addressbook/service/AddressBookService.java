package com.example.addressbook.service;

import com.example.addressbook.model.Address;
import com.example.addressbook.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressBookService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public ResponseEntity<Address> getAddressById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        return address.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    public ResponseEntity<Address> updateAddress(Long id, Address updatedAddress) {
        return addressRepository.findById(id)
                .map(existingAddress -> {
                    existingAddress.setName(updatedAddress.getName());
                    existingAddress.setPhoneNumber(updatedAddress.getPhoneNumber());
                    existingAddress.setEmail(updatedAddress.getEmail());
                    addressRepository.save(existingAddress);
                    return ResponseEntity.ok(existingAddress);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deleteAddress(Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
