<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="elem-container">
    <h1 class="header-top">Личный кабинет
    <#if ${user.type}=='employer'>
        Работодателя
        <#else>
            Соискателя
    </#if>
</h1>
    </div>
    <div class="d-flex elem-container row justify-content-around">
        <div class="align-self-start mb-3">
<#--            ${user.photo}-->
photo
        </div>
        <div class="align-self-start mb-3">
            <div class="d-flex flex-column user-info justify-content-around">
                <#if ${user.type}=='employer'>
                    Company name
                    <#else>
                        ${user.firstName} ${user.lastName}
                        55 лет
                </#if>
                <button class="lined-green-edit">Редактировать</button>
            </div>
        </div>
        <div class="lined-red align-self-start mb-3">
            + Создать резюме
        </div>
        <div class="solid-white align-self-start mb-3">
            Отклики
        </div>
    </div>
    <div class="elem-container">
        <h2 class="section-header">

                    Резюме:
        </h2>
        <div class="d-flex justify-content-around">
            <div class="card">
                <div class="card-body card-add-border">
                    ${resume.title} ${vacancy.title}
                </div>
                <div class="d-flex justify-content-center">
                    <button class="lined-blue">Обновить</button>
                    <button class="lined-green">Ред.</button>
                </div>
            </div>
            <div class="card">
                <div class="card-body card-add-border">
                    ${resume.title} ${vacancy.title}
                </div>
                <div class="d-flex justify-content-center">
                    <button class="lined-blue">Обновить</button>
                    <button class="lined-green">Ред.</button>
                </div>
            </div>
            <div class="card">
                <div class="card-body card-add-border">
                    ${resume.title} ${vacancy.title}
                    <p>Дата обновления:</p>
                    <p>${date}</p>
                </div>
                <div class="d-flex justify-content-center">
                    <button class="lined-blue">Обновить</button>
                    <button class="lined-green">Ред.</button>
                </div>
            </div>
        </div>
    </div>

</@main.layout>