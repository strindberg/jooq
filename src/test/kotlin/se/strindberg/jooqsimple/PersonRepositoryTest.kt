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

        personRepo.insertPerson(PersonIn("First", "Person", listOf(Address("Line1", "Line2"))))
        personRepo.insertPerson(PersonIn("Second", "Person", listOf(Address("Line3", "Line4"))))
        personRepo.insertPerson(PersonIn("Third", "Person", listOf(Address("Line4", "Line5"), Address("Line6", "Line7"))))
        personRepo.insertPerson(PersonIn("Fourth", "Person", listOf()))

        val retrievedPersons = personRepo.search("First", "Person")

        val retrievedAddresses = personRepo.getAddresses()
    }
}
