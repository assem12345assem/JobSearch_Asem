<#import "../layout.ftlh" as main>
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
                        <#else> Название компании не указано
                        </#if>
                        <a href="/auth/profile/company/${company.email}" class="btn btn-success">Посмотреть профиль
                            компании</a>
                    </div>
                </div>
            </#list>
        </#if>
    </div>

    <ul class="pagination">
        <li class="page-item">
            <a class="page-link" href="/vacancy/all/companies?pageNumber=0">First</a>
        </li>
        <#if (pageNumber > 0)>
            <li class="page-item">
                <a class="page-link" href="/vacancy/all/companies?pageNumber=${pageNumber - 1}">&laquo; Previous</a>
            </li>
        </#if>
        <#list 1..totalPages as pageNo>
            <li class="page-item">
                <a class="page-link" href="/vacancy/all/companies?pageNumber=${pageNo-1}">${pageNo}</a>
            </li>
        </#list>
        <#if (pageNumber < (totalPages - 1))>
            <li class="page-item">
                <a class="page-link" href="/vacancy/all/companies?pageNumber=${pageNumber + 1}">Next &raquo;</a>
            </li>
        </#if>
        <li class="page-item">
            <a class="page-link" href="/vacancy/all/companies?pageNumber=${totalPages - 1}">Last</a>
        </li>
    </ul>

</@main.layout>