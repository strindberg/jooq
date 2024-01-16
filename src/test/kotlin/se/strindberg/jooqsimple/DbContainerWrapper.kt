package se.strindberg.jooqsimple

import org.testcontainers.containers.PostgreSQLContainer

private const val CONTAINER_VERSION = "postgres:15-alpine"

class DbContainerWrapper {

    companion object {
        private val container = PostgreSQLContainer<Nothing>(CONTAINER_VERSION).apply { start() }

        fun jdbcUrl() = "jdbc:postgresql://${container.host}:${container.firstMappedPort}/${container.databaseName}" +
            "?user=${container.username}&password=${container.password}"

        fun r2dbcUrl() = "r2dbc:postgresql://${container.username}:${container.password}" +
            "@${container.host}:${container.firstMappedPort}/${container.databaseName}"
    }
}
