package se.strindberg.jooqsimple

import org.jooq.DSLContext
import org.jooq.impl.DSL.row
import org.jooq.impl.DSL.value
import org.jooq.kotlin.fetchValue
import se.biobanksverige.sbr.db.Tables.ISSUE

class WrapperRepository(val jooq: DSLContext) {

    fun getWrappers(): Wrapper? = jooq.select(
        row((value(5))).mapping(::Wrapper)
    ).from(
        ISSUE
            .join(ISSUE.issueComplete())
            .join(ISSUE.issueConsent())
    ).fetchValue()
}
