drop table if exists naver_dto;
create table naver_dto(
    srtn_cd varchar(32),
    first_roe double,
    second_roe double,
    third_roe double,
    bps int,
    primary key (srtn_cd)
);