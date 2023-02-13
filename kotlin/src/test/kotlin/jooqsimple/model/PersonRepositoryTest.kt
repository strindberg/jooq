package jooqsimple.model;

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PersonRepositoryTest : AbstractIntegrationTest() {

    @BeforeEach
    fun reset() {
        val personRepo = PersonRepository(dslContext)
        personRepo.deleteAll()
    }

    @Test
    fun insertAndSearch() {
        val personRepo = PersonRepository(dslContext)
        // Given
        personRepo.insertPerson(
            Person(
                1L,
                "First",
                "Person",
                listOf(Address("Line1", "Line2"))
            )
        )
        personRepo.insertPerson(
            Person(
                2L,
                "Second",
                "Person",
                listOf()
            )
        )

        // When
        val retrievedPersons = personRepo.search("First", "Person");

        // Then
        assertEquals(1, retrievedPersons.size);
        assertEquals(1, retrievedPersons[0].addresses.size);
    }

}
