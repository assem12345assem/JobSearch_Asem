<#import '../layout.ftlh' as main>
<@main.layout>
    <div class="container">
        <div class="main-body">
            <#if user.userType == "applicant">
                <h2 class="text-primary fs-2 text-center"> Отклик на вакансию</h2>
            <#else >
                <h2 class="text-primary fs-2 text-center"> Предложение вакансии от работодателя</h2>

            </#if>

            <div class="row gutters-sm mt-5">
                <div class="col-md-4 mb-3">
                    <div class="card">
                        <div class="card-body">
                            <div class="d-flex flex-column align-items-center text-center">
                                <#if user.photo?has_content>
                                    <img class="img-account-profile rounded-circle mb-2"
                                         src="/auth/images/${user.email}" alt="photo" width="250" height="auto">
                                <#else>
                                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXPgCd93aiNba8lHWJYHQ1C1YwBPwcH_NUmw&usqp=CAU"
                                         alt="profile picture">
                                </#if>
                                <div class="mt-3">

                                    <#if user.userType == "applicant">
                                        <#assign v=vacancy.profile.companyName!"Название компании не указано">
                                        <h4>${v}</h4>
                                    <#else >
                                        <h4><#if (resume.profile.firstName)??>${resume.profile.firstName}</#if>  <#if (resume.profile.lastName)??>${resume.profile.lastName}</#if></h4>
                                        <p class="text-secondary mb-1">Дата рождения:</p>
                                        <p class="text-muted font-size-sm"><#if (resume.profile.dateOfBirth)??> ${resume.profile.dateOfBirth}</#if></p>

                                    </#if>

                                </div>
                            </div>
                        </div>
                    </div>


                </div>


                <div class="col-md-8">
                    <div class="card mb-3">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-sm-3">
                                    <#if user.userType == "applicant">
                                        <h6 class="mb-0">Название вакансии:</h6>
                                    <#else >
                                        <h6 class="mb-0">Название резюме:</h6>

                                    </#if>
                                </div>


                                <div class="col-sm-9 text-secondary">
                                    <#if user.userType == "applicant">
                                        <#if (vacancy.vacancyName)??>
                                            <p class="form-control"> ${vacancy.vacancyName}</p>
                                        <#else>
                                            <p class="form-control"> Не указано</p>

                                        </#if>
                                    <#else >
                                        <#if (resume.resumeTitle)??>
                                            <p class="form-control"> ${resume.resumeTitle}</p>
                                        <#else>
                                            <p class="form-control"> Не указано</p>

                                        </#if>

                                    </#if>

                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="card mb-3">
                        <div class="card-body">
                            <div class="row">
                                <#if (messages?size > 0)>
                                    <#assign firstMessage = messages[0]>
                                    <div class="col-sm-3">
                                        <h6 class="mb-0">${firstMessage.author}</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <#if user.userType == "applicant">
                                            <a href="${firstMessage.message}">ссылка на резюме</a>
                                        <#else >
                                            <a href="${firstMessage.message}">ссылка на вакансию</a>

                                        </#if>
                                    </div>

                                    <div class="message-container">
                                        <#list 1..(messages?size - 1) as index>
                                            <#assign message = messages[index]>
                                            <div class="row message-card">
                                                <div class="col-sm-3 message-header">
                                                    <h6 class="mb-0">${message.author}</h6>
                                                </div>
                                                <div class="col-sm-9 text-secondary">
                                                    <p class="form-control">${message.message}</p>
                                                </div>
                                                <footer class="message-footer">
                                                    ${message.createTime}
                                                </footer>
                                            </div>
                                        </#list>
                                    </div>
                                </#if>
                            </div>
                            <div id="message_block">
                                <form id="message_form" action="/apply/all/send_message" type="post">
                                    <#if _csrf??>
                                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                               value="${(_csrf.token)!'csrf-token'}"/>
                                    </#if>
                                    <small>Сообщение</small>
                                    <input type="hidden" name="jobApplicationId"
                                           value="${firstMessage.jobApplicationId}">
                                    <input type="text" name="message">
                                    <button type="submit" class="btn btn-success">Отправить</button>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>


            </div>

        </div>


    </div>
    <script>
        document.getElementById('message_form').addEventListener('submit', async function (e) {
            e.preventDefault();

            const formData = new FormData(this);

            const formDataObject = {};
            formData.forEach((value, key) => {
                formDataObject[key] = value;

            });

            const jsonData = JSON.stringify(formDataObject);

            try {
                const response = await fetch(baseUrl + '/apply/all/send_message', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: jsonData,
                });

                if (response.ok) {
                    window.location.reload();

                } else {
                    console.error('Request failed with status:', response.status);
                }
            } catch (error) {
                console.error('Error:', error);
            }
        });
    </script>


    <style>
        body {
            margin-top: 20px;
            color: #1a202c;
            text-align: left;
            background-color: #e2e8f0;
        }


        .main-body {
            padding: 15px;
        }

        .card {
            box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .1), 0 1px 2px 0 rgba(0, 0, 0, .06);
        }

        .card {
            position: relative;
            display: flex;
            flex-direction: column;
            min-width: 0;
            word-wrap: break-word;
            background-color: #fff;
            background-clip: border-box;
            border: 0 solid rgba(0, 0, 0, .125);
            border-radius: .25rem;
        }

        .card-body {
            flex: 1 1 auto;
            min-height: 1px;
            padding: 1rem;
        }

        .gutters-sm {
            margin-right: -8px;
            margin-left: -8px;
        }

        .gutters-sm > .col,
        .gutters-sm > [class*=col-] {
            padding-right: 8px;
            padding-left: 8px;
        }

        .mb-3,
        .my-3 {
            margin-bottom: 1rem !important;
        }

        .bg-gray-300 {
            background-color: #e2e8f0;
        }

        .h-100 {
            height: 100% !important;
        }

        .shadow-none {
            box-shadow: none !important;
        }
    </style>

</@main.layout>