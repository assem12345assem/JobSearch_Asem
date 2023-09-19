<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>
    <div class="container">
    <div class="d-flex">
        <#if companies??>
            <#list companies.content as company>

                <div class="card mx-3" style="width: 18rem;">
                    <#if company.photo?has_content>
                        <img class="card-img-top" src="/auth/images/${company.email}" alt="photo">
                    <#else>
                        <img class="card-img-top"
                             src="https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510_640.jpg"
                             alt="company profile picture">
                    </#if>

                    <div class="card-body">
                        <h5 class="card-title">
                            <#if company.name??>
                            ${company.name}</h5>
                        <#else> <@spring.message 'companies.no_company_name'/>
                        </#if>
                        <a href="/auth/profile/company/${company.email}" class="btn btn-success"><@spring.message 'companies.more_info'/></a>
                    </div>
                </div>
            </#list>
        </#if>
    </div>

    <ul class="pagination">
        <li class="page-item">
            <a class="page-link" href="/vacancy/all/companies?pageNumber=0"><@spring.message 'pagination.first'/></a>
        </li>
        <#if (pageNumber > 0)>
            <li class="page-item">
                <a class="page-link" href="/vacancy/all/companies?pageNumber=${pageNumber - 1}">&laquo; <@spring.message 'pagination.previous'/></a>
            </li>
        </#if>
        <#list 1..totalPages as pageNo>
            <li class="page-item">
                <a class="page-link" href="/vacancy/all/companies?pageNumber=${pageNo-1}">${pageNo}</a>
            </li>
        </#list>
        <#if (pageNumber < (totalPages - 1))>
            <li class="page-item">
                <a class="page-link" href="/vacancy/all/companies?pageNumber=${pageNumber + 1}"><@spring.message 'pagination.next'/> &raquo;</a>
            </li>
        </#if>
        <li class="page-item">
            <a class="page-link" href="/vacancy/all/companies?pageNumber=${totalPages - 1}"><@spring.message 'pagination.last'/></a>
        </li>
    </ul>

</@main.layout>