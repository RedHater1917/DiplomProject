-- begin EQUIPMENTRECORDS_EQUIPMENT
create table EQUIPMENTRECORDS_EQUIPMENT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER bigint not null,
    NAME varchar(255) not null,
    DESCRIPTION varchar(255),
    MADE_BY varchar(255) not null,
    COST integer not null,
    --
    primary key (ID)
)^
-- end EQUIPMENTRECORDS_EQUIPMENT
-- begin EQUIPMENTRECORDS_EFFICIENCY_INDICATORS
create table EQUIPMENTRECORDS_EFFICIENCY_INDICATORS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    EQUIPMENT_ID uuid not null,
    WORKER_ID uuid not null,
    PRODUCED_PRODUCTION integer not null,
    DEFECTIVE_PERCENT integer not null,
    DATE date not null,
    --
    primary key (ID)
)^
-- end EQUIPMENTRECORDS_EFFICIENCY_INDICATORS
-- begin EQUIPMENTRECORDS_WORKER
create table EQUIPMENTRECORDS_WORKER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    FIO varchar(255),
    AGE integer,
    EXPERIENCE integer,
    POSITION_ varchar(255),
    --
    primary key (ID)
)^
-- end EQUIPMENTRECORDS_WORKER
