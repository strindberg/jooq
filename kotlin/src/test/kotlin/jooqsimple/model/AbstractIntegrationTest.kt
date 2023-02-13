package jooqsimple.model;

import jooqsimple.DBPASSWORD
import jooqsimple.DBURL
import jooqsimple.DBUSERNAME
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import java.sql.Connection
import java.sql.DriverManager

abstract class AbstractIntegrationTest {

    lateinit var dslContext: DSLContext

    lateinit var connection: Connection

    @BeforeAll
    fun setup() {
        connection = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD)
        dslContext = DSL.using(jooqConfiguration(connection))
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
