/*
 * This file is generated by jOOQ.
 */
package se.biobanksverige.sbr.db.tables;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Check;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import se.biobanksverige.sbr.db.Keys;
import se.biobanksverige.sbr.db.Public;
import se.biobanksverige.sbr.db.enums.IssueType;
import se.biobanksverige.sbr.db.tables.Issue.IssuePath;
import se.biobanksverige.sbr.db.tables.records.IssueCompleteRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.19.2",
        "schema version:none"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IssueComplete extends TableImpl<IssueCompleteRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.issue_complete</code>
     */
    public static final IssueComplete ISSUE_COMPLETE = new IssueComplete();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<IssueCompleteRecord> getRecordType() {
        return IssueCompleteRecord.class;
    }

    /**
     * The column <code>public.issue_complete.id</code>.
     */
    public final TableField<IssueCompleteRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.issue_complete.type</code>.
     */
    public final TableField<IssueCompleteRecord, IssueType> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR.nullable(false).asEnumDataType(IssueType.class), this, "");

    private IssueComplete(Name alias, Table<IssueCompleteRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private IssueComplete(Name alias, Table<IssueCompleteRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.issue_complete</code> table reference
     */
    public IssueComplete(String alias) {
        this(DSL.name(alias), ISSUE_COMPLETE);
    }

    /**
     * Create an aliased <code>public.issue_complete</code> table reference
     */
    public IssueComplete(Name alias) {
        this(alias, ISSUE_COMPLETE);
    }

    /**
     * Create a <code>public.issue_complete</code> table reference
     */
    public IssueComplete() {
        this(DSL.name("issue_complete"), null);
    }

    public <O extends Record> IssueComplete(Table<O> path, ForeignKey<O, IssueCompleteRecord> childPath, InverseForeignKey<O, IssueCompleteRecord> parentPath) {
        super(path, childPath, parentPath, ISSUE_COMPLETE);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class IssueCompletePath extends IssueComplete implements Path<IssueCompleteRecord> {
        public <O extends Record> IssueCompletePath(Table<O> path, ForeignKey<O, IssueCompleteRecord> childPath, InverseForeignKey<O, IssueCompleteRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private IssueCompletePath(Name alias, Table<IssueCompleteRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public IssueCompletePath as(String alias) {
            return new IssueCompletePath(DSL.name(alias), this);
        }

        @Override
        public IssueCompletePath as(Name alias) {
            return new IssueCompletePath(alias, this);
        }

        @Override
        public IssueCompletePath as(Table<?> alias) {
            return new IssueCompletePath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<IssueCompleteRecord> getPrimaryKey() {
        return Keys.ISSUE_COMPLETE_PKEY;
    }

    @Override
    public List<ForeignKey<IssueCompleteRecord, ?>> getReferences() {
        return Arrays.asList(Keys.ISSUE_COMPLETE__ISSUE);
    }

    private transient IssuePath _issue;

    /**
     * Get the implicit join path to the <code>public.issue</code> table.
     */
    public IssuePath issue() {
        if (_issue == null)
            _issue = new IssuePath(this, Keys.ISSUE_COMPLETE__ISSUE, null);

        return _issue;
    }

    @Override
    public List<Check<IssueCompleteRecord>> getChecks() {
        return Arrays.asList(
            Internal.createCheck(this, DSL.name("type_constraint"), "((type = 'COMPLETE'::issue_type))", true)
        );
    }

    @Override
    public IssueComplete as(String alias) {
        return new IssueComplete(DSL.name(alias), this);
    }

    @Override
    public IssueComplete as(Name alias) {
        return new IssueComplete(alias, this);
    }

    @Override
    public IssueComplete as(Table<?> alias) {
        return new IssueComplete(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public IssueComplete rename(String name) {
        return new IssueComplete(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public IssueComplete rename(Name name) {
        return new IssueComplete(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public IssueComplete rename(Table<?> name) {
        return new IssueComplete(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public IssueComplete where(Condition condition) {
        return new IssueComplete(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public IssueComplete where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public IssueComplete where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public IssueComplete where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public IssueComplete where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public IssueComplete where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public IssueComplete where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public IssueComplete where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public IssueComplete whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public IssueComplete whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
