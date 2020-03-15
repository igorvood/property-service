package ru.vood.property.server.dao


import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import ru.vood.property.server.dao.abstraction.AbstractDaoService
import ru.vood.property.server.dao.dto.SqlOperation
import ru.vood.property.server.generation.dto.MetaCnsServiceException

@Service
class MetaCnsServiceExceptionDao(private val jdbcOperations: JdbcOperations) : AbstractDaoService<MetaCnsServiceException>(MetaCnsServiceException::class.java) {

    val rowMapper =
            RowMapper<MetaCnsServiceException> { rs, rowNum -> MetaCnsServiceException(rs.getString(1), rs.getString(1), rs.getString(1)) }

    override fun getOne(entity: MetaCnsServiceException): MetaCnsServiceException {
        val s = sqls[SqlOperation.SELECT_ONE]!!

        return jdbcOperations.queryForObject(s, rowMapper)!!
    }

    override fun getAll(): List<MetaCnsServiceException> {
        TODO("Not yet implemented")
    }

    override fun delete(entity: MetaCnsServiceException) {
        TODO("Not yet implemented")
    }

    override fun update(entity: MetaCnsServiceException) {
        TODO("Not yet implemented")
    }

    override fun insert(entity: MetaCnsServiceException) {
        TODO("Not yet implemented")
    }
}