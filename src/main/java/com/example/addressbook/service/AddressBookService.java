package com.example.addressbook.service;

import com.example.addressbook.dto.AddressDTO;
import com.example.addressbook.model.Address;
import com.example.addressbook.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressBookService {

    @Autowired
    private AddressRepository addressRepository;

    // Convert Model to DTO
    private AddressDTO convertToDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setName(address.getName());
        dto.setPhoneNumber(address.getPhoneNumber());
        dto.setEmail(address.getEmail());
        return dto;
    }

    // Get all addresses
    public List<AddressDTO> getAllAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get address by ID
    public ResponseEntity<AddressDTO> getAddressById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        return address.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new address
    public AddressDTO addAddress(AddressDTO addressDTO) {
        Address address = new Address(addressDTO.getName(), addressDTO.getPhoneNumber(), addressDTO.getEmail());
        Address savedAddress = addressRepository.save(address);
        return convertToDTO(savedAddress);
    }

    // Update an address
    public ResponseEntity<AddressDTO> updateAddress(Long id, AddressDTO updatedDTO) {
        return addressRepository.findById(id)
                .map(existingAddress -> {
                    existingAddress.setName(updatedDTO.getName());
                    existingAddress.setPhoneNumber(updatedDTO.getPhoneNumber());
                    existingAddress.setEmail(updatedDTO.getEmail());
                    addressRepository.save(existingAddress);
                    return ResponseEntity.ok(convertToDTO(existingAddress));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete an address
    public ResponseEntity<Void> deleteAddress(Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
