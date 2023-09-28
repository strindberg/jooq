package se.strindberg.jooqsimple

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitLast
import org.jooq.DSLContext
import org.jooq.Record1
import org.jooq.ResultQuery
import org.jooq.impl.DSL.row
import se.strindberg.jooqsimple.db.Tables.ADDRESS
import se.strindberg.jooqsimple.db.Tables.PERSON

class ReactivePersonRepository(val jooq: DSLContext) {

    suspend fun insertPersonWithMap(person: PersonIn) {
        val id = jooq.insertInto(PERSON)
            .columns(PERSON.FIRST_NAME, PERSON.LAST_NAME)
            .values(person.firstName, person.lastName).returningResult(PERSON.ID).awaitValue()
        person.addresses.forEach { address ->
            jooq.insertInto(ADDRESS)
                .columns(ADDRESS.LINE1, ADDRESS.LINE2, ADDRESS.PERSON_ID)
                .values(address.line1, address.line2, id)
                .awaitFirst()
        }
    }

    suspend fun insertPersonWithValues(person: PersonIn) {
        val id = jooq.insertInto(PERSON)
            .columns(PERSON.FIRST_NAME, PERSON.LAST_NAME)
            .values(person.firstName, person.lastName).returningResult(PERSON.ID).awaitValue()
        jooq.insertInto(ADDRESS)
            .columns(ADDRESS.LINE1, ADDRESS.LINE2, ADDRESS.PERSON_ID)
            .valuesOfRows(person.addresses.map { address -> row(address.line1, address.line2, id) })
            .awaitLast()
    }

    suspend fun getAddresses(): List<StandaloneAddress> =
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
            .awaitList()

    suspend fun deleteAll() {
        jooq.delete(ADDRESS).awaitFirst()
        jooq.delete(PERSON).awaitFirst()
    }
}

suspend fun <E, R : Record1<E>> ResultQuery<R>.awaitList(): List<E> = asFlow().map { it.value1() }.toList()

suspend fun <E, R : Record1<E>> ResultQuery<R>.awaitValue(): E = awaitFirst().value1()
