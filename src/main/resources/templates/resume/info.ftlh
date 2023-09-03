<#import "../layout.ftlh" as main>
<@main.layout>

    <div class="container">
        <div class="main-body">
            <h2 class="text-primary fs-2 text-center"> Резюме пользователя:</h2>
            <#if user.email == viewer.email>
                <div class="row text-center">
                    <div class="col-sm-12">
                        <a class="btn btn-info mt-3 text-dark px-4" target="__blank" href="/resume/edit/${resume.id}">Редактировать</a>
                    </div>
                </div>
            </#if>

            <!-- /Breadcrumb -->

            <div class="row gutters-sm mt-5">
                <div class="col-md-4 mb-3">
                    <div class="card">
                        <div class="card-body">
                            <div class="d-flex flex-column align-items-center text-center">
                                <#if user.photo?has_content>
                                    <img class="img-account-profile rounded-circle mb-2" src="/auth/images/${user.email}" alt="photo" width="250" height="auto">
                                <#else>
                                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXPgCd93aiNba8lHWJYHQ1C1YwBPwcH_NUmw&usqp=CAU" alt="photo">
                                </#if>                        <div class="mt-3">
                                    <h4><#if (resume.profile.firstName)??>${resume.profile.firstName}</#if>  <#if (resume.profile.lastName)??>${resume.profile.lastName}</#if></h4>
                                    <p class="text-secondary mb-1">Дата рождения:</p>
                                    <p class="text-muted font-size-sm"><#if (resume.profile.dateOfBirth)??> ${resume.profile.dateOfBirth}</#if></p>
                                    <#if user.email == viewer.email>
                                        <a class="btn btn-success"
                                           href="/auth/edit/${user.email}">Редактировать профиль</a>

                                    </#if>
                                    <#if viewer.userType == "employer">
                                        <a class="btn btn-success"
                                           href="/apply/offerjob/${resume.id}">Предложить вакансию</a>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card mt-3">
                        <ul class="list-group list-group-flush">
                            <#if resume.contact.telegram??>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                    <h6 class="mb-0"><i class="fa-brands fa-telegram fs-3"></i> Telegram:</h6>
                                    <span class="text-secondary">${resume.contact.telegram}</span>
                                </li>
                            </#if>
                            <#if resume.contact.email??>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                    <h6 class="mb-0"><i class="fa-solid fa-envelope fs-3"></i> Email:</h6>
                                    <span class="text-secondary">${resume.contact.email}</span>
                                </li>
                            </#if>
                            <#if resume.contact.facebook??>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                    <h6 class="mb-0"><i class="fa-brands fa-square-facebook fs-3"></i> Facebook:</h6>
                                    <span class="text-secondary">${resume.contact.facebook}</span>
                                </li>
                            </#if>
                            <#if resume.contact.linkedIn??>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                    <h6 class="mb-0"><i class="fa-brands fa-linkedin-in fs-3"></i> LinkedIn:</h6>
                                    <span class="text-secondary">${resume.contact.linkedIn}</span>
                                </li>
                            </#if>
                            <#if resume.contact.phoneNumber??>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                    <h6 class="mb-0"><i class="fa-solid fa-phone fs-3"></i> Phone Number:</h6>
                                    <span class="text-secondary">${resume.contact.phoneNumber}</span>
                                </li>
                            </#if>
                        </ul>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="card mb-3">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Название резюме:</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <#assign var2 = resume.resumeTitle!"Не указано">
                                    ${var2}
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Категория:</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <#assign var1 = resume.category! "Категория не известна">
                                    ${var1}
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Ожидаемый уровень оклада:</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <#assign var3 = (resume.expectedSalary)!"Не указано">
                                    ${var3}
                                </div>
                            </div>
                            <hr>
                        </div>
                    </div>

                    <div class="row gutters-sm">
                        <div class="col-sm-6 mb-3">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h6 class="d-flex align-items-center mb-3">Опыт работы</h6>

                                    <#list resume.workList as item>
                                        <button class="btn btn-info rounded-circle px-3 mb-2">${item_index + 1}</button><br>


                                        <small>Название компании</small>
                                        <div>
                                            ${item.companyName! "Не указано"}
                                        </div>
                                        <small>Должность</small>
                                        <div>
                                            ${item.position! "Не указано"}
                                        </div>
                                        <small>Обязанности:</small>
                                        <div>
                                            ${item.responsibilities! "Не указано"}
                                        </div>
                                        <small>Начало работы: </small>
                                        <div>
                                            ${item.dateStart! "Не указано"}
                                        </div>
                                        <small>Завершение работы: </small>
                                        <div>
                                            ${item.dateEnd! "Не указано"}
                                        </div>
                                        <hr>
                                    </#list>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 mb-3">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h6 class="d-flex align-items-center mb-3">Образование</h6>
                                    <#list resume.eduList as item>
                                        <button class="btn btn-info rounded-circle px-3 mb-2">${item_index + 1}</button><br>

                                        <small>Образование</small>
                                        <div>
                                            ${item.education! "Не указано"}
                                        </div>
                                        <small>Где учился:</small>
                                        <div>
                                            ${item.schoolName! "Не указано"}
                                        </div>
                                        <small>Начало обучения: </small>
                                        <div>
                                            ${item.startDate! "Не указано"}
                                        </div>
                                        <small>Дата выпуска: </small>
                                        <div>
                                            ${item.graduationDate! "Не указано"}
                                        </div>
                                        <hr>
                                    </#list>


                                </div>
                            </div>
                        </div>
                    </div>



                </div>
            </div>

        </div>
    </div>
    <style>
        body{
            margin-top:20px;
            color: #1a202c;
            text-align: left;
            background-color: #e2e8f0;
        }
        .main-body {
            padding: 15px;
        }
        .card {
            box-shadow: 0 1px 3px 0 rgba(0,0,0,.1), 0 1px 2px 0 rgba(0,0,0,.06);
        }
        .card {
            position: relative;
            display: flex;
            flex-direction: column;
            min-width: 0;
            word-wrap: break-word;
            background-color: #fff;
            background-clip: border-box;
            border: 0 solid rgba(0,0,0,.125);
            border-radius: .25rem;
        }

        .card-body {
            flex: 1 1 auto;
            min-height: 1px;
            padding: 1rem;
        }

        .gutters-sm {
            margin-right: -8px;
            margin-left: -8px;
        }

        .gutters-sm>.col, .gutters-sm>[class*=col-] {
            padding-right: 8px;
            padding-left: 8px;
        }
        .mb-3, .my-3 {
            margin-bottom: 1rem!important;
        }

        .bg-gray-300 {
            background-color: #e2e8f0;
        }
        .h-100 {
            height: 100%!important;
        }
        .shadow-none {
            box-shadow: none!important;
        }
    </style>


</@main.layout>