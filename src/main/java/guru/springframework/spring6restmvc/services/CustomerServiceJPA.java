package guru.springframework.spring6restmvc.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  @Override
  public Optional<CustomerDTO> getCustomerById(UUID uuid) {
    return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.findById(uuid).orElse(null)));
  }

  @Override
  public List<CustomerDTO> listCustomers() {
    return customerRepository.findAll()
        .stream()
        .map(customerMapper::customerToCustomerDto)
        .collect(Collectors.toList());
  }
}
