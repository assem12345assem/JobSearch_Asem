<#import '../layout.ftlh' as main>
<#import '/spring.ftl' as spring>

<@main.layout>
    <div class="container">
        <h2> <@spring.message 'applications.list'/> </h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><@spring.message 'applications.vacancy.name'/></th>
                <th scope="col"><@spring.message 'companies.company'/></th>
                <th scope="col"><@spring.message 'profile.type.applicant'/></th>
                <th scope="col"><@spring.message 'applications.message_number'/></th>
            </tr>
            </thead>
            <tbody>
            <#list chatList as item>
            <tr>

                    <th scope="row">${item_index + 1}</th>
                    <td>${item.vacancyName}</td>
                    <td>${item.publisher}</td>
                    <td>${item.applicant}</td>
                    <td><a href="/apply/all/chat/${item.jobApplicationId}">${item.newMessage} </a></td>
            </tr>
            </#list>


            </tbody>
        </table>
    </div>

</@main.layout>