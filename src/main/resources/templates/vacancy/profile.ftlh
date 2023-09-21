<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<link rel="stylesheet" src="/static/css/profile.css">

<@main.layout>
    <div class="container-xl px-4 mt-4">

    <hr class="mt-0 mb-4">
    <div class="row">
        <!-- Profile picture card-->
        <div class="col-xl-4">
            <div class="card mb-4 mb-xl-0">
                <div class="card-header"><@spring.message 'companies.profile'/>
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
                        <@spring.message 'companies.info'/>
                    </div>
                </div>
                <!-- Form Group (username)-->
                <div class="card-body">
                    <div>
                        <!-- Form Row-->

                        <div class="mb-3">
                            <#assign str = company.name!"${springMacroRequestContext.getMessage('companies.no_company_name')}">
                            <label class="small mb-1" for="orgName"><@spring.message 'companies.name'/></label>
                            <p class="form-control" id="orgName" type="text">${str}</p>
                        </div>
                        <!-- Form Row        -->
                        <div class="mb-3">
                            <label class="small mb-1" for="inputPhone"><@spring.message 'contact.phone_number'/></label>
                            <p class="form-control" id="inputPhone" type="text">
                                <#if (company.phoneNumber)??>${company.phoneNumber}<#else >  <@spring.message 'no.info'/></#if>
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
            <h3><@spring.message 'companies.vacancies'/> </h3>

            <#if (myList)??>
                <#list myList.content as item>
                    <#if item?index == 0 || item?index % 3 == 0>
                        <div class="row my-3">
                    </#if>
                    <div class="card col mx-1">
                        <div class="card-body">
                            <h5 class="card-title"><#if (item.title)??> ${item.title}<#else> <@spring.message 'no.title'/></#if></h5>
                            <p class="card-text"><#if (item.dateTime)??>${item.dateTime}<#else ><@spring.message 'no.date'/></#if></p>
                            <div class="d-flex">
                                <a href="/vacancy/${item.id}" class="btn btn-primary me-3"><@spring.message 'button.view'/></a>

                            </div>

                        </div>
                    </div>
                    <#if item?index % 3 == 2 || item?is_last>
                        </div>
                    </#if>
                </#list>
            <#else >
                <@spring.message 'vacancies.none.yet'/>
            </#if>
        </div>

        <!-- Pagination controls -->
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link"
                   href="/auth/profile/company/${company.email}?pageNumber=0"><@spring.message 'pagination.first'/></a>
            </li>
            <li class="page-item">
                <#if (pageNumber > 0)>
                    <a class="page-link"
                       href="/auth/profile/company/${company.email}?pageNumber=${pageNumber - 1}">&laquo;
                        <@spring.message 'pagination.previous'/></a>
                </#if>
            </li>
            <#list 1..totalPages as pageNo>
                <li class="page-item">
                    <a class="page-link"
                       href="/auth/profile/company/${company.email}?pageNumber=${pageNo - 1}">
                        ${pageNo}</a>
                </li>
            </#list>
            <li class="page-item">
                <#if pageNumber < (totalPages-1)>
                    <a class="page-link"
                       href="/auth/profile/company/${company.email}?pageNumber=${pageNumber + 1}"><@spring.message 'pagination.next'/>
                        &raquo;</a>
                </#if>
            </li>
            <li class="page-item">
                <a class="page-link"
                   href="/auth/profile/company/${company.email}?pageNumber=${totalPages-1}"><@spring.message 'pagination.last'/></a>
            </li>
        </ul>
    </div>



</@main.layout>