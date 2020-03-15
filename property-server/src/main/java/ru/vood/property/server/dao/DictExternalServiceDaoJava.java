package ru.vood.property.server.dao;

import com.example.demo.generate.DictExternalService;
import org.springframework.stereotype.Service;
import ru.vood.property.server.dao.abstraction.AbstractDaoService;

@Service
public class DictExternalServiceDaoJava extends AbstractDaoService<DictExternalService> {

    public DictExternalServiceDaoJava() {
        super(DictExternalService.class);
    }
}
