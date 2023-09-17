<#import '../layout.ftlh' as main>
<#import "/spring.ftl" as spring>

<@main.layout>
    <script type="text/javascript" src="/static/scripts/searchFormResume.js"></script>

    <div class="container">
        <h1 class="my-3 text-center"><@spring.message 'navbar.navi.resumes'/></h1>
        <form class="d-flex m-5" id="searchForm">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}"/>
            </#if>
            <input class="form-control me-2" type="search" placeholder="<@spring.message 'resume.search.text'/>"
                   aria-label="Search" name="searchWord" id="searchWord">
            <button class="btn btn-outline-success" type="submit"><@spring.message 'button.search'/></button>
        </form>
        <div class="m-3">
            <h3> <@spring.message 'button.sort'/></h3>
            <div class="container">
                    <div class="col-2 mt-3">
                        <a href="/resume/all/view?pageNumber=0&sort=dateTime&category=default&searchWord=default"
                           class="btn btn-info">
                            <@spring.message 'button.sort.by_date' /></a>

                    </div>
                    <form class="row my-3" id="categoryForm">
                        <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                               value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>
                        <div class="col-3"><@spring.message 'button.sort.by_category'/>:</div>
                        <div class="col-6">
                            <select class="form-select border-dark col-5"
                                    aria-label="<@spring.message 'button.sort.by_category'/>"
                                    name="category" id="category">
                                <option selected><@spring.message 'button.sort.by_category'/></option>
                                <option value="Technology"
                                        class="form-brd-radius col-6"><@spring.message 'category.Technology'/></option>
                                <option value="Healthcare"
                                        class="form-brd-radius col-6"><@spring.message 'category.Healthcare'/></option>
                                <option value="Finance"
                                        class="form-brd-radius col-6"><@spring.message 'category.Finance'/></option>
                                <option value="Education"
                                        class="form-brd-radius col-6"><@spring.message 'category.Education'/></option>
                                <option value="Hospitality"
                                        class="form-brd-radius col-6"><@spring.message 'category.Hospitality'/></option>
                                <option value="Marketing"
                                        class="form-brd-radius col-6"><@spring.message 'category.Marketing'/></option>
                                <option value="Engineering"
                                        class="form-brd-radius col-6"><@spring.message 'category.Engineering'/></option>
                                <option value="Retail"
                                        class="form-brd-radius col-6"><@spring.message 'category.Retail'/></option>
                                <option value="ArtAndDesign"
                                        class="form-brd-radius col-6"><@spring.message 'category.ArtAndDesign'/></option>
                                <option value="CustomerService"
                                        class="form-brd-radius col-6"><@spring.message 'category.CustomerService'/></option>
                                <option value="Sales"
                                        class="form-brd-radius col-6"><@spring.message 'category.Sales'/></option>
                                <option value="Manufacturing"
                                        class="form-brd-radius col-6"><@spring.message 'category.Manufacturing'/></option>
                                <option value="MediaAndEntertainment"
                                        class="form-brd-radius col-6"><@spring.message 'category.MediaAndEntertainment'/></option>
                                <option value="Science"
                                        class="form-brd-radius col-6"><@spring.message 'category.Science'/></option>
                                <option value="Legal"
                                        class="form-brd-radius col-6"><@spring.message 'category.Legal'/></option>
                                <option value="HumanResources"
                                        class="form-brd-radius col-6"><@spring.message 'category.HumanResources'/></option>
                                <option value="Transportation"
                                        class="form-brd-radius col-6"><@spring.message 'category.Transportation'/></option>
                                <option value="Construction"
                                        class="form-brd-radius col-6"><@spring.message 'category.Construction'/></option>
                                <option value="NonProfit"
                                        class="form-brd-radius col-6"><@spring.message 'category.NonProfit'/></option>
                                <option value="Agriculture"
                                        class="form-brd-radius col-6"><@spring.message 'category.Agriculture'/></option>
                                <option value="Other"
                                        class="form-brd-radius col-6"><@spring.message 'category.Other'/></option>

                            </select>
                        </div>
                        <button type="submit" class="btn btn-info col-2 mx-2"> OK</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="row row-cols-4 row-cols-lg-3 g-2 g-lg-3">
            <#if resumes??>
                <#list resumes.content as item>
                    <div class="col">
                        <div class="p-3 border bg-light">

                            <#if (item.resumeTitle)?has_content>
                                <p>${item.resumeTitle}</p>
                            </#if>

                            <#if (item.expectedSalary)?has_content>
                                <p>${item.expectedSalary}</p>
                            </#if>

                            <#if (item.dateTime)?has_content>
                                <p>${item.dateTime}</p>
                            </#if>
                            <a href="/resume/view/by_employer/${item.id}"
                               class="btn btn-success d-flex justify-content-center"><@spring.message 'button.info'/></a>
                        </div>
                    </div>
                </#list>
            <#else>
                <@spring.message 'info.no_result'/>
            </#if>
        </div>
        <!-- Pagination controls -->
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link"
                   href="/resume/all/view?pageNumber=0&sort=${sortCriteria}&category=${category}&searchWord=${searchWord}"><@spring.message 'pagination.first'/></a>
            </li>
            <li class="page-item">
                <#if (pageNumber > 0)>
                    <a class="page-link"
                       href="/resume/all/view?pageNumber=${pageNumber - 1}&sort=${sortCriteria}&category=${category}&searchWord=${searchWord}">&laquo;
                        <@spring.message 'pagination.previous'/></a>
                </#if>
            </li>
            <#list 1..totalPages as pageNo>
                <li class="page-item">
                    <a class="page-link"
                       href="/resume/all/view?pageNumber=${pageNo - 1}&sort=${sortCriteria}&category=${category}&searchWord=${searchWord}">
                        ${pageNo}</a>
                </li>
            </#list>
            <li class="page-item">
                <#if pageNumber < (totalPages-1)>
                    <a class="page-link"
                       href="/resume/all/view?pageNumber=${pageNumber + 1}&sort=${sortCriteria}&category=${category}&searchWord=${searchWord}"><@spring.message 'pagination.next'/>
                        &raquo;</a>
                </#if>
            </li>
            <li class="page-item">
                <a class="page-link"
                   href="/resume/all/view?pageNumber=${totalPages-1}&sort=${sortCriteria}&category=${category}&searchWord=${searchWord}"><@spring.message 'pagination.last'/></a>
            </li>
        </ul>


</@main.layout>