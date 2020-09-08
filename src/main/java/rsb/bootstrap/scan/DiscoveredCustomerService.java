package rsb.bootstrap.scan;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;


import rsb.bootstrap.templates.TransactionTemplateCustomerService;

@Service
public class DiscoveredCustomerService extends TransactionTemplateCustomerService {

    public DiscoveredCustomerService(DataSource dataSource,
            TransactionTemplate transactionTemplate) {

        super(dataSource, transactionTemplate);
    }
}
