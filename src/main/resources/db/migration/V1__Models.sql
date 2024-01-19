create type issue_type as enum ('CONSENT', 'COMPLETE');

create table issue
(
    id              bigint generated by default as identity primary key,
    type            issue_type not null,
    unique (id, type)
);

create table issue_consent
(
    id   bigint     not null primary key,
    type issue_type not null
        constraint type_constraint check (type = 'CONSENT'::issue_type),
    constraint issue foreign key (id, type) references issue (id, type)
);

create table issue_complete
(
    id   bigint     not null primary key,
    type issue_type not null
        constraint type_constraint check (type = 'COMPLETE'::issue_type),
    constraint issue foreign key (id, type) references issue (id, type)
);
