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
