drop table if exists open_dart_code_dto;
create table open_dart_code_dto(
    stock_code varchar(32),
    corp_name varchar(32),
    corp_code varchar(32),
    primary key (stock_code)
);