<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container-xl px-4 mt-4">
        <div class="text-center mb-4">
            <a href="/auth/edit/${user.email}" id="edit_profile_button">
                <button class="btn btn-warning">Редактировать профиль</button>
            </a>
        </div>
        <hr class="mt-0 mb-4">
        <div class="row">
            <!-- Profile picture card-->
            <div class="col-xl-4">
                <div class="card mb-4 mb-xl-0">
                    <div class="card-header">Личный кабинет
                        <#if user.userType=="employer">Работодателя<#else>Соискателя</#if>
                    </div>
                    <div class="card-body text-center">
                        <#if user.photo?has_content>
                        <img class="img-account-profile rounded-circle mb-2" src="/auth/images/${user.email}" alt="photo">
                        <#else>
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXPgCd93aiNba8lHWJYHQ1C1YwBPwcH_NUmw&usqp=CAU" alt="profile picture">
                        </#if>
                    </div>
                </div>
            </div>
            <!-- add resume button-->
            <div class="col-xl-3 d-flex align-items-center justify-content-center">
                <div class="card-body text-center">
                    <div>
                        <#if user.userType=="applicant">
                            <div class="text-center">
                                <a href="/resume/add" class="btn btn-warning p-3">
                                    +<br> Создать <br>резюме
                                </a>
                            </div>
                        <#else>
                            <div class="text-center">
                                <a href="/vacancy/add" class="btn btn-warning p-3">
                                    +<br> Создать <br>вакансию
                                </a>
                            </div>
                        </#if>
                    </div>
                    <div>
                        <a href="/apply/messages" class="btn btn-warning p-4">
                            Отклики <span class="badge bg-danger"> ${new_message_number} </span> <br>
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-8">
            <!-- Account details card-->
            <div class="card mb-4">
                <div class="card-header ">
                    <div class="col-8">
                        Информация о пользователе
                    </div>
                </div>
                <!-- Form Group (username)-->
                <div class="card-body">
                    <div>
                        <div class="mb-3">
                            <label class="small mb-1" for="inputUsername">Имя пользователя </label>
                            <p class="form-control" id="inputUsername" type="text">${user.userName}</p>
                        </div>
                        <!-- Form Row        -->
                        <div class="mb-3">
                            <label class="small mb-1" for="inputPhone">Номер телефона</label>
                            <p class="form-control" id="inputPhone" type="text">${user.phoneNumber}</p>
                        </div>
                        <!-- Form Row-->
                        <#if user.userType=="applicant">
                            <#assign variable1 = applicantProfile.firstName!"Имя не указано">
                            <#assign variable2 = applicantProfile.lastName!"Фамилия не указана">
                            <#assign variable3 = applicantProfile.dateOfBirth!"Дата рождения не указана">
                            <div class="row gx-3 mb-3">
                                <div class="col-md-6">
                                    <label class="small mb-1" for="inputFirstName">Имя</label>
                                    <p class="form-control" id="inputFirstName" type="text">${variable1}</p>
                                </div>
                                <div class="col-md-6">
                                    <label class="small mb-1" for="inputLastName">Фамилия</label>
                                    <p class="form-control" id="inputLastName" type="text">${variable2}</p>
                                </div>
                            </div>
                            <div class="row gx-3 mb-3">
                                <div class="col-md-6">
                                    <label class="small mb-1" for="inputBirthday">Дата рождения</label>
                                    <p class="form-control" id="inputBirthday">${variable3}</p>
                                </div>
                            </div>
                        <#else>
                            <div class="mb-3">
                                <#assign str = employerProfile.companyName!"Название компании не указано">
                                <label class="small mb-1" for="orgName">Название компании</label>
                                <p class="form-control" id="orgName" type="text">${str}</p>
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
        <!-- Account resumes/vacancies-->

    </div>
<div class="container">
    <div class="row">
        <#if user.userType=="employer">
            <h3>Вакансии работодателя</h3>
        <#else>
            <h3>Резюме соискателя</h3>
        </#if>
        <#if (myList)??>
            <#list myList as item>
                <#if item?index == 0 || item?index % 3 == 0>
                    <div class="row my-3">
                </#if>
                <div class="card col mx-1">
                    <div class="card-body">
                        <h5 class="card-title"><#if (item.title)??> ${item.title}<#else> Нет названия</#if></h5>
                        <p class="card-text"><#if (item.dateTime)??>${item.dateTime}<#else >Нет даты</#if></p>
                        <div class="d-flex">
                            <#if user.userType=="employer">
                                <a href="/vacancy/edit/${item.id}" class="btn btn-primary me-3">Редактировать</a>
                                <a href="/vacancy/datefix/${item.id}" class="btn btn-success">Обнов.дату</a>
                            <#else>
                                <a href="/resume/edit/${item.id}" class="btn btn-primary me-3">Редактировать</a>
                                <a href="/resume/datefix/${item.id}" class="btn btn-success">Обнов.дату</a>
                            </#if>
                        </div>
                        <div class="text-center my-2">
                            <#if user.userType=="employer">
                                <a href="/vacancy/delete/${item.id}" class="btn btn-danger">Удалить</a>
                            <#else>
                                <a href="/resume/delete/${item.id}" class="btn btn-danger">Удалить</a>
                            </#if>
                        </div>
                    </div>
                </div>
                <#if item?index % 3 == 2 || item?is_last>
                    </div>
                </#if>
            </#list>
        <#else >
            Ещё не созданы
        </#if>
    </div>
</div>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            let editProfileButton = document.getElementById('edit_profile_button');
            if (editProfileButton) {
                editProfileButton.addEventListener('click', async (e) => {
                    e.preventDefault();

                    try {
                        let user = await readUser();
                        if (user) {
                            let newUrl = baseUrl + '/auth/edit/' + user.username;
                            window.location.href = newUrl;
                        }
                    } catch (e) {
                        console.error(e);
                    }
                });
            }
        });

    </script>
    <style>
        body {
            margin-top: 20px;
            background-color: #f2f6fc;
            color: #69707a;
        }

        .img-account-profile {
            height: 10rem;
        }

        .rounded-circle {
            border-radius: 50% !important;
        }

        .card {
            box-shadow: 0 0.15rem 1.75rem 0 rgb(33 40 50 / 15%);
        }

        .card .card-header {
            font-weight: 500;
        }

        .card-header:first-child {
            border-radius: 0.35rem 0.35rem 0 0;
        }

        .card-header {
            padding: 1rem 1.35rem;
            margin-bottom: 0;
            background-color: rgba(33, 40, 50, 0.03);
            border-bottom: 1px solid rgba(33, 40, 50, 0.125);
        }

        .form-control, .dataTable-input {
            display: block;
            width: 100%;
            padding: 0.875rem 1.125rem;
            font-size: 0.875rem;
            font-weight: 400;
            line-height: 1;
            color: #69707a;
            background-color: #fff;
            background-clip: padding-box;
            border: 1px solid #c5ccd6;
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
            border-radius: 0.35rem;
            transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
        }

    </style>
</@main.layout>