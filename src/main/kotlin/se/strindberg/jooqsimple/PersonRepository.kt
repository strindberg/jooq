package se.strindberg.jooqsimple

import org.jooq.DSLContext
import org.jooq.Records.mapping
import org.jooq.impl.DSL.insertInto
import org.jooq.impl.DSL.multiset
import org.jooq.impl.DSL.noCondition
import org.jooq.impl.DSL.row
import org.jooq.impl.DSL.select
import org.jooq.kotlin.mapping
import se.strindberg.jooqsimple.Person.Companion.toPerson
import se.strindberg.jooqsimple.db.Tables.ADDRESS
import se.strindberg.jooqsimple.db.Tables.PERSON
import se.strindberg.jooqsimple.db.Tables.PET

class PersonRepository(val jooq: DSLContext) {

    fun insertPerson(person: Person) {
        jooq.insertInto(PERSON).values(person.id, person.firstName, person.lastName).execute()
        jooq.batch(
            person.addresses.map { address ->
                insertInto(ADDRESS).values(address.line1, address.line2, person.id)
            },
        ).execute()
        jooq.batch(
            person.pets.map { pet ->
                insertInto(PET).values(pet.name, person.id)
            },
        ).execute()
    }

    fun search(firstName: String?, lastName: String?): List<Person> {
        return jooq.select(
            PERSON.ID,
            PERSON.FIRST_NAME,
            PERSON.LAST_NAME,
            multiset(
                select(ADDRESS.LINE1, ADDRESS.LINE2)
                    .from(ADDRESS)
                    .where(ADDRESS.PERSON_ID.eq(PERSON.ID)),
            ).mapping(::Address),
            multiset(
                select(PET.NAME)
                    .from(PET)
                    .where(PET.PERSON_ID.eq(PERSON.ID)),
            ).mapping(::Pet),
        )
            .from(PERSON)
            .where(
                if (firstName != null) {
                    PERSON.FIRST_NAME.eq(firstName)
                } else noCondition()
                    .and(if (lastName != null) PERSON.LAST_NAME.eq(lastName) else noCondition()),
            )
            .fetch(mapping(::Person))
    }

    fun getChildren(): List<AddressChild> =
        jooq.select(
            ADDRESS.LINE1,
            ADDRESS.LINE2,
            row(
                ADDRESS.person().ID,
                ADDRESS.person().FIRST_NAME,
                ADDRESS.person().LAST_NAME,
            ).mapping(::toPerson),
        ).from(ADDRESS)
            .fetch(mapping(::AddressChild))

    fun deleteAll() {
        jooq.deleteFrom(ADDRESS).execute()
        jooq.deleteFrom(PET).execute()
        jooq.deleteFrom(PERSON).execute()
    }

    fun getHomeless(): List<Person> =
        jooq.select(PERSON.ID, PERSON.FIRST_NAME, PERSON.LAST_NAME)
            .from(PERSON)
            .leftAntiJoin(ADDRESS).on(ADDRESS.PERSON_ID.eq(PERSON.ID))
            .fetch(mapping(::toPerson))
}
