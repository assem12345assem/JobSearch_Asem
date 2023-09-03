<#import "layout.ftlh" as main>
<@main.layout>
<h1 class="text-center my-5 text-danger">Job Search</h1>
    <p class="mb-5"> Перейдите по ссылкам на панели навигации для просмотра всего списка.</p>

    <h2 class="text-success">Новые вакансии:</h2>
    <div class="mb-5 d-flex">
        <#list vacancies as vacancy>
            <div class="card mx-3" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title"><#if (vacancy.title)??>${vacancy.title}</#if></h5>
                    <p class="card-text"><#if (vacancy.dateTime)??>${vacancy.dateTime}</#if></p>
                </div>
            </div>
        </#list>
    </div>
    <h2 class="text-info">Новые резюме:</h2>
    <div class="mb-5 d-flex">

    <#list resumes as resume>
    <div class="card mx-3" style="width: 18rem;">
        <div class="card-body">
            <h5 class="card-title"><#if (resume.title)??>${resume.title}</#if></h5>
            <p class="card-text"><#if (resume.dateTime)??>${resume.dateTime}</#if></p>
        </div>
    </div>

</#list>
    </div>
</@main.layout>