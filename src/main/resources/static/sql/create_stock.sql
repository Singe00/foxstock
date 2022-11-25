drop table if exists stock;
create table stock(
    srtn_cd varchar(32),
    name varchar(32),
    current_price int,
    bps int,
    pbr double,
    average_roe double,
    expected_return double,
    purchase_price int,
    mrkt_ctg varchar(16),

    primary key (srtn_cd)
);


