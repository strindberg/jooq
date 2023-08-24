package se.strindberg.jooqsimple

import org.flywaydb.core.Flyway
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.postgresql.ds.PGSimpleDataSource
import java.sql.Connection
import java.sql.DriverManager

abstract class AbstractIntegrationTest {

    lateinit var dslContext: DSLContext

    lateinit var connection: Connection

    @BeforeAll
    fun setup() {
        runFlyway()
        connection = DriverManager.getConnection(DbContainerWrapper.jdbcUrl())
        dslContext = DSL.using(jooqConfiguration(connection))
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

    private fun jooqConfiguration(connectionProvider: Connection?): DefaultConfiguration =
        DefaultConfiguration().apply {
            set(SQLDialect.POSTGRES)
            set(connectionProvider)
            set(PrettyPrinter())
            set(Settings().withRenderFormatted(true).withRenderSchema(false))
        }

    @AfterAll
    fun after() {
        connection.close()
    }
}
