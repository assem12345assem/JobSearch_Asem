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
        Образование:
        <#list educationList as item>
            ${item.education}
            ${item.schoolName}
            ${item.startDate}
            ${item.graduationDate}
            ------
        </#list>
        Опыт работы:
<#list workList as item>
    ${item.companyName}
    ${item.position}
    ${item.responsibilities}
    ${item.dateStart}
    ${item.dateEnd}
    ------
</#list>
        <p>Ссылки на соц. сети:</p>
        <p>telegram:</p>
        <p class="form-brd-radius col-6">${contactInfo.getTelegram()}</p>
        <p>facebook:</p>
        <p class="form-brd-radius col-6">${contactInfo.getFacebookAccount()}</p>
        <p>email:</p>
        <p class="form-brd-radius col-6">${contactInfo.getEmail()}</p>
        <p>linkedin:</p>
        <p class="form-brd-radius col-6">${contactInfo.getLinkedinAccount()}</p>
        <p>phone number:</p>
        <p class="form-brd-radius col-6">${contactInfo.getPhoneNumber()}</p>


    </div>
</@main.layout>