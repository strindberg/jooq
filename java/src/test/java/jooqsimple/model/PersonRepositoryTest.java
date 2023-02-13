package jooqsimple.model;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryTest extends AbstractIntegrationTest {

    @BeforeEach
    public void reset() {
        new PersonRepository(dslContext).deleteAll();
    }

    @Test
    void insertAndSearch() {
        // Given
        PersonRepository personRepo = new PersonRepository(dslContext);

        personRepo.insertPerson(
                new Person(
                        1L,
                        "First",
                        "Person",
                        List.of(new Address("Line1", "Line2"))));
        personRepo.insertPerson(
                new Person(
                        2L,
                        "Second",
                        "Person",
                        new ArrayList<>()));

        // When
        var retrievedPersons = personRepo.search("First", "Person");

        // Then
        assertEquals(1, retrievedPersons.size());
        assertEquals(1, retrievedPersons.get(0).addresses().size());
    }

}
