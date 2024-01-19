/*
 * This file is generated by jOOQ.
 */
package se.biobanksverige.sbr.db.tables;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
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
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import se.biobanksverige.sbr.db.Keys;
import se.biobanksverige.sbr.db.Public;
import se.biobanksverige.sbr.db.enums.IssueType;
import se.biobanksverige.sbr.db.tables.IssueComplete.IssueCompletePath;
import se.biobanksverige.sbr.db.tables.IssueConsent.IssueConsentPath;
import se.biobanksverige.sbr.db.tables.records.IssueRecord;


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
public class Issue extends TableImpl<IssueRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.issue</code>
     */
    public static final Issue ISSUE = new Issue();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<IssueRecord> getRecordType() {
        return IssueRecord.class;
    }

    /**
     * The column <code>public.issue.id</code>.
     */
    public final TableField<IssueRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.issue.type</code>.
     */
    public final TableField<IssueRecord, IssueType> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR.nullable(false).asEnumDataType(IssueType.class), this, "");

    private Issue(Name alias, Table<IssueRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Issue(Name alias, Table<IssueRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.issue</code> table reference
     */
    public Issue(String alias) {
        this(DSL.name(alias), ISSUE);
    }

    /**
     * Create an aliased <code>public.issue</code> table reference
     */
    public Issue(Name alias) {
        this(alias, ISSUE);
    }

    /**
     * Create a <code>public.issue</code> table reference
     */
    public Issue() {
        this(DSL.name("issue"), null);
    }

    public <O extends Record> Issue(Table<O> path, ForeignKey<O, IssueRecord> childPath, InverseForeignKey<O, IssueRecord> parentPath) {
        super(path, childPath, parentPath, ISSUE);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class IssuePath extends Issue implements Path<IssueRecord> {
        public <O extends Record> IssuePath(Table<O> path, ForeignKey<O, IssueRecord> childPath, InverseForeignKey<O, IssueRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private IssuePath(Name alias, Table<IssueRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public IssuePath as(String alias) {
            return new IssuePath(DSL.name(alias), this);
        }

        @Override
        public IssuePath as(Name alias) {
            return new IssuePath(alias, this);
        }

        @Override
        public IssuePath as(Table<?> alias) {
            return new IssuePath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<IssueRecord, Long> getIdentity() {
        return (Identity<IssueRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<IssueRecord> getPrimaryKey() {
        return Keys.ISSUE_PKEY;
    }

    @Override
    public List<UniqueKey<IssueRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.ISSUE_ID_TYPE_KEY);
    }

    private transient IssueCompletePath _issueComplete;

    /**
     * Get the implicit to-many join path to the
     * <code>public.issue_complete</code> table
     */
    public IssueCompletePath issueComplete() {
        if (_issueComplete == null)
            _issueComplete = new IssueCompletePath(this, null, Keys.ISSUE_COMPLETE__ISSUE.getInverseKey());

        return _issueComplete;
    }

    private transient IssueConsentPath _issueConsent;

    /**
     * Get the implicit to-many join path to the
     * <code>public.issue_consent</code> table
     */
    public IssueConsentPath issueConsent() {
        if (_issueConsent == null)
            _issueConsent = new IssueConsentPath(this, null, Keys.ISSUE_CONSENT__ISSUE.getInverseKey());

        return _issueConsent;
    }

    @Override
    public Issue as(String alias) {
        return new Issue(DSL.name(alias), this);
    }

    @Override
    public Issue as(Name alias) {
        return new Issue(alias, this);
    }

    @Override
    public Issue as(Table<?> alias) {
        return new Issue(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Issue rename(String name) {
        return new Issue(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Issue rename(Name name) {
        return new Issue(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Issue rename(Table<?> name) {
        return new Issue(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Issue where(Condition condition) {
        return new Issue(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Issue where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Issue where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Issue where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Issue where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Issue where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Issue where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Issue where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Issue whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Issue whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
