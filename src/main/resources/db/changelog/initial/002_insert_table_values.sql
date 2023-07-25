insert into USERS (ID, PHONE_NUMBER, USER_NAME, USER_TYPE, PASSWORD, ENABLED)
values ('timcook@com.com','968-859-5215','Tim Cook', 'applicant',
        '$2a$12$EFiHl2LjTXGjgsD7Mh5j1eVfmCq4ZFevhggr5rHzP51IWX1YpRSwm', true),
       ('leejy@com.com','578-525-5455', 'Lee JY', 'applicant',
        '$2a$12$EFiHl2LjTXGjgsD7Mh5j1eVfmCq4ZFevhggr5rHzP51IWX1YpRSwm', true),
       ('samsung@com.com','548-556-8455','Samsung', 'employer',
        '$2a$12$EFiHl2LjTXGjgsD7Mh5j1eVfmCq4ZFevhggr5rHzP51IWX1YpRSwm', true),
       ('apple@com.com','758-554-5662', 'Apple', 'employer',
        '$2a$12$EFiHl2LjTXGjgsD7Mh5j1eVfmCq4ZFevhggr5rHzP51IWX1YpRSwm', true),
       ('test@com.com','454-52-15','Test','employer',
        '$2a$12$EFiHl2LjTXGjgsD7Mh5j1eVfmCq4ZFevhggr5rHzP51IWX1YpRSwm', true);
// password is 'qwe'
insert into EMPLOYERS (USERID, COMPANYNAME)
values ((select ID from USERS where ID like 'samsung%'), 'Samsung'),
       ((select ID from USERS where ID like 'apple%'), 'Apple'),
       ((select ID from USERS where ID like 'test%'),'TestCompany');

insert into APPLICANTS (USERID, FIRSTNAME, LASTNAME, DATEOFBIRTH)
VALUES ( (select ID from USERS where ID like 'tim%'), 'Tim','Cook','1960-11-01'),
       ( (select ID from USERS where ID like 'lee%'), 'Jae-yong','Lee','1968-06-23');

insert into CATEGORIES (CATEGORY)
values ('accounting'),
       ('sales'),
       ('construction'),
       ('management'),
       ('agriculture'),
       ('web development'),
       ('service'),
       ('healthcare'),
       ('education'),
       ('government'),
       ('design'),
       ('media');

insert into VACANCIES (EMPLOYERID, VACANCYNAME, CATEGORY, SALARY, DESCRIPTION,
                       REQUIREDEXPERIENCEMIN, REQUIREDEXPERIENCEMAX, ISACTIVE, ISPUBLISHED, PUBLISHEDDATETIME)
values ( (select ID from EMPLOYERS where COMPANYNAME like 'Samsung'), 'Samsung CEO',
         'management', 1000,
         'Sed ut perspiciatis unde omnis iste natus', 10, 15, false, true, '2023-03-31 18:00:00' ),
       ((select ID from EMPLOYERS where COMPANYNAME like 'Apple'), 'Apple CEO',
        'management', 1000,
        'ut perspiciatis unde omnis iste', 10, 15, false, true, '2023-03-31 18:00:00' ),
       ((select ID from EMPLOYERS where COMPANYNAME like 'Test%'), 'Test accountant',
        'accounting', 1000,
        'At vero eos et accusamus et iusto', 1, 2, false, true, '2023-03-31 18:00:00');

insert into RESUMES (APPLICANTID, RESUMETITLE, CATEGORY, EXPECTEDSALARY, ISACTIVE, ISPUBLISHED)
values ((select ID from APPLICANTS where LASTNAME like 'Cook'), 'Apple CEO',
        'management', 1000, false, false),
       ((select ID from APPLICANTS where LASTNAME like 'Lee'), 'Samsung CEO',
        'management', 1000, false, false);

insert into EDUCATION (RESUMEID, EDUCATION, SCHOOLNAME, STARTDATE, GRADUATIONDATE)
values ((select id from RESUMES where RESUMETITLE like 'Apple%'),
        'Bachelor of Science in industrial engineering','Auburn University ','1978-09-01','1982-06-01'),
       ((select id from RESUMES where RESUMETITLE like 'Apple%'),
        'Master of Business Administration','Duke University','1980-09-01','1988-06-01'),
       ((select id from RESUMES where RESUMETITLE like 'Samsung%'),
        'Bachelor of Arts in East Asian history','Seoul National University','1985-01-01','1989-01-01'),
       ((select id from RESUMES where RESUMETITLE like 'Samsung%'),
        'MBA','Keio University','1989-01-01','1991-01-01');

insert into WORKEXPERIENCE (RESUMEID, DATESTART, DATEEND, COMPANYNAME, POSITION, RESPONSIBILITIES)
VALUES ((select id from RESUMES where RESUMETITLE like 'Apple%'),
        '2011-01-01','2023-01-01','Apple','CEO','leadership, innovation, management'),
       ((select id from RESUMES where RESUMETITLE like 'Apple%'),
        '2007-01-01','2011-01-01','Apple','Lead Operations','major decisions'),
       ((select id from RESUMES where RESUMETITLE like 'Apple%'),
        '1997-01-01','2007-01-01','IBM','CEO & other positions','leadership, innovation, management'),
       ((select id from RESUMES where RESUMETITLE like 'Samsung%'),
        '2022-10-01','2023-01-01','Samsung','CEO','leadership, innovation, management'),
       ((select id from RESUMES where RESUMETITLE like 'Samsung%'),
        '1991-01-01','2022-10-01','Samsung','VP & other positions','major decisions');

insert into CONTACTINFO (RESUMEID, TELEGRAM, EMAIL, PHONENUMBER, FACEBOOK, LINKEDIN)
VALUES ((select id from RESUMES where RESUMETITLE like 'Apple%'),
        '@timcook', 'timcook@com.com', '968-859-5215', 'facebook.com/timc', 'www.linkedin.com/in/timc'),
       ((select id from RESUMES where RESUMETITLE like 'Samsung%'),
        '@leeyu', 'leejy@com.com','578-525-5455', 'facebook.com/lee', 'www.linkedin.com/in/lee');

insert into JOBAPPLICATIONS (VACANCYID, RESUMEID, DATETIME)
VALUES ((select id from VACANCIES where VACANCYNAME like 'Apple%'),
        (select id from RESUMES where RESUMETITLE like 'Apple%'),
        '2023-07-01 10:00:00'),
       ((select id from VACANCIES where VACANCYNAME like 'Samsung%'),
        (select id from RESUMES where RESUMETITLE like 'Samsung%'),
        '2023-07-02 10:00:00');

insert into roles(role, user_email)
values ('EMPLOYER', 'test@com.com'),
       ('EMPLOYER', 'apple@com.com'),
       ('EMPLOYER', 'samsung@com.com'),
       ('APPLICANT','timcook@com.com'),
       ('APPLICANT', 'leejy@com.com');

insert into authorities (authority, role)
values('ADD_VACANCY', 'EMPLOYER'),
      ('EDIT_VACANCY', 'EMPLOYER'),
      ('DELETE_VACANCY', 'EMPLOYER'),
      ('ADD_RESUME', 'APPLICANT'),
      ('EDIT_RESUME', 'APPLICANT'),
      ('DELETE_RESUME', 'APPLICANT'),
      ('JOB_APPLY', 'APPLICANT');



