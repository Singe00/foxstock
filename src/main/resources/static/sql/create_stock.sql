create table stock(
    srtn_cd varchar(32),
    crno varchar(32),
    name varchar(32),
    expected_return decimal(4,2),
    purchase_price int,
    current_price int,
    lstg_st_cnt long,
    primary key (srtn_cd)
);
