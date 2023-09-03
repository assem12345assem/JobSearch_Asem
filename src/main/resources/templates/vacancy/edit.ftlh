<#import '../layout.ftlh' as main>
<@main.layout>

    <div class="container">
        <div class="main-body">
            <h2 class="text-primary fs-2 text-center"> Вакансия пользователя:</h2>
            <form action="/vacancy/edit/${vacancy.id}" method="post">

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
                                             src="/auth/images/${user.email}" alt="photo" width="250"
                                             height="auto">
                                    <#else>
                                        <img
                                                src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXPgCd93aiNba8lHWJYHQ1C1YwBPwcH_NUmw&usqp=CAU" alt="profile picture">
                                    </#if>
                                    <div class="mt-3">
                                        <#assign v=vacancy.profile.companyName!"Название компании не указано">
                                        <h4>${v}</h4>

                                        <a class="btn btn-success"
                                           href="/auth/edit/${user.email}">Редактировать профиль</a>



                                    </div>
                                </div>
                            </div>
                        </div>


                    </div>


                    <div class="col-md-8">
                        <div class="card mb-3">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Название вакансии:</h6>
                                    </div>


                                    <div class="col-sm-9 text-secondary">
                                        <#if (vacancy.vacancyName)??>
                                            <input class="form-control" type="text"
                                                   placeholder="${vacancy.vacancyName}" name="vacancyName"
                                                   value="${vacancy.vacancyName}">
                                        <#else>
                                            <input class="form-control" type="text"
                                                   placeholder="Введите название" name="vacancyName">
                                        </#if>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Категория:</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <#assign var1=vacancy.category! "Категория не известна">
                                        <#if (var1)?has_content>
                                            выбрать другую категорию:
                                            <select class="form-select border-dark"
                                                    aria-label="Default select example" name="category"
                                                    id="category">
                                                <#assign var1=vacancy.category! "Выберите категорию">
                                                <option selected>${var1}
                                                <option value="Technology" class="form-brd-radius col-6">Technology</option>
                                                <option value="Healthcare" class="form-brd-radius col-6">Healthcare</option>
                                                <option value="Finance" class="form-brd-radius col-6">Finance</option>
                                                <option value="Education" class="form-brd-radius col-6">Education</option>
                                                <option value="Hospitality" class="form-brd-radius col-6">Hospitality</option>
                                                <option value="Marketing" class="form-brd-radius col-6">Marketing</option>
                                                <option value="Engineering" class="form-brd-radius col-6">Engineering</option>
                                                <option value="Retail" class="form-brd-radius col-6">Retail</option>
                                                <option value="Art and Design" class="form-brd-radius col-6">Art and Design</option>
                                                <option value="Customer Service" class="form-brd-radius col-6">Customer Service</option>
                                                <option value="Sales" class="form-brd-radius col-6">Sales</option>
                                                <option value="Manufacturing" class="form-brd-radius col-6">Manufacturing</option>
                                                <option value="Media and Entertainment" class="form-brd-radius col-6">Media and Entertainment</option>
                                                <option value="Science" class="form-brd-radius col-6">Science</option>
                                                <option value="Legal" class="form-brd-radius col-6">Legal</option>
                                                <option value="Human Resources" class="form-brd-radius col-6">Human Resources</option>
                                                <option value="Transportation" class="form-brd-radius col-6">Transportation</option>
                                                <option value="Construction" class="form-brd-radius col-6">Construction</option>
                                                <option value="Non-profit" class="form-brd-radius col-6">Non-profit</option>
                                                <option value="Agriculture" class="form-brd-radius col-6">Agriculture</option>
                                                <option value="Other" class="form-brd-radius col-6">Other</option>

                                            </select>
                                        <#else>
                                            <select class="form-select border-dark"
                                                    aria-label="Default select example" name="category"
                                                    id="category">

                                                <option value="Technology" class="form-brd-radius col-6">Technology</option>
                                                <option value="Healthcare" class="form-brd-radius col-6">Healthcare</option>
                                                <option value="Finance" class="form-brd-radius col-6">Finance</option>
                                                <option value="Education" class="form-brd-radius col-6">Education</option>
                                                <option value="Hospitality" class="form-brd-radius col-6">Hospitality</option>
                                                <option value="Marketing" class="form-brd-radius col-6">Marketing</option>
                                                <option value="Engineering" class="form-brd-radius col-6">Engineering</option>
                                                <option value="Retail" class="form-brd-radius col-6">Retail</option>
                                                <option value="Art and Design" class="form-brd-radius col-6">Art and Design</option>
                                                <option value="Customer Service" class="form-brd-radius col-6">Customer Service</option>
                                                <option value="Sales" class="form-brd-radius col-6">Sales</option>
                                                <option value="Manufacturing" class="form-brd-radius col-6">Manufacturing</option>
                                                <option value="Media and Entertainment" class="form-brd-radius col-6">Media and Entertainment</option>
                                                <option value="Science" class="form-brd-radius col-6">Science</option>
                                                <option value="Legal" class="form-brd-radius col-6">Legal</option>
                                                <option value="Human Resources" class="form-brd-radius col-6">Human Resources</option>
                                                <option value="Transportation" class="form-brd-radius col-6">Transportation</option>
                                                <option value="Construction" class="form-brd-radius col-6">Construction</option>
                                                <option value="Non-profit" class="form-brd-radius col-6">Non-profit</option>
                                                <option value="Agriculture" class="form-brd-radius col-6">Agriculture</option>
                                                <option value="Other" class="form-brd-radius col-6" selected>Other</option>

                                            </select>
                                        </#if>

                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Заработная плата:</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <#assign var3=vacancy.salary>
                                        <#if (var3)?has_content>
                                            <input class="form-control" type="number" placeholder="${var3}"
                                                   name="salary" value="${var3}">
                                        <#else>
                                            <input class="form-control" type="number"
                                                   placeholder="Введите оклад" name="salary">
                                        </#if>
                                    </div>
                                </div>
                                <hr>

                                <div class="row">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Описание вакансии:</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <#if (vacancy.description)?has_content>
                                            <input class="form-control" type="number"
                                                   placeholder="${vacancy.description}" name="description"
                                                   value="${vacancy.description}">
                                        <#else>
                                            <input class="form-control" type="text"
                                                   placeholder="Введите описание" name="description">
                                        </#if>
                                    </div>
                                </div>
                                <hr>

                                <div class="row">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">Требуемый опыт:</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <#if (vacancy.requiredExperienceMin)?has_content>
                                            <input class="form-control" type="number"
                                                   placeholder="${vacancy.requiredExperienceMin}"
                                                   name="requiredExperienceMin"
                                                   value="${vacancy.requiredExperienceMin}">
                                        <#else>
                                            <input class="form-control" type="text" placeholder="от"
                                                   name="requiredExperienceMin">
                                        </#if>
                                        <#if (vacancy.requiredExperienceMax)?has_content>
                                            <input class="form-control" type="number"
                                                   placeholder="${vacancy.requiredExperienceMax}"
                                                   name="requiredExperienceMax"
                                                   value="${vacancy.requiredExperienceMax}">
                                        <#else>
                                            <input class="form-control" type="text" placeholder="до"
                                                   name="requiredExperienceMax">
                                        </#if>
                                    </div>
                                </div>



                                <hr>
                            </div>
                        </div>


                    </div>
                </div>
            </form>

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

        .gutters-sm>.col,
        .gutters-sm>[class*=col-] {
            padding-right: 8px;
            padding-left: 8px;
        }

        .mb-3,
        .my-3 {
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