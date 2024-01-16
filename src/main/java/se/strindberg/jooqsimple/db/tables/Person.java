/*
 * This file is generated by jOOQ.
 */
package se.strindberg.jooqsimple.db.tables;


import java.util.Collection;

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

import se.strindberg.jooqsimple.db.DefaultSchema;
import se.strindberg.jooqsimple.db.Keys;
import se.strindberg.jooqsimple.db.tables.Address.AddressPath;
import se.strindberg.jooqsimple.db.tables.records.PersonRecord;


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
public class Person extends TableImpl<PersonRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>person</code>
     */
    public static final Person PERSON = new Person();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PersonRecord> getRecordType() {
        return PersonRecord.class;
    }

    /**
     * The column <code>person.id</code>.
     */
    public final TableField<PersonRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>person.first_name</code>.
     */
    public final TableField<PersonRecord, String> FIRST_NAME = createField(DSL.name("first_name"), SQLDataType.VARCHAR(1000000000), this, "");

    /**
     * The column <code>person.last_name</code>.
     */
    public final TableField<PersonRecord, String> LAST_NAME = createField(DSL.name("last_name"), SQLDataType.VARCHAR(1000000000), this, "");

    private Person(Name alias, Table<PersonRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Person(Name alias, Table<PersonRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>person</code> table reference
     */
    public Person(String alias) {
        this(DSL.name(alias), PERSON);
    }

    /**
     * Create an aliased <code>person</code> table reference
     */
    public Person(Name alias) {
        this(alias, PERSON);
    }

    /**
     * Create a <code>person</code> table reference
     */
    public Person() {
        this(DSL.name("person"), null);
    }

    public <O extends Record> Person(Table<O> path, ForeignKey<O, PersonRecord> childPath, InverseForeignKey<O, PersonRecord> parentPath) {
        super(path, childPath, parentPath, PERSON);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class PersonPath extends Person implements Path<PersonRecord> {
        public <O extends Record> PersonPath(Table<O> path, ForeignKey<O, PersonRecord> childPath, InverseForeignKey<O, PersonRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private PersonPath(Name alias, Table<PersonRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public PersonPath as(String alias) {
            return new PersonPath(DSL.name(alias), this);
        }

        @Override
        public PersonPath as(Name alias) {
            return new PersonPath(alias, this);
        }

        @Override
        public PersonPath as(Table<?> alias) {
            return new PersonPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<PersonRecord, Long> getIdentity() {
        return (Identity<PersonRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<PersonRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_C;
    }

    private transient AddressPath _address;

    /**
     * Get the implicit to-many join path to the <code>PUBLIC.address</code>
     * table
     */
    public AddressPath address() {
        if (_address == null)
            _address = new AddressPath(this, null, Keys.CONSTRAINT_B.getInverseKey());

        return _address;
    }

    @Override
    public Person as(String alias) {
        return new Person(DSL.name(alias), this);
    }

    @Override
    public Person as(Name alias) {
        return new Person(alias, this);
    }

    @Override
    public Person as(Table<?> alias) {
        return new Person(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Person rename(String name) {
        return new Person(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Person rename(Name name) {
        return new Person(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Person rename(Table<?> name) {
        return new Person(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Person where(Condition condition) {
        return new Person(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Person where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Person where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Person where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Person where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Person where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Person where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Person where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Person whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Person whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
