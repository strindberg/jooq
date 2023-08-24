package se.strindberg.jooqsimple

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import org.flywaydb.core.Flyway
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.junit.jupiter.api.BeforeAll
import org.postgresql.ds.PGSimpleDataSource

abstract class AbstractIntegrationTest {

    lateinit var r2dbcContext: DSLContext

    @BeforeAll
    fun setup() {
        runFlyway()
        r2dbcContext = jooqContext(ConnectionFactories.get(DbContainerWrapper.r2dbcUrl()))
    }

    private fun runFlyway() {
        val flyway =
            Flyway.configure().dataSource(PGSimpleDataSource().apply { setURL(DbContainerWrapper.jdbcUrl()) }).load()
        try {
            flyway.info()
            flyway.migrate()
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }

    private fun jooqContext(connectionFactory: ConnectionFactory): DSLContext =
        DSL.using(connectionFactory, SQLDialect.POSTGRES, Settings().withRenderFormatted(true).withRenderSchema(false))
}
