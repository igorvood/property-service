package ru.vood.property.server.dao;

import com.example.demo.generate.DictExternalService;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import ru.vood.property.server.dao.abstraction.AbstractDaoService;

@Service
public class DictExternalServiceDaoJava extends AbstractDaoService<DictExternalService> {

    private final JdbcOperations jdbcOperations;

    public DictExternalServiceDaoJava(JdbcOperations jdbcOperations) {
        super(DictExternalService.class);
        this.jdbcOperations = jdbcOperations;
    }
}
