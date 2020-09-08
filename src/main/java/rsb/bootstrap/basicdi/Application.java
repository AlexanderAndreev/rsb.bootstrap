package rsb.bootstrap.basicdi;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


import rsb.bootstrap.DataSourceUtils;
import rsb.bootstrap.Demo;

public class Application {

    public static void main(String[] args) {
        var dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        var initializedDataSource = DataSourceUtils.initializeDbl(dataSource);
        var customerService = new DataSourceCustomerService(initializedDataSource);
        Demo.workWithCustomerService(Application.class, customerService);
    }
}
