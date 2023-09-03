<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="elem-container">
        <h1 class="header-top">Личный кабинет
            <#if user.userType=="employer">
                Работодателя
            <#else>
                Соискателя
            </#if>
        </h1>
    </div>
    <div class="d-flex elem-container row justify-content-around">
        <div class="align-self-start mb-3">
            <img src="/images/${user.id}" alt="photo">
        </div>
        <div class="align-self-start mb-3">
            <div class="d-flex flex-column user-info justify-content-around">
                <#if user.userType=="employer">
                    <#assign str = profile.companyName!"Наименование компании не указано">
                    ${str}

                <#else>
                    <#assign variable1 = profile.getFirstName!"">
                    <#assign variable2 = profile.getLastName!"">

                    <#assign combinedValue = variable1! + variable2!>
                    <#assign result = combinedValue!"Пользователь не указал своего имени и фамилии">

                    ${result}

                    55 лет
                </#if>
                <a class="btn lined-green-edit mt-4" href="/users/profile/edit/${user.id}">Редактировать</a>
            </div>
        </div>
        <div class="lined-red align-self-start mb-3">
            <#if user.userType=="employer">
                <a href="/vacancies/add/${user.id}">+ Создать вакансию</a>
            <#else>
                <a href="/resumes/add/${user.id}">+ Создать резюме</a>

            </#if>
        </div>
        <div class="solid-white align-self-start mb-3">
            Отклики
        </div>
    </div>
    <div class="elem-container">
        <h2 class="section-header">
            <#if user.userType=="employer">
                Вакансии:
            <#else>
                Резюме:

            </#if>
        </h2>
        <div class="d-flex justify-content-around">
            <#if items??>
                <#list items as item>
                    <div class="card">
                        <div class="card-body card-add-border">
                            <#if item.resumeTitle??>
                                ${item.resumeTitle}
                                <#else>
                                ${item.vacancyName}
                            </#if>

                        </div>
                        <div class="d-flex justify-content-center">
                            <button class="lined-blue">Обновить</button>
                            <button class="lined-green">Ред.</button>
                        </div>
                    </div>

                </#list>
            <#else>
                Пользователь ещё не создал <#if user.userType=="employer">
                ни одной вакансии            <#else>
                ни одного резюме
            </#if>
            </#if>

        </div>
    </div>

</@main.layout>