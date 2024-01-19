package se.strindberg.jooqsimple

import org.jooq.DSLContext
import org.jooq.impl.DSL.multiset
import org.jooq.impl.DSL.noCondition
import org.jooq.impl.DSL.row
import org.jooq.impl.DSL.select
import org.jooq.kotlin.fetchList
import org.jooq.kotlin.fetchValue
import org.jooq.kotlin.mapping
import se.strindberg.jooqsimple.db.Tables.ADDRESS
import se.strindberg.jooqsimple.db.Tables.PERSON

class PersonRepository(val jooq: DSLContext) {

    fun insertPerson(person: PersonIn) {
        val id = jooq.insertInto(PERSON)
            .columns(PERSON.FIRST_NAME, PERSON.LAST_NAME)
            .values(person.firstName, person.lastName).returningResult(PERSON.ID).fetchValue()
        person.addresses.forEach { address ->
            jooq.insertInto(ADDRESS)
                .columns(ADDRESS.LINE1, ADDRESS.LINE2, ADDRESS.PERSON_ID)
                .values(address.line1, address.line2, id)
                .execute()
        }
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
                } else {
                    noCondition()
                        .and(if (lastName != null) PERSON.LAST_NAME.eq(lastName) else noCondition())
                },
            )
            .fetchList()
    }

    fun getAddresses(): List<StandaloneAddress> = jooq.select(
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
        jooq.delete(ADDRESS).execute()
        jooq.delete(PERSON).execute()
    }
}

