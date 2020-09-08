package rsb.bootstrap.hardcoded;

import javax.sql.DataSource;


import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


import rsb.bootstrap.BaseCustomerService;
import rsb.bootstrap.DataSourceUtils;

public class DevelopmentOnlyCustomerService extends BaseCustomerService {

    public DevelopmentOnlyCustomerService() {

        super(buildDataSource());
    }

    private static DataSource buildDataSource() {
        var dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        return DataSourceUtils.initializeDbl(dataSource);
    }
}
