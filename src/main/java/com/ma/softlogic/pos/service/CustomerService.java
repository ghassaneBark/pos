package com.ma.softlogic.pos.service;

import com.ma.softlogic.pos.dto.request.CustomerRequest;
import com.ma.softlogic.pos.dto.response.CustomerResponse;
import com.ma.softlogic.pos.mapper.CustomerMapper;
import com.ma.softlogic.pos.model.Customer;
import com.ma.softlogic.pos.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = mapper.toEntity(request);
        customer = repository.save(customer);
        return mapper.toResponse(customer);
    }

    public CustomerResponse updateCustomer(UUID id, CustomerRequest request) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'id : " + id));

        mapper.updateCustomerFromRequest(request, customer);
        customer = repository.save(customer);
        return mapper.toResponse(customer);
    }

    public List<CustomerResponse> getAllCustomers() {
        return null;
    }

    public CustomerResponse getCustomerById(UUID id) {
        return null;
    }

    public void deleteCustomer(UUID id) {
        
    }
}