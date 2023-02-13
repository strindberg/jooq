package jooqsimple.model;

import org.jooq.*;
import org.jooq.conf.*;
import org.jooq.impl.*;

public class PrettyPrinter implements ExecuteListener {

    @Override
    public void executeStart(ExecuteContext ctx) {
        DSLContext create = DSL.using(ctx.dialect(), new Settings().withRenderFormatted(true));

        if (ctx.query() != null) {
            System.out.println(create.renderInlined(ctx.query()));
        } else if (ctx.routine() != null) {
            System.out.println(create.renderInlined(ctx.routine()));
        } else {
            for (Query query : ctx.batchQueries()) {
                System.out.println(create.renderInlined(query));
            }
        }
        System.out.println();
    }

}
