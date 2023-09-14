<#import "../layout.ftlh" as main>
<#import '/spring.ftl' as spring>

<@main.layout>
    <div class="container">
        <div class="main-body">
            <h2 class="text-primary fs-2 text-center"> Резюме пользователя:</h2>
            <form action="/resume/edit/${resume.id}" method="post">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                           value="${(_csrf.token)!'csrf-token'}"/>
                </#if>
                <div class="d-grid gap-2 fixed-button-container">
                    <button class="btn btn-success mt-3 bottom-button" type="submit" id="submitResume">Сохранить
                    </button>
                </div>

                <div class="row gutters-sm mt-5">
                    <div class="col-md-4 mb-3">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex flex-column align-items-center text-center">
                                    <#if user.photo?has_content>
                                        <img class="img-account-profile rounded-circle mb-2"
                                             src="/auth/images/${user.email}" alt="photo" width="250" height="auto">
                                    <#else>
                                        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXPgCd93aiNba8lHWJYHQ1C1YwBPwcH_NUmw&usqp=CAU"
                                             alt="profile picture">
                                    </#if>
                                    <div class="mt-3">
                                        <h4><#if (resume.profile.firstName)??>${resume.profile.firstName}</#if> <#if (resume.profile.lastName)??>${resume.profile.lastName}</#if></h4>
                                        <p class="text-secondary mb-1">Дата рождения:</p>
                                        <p class="text-muted font-size-sm"><#if (resume.profile.dateOfBirth)??>${resume.profile.dateOfBirth}</#if></p>
                                        <a class="btn btn-success" href="/auth/edit/${user.email}">Редактировать
                                            профиль</a>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="card mt-3">
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">

                                    <#if (resume.contact.telegram)??>
                                        <label for="telegram" class="fs-6"><i class="fa-brands fa-telegram fs-3"></i>
                                            Telegram:</label>
                                        <input id="telegram" type="text" name="contact.telegram"
                                               placeholder="${resume.contact.telegram}" class="form-control"
                                               value="${resume.contact.telegram}"
                                        >

                                    <#else>
                                        <label for="telegram" class="fs-6"><i class="fa-brands fa-telegram fs-3"></i>
                                            Telegram:</label>
                                        <input id="telegram" class="form-control" type="text" placeholder="telegram"
                                               name="contact.telegram">
                                    </#if>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">

                                    <#if (resume.contact.email)??>
                                        <label for="email" class="fs-6"><i class="fa-solid fa-envelope fs-3"></i> Email:</label>
                                        <input id="email" class="form-control" type="text"
                                               placeholder="${resume.contact.email}"
                                               name="contact.email" value="${resume.contact.email}">

                                    <#else>
                                        <label for="email" class="fs-6"><i class="fa-solid fa-envelope fs-3"></i> Email:</label>
                                        <input id="email" class="form-control" type="text" placeholder="email"
                                               name="contact.email">
                                    </#if>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                    <#if (resume.contact.facebook)??>
                                        <label for="facebook" class="fs-6"><i
                                                    class="fa-brands fa-square-facebook fs-3"></i> Facebook:</label>
                                        <input id="facebook" class="form-control" type="text"
                                               placeholder="${resume.contact.facebook}"
                                               name="contact.facebook" value=${resume.contact.facebook}">
                                        <#else >
                                            <label for="facebook" class="fs-6"><i class="fa-brands fa-square-facebook fs-3"></i> Facebook:</label>
                                            <input id="facebook" class="form-control" type="text" placeholder="facebook"
                                                                                       name="contact.facebook">
                                        </#if>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">

                                    <#if (resume.contact.linkedIn)??>
                                        <label for="linkedin" class="fs-6"><i class="fa-brands fa-linkedin-in fs-3"></i>
                                            LinkedIn:</label>
                                        <input id="linkedin" class="form-control" type="text"
                                               placeholder="${resume.contact.linkedIn}"
                                               name="contact.linkedIn" value="${resume.contact.linkedIn}">
                                    <#else >
                                        <label for="linkedin" class="fs-6"><i class="fa-brands fa-linkedin-in fs-3"></i>
                                            LinkedIn:</label>
                                        <input id="linkedin" class="form-control" type="text" placeholder="linkedIn"
                                               name="contact.linkedIn">

                                    </#if>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">

                                    <#if (resume.contact.phoneNumber)??>
                                        <label class="fs-6"><i class="fa-solid fa-phone fs-3"></i> Phone Number:</label>
                                        <input class="form-control" type="text"
                                               placeholder="${resume.contact.phoneNumber}"
                                               name="contact.phoneNumber" value="${resume.contact.phoneNumber}">
                                    <#else >
                                        <label class="fs-6"><i class="fa-solid fa-phone fs-3"></i> Phone Number:</label>
                                        <input class="form-control" type="text" placeholder="+996 999 555 555"
                                               name="contact.phoneNumber">
                                    </#if>
                                </li>

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
                                        <#if (resume.resumeTitle)??>
                                            <input class="form-control" type="text" placeholder="${resume.resumeTitle}"
                                                   name="resumeTitle" value="${resume.resumeTitle}">
                                        <#else>
                                            <input class="form-control" type="text" placeholder="Введите название"
                                                   name="resumeTitle">
                                        </#if>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Категория:</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <#assign var1 = resume.category! "Категория не известна">
                                        <#if (var1)?has_content>
                                            выбрать другую категорию:
                                            <select class="form-select border-dark" aria-label="Default select example"
                                                    name="category" id="category">
                                                <#assign var1=resume.category! "Выберите категорию">
                                                <option selected>${var1}
                                                <option value="Technology" class="form-brd-radius col-6">Technology
                                                </option>
                                                <option value="Healthcare" class="form-brd-radius col-6">Healthcare
                                                </option>
                                                <option value="Finance" class="form-brd-radius col-6">Finance</option>
                                                <option value="Education" class="form-brd-radius col-6">Education
                                                </option>
                                                <option value="Hospitality" class="form-brd-radius col-6">Hospitality
                                                </option>
                                                <option value="Marketing" class="form-brd-radius col-6">Marketing
                                                </option>
                                                <option value="Engineering" class="form-brd-radius col-6">Engineering
                                                </option>
                                                <option value="Retail" class="form-brd-radius col-6">Retail</option>
                                                <option value="Art and Design" class="form-brd-radius col-6">Art and
                                                    Design
                                                </option>
                                                <option value="Customer Service" class="form-brd-radius col-6">Customer
                                                    Service
                                                </option>
                                                <option value="Sales" class="form-brd-radius col-6">Sales</option>
                                                <option value="Manufacturing" class="form-brd-radius col-6">
                                                    Manufacturing
                                                </option>
                                                <option value="Media and Entertainment" class="form-brd-radius col-6">
                                                    Media and Entertainment
                                                </option>
                                                <option value="Science" class="form-brd-radius col-6">Science</option>
                                                <option value="Legal" class="form-brd-radius col-6">Legal</option>
                                                <option value="Human Resources" class="form-brd-radius col-6">Human
                                                    Resources
                                                </option>
                                                <option value="Transportation" class="form-brd-radius col-6">
                                                    Transportation
                                                </option>
                                                <option value="Construction" class="form-brd-radius col-6">
                                                    Construction
                                                </option>
                                                <option value="Non-profit" class="form-brd-radius col-6">Non-profit
                                                </option>
                                                <option value="Agriculture" class="form-brd-radius col-6">Agriculture
                                                </option>
                                                <option value="Other" class="form-brd-radius col-6">Other</option>

                                            </select>
                                        <#else >
                                            <select class="form-select border-dark" aria-label="Default select example"
                                                    name="category" id="category">
                                                <option value="Technology" class="form-brd-radius col-6">Technology
                                                </option>
                                                <option value="Healthcare" class="form-brd-radius col-6">Healthcare
                                                </option>
                                                <option value="Finance" class="form-brd-radius col-6">Finance</option>
                                                <option value="Education" class="form-brd-radius col-6">Education
                                                </option>
                                                <option value="Hospitality" class="form-brd-radius col-6">Hospitality
                                                </option>
                                                <option value="Marketing" class="form-brd-radius col-6">Marketing
                                                </option>
                                                <option value="Engineering" class="form-brd-radius col-6">Engineering
                                                </option>
                                                <option value="Retail" class="form-brd-radius col-6">Retail</option>
                                                <option value="Art and Design" class="form-brd-radius col-6">Art and
                                                    Design
                                                </option>
                                                <option value="Customer Service" class="form-brd-radius col-6">Customer
                                                    Service
                                                </option>
                                                <option value="Sales" class="form-brd-radius col-6">Sales</option>
                                                <option value="Manufacturing" class="form-brd-radius col-6">
                                                    Manufacturing
                                                </option>
                                                <option value="Media and Entertainment" class="form-brd-radius col-6">
                                                    Media and Entertainment
                                                </option>
                                                <option value="Science" class="form-brd-radius col-6">Science</option>
                                                <option value="Legal" class="form-brd-radius col-6">Legal</option>
                                                <option value="Human Resources" class="form-brd-radius col-6">Human
                                                    Resources
                                                </option>
                                                <option value="Transportation" class="form-brd-radius col-6">
                                                    Transportation
                                                </option>
                                                <option value="Construction" class="form-brd-radius col-6">
                                                    Construction
                                                </option>
                                                <option value="Non-profit" class="form-brd-radius col-6">Non-profit
                                                </option>
                                                <option value="Agriculture" class="form-brd-radius col-6">Agriculture
                                                </option>
                                                <option value="Other" class="form-brd-radius col-6" selected>Other
                                                </option>

                                            </select>
                                        </#if>

                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Ожидаемый уровень оклада:</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <#if (resume.expectedSalary)?has_content>
                                            <input class="form-control" type="number"
                                                   placeholder="${resume.expectedSalary}"
                                                   name="expectedSalary" value="${resume.expectedSalary}">
                                        <#else >
                                            <input class="form-control" type="number" placeholder="Введите оклад"
                                                   name="expectedSalary">
                                        </#if>
                                    </div>
                                </div>
                                <hr>
                                <div class="row mt-1">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Активно:</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <input class="form-check-input fs-2 border-dark" type="checkbox"
                                               id="checkboxId" value="active"
                                               name="isActive" <#if resume.isActive == "true">checked</#if>
                                        />
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
                                        <!-- Button trigger modal -->
                                        <div class="d-flex justify-content-center">
                                            <button type="button" class="btn btn-primary" id="addWorkExperienceButton"
                                                    data-bs-toggle="modal" data-bs-target="#workModal">
                                                +
                                            </button>
                                        </div>

                                        <#if (resume.workList)??>

                                            <#list resume.workList as item>
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <button class="btn btn-info rounded-circle px-3 mb-2">${item_index + 1}</button>
                                                    <a class="btn btn-danger"
                                                       href="/resume/edit/delete_work/${item.id}">Удалить</a>
                                                </div>


                                                <small>Название компании</small>
                                                <div>
                                                    <#if (item.companyName)?has_content>
                                                        <input class="form-control" type="text"
                                                               placeholder="${item.companyName}"
                                                               name="workList[${item_index}].companyName"
                                                               value="${item.companyName}">
                                                    <#else >
                                                        <input class="form-control" type="text"
                                                               placeholder="Название компании"
                                                               name="workList[${item_index}].companyName">
                                                    </#if>


                                                </div>
                                                <small>Должность</small>
                                                <div>
                                                    <#if (item.position)?has_content>
                                                        <input class="form-control" type="text"
                                                               placeholder="${item.position}"
                                                               name="workList[${item_index}].position"
                                                               value="${item.position}">
                                                    <#else >
                                                        <input class="form-control" type="text" placeholder="Должность"
                                                               name="workList[${item_index}].position">
                                                    </#if>


                                                </div>
                                                <small>Обязанности:</small>
                                                <div>
                                                    <#if (item.responsibilities)?has_content>
                                                        <input class="form-control" type="text"
                                                               placeholder="${item.responsibilities}"
                                                               name="workList[${item_index}].responsibilities"
                                                               value="${item.responsibilities}">
                                                    <#else >
                                                        <input class="form-control" type="text"
                                                               placeholder="Обязанности"
                                                               name="workList[${item_index}].responsibilities">
                                                    </#if>


                                                </div>
                                                <small>Начало работы: </small>
                                                <div>
                                                    <#if (item.dateStart)?has_content>

                                                        <input class="form-control" type="date"
                                                               placeholder="Начало работы"
                                                               name="workList[${item_index}].dateStart"
                                                               value="${item.dateStart}"> <br>
                                                    <#else >
                                                        <input class="form-control" type="date"
                                                               placeholder="Начало работы"
                                                               name="workList[${item_index}].dateStart">
                                                    </#if>
                                                </div>

                                                <small>Завершение работы: </small>
                                                <div>
                                                    <#if (item.dateEnd)?has_content>

                                                        <input class="form-control" type="date"
                                                               placeholder="Завершение работы"
                                                               name="workList[${item_index}].dateEnd"
                                                               value="${item.dateEnd}"> <br>
                                                    <#else >
                                                        <input class="form-control" type="date"
                                                               placeholder="Завершение работы"
                                                               name="workList[${item_index}].dateEnd">
                                                    </#if>
                                                </div>




                                                <hr>
                                            </#list>
                                        </#if>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 mb-3">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <h6 class="d-flex align-items-center mb-3">Образование</h6>
                                        <div class="d-flex justify-content-center">
                                            <button type="button" class="btn btn-primary" id="addEducationButton"
                                                    data-bs-toggle="modal" data-bs-target="#eduModal">
                                                +
                                            </button>
                                        </div>
                                        <#if (resume.eduList)??>
                                        <#list resume.eduList as item>
                                        <div class="d-flex justify-content-between align-items-center">
                                            <button class="btn btn-info rounded-circle px-3 mb-2">${item_index + 1}</button>
                                            <a class="btn btn-danger"
                                               href="/resume/edit/delete_education/${item.id}">Удалить</a>
                                        </div>

                                        <small>Образование</small>
                                        <div>
                                            <#if (item.education)?has_content>
                                                <input class="form-control" type="text"
                                                       placeholder="${item.education}"
                                                       name="eduList[${item_index}].education"
                                                       value="${item.education}">
                                            <#else >
                                                <input class="form-control" type="text"
                                                       placeholder="Образование"
                                                       name="eduList[${item_index}].education">
                                            </#if>
                                        </div>
                                        <small>Где учился:</small>
                                        <div>

                                            <#if (item.schoolName)?has_content>
                                                <input class="form-control" type="text"
                                                       placeholder="${item.schoolName}"
                                                       name="eduList[${item_index}].schoolName"
                                                       value="${item.schoolName}">
                                            <#else >
                                                <input class="form-control" type="text"
                                                       placeholder="Учебное заведение"
                                                       name="eduList[${item_index}].schoolName">
                                            </#if>
                                        </div>
                                        <small>Начало обучения: </small>
                                        <div>
                                            <#if (item.startDate)?has_content>

                                                <input class="form-control" type="date"
                                                       placeholder="Начало обучения"
                                                       name="eduList[${item_index}].startDate"
                                                       value="${item.startDate}"> <br>
                                            <#else >

                                                <input class="form-control" type="date"
                                                       placeholder="Начало обучения"
                                                       name="eduList[${item_index}].startDate">
                                            </#if>
                                        </div>
                                        <small>Дата выпуска: </small>
                                        <#if (item.graduationDate)?has_content>


                                            <input class="form-control" type="date"
                                                   placeholder="Дата выпуска"
                                                   name="eduList[${item_index}].graduationDate"
                                                   value="${item.graduationDate}"> <br>
                                        <#else >


                                            <input class="form-control" type="date"
                                                   placeholder="Дата выпуска"
                                                   name="eduList[${item_index}].graduationDate">
                                        </#if>
                                    </div>
                                    <hr>
                                    </#list>
                                    </#if>


                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
        </form>

    </div>
    </div>


    <!-- Modal -->
    <div class="modal fade" id="workModal" tabindex="-1" aria-labelledby="workModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="workModalLabel">Опыт работы</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form method="post" action="/resume/edit/add_one_work/${resume.id}">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                               value="${(_csrf.token)!'csrf-token'}"/>
                    </#if>
                    <div class="modal-body">

                        <small>Название компании</small>
                        <input class="form-control" type="text" placeholder="Название компании"
                               name="companyName">
                        <small>Должность</small>
                        <input class="form-control" type="text" placeholder="Должность"
                               name="position">
                        <small>Обязанности:</small>
                        <input class="form-control" type="text" placeholder="Обязанности"
                               name="responsibilities">
                        <small>Начало работы: </small>
                        <input class="form-control" type="date" placeholder="Начало работы"
                               name="dateStart">
                        <small>Завершение работы: </small>
                        <input class="form-control" type="date" placeholder="Завершение работы"
                               name="dateEnd">
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary" id="addWorkExperience">OK</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <!-- Modal -->
    <div class="modal fade" id="eduModal" tabindex="-1" aria-labelledby="eduModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="eduModalLabel">Образование</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form method="post" action="/resume/edit/add_one_edu/${resume.id}">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                               value="${(_csrf.token)!'csrf-token'}"/>
                    </#if>
                    <div class="modal-body">
                        <small>Образование</small>
                        <input class="form-control" type="text" placeholder="Образование"
                               name="education">

                        <small>Где учился</small>
                        <input class="form-control" type="text" placeholder="Учебное заведение"
                               name="schoolName">

                        <small>Начало обучения</small>

                        <input class="form-control" type="date" placeholder="Начало обучения"
                               name="startDate">
                        <small>Дата выпуска: </small>


                        <input class="form-control" type="date" placeholder="Дата выпуска"
                               name="graduationDate">
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary" id="addEducation">OK</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="mb-5 pb-5"></div>

    <style>
        body {
            margin-top: 20px;
            color: #1a202c;
            text-align: left;
            background-color: #e2e8f0;
        }

        .fixed-button-container {
            position: fixed;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            z-index: 1000;
            width: 100%;
            padding: 10px 0 20px;
            background-color: #1a202ca5;
        }

        .main-body {
            padding: 15px;
        }

        .card {
            box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .1), 0 1px 2px 0 rgba(0, 0, 0, .06);
        }

        .card {
            position: relative;
            display: flex;
            flex-direction: column;
            min-width: 0;
            word-wrap: break-word;
            background-color: #fff;
            background-clip: border-box;
            border: 0 solid rgba(0, 0, 0, .125);
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

        .gutters-sm > .col, .gutters-sm > [class*=col-] {
            padding-right: 8px;
            padding-left: 8px;
        }

        .mb-3, .my-3 {
            margin-bottom: 1rem !important;
        }

        .bg-gray-300 {
            background-color: #e2e8f0;
        }

        .h-100 {
            height: 100% !important;
        }

        .shadow-none {
            box-shadow: none !important;
        }
    </style>


</@main.layout>