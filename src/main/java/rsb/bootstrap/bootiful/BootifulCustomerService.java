package rsb.bootstrap.bootiful;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;


import rsb.bootstrap.enable.TransactionalCustomerService;

@Service
public class BootifulCustomerService extends TransactionalCustomerService {

    public BootifulCustomerService(DataSource dataSource) {

        super(dataSource);
    }
}
