create table users (
                       user_id int auto_increment primary key,
                       username varchar(20) not null,
                       password_ varchar(20) not null,
                       photoUrl varchar(200) not null
);
create table messages(
                         mg_id int auto_increment primary key,
                         from_id int not null,
                         to_id int not null,
                         message varchar(200),
                         constraint from_user foreign key (from_id) references users(user_id),
                         constraint to_user foreign key (to_id) references users(user_id)
);
create table liked(
                      lk_id int auto_increment primary key,
                      user_id int not null,
                      pf_id int not null,
                      constraint liked_user foreign key (user_id) references users(user_id),
                      constraint liked_pf foreign key (pf_id) references users(user_id)
);