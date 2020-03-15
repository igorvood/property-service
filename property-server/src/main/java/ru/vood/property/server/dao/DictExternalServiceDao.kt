package ru.vood.property.server.dao

import com.example.demo.generate.DictExternalService
import org.springframework.jdbc.core.JdbcOperations
import ru.vood.property.server.dao.abstraction.AbstractDaoService

//@Service
class DictExternalServiceDao(private val jdbcOperations: JdbcOperations) : AbstractDaoService<DictExternalService>(DictExternalService::class.java) {


//    fun getAllDictExternalService():List<DictExternalService>{
//        jdbcOperations.queryForList(sqls[S])
//    }

}