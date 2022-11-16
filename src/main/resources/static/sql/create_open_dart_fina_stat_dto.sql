drop table if exists open_dart_fina_stat_dto;
create table open_dart_fina_stat_dto(
    srtn_cd varchar(32),
    corp_code varchar(32),
    corp_name varchar(32),
    first_capital long,
    second_capital long,
    third_capital long,
    first_profit long,
    second_profit long,
    third_profit long,
    primary key (srtn_cd)
);