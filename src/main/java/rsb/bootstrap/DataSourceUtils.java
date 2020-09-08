package rsb.bootstrap;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class DataSourceUtils {

    public static DataSource initializeDbl(DataSource dataSource) {

        var populator = new ResourceDatabasePopulator(new ClassPathResource("/schema.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);
        return dataSource;
    }
}
