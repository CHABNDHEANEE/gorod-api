create table IF NOT EXISTS SERVICES
(
    ID          INTEGER identity
        constraint SERVICES_PK
            primary key,
    NAME        VARCHAR(255) not null
);
create table SERVICES_CHILDREN
(
    PARENT_ID INTEGER not null
        constraint "services_children_SERVICES_ID_fk"
            references SERVICES ON DELETE CASCADE,
    CHILD_ID  INTEGER not null
        constraint "services_children_SERVICES_ID_fk2"
            references SERVICES ON DELETE CASCADE
);
create table IF NOT EXISTS SUBSCRIBERS
(
    ID         INTEGER identity
        constraint SUBSCRIBERS_PK
            primary key,
    FIO        VARCHAR(255) not null,
    ACCOUNT    VARCHAR(255) not null,
    SERVICE_ID INTEGER      not null
        constraint "subscribers_SERVICES_ID_fk"
            references SERVICES
);