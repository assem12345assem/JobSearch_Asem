<#import '../layout.ftlh' as main>
<@main.layout>
    <div class="container">
        <div class="main-body">
            <h2 class="text-primary fs-2 text-center"> Предложить вакансию:</h2>



            <div class="row gutters-sm mt-5">
                <div class="col-md-4 mb-3">
                    <div class="card">
                        <div class="card-body">
                            <div class="d-flex flex-column align-items-center text-center">
                                <#if user.photo?has_content>
                                    <img class="img-account-profile rounded-circle mb-2"
                                         src="/auth/images/${user.email}" alt="photo" width="250" height="auto">
                                <#else>
                                    <img
                                            src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXPgCd93aiNba8lHWJYHQ1C1YwBPwcH_NUmw&usqp=CAU" alt="profile picture">
                                </#if>
                                <div class="mt-3">

                                    <#assign r=resume.profile.companyName!"Название компании не указано">
                                    <h4>${resume.profile.firstName} ${resume.profile.lastName}</h4>
                                    <p class="text-secondary mb-1">Дата рождения:</p>
                                    <p class="text-muted font-size-sm">${resume.profile.dateOfBirth}</p>

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
                                    <h6 class="mb-0">Название резюме:</h6>
                                </div>



                                <div class="col-sm-9 text-secondary">
                                    <#if (resume.resumeTitle)??>
                                        <p class="form-control"> ${resume.resumeTitle!}</p>
                                    <#else>
                                        <p class="form-control"> Не указано</p>

                                    </#if>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="card mb-3">
                        <div class="card-body">
                            <div class="card-title">
<h3>Ваша вакансия:</h3>
                            </div>
                            <form action="/apply/offerjob/${resume.id}" method="post">
                                <input type="hidden" name="resumeId" value="${resume.id}">
                                <small>Текст сообщения:</small>
                                <input class="form-control" type="text" placeholder="текст сообщения"
                                       name="messageText">
                                <small>Предложенная вакансия</small>

                                <#if (vacancies)??>
                                    <select class="form-select border-dark"
                                            aria-label="Default select example" name="vacancyId">

                                    <#list vacancies as vacancy>
                                        Выберите вакансию из списка:
                                            <option value="${vacancy.id}" class="form-brd-radius col-6">
                                                ${vacancy.vacancyName}</option>
                                    </#list>
                                    </select>

                                <#else>
                                    Создайте вакансию, чтобы предложить её соискателю:
                                    <a href="vacancy/add" class="btn btn-info">Создать вакансию</a>
                                </#if>

                                <button type="submit" class="btn btn-primary"> Отправить </button>
                            </form>
                        </div>
                    </div>

                </div>


            </div>

        </div>


    </div>



    <style>
        body {
            margin-top: 20px;
            color: #1a202c;
            text-align: left;
            background-color: #e2e8f0;
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