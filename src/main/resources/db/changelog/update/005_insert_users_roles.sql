INSERT INTO USERS_ROLES (USER_EMAIL, ROLE_ID)
SELECT
    U.EMAIL,
    R.ID
FROM USER_TABLE U
         JOIN ROLES R ON
        (U.USER_TYPE = 'employer' AND R.ROLE = 'ROLE_EMPLOYER') OR
        (U.USER_TYPE = 'applicant' AND R.ROLE = 'ROLE_APPLICANT')
WHERE U.USER_TYPE IN ('employer', 'applicant');
