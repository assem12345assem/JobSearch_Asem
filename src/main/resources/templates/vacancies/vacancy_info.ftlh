<#import '../layout.ftlh' as main>
<@main.layout>
    <div>
        <h3 class="header-top">Вакансия: </h3>
    </div>

    <div class="elem-container">
            <p> Название вакансии: </p>
            <p class="form-brd-radius col-6">${vacancy.vacancyName}</p>
            <p> Категория:</p>
            <p class="form-brd-radius col-6">${vacancy.category}</p>
            <p> Заработная плата:</p>
            <p class="form-brd-radius col-6">${vacancy.salary}</p>
            <p> Описание вакансии:</p>
            <p class="form-brd-radius col-6">${vacancy.description}</p>

            <p>Требуемый опыт:</p>
            <div class="form-row">
                <p class="mr-2 ml-5 ">от</p>
                <p class="col-sm-1 form-brd-radius"> ${vacancy.requiredExperienceMin}</p>

                <p class="mr-2 ml-5">до</p>
                <p class="col-sm-1 form-brd-radius">${vacancy.requiredExperienceMax}</p>
            </div>

    </div>
</@main.layout>

