package rsb.bootstrap.templates;

import java.util.Collection;

import javax.sql.DataSource;


import org.springframework.transaction.support.TransactionTemplate;


import rsb.bootstrap.BaseCustomerService;
import rsb.bootstrap.Customer;

public class TransactionTemplateCustomerService extends BaseCustomerService {

    private final TransactionTemplate transactionTemplate;

    public TransactionTemplateCustomerService(DataSource dataSource, TransactionTemplate transactionTemplate) {

        super(dataSource);
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Collection<Customer> save(String... names) {

        return this.transactionTemplate.execute(it -> super.save(names));
    }

    @Override
    public Customer findById(Long id) {

        return this.transactionTemplate.execute(it -> super.findById(id));
    }

    @Override
    public Collection<Customer> findAll() {

        return this.transactionTemplate.execute(it -> super.findAll());
    }
}
