<#import "../layout.ftlh" as main>
<@main.layout>
    <h3 class="header-top">Все резюме</h3>
    <div class="row justify-content-around">
        <#list resumes as resume>
            <div class="card col-3 m-1">
                <h5 class="card-header">${resume.resumeTitle}</h5>
                <div class="card-body">
                    <h5 class="card-title">${resume.category}</h5>
                    <a href="/?=resumeId=${resume.id}" class="btn btn-primary">More info</a>
                </div>
            </div>
        </#list>
    </div>

</@main.layout>