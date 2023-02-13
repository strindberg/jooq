package jooqsimple.model;

import jooqsimple.model.*;
import org.jooq.*;

import java.util.*;
import java.util.stream.*;

import static jooqsimple.db.Tables.*;
import static org.jooq.Records.*;
import static org.jooq.impl.DSL.*;


public class PersonRepository {

    private final DSLContext jooq;

    public PersonRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public void insertPerson(Person person) {
        jooq.insertInto(PERSON).values(person.id(), person.firstName(), person.lastName()).execute();
        jooq.batch(
                person.addresses().stream().map(address ->
                        insertInto(ADDRESS).values(address.line1(), address.line2(), person.id())
                ).collect(Collectors.toList())
        ).execute();
    }

    public List<Person> search(String firstName, String lastName) {
        return jooq.select(PERSON.ID,
                        PERSON.FIRST_NAME,
                        PERSON.LAST_NAME,
                        multiset(select(ADDRESS.LINE1, ADDRESS.LINE2)
                                .from(ADDRESS)
                                .where(ADDRESS.PERSON_ID.eq(PERSON.ID)))
                                .convertFrom(r -> r.map(mapping(Address::new))))
                .from(PERSON)
                .where(
                        firstName != null ? PERSON.FIRST_NAME.eq(firstName) : noCondition()
                                .and(lastName != null ? PERSON.LAST_NAME.eq(lastName) : noCondition())
                ).fetch(mapping(Person::new));
    }

    public void deleteAll() {
        jooq.deleteFrom(ADDRESS).execute();
        jooq.deleteFrom(PERSON).execute();
    }

}
