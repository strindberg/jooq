package se.strindberg.jooqsimple

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PersonRepositoryTest : AbstractIntegrationTest() {

    @BeforeEach
    fun reset() {
        val personRepo = PersonRepository(jdbcContext)
        personRepo.deleteAll()
    }

    @Test
    fun insertAndSearch() {
        val personRepo = PersonRepository(jdbcContext)

        personRepo.insertPerson(Person(1L, "First", "Person", listOf(Address("Line1", "Line2"))))
        personRepo.insertPerson(Person(2L, "Second", "Person", listOf(Address("Line3", "Line4"))))
        personRepo.insertPerson(Person(3L, "Third", "Person", listOf(Address("Line4", "Line5"), Address("Line6", "Line7"))))
        personRepo.insertPerson(Person(4L, "Fourth", "Person", listOf()))

        val retrievedPersons = personRepo.search("First", "Person")

        val retrievedAddresses = personRepo.getAddresses()
    }
}
