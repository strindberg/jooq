create table person
(
    id         bigint primary key,
    first_name varchar,
    last_name  varchar
);

create table address
(
    line1     varchar,
    line2     varchar,
    person_id bigint
        references person (id)
);

create table pet
(
    name      varchar,
    person_id bigint
        references person (id)
);
