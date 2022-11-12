create table stock(
    srtn_cd varchar(32),
    crno varchar(32),
    name varchar(32),
    current_price int,
    lstg_st_cnt long,
    capital long,
    bps int,
    pbr decimal(4,2),
    average_roe decimal(4,2),
    expected_return decimal(4,2),
    purchase_price int,
    primary key (srtn_cd)
);


