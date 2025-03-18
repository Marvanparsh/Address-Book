package com.example.addressbook.service;

import com.example.addressbook.dto.AddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
        try {
            log.info("Fetching all addresses");
            return addressList;
        } catch (Exception e) {
            log.error("Error fetching addresses: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Cacheable(value = "addresses", key = "#id")
    public ResponseEntity<AddressDTO> getAddressById(Long id) {
        try {
            log.info("Fetching address with ID: {}", id);
            return addressList.stream()
                    .filter(address -> address.getId().equals(id))
                    .findFirst()
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        log.warn("Address with ID {} not found", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error fetching address with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @CachePut(value = "addresses", key = "#result.id")
    public AddressDTO addAddress(AddressDTO addressDTO) {
        try {
            addressDTO.setId(idCounter.getAndIncrement());
            addressList.add(addressDTO);
            log.info("Added new address: {}", addressDTO);
            return addressDTO;
        } catch (Exception e) {
            log.error("Error adding address: {}", e.getMessage(), e);
            return null;
        }
    }

    @CachePut(value = "addresses", key = "#id")
    public ResponseEntity<AddressDTO> updateAddress(Long id, AddressDTO updatedDTO) {
        try {
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
        } catch (Exception e) {
            log.error("Error updating address with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @CacheEvict(value = "addresses", key = "#id")
    public ResponseEntity<Void> deleteAddress(Long id) {
        try {
            boolean removed = addressList.removeIf(address -> address.getId().equals(id));
            if (removed) {
                log.info("Deleted address with ID: {}", id);
                return ResponseEntity.noContent().build();
            } else {
                log.warn("Address with ID {} not found for deletion", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error deleting address with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
