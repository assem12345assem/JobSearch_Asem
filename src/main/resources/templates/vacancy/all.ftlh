<#import '../layout.ftlh' as main>
<#import "/spring.ftl" as spring>
<@main.layout>
    <script type="text/javascript" src="/static/scripts/searchForm.js"></script>
    <div class="container">
        <h1 class="my-3 text-center"><@spring.message 'vacancy.vacancies'/></h1>
        <form class="d-flex m-5" id="searchForm">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}"/>
            </#if>
            <input class="form-control me-2" type="search"
                   placeholder="<@spring.message 'vacancy.search.text'/>" aria-label="Search" name="searchWord" id="searchWord">
            <button class="btn btn-outline-success" type="submit"><@spring.message 'button.search'/></button>
        </form>
        <div class="m-3">
            <h3> <@spring.message 'button.sort'/></h3>
            <div class="d-flex">
                <a href="/vacancy/all/view?pageNumber=0&sort=dateTime&category=default&date=default&application=default&searchWord=default" class="btn btn-info"><@spring.message 'button.sort.by_date'/></a>
                <a href="/vacancy/all/view?pageNumber=0&sort=salary&category=default&date=default&application=default&searchWord=default" class="mx-3 btn btn-info"><@spring.message 'vacancy.sort.by_salary'/></a>


                <form class="row mx-4" id="categoryForm">
                    <select class="form-select border-dark col" aria-label="<@spring.message 'button.sort.by_category'/>"
                            name="category" id="category">
                        <option selected><@spring.message 'button.sort.by_category'/></option>
                        <option value="Technology" class="form-brd-radius col-6"><@spring.message 'category.Technology'/></option>
                        <option value="Healthcare" class="form-brd-radius col-6"><@spring.message 'category.Healthcare'/></option>
                        <option value="Finance" class="form-brd-radius col-6"><@spring.message 'category.Finance'/></option>
                        <option value="Education" class="form-brd-radius col-6"><@spring.message 'category.Education'/></option>
                        <option value="Hospitality" class="form-brd-radius col-6"><@spring.message 'category.Hospitality'/></option>
                        <option value="Marketing" class="form-brd-radius col-6"><@spring.message 'category.Marketing'/></option>
                        <option value="Engineering" class="form-brd-radius col-6"><@spring.message 'category.Engineering'/></option>
                        <option value="Retail" class="form-brd-radius col-6"><@spring.message 'category.Retail'/></option>
                        <option value="ArtAndDesign" class="form-brd-radius col-6"><@spring.message 'category.ArtAndDesign'/></option>
                        <option value="CustomerService" class="form-brd-radius col-6"><@spring.message 'category.CustomerService'/></option>
                        <option value="Sales" class="form-brd-radius col-6"><@spring.message 'category.Sales'/></option>
                        <option value="Manufacturing" class="form-brd-radius col-6"><@spring.message 'category.Manufacturing'/></option>
                        <option value="MediaAndEntertainment" class="form-brd-radius col-6"><@spring.message 'category.MediaAndEntertainment'/></option>
                        <option value="Science" class="form-brd-radius col-6"><@spring.message 'category.Science'/></option>
                        <option value="Legal" class="form-brd-radius col-6"><@spring.message 'category.Legal'/></option>
                        <option value="HumanResources" class="form-brd-radius col-6"><@spring.message 'category.HumanResources'/></option>
                        <option value="Transportation" class="form-brd-radius col-6"><@spring.message 'category.Transportation'/></option>
                        <option value="Construction" class="form-brd-radius col-6"><@spring.message 'category.Construction'/></option>
                        <option value="NonProfit" class="form-brd-radius col-6"><@spring.message 'category.NonProfit'/></option>
                        <option value="Agriculture" class="form-brd-radius col-6"><@spring.message 'category.Agriculture'/></option>
                        <option value="Other" class="form-brd-radius col-6"><@spring.message 'category.Other'/></option>

                    </select>
                    <button type="submit" class="btn btn-info col-2 mx-2 px-3"> OK </button>
                </form>


            </div>
            <div class="row my-3">
                <form class="col-6 d-flex" id="dateForm">
                    <select class="form-select border-dark " aria-label="<@spring.message 'vacancy.filter.by_date'/>"
                            name="date" id="date">
                        <option selected><@spring.message 'vacancy.filter.by_date'/></option>
                        <#list dates as date>
                            <option value="${date}" class="form-brd-radius col-6">${date}</option>
                        </#list>
                    </select>
                    <button type="submit" class="btn btn-info  mx-3 ps-3"> OK  </button>
                </form>
                <#assign uniqueApplications = []>
                <#list applications as application>
                    <#if !uniqueApplications?seq_contains(application)>
                        <#assign uniqueApplications = uniqueApplications + [application]>
                    </#if>
                </#list>
                <form class="col-6 d-flex " id="applicationsForm">
                    <select class="form-select border-dark " aria-label="<@spring.message 'vacancy.filter.by_application'/>"
                            name="application" id="applications">
                        <option selected><@spring.message 'vacancy.filter.by_application'/></option>
                        <#list uniqueApplications as application>
                            <option value="${application}" class="form-brd-radius ">${application}</option>
                        </#list>


                    </select>
                    <button type="submit" class="btn btn-info mx-2 px-3"> OK </button>
                </form>
            </div>
        </div>

        <div class="row row-cols-4 row-cols-lg-3 g-2 g-lg-3 mb-4">
            <#if vacancies?has_content>
                <#list vacancies.content as item>
                    <div class="col">
                        <div class="p-3 border bg-light">
                            <#if (item.vacancyName)??>
                                <p>${item.vacancyName}</p>
