package se.strindberg.jooqsimple

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ReactivePersonRepositoryTest : AbstractIntegrationTest() {

    @BeforeEach
    fun reset() {
        runBlocking {
            val personRepo = ReactivePersonRepository(r2dbcContext)
            personRepo.deleteAll()
        }
    }

    @Test
    fun insertAndSearch() {
        runBlocking {
            val personRepo = ReactivePersonRepository(r2dbcContext)

            personRepo.insertPersonWithMap(PersonIn("First", "Person", listOf(Address("Line1", "Line2"))))
            personRepo.insertPersonWithMap(PersonIn("Second", "Person", listOf(Address("Line3", "Line4"))))
            personRepo.insertPersonWithMap(PersonIn("Third", "Person", listOf(Address("Line4", "Line5"), Address("Line6", "Line7"))))
            personRepo.insertPersonWithMap(PersonIn("Fourth", "Person", listOf()))

            val retrievedPersons = personRepo.search("First", "Person")

            val retrievedAddresses = personRepo.getAddresses()
        }
    }
}
