<#import "../layout.ftlh" as main>
<@main.layout>
    <div>
        <h3 class="header-top">Резюме: </h3>
    </div>

    <div class="elem-container">
        <p>Название резюме:</p>
        <p class="form-brd-radius col-6">${resume.resumeTitle}</p>
        <p>Категория:</p>
        <p class="form-brd-radius col-6">${resume.category}</p>

        <p>Ожидаемый уровень оклада:</p>
        <p class="form-brd-radius col-6">${resume.expectedSalary}</p>

        <p>О себе:</p>


        <p>Ссылки на соц. сети:</p>
        <p>telegram:</p>
        <p class="form-brd-radius col-6"></p>
        <p>facebook:</p>
        <p class="form-brd-radius col-6"></p>
        <p>email:</p>
        <p class="form-brd-radius col-6"></p>
        <p>linkedin:</p>
        <p class="form-brd-radius col-6"></p>
        <p>phone number:</p>
        <p class="form-brd-radius col-6"></p>


    </div>
</@main.layout>