<#else> <p></p>
                            </#if>

                            <#if (item.salary)??>
                                <p>${item.salary}</p> <#else > <p></p>
                            </#if>

                            <#if (item.dateTime)??>
                                <p>${item.dateTime}</p><#else ><p></p>
                            </#if>

                            <a href="/vacancy/${item.id}"
                               class="btn btn-success d-flex justify-content-center"><@spring.message 'button.info'/></a>

                        </div>
                    </div>

                </#list>
            <#else >
                <p><@spring.message 'info.no_result'/></p>
            </#if>


        </div>
        <!-- Pagination controls -->
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link"
                   href="/vacancy/all/view?pageNumber=0&sort=${sortCriteria}&category=${category}&date=${date}&application=${application}&searchWord=${searchWord}"><@spring.message 'pagination.first'/></a>
            </li>
            <li class="page-item">
                <#if (pageNumber > 0)>
                    <a class="page-link"
                       href="/vacancy/all/view?pageNumber=${pageNumber - 1}&sort=${sortCriteria}&category=${category}&date=${date}&application=${application}&searchWord=${searchWord}">&laquo;
                        <@spring.message 'pagination.previous'/></a>
                </#if>
            </li>
            <#list 1..totalPages as pageNo>
                <li class="page-item">
                    <a class="page-link"
                       href="/vacancy/all/view?pageNumber=${pageNo - 1}&sort=${sortCriteria}&category=${category}&date=${date}&application=${application}&searchWord=${searchWord}">
                        ${pageNo}</a>
                </li>
            </#list>
            <li class="page-item">
                <#if pageNumber < (totalPages-1)>
                    <a class="page-link"
                       href="/vacancy/all/view?pageNumber=${pageNumber + 1}&sort=${sortCriteria}&category=${category}&date=${date}&application=${application}&searchWord=${searchWord}"><@spring.message 'pagination.next'/>
                        &raquo;</a>
                </#if>
            </li>
            <li class="page-item">
                <a class="page-link"
                   href="/vacancy/all/view?pageNumber=${totalPages-1}&sort=${sortCriteria}&category=${category}&date=${date}&application=${application}&searchWord=${searchWord}"><@spring.message 'pagination.last'/></a>
            </li>
        </ul>


    </div>

</@main.layout>