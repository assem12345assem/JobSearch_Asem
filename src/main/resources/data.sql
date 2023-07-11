drop table if exists users;
create table users (
    id  int auto_increment primary key,
    firstName varchar(50),
    lastName varchar(50),
    email varchar(50),
    phoneNumber varchar(50),
    userType enum('JOBSEEKER', 'EMPLOYER'),
    password varchar(50)
);

insert into users (firstName, lastName, email, phoneNumber, userType, password) values
('John', 'Doe', 'john@qwe.qwe', '23-05-25','JOBSEEKER', '123'),
('Kyle', 'Sue', 'kyle@qwe.qwe', '25-55-41', 'EMPLOYER', '123');

drop table if exists resumes;
create table resumes(
    id  int auto_increment primary key,
    userId  int,
    position varchar(50),
    expectedSalary int,
    category enum('FULL_TIME', 'PART_TIME')
);
alter table resumes
    add foreign key (userId) references users(id);
insert into resumes (userId, position, expectedSalary, category)
values (1, 'cook', 1000, 'FULL_TIME'), (1, 'confectioner', 1500, 'FULL_TIME');

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
   resumeId int,
   yearStart int,
   yearEnd int,
   companyName varchar(50),
   position varchar(50),
   responsibilities varchar(200)
);
alter table workExperience
    add foreign key (resumeId) references resumes(id);
insert into workExperience(resumeId, yearStart, yearEnd, companyName, position, responsibilities) values
    (1, 2020, 2023, 'Luctus sagittis', 'orci nulla', 'sodales. est, integer ultrices. nec tellus, a commodo. ultricies aliquam'),
    (1, 2018, 2020, 'Neque nec', 'ultricies enim. fermentum', 'placerat nec. ipsum, tellus.'),
    (2, 2022, 2023, 'Luctus orci', 'nulla nulla', 'sodales. commodo. ultricies aliquam ipsum'),
    (2, 2015, 2022, 'enim nec', 'ultricies fermentum', 'ultricies enim placerat nec. ipsum, est.');


drop table if exists education;
create table education(
  id int auto_increment primary key ,
  resumeId int,
  education varchar(50),
  schoolName varchar(100),
  graduationYear int
);
alter table education
    add foreign key (resumeId) references resumes(id);
insert into education(resumeId, education, schoolName, graduationYear) values
 ( 1, 'Cubilia', 'velit. odio, mollis.', 2021),
 (2, 'Tellus,', 'iaculis. tortor, at.', 2018),
 (2, 'Luctus,', 'iaculis. tortor, at.', 2012);




drop table if exists vacancies;
create table vacancies(
    id int auto_increment primary key,
    userId  int,
    vacancyName varchar(50),
    salary int,
    description varchar(200),
    requiredJobExperience varchar (200),
    category enum('FULL_TIME', 'PART_TIME'),
    dateTimeVacancyPublished timestamp
);
alter table vacancies
    add foreign key (userId) references users(id);
insert into vacancies(userId, vacancyName, salary, description, requiredJobExperience, category, dateTimeVacancyPublished)
values ( 2, 'manager', 500, 'Qui quis soluta et natusex eius quod qui Quis velit.',
        'minus voluptas ab adipisci voluptatibus qui numquam quia ea nihil vitae.',
        'PART_TIME', '2023-07-01 23:59:59'),
    (2, 'cook', 1000, 'ut error iure eos culpa laborum qui enim sint nam voluptas',
     'recusandae et veritatis vero ut labore mollitia',
     'FULL_TIME', '2023-07-02 23:59:59');


drop table if exists applicants;
create table applicants(
    id int auto_increment primary key ,
    applicantUserId int,
    resumeId int,
    vacancyId int,
    dateTimeApplication timestamp
);
alter table applicants
add foreign key (applicantUserId) references users(id);
alter table applicants
add foreign key (resumeId) references resumes(id);
alter table applicants
add foreign key (vacancyId) references vacancies(id);
insert into applicants(applicantUserId, resumeId, vacancyId, dateTimeApplication) values
( 1, 1, 2, '2023-07-11 23:59:59'),
( 1, 2, 1, '2023-07-11 21:59:59');

