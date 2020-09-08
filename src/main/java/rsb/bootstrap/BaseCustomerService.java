package rsb.bootstrap;

import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.util.Assert;

public class BaseCustomerService implements CustomerService {

    private final RowMapper<Customer> rowMapper = (rs, i) -> new Customer(rs.getLong("id"), rs.getString("name"));

    private final JdbcTemplate jdbcTemplate;

    public BaseCustomerService(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Collection<Customer> save(String... names) {

        return Arrays.asList(names).stream().map(name -> {
            Assert.notNull(name, "name must not be null");
            var keyHolder = new GeneratedKeyHolder();
            this.jdbcTemplate.update(con -> {
                var ps = con.prepareStatement("insert into CUSTOMERS (name) values(?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                return ps;
            }, keyHolder);
            var keyHolderKey = Objects.requireNonNull(keyHolder.getKey()).longValue();
            return findById(keyHolderKey);
        }).collect(Collectors.toList());
    }

    @Override
    public Customer findById(Long id) {

        return this.jdbcTemplate.queryForObject("select * from CUSTOMERS where id = ?", this.rowMapper, id);
    }

    @Override
    public Collection<Customer> findAll() {

        return this.jdbcTemplate.query("select * from CUSTOMERS", this.rowMapper);
    }
}
