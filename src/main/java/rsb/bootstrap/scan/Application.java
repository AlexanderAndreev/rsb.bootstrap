package rsb.bootstrap.scan;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;


import rsb.bootstrap.CustomerService;
import rsb.bootstrap.DataSourceConfiguration;
import rsb.bootstrap.Demo;
import rsb.bootstrap.SpringUtils;

@Configuration
@ComponentScan
@Import(DataSourceConfiguration.class)
public class Application {

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {

        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {

        return new TransactionTemplate(transactionManager);
    }

    public static void main(String[] args) {
        var context = SpringUtils.run(Application.class, "prod");
        var customerService = context.getBean(CustomerService.class);
        Demo.workWithCustomerService(Application.class, customerService);
    }
}
