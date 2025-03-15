package com.example.addressbook.service;

import com.example.addressbook.dto.AddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class AddressBookService {

    private final List<AddressDTO> addressList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<AddressDTO> getAllAddresses() {
        log.info("Fetching all addresses");
        return addressList;
    }
//add by id
    public ResponseEntity<AddressDTO> getAddressById(Long id) {
        log.info("Fetching address with ID: {}", id);
        return addressList.stream()
                .filter(address -> address.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Address with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }
//get
    public AddressDTO addAddress(AddressDTO addressDTO) {
        addressDTO.setId(idCounter.getAndIncrement());
        addressList.add(addressDTO);
        log.info("Added new address: {}", addressDTO);
        return addressDTO;
    }
//update
    public ResponseEntity<AddressDTO> updateAddress(Long id, AddressDTO updatedDTO) {
        for (AddressDTO address : addressList) {
            if (address.getId().equals(id)) {
                address.setName(updatedDTO.getName());
                address.setPhoneNumber(updatedDTO.getPhoneNumber());
                address.setEmail(updatedDTO.getEmail());
                log.info("Updated address with ID: {}", id);
                return ResponseEntity.ok(address);
            }
        }
        log.warn("Address with ID {} not found for update", id);
        return ResponseEntity.notFound().build();
    }
//delete
    public ResponseEntity<Void> deleteAddress(Long id) {
        boolean removed = addressList.removeIf(address -> address.getId().equals(id));
        if (removed) {
            log.info("Deleted address with ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Address with ID {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }
    }
}
