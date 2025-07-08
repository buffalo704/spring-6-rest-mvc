package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
  private final CustomerService customerService;

  @RequestMapping(method = RequestMethod.GET)
  public List<CustomerDTO> listCustomers() {
    log.debug("List customers - in controller");

    return customerService.listCustomers();
  }

  @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
  public CustomerDTO getCustomerById(@PathVariable("customerId") UUID customerId) {
    log.debug("Get customer by id - in controller: {}", customerId);

    return customerService.getCustomerById(customerId);
  }
}
