package se.strindberg.jooqsimple

import org.junit.jupiter.api.Test

class WrapperRepositoryTest : AbstractIntegrationTest() {

    @Test
    fun getCases() {
        val repo = WrapperRepository(jdbcContext)

        repo.getWrappers()
    }
}
