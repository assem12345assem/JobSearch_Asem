create table if not exists users (
                                     id          int auto_increment primary key,
                                     firstName   varchar(50),
                                     lastName    varchar(50),
                                     email       varchar(50),
                                     userType    enum('JOBSEEKER', 'EMPLOYER'),
                                     password    varchar(50)
);

insert into users (firstName, lastName, email, userType, password) values
                                                                       ('John', 'Doe', 'john@qwe.qwe', 'JOBSEEKER', '123'),
                                                                       ('Kyle', 'Sue', 'kyle@qwe.qwe', 'EMPLOYER', '123');
