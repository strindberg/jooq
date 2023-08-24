package se.strindberg.jooqsimple

import org.jooq.DSLContext
import org.jooq.impl.DSL.insertInto
import org.jooq.impl.DSL.multiset
import org.jooq.impl.DSL.noCondition
import org.jooq.impl.DSL.row
import org.jooq.impl.DSL.select
import org.jooq.kotlin.fetchList
import org.jooq.kotlin.mapping
import se.strindberg.jooqsimple.db.Tables.ADDRESS
import se.strindberg.jooqsimple.db.Tables.PERSON

class PersonRepository(val jooq: DSLContext) {

    fun insertPerson(person: Person) {
        jooq.insertInto(PERSON).values(person.id, person.firstName, person.lastName).execute()
        jooq.batch(
            person.addresses.map { address ->
                insertInto(ADDRESS).values(address.line1, address.line2, person.id)
            },
        ).execute()
    }

    fun search(firstName: String?, lastName: String?): List<Person> {
        return jooq.select(
            row(
                PERSON.ID,
                PERSON.FIRST_NAME,
                PERSON.LAST_NAME,
                multiset(
                    select(ADDRESS.LINE1, ADDRESS.LINE2)
                        .from(ADDRESS)
                        .where(ADDRESS.PERSON_ID.eq(PERSON.ID)),
                ).mapping(::Address),
            ).mapping(::Person),
        )
            .from(PERSON)
            .where(
                if (firstName != null) {
                    PERSON.FIRST_NAME.eq(firstName)
                } else noCondition()
                    .and(if (lastName != null) PERSON.LAST_NAME.eq(lastName) else noCondition()),
            )
            .fetchList()
    }

    fun getAddresses(): List<StandaloneAddress> =
        jooq.select(
            row(
                ADDRESS.LINE1,
                ADDRESS.LINE2,
                row(
                    ADDRESS.person().ID,
                    ADDRESS.person().FIRST_NAME,
                    ADDRESS.person().LAST_NAME,
                ).mapping { id, firstName, lastName -> Person(id, firstName, lastName, emptyList()) },
            ).mapping(::StandaloneAddress),
        ).from(ADDRESS)
            .fetchList()

    fun deleteAll() {
        jooq.deleteFrom(ADDRESS).execute()
        jooq.deleteFrom(PERSON).execute()
    }
}
