package jooqsimple.model;

import jooqsimple.db.Tables.ADDRESS
import jooqsimple.db.Tables.PERSON
import org.jooq.DSLContext
import org.jooq.Records.mapping
import org.jooq.impl.DSL.insertInto
import org.jooq.impl.DSL.multiset
import org.jooq.impl.DSL.noCondition
import org.jooq.impl.DSL.select
import org.jooq.kotlin.mapping

class PersonRepository(val jooq: DSLContext) {

    fun insertPerson(person: Person) {
        jooq.insertInto(PERSON).values(person.id, person.firstName, person.lastName).execute();
        jooq.batch(
            person.addresses.map { address ->
                insertInto(ADDRESS).values(address.line1, address.line2, person.id)
            }
        ).execute();
    }

    fun search(firstName: String?, lastName: String?): List<Person> {
        return jooq.select(
            PERSON.ID,
            PERSON.FIRST_NAME,
            PERSON.LAST_NAME,
            multiset(
                select(ADDRESS.LINE1, ADDRESS.LINE2)
                    .from(ADDRESS)
                    .where(ADDRESS.PERSON_ID.eq(PERSON.ID))
            ).mapping(::Address)
        )
            .from(PERSON)
            .where(
                if (firstName != null) PERSON.FIRST_NAME.eq(firstName) else noCondition()
                    .and(if (lastName != null) PERSON.LAST_NAME.eq(lastName) else noCondition())
            )
            .fetch(mapping(::Person))
    }

    fun deleteAll() {
        jooq.deleteFrom(ADDRESS).execute()
        jooq.deleteFrom(PERSON).execute()
    }

}
