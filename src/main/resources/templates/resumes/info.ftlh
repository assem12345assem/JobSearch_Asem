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
            <p>${item.education}</p>
            <p>${item.schoolName}</p>
            <p>${item.startDate}</p>
            <p>${item.graduationDate}</p>
            ------
        </#list>
        Опыт работы:
<#list workList as item>
        <p>${item.companyName}</p>
        <p>${item.position}</p>
        <p>${item.responsibilities}</p>
        <p>${item.dateStart}</p>
        <p>${item.dateEnd}</p>
    ------
</#list>
        <p>Ссылки на соц. сети:</p>
        <#if contactInfo.getTelegram()??>
            <p>telegram:</p>
            <p class="form-brd-radius col-6">${contactInfo.getTelegram()}</p>
            <#else >

        </#if>
        <#if contactInfo.getFacebookAccount()??>
            <p>facebook:</p>
            <p class="form-brd-radius col-6">${contactInfo.getFacebookAccount()}</p>
            <#else >

        </#if>

        <#if contactInfo.getEmail()??>
            <p>email:</p>
        <p class="form-brd-radius col-6">${contactInfo.getEmail()}</p>
        <#else >

        </#if>
        <#if contactInfo.getLinkedinAccount()??>
            <p>linkedin:</p>
            <p class="form-brd-radius col-6">${contactInfo.getLinkedinAccount()}</p>
            <#else >

        </#if>
        <#if contactInfo.getPhoneNumber()??>
            <p>phone number:</p>
            <p class="form-brd-radius col-6">${contactInfo.getPhoneNumber()}</p>
            <#else >

        </#if>


    </div>
</@main.layout>