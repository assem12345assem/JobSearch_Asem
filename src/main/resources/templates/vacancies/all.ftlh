<#import "../layout.ftlh" as main>
<@main.layout>
    <h3 class="header-top">Все резюме</h3>
    <div class="row justify-content-around">
        <#list vacancies as vacancy>
            <div class="card col-3 m-1">
                <h5 class="card-header">${vacancy.vacancyName}</h5>
                <div class="card-body">
                    <h5 class="card-title">${vacancy.category}</h5>
                    <a href="vacancies/${vacancy.id}" class="btn btn-primary">More info</a>
                </div>
            </div>
        </#list>
    </div>

</@main.layout>