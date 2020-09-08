package rsb.bootstrap.bootiful;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import rsb.bootstrap.Customer;
import rsb.bootstrap.CustomerService;

@RestController
public class BootifulCustomerController {

    private final CustomerService customerService;

    public BootifulCustomerController(CustomerService customerService) {

        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public Collection<Customer> get() {

        return this.customerService.findAll();
    }
}
