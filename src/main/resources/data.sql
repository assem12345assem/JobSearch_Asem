drop table if exists users;
create table users (
    id  int auto_increment primary key,
    firstName varchar(50),
    lastName varchar(50),
    email varchar(50),
    userType enum('JOBSEEKER', 'EMPLOYER'),
    password varchar(50)
);

insert into users (firstName, lastName, email, userType, password) values
('John', 'Doe', 'john@qwe.qwe', 'JOBSEEKER', '123'),
('Kyle', 'Sue', 'kyle@qwe.qwe', 'EMPLOYER', '123');

drop table if exists contactInfo;
create table contactInfo(
    id int auto_increment primary key,
    userId int,
    telegram varchar(50),
    email varchar(50),
    phoneNumber varchar(50),
    facebookAccount varchar(50),
    linkedinAccount varchar(50)
);
alter table contactInfo
    add foreign key (userId) references USERS(id);

insert into contactInfo(userId, telegram, email,
                        phoneNumber, facebookAccount, linkedinAccount)
values (1, '@johndoe11', 'johndoe@qwe.qwe', '235-855-8970',
        'facebook.com/johndoe', 'www.linkedin.com/in/johndoe' );

drop table if exists workExperience;
create table workExperience(
   id int auto_increment primary key ,
   userId int,
   workExperienceItem varchar(200)
);
alter table workExperience
    add foreign key (userId) references users(id);
insert into workExperience(userId, workExperienceItem) values
    (1, '2020-2023 Luctus sagittis orci nulla. sodales. est, integer ultrices. nec tellus, a commodo. ultricies aliquam'),
    (1, '2018-2020 Neque nec ultricies enim. fermentum placerat nec. ipsum, tellus.');

drop table if exists education;
create table education(
  id int auto_increment primary key ,
  userId int,
  educationItem varchar(100)
);
alter table education
    add foreign key (userId) references users(id);
insert into education(userId, educationItem) values
 ( 1, 'Cubilia velit. odio, mollis.'),
 (1, 'Tellus, iaculis. tortor, at.');

drop table if exists jobseekerResumes;
create table jobseekerResumes(
 id  int auto_increment primary key,
 userId  int,
 position varchar(50),
 expectedSalary int
);
alter table jobseekerResumes
add foreign key (userId) references users(id);
insert into jobseekerResumes (userId, position, expectedSalary)
values (1, 'cook', 1000), (1, 'confectioner', 1500);