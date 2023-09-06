<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container-xl px-4 mt-4">

    <hr class="mt-0 mb-4">
    <div class="row">
        <!-- Profile picture card-->
        <div class="col-xl-4">
            <div class="card mb-4 mb-xl-0">
                <div class="card-header">Профиль компании
                </div>
                <div class="card-body text-center pb-5">
                    <#if company.photo?has_content>
                        <img style="max-width: 15rem;" class="img-account-profile rounded-circle mb-2" src="/auth/images/${company.email}" alt="photo">
                    <#else>
                        <img style="max-width: 15rem;" src="https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510_640.jpg" alt="company profile picture">
                    </#if>
                </div>
            </div>
        </div>


        <div class="col-xl-8">
            <!-- Account details card-->
            <div class="card mb-4">
                <div class="card-header ">
                    <div class="col-8">
                        Информация о компании
                    </div>
                </div>
                <!-- Form Group (username)-->
                <div class="card-body">
                    <div>
                        <!-- Form Row-->

                        <div class="mb-3">
                            <#assign str = company.name!"Название компании не указано">
                            <label class="small mb-1" for="orgName">Название компании</label>
                            <p class="form-control" id="orgName" type="text">${str}</p>
                        </div>
                        <!-- Form Row        -->
                        <div class="mb-3">
                            <label class="small mb-1" for="inputPhone">Номер телефона</label>
                            <p class="form-control" id="inputPhone" type="text">
                                <#if (company.phoneNumber)??>${company.phoneNumber}<#else >  не указано</#if>
                            </p>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <!-- Account resumes/vacancies-->

    </div>
    <div class="container">
        <div class="row">
            <h3>Вакансии компании </h3>

            <#if (myList)??>
                <#list myList.content as item>
                    <#if item?index == 0 || item?index % 3 == 0>
                        <div class="row my-3">
                    </#if>
                    <div class="card col mx-1">
                        <div class="card-body">
                            <h5 class="card-title"><#if (item.title)??> ${item.title}<#else> Нет названия</#if></h5>
                            <p class="card-text"><#if (item.dateTime)??>${item.dateTime}<#else >Нет даты</#if></p>
                            <div class="d-flex">
                                <a href="/vacancy/${item.id}" class="btn btn-primary me-3">Посмотреть</a>

                            </div>

                        </div>
                    </div>
                    <#if item?index % 3 == 2 || item?is_last>
                        </div>
                    </#if>
                </#list>
            <#else >
                Ещё нет вакансий
            </#if>
        </div>

        <!-- Pagination controls -->
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link"
                   href="/auth/profile/company/${company.email}?pageNumber=0">First</a>
            </li>
            <li class="page-item">
                <#if (pageNumber > 0)>
                    <a class="page-link"
                       href="/auth/profile/company/${company.email}?pageNumber=${pageNumber - 1}">&laquo;
                        Previous</a>
                </#if>
            </li>
            <#list 1..totalPages as pageNo>
                <li class="page-item">
                    <a class="page-link"
                       href="/auth/profile/company/${company.email}?pageNumber=${pageNo - 1}">
                        <#if pageNumber == pageNo> </#if>${pageNo}</a>
                </li>
            </#list>
            <li class="page-item">
                <#if pageNumber < (totalPages-1)>
                    <a class="page-link"
                       href="/auth/profile/company/${company.email}?pageNumber=${pageNumber + 1}">Next
                        &raquo;</a>
                </#if>
            </li>
            <li class="page-item">
                <a class="page-link"
                   href="/auth/profile/company/${company.email}?pageNumber=${totalPages-1}">Last</a>
            </li>
        </ul>
    </div>

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