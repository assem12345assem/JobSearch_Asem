<#import "layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>
<h1 class="text-center my-5 text-danger"><@spring.message 'navbar.logo'/></h1>
    <p class="mb-5"> <@spring.message 'index.intro'/></p>
    <h2 class="text-success"><@spring.message 'index.new.vacancies'/></h2>
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
    <h2 class="text-info"><@spring.message 'index.new.resumes'/></h2>
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