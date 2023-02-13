package jooqsimple.model;

import org.jooq.*;
import org.jooq.conf.*;
import org.jooq.impl.*;
import org.junit.jupiter.api.*;

import java.sql.*;

import static jooqsimple.DbConf.*;

abstract class AbstractIntegrationTest {

    private java.sql.Connection connection = null;

    DSLContext dslContext = null;

    @BeforeAll
    public void setup() throws SQLException {
        connection = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
        dslContext = DSL.using(jooqConfiguration(connection));
    }

    private DefaultConfiguration jooqConfiguration(Connection connection) {
        var config = new DefaultConfiguration();
        config.set(SQLDialect.POSTGRES);
        config.set(connection);
        config.set(new PrettyPrinter());
        config.set(new Settings().withRenderFormatted(true).withRenderSchema(false));
        return config;
    }

    @AfterAll
    public void after() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

}
