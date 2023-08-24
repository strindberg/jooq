package se.strindberg.jooqsimple

import org.jooq.ExecuteContext
import org.jooq.ExecuteListener
import org.jooq.conf.Settings
import org.jooq.impl.DSL

class PrettyPrinter : ExecuteListener {

    override fun executeStart(ctx: ExecuteContext) {
        val create = DSL.using(ctx.dialect(), Settings().withRenderFormatted(true))

        if (ctx.query() != null) {
            println(create.renderInlined(ctx.query()))
        } else if (ctx.routine() != null) {
            println(create.renderInlined(ctx.routine()))
        } else {
            ctx.batchQueries().forEach {
                println(create.renderInlined(it))
            }
        }
        println()
    }
}
