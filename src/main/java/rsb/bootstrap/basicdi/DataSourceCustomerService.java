package rsb.bootstrap.basicdi;

import javax.sql.DataSource;


import rsb.bootstrap.BaseCustomerService;

public class DataSourceCustomerService extends BaseCustomerService {

    public DataSourceCustomerService(DataSource dataSource) {

        super(dataSource);
    }
}
