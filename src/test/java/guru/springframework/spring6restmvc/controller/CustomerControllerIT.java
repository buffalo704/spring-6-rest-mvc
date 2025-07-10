package guru.springframework.spring6restmvc.controller;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.event.TransactionalEventListener;

import guru.springframework.spring6restmvc.entities.Customer;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CustomerControllerIT {
  @Autowired
  CustomerController customerController;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  CustomerMapper customerMapper;

  @Rollback
  @Transactional
  @Test
  void testSaveNewCustomer() {
    CustomerDTO customerDTO = CustomerDTO.builder()
        .name("New Customer")
        .build();

    ResponseEntity responseEntity = customerController.handlePost(customerDTO);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
    assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

    String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
    UUID savedUUID = UUID.fromString(locationUUID[4]);

    Customer customer = customerRepository.findById(savedUUID).get();
    assertThat(customer).isNotNull();
  }

  @Test
  void testCustomerIdNotFound() {
    assertThrows(NotFoundException.class, () -> {
      customerController.getCustomerById(UUID.randomUUID());
    });
  }

  @Test
  void testGetCustomerById() {
    Customer customer = customerRepository.findAll().get(0);
    CustomerDTO dto = customerController.getCustomerById(customer.getId());

    assertThat(dto).isNotNull();
  }

  @Test
  void testListCustomers() {
    List<CustomerDTO> dtos = customerController.listCustomers();
    assertThat(dtos.size()).isEqualTo(3);
  }

  @Rollback
  @Transactional
  @Test
  void testEmptyList() {
    customerRepository.deleteAll();

    List<CustomerDTO> dtos = customerController.listCustomers();
    assertThat(dtos.size()).isEqualTo(0);
  }
}
