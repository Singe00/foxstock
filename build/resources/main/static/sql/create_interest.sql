create table interest(
    id int auto_increment,
    user_id int,
    stock_id int,
    primary key (id),
    foreign key (user_id) references user(id),
    foreign key (stock_id) references stock(id),

);