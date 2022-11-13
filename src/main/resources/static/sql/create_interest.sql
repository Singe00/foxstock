create table interest(
    id int auto_increment,
    user_id int,
    srtn_cd varchar(32),
    primary key (id),
    foreign key (user_id) references user(id),
    foreign key (srtn_cd) references stock(srtn_cd)
);