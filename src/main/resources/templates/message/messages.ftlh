<#import '../layout.ftlh' as main>
<@main.layout>
    <div class="container">
        <h2> Список откликов </h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Название вакансии</th>
                <th scope="col">Компания</th>
                <th scope="col">Соискатель</th>
                <th scope="col">Количество сообщений</th>
            </tr>
            </thead>
            <tbody>
            <#list chatList as item>
            <tr>

                    <th scope="row">${item_index + 1}</th>
                    <td>${item.vacancyName}</td>
                    <td>${item.publisher}</td>
                    <td>${item.applicant}</td>
                    <td><a href="/apply/chat/${item.jobApplicationId}">${item.newMessage} </a></td>
            </tr>
            </#list>


            </tbody>
        </table>
    </div>

</@main.layout>