package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

  private Map<UUID, CustomerDTO> customerMap;

  public CustomerServiceImpl() {
    this.customerMap = new HashMap<>();

    CustomerDTO customer1 = CustomerDTO.builder()
            .id(UUID.randomUUID())
            .version(1)
            .name("John Doe")
            .createdDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();

    CustomerDTO customer2 = CustomerDTO.builder()
            .id(UUID.randomUUID())
            .version(1)
            .name("Jane Smith")
            .createdDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();

    CustomerDTO customer3 = CustomerDTO.builder()
            .id(UUID.randomUUID())
            .version(1)
            .name("Alice Johnson")
            .createdDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();

    customerMap.put(customer1.getId(),customer1);
    customerMap.put(customer2.getId(),customer2);
    customerMap.put(customer3.getId(),customer3);
  }

  @Override
  public List<CustomerDTO> listCustomers() {
    return new ArrayList<>(customerMap.values());
  }

  @Override
  public CustomerDTO getCustomerById(UUID id) {

    log.debug("Get Customer by Id - in service. Id: " + id.toString());

    return customerMap.get(id);
  }
}
