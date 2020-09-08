package rsb.bootstrap;

import java.util.stream.Stream;


import lombok.extern.log4j.Log4j2;

@Log4j2
public class Demo {

    public static void workWithCustomerService(Class<?> label, CustomerService customerService) {

        log.info("==========================");
        log.info(label.getName());
        log.info("==========================");
        Stream.of("A", "B", "C").map(customerService::save).forEach(customer -> log.info("saved " + customer));
        customerService.findAll().stream().map(Customer::getId).map(customerService::findById)
                .forEach(customer -> log.info("find " + customer));
        log.info("==========================");
    }
}
