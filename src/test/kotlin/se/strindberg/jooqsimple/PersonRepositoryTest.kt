package se.strindberg.jooqsimple

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
        personRepo.insertPerson(Person(1L, "First", "Person", listOf(Address("Line1", "Line2")), listOf(Pet("Rufus"))))
        personRepo.insertPerson(Person(2L, "Second", "Person", listOf(Address("Line3", "Line4")), listOf(Pet("Precious"))))
        personRepo.insertPerson(Person(3L, "Third", "Person", listOf(Address("Line4", "Line5"), Address("Line6", "Line7")), listOf(Pet("Birdie"), Pet("Husky"))))
        personRepo.insertPerson(Person(4L, "Fourth", "Person", listOf(), listOf(Pet("Pålle"))))

        // When
        println("Homeless ==========================================================================================================================================")
        val homeless = personRepo.getHomeless()

        println("Search ============================================================================================================================================")
        val retrievedPersons = personRepo.search("First", "Person")

        println("Children ==========================================================================================================================================")
        val retrievedChildren = personRepo.getChildren()

        // Then
    }
}
