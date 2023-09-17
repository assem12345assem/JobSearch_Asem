<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>
    <div class="container-xl px-4 mt-4">
        <div class="text-center mb-4">
            <a href="/auth/edit/${user.email}" id="edit_profile_button">
                <button class="btn btn-warning"><@spring.message 'profile.edit'/></button>
            </a>
        </div>
        <hr class="mt-0 mb-4">
        <div class="row">
            <!-- Profile picture card-->
            <div class="col-xl-4">
                <div class="card mb-4 mb-xl-0">
                    <div class="card-header"><@spring.message 'profile.profile'/>
                        <#if user.userType=="employer">
                        <@spring.message 'profile.of_Employer'/>
                        <#else>
                        <@spring.message 'profile.of_Applicant'/>
                        </#if>                    </div>
                    <div class="card-body text-center">
                        <#if user.photo?has_content>
                        <img class="img-account-profile rounded-circle mb-2" src="/auth/images/${user.email}" alt="photo">
                        <#else>
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXPgCd93aiNba8lHWJYHQ1C1YwBPwcH_NUmw&usqp=CAU" alt="profile picture">
                        </#if>
                    </div>
                </div>
            </div>
            <!-- add resume button-->
            <div class="col-xl-3 d-flex align-items-center justify-content-center">
                <div class="card-body text-center d-flex ">
                    <div class="mx-3">
                        <#if user.userType=="applicant">
                            <div class="text-center">
                                <#if _csrf??>
                                <form action="/resume/add" method="post">
                                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                            value="${(_csrf.token)!'--no-token--'}"/>
                                    <button type="submit" class="btn btn-warning p-3">+<br> <@spring.message 'profile.create'/> <br><@spring.message 'profile.resume_word'/></button>
                                </form>
                                </#if>

                            </div>
                        <#else>
                            <div class="text-center">
                                <a href="/vacancy/add" class="btn btn-warning p-3">
                                    +<br> <@spring.message 'profile.create'/> <br><@spring.message 'profile.vacancy_word'/>
                                </a>
                            </div>
                        </#if>
                    </div>
                    <div>
                        <a href="/apply/all/messages" class="btn btn-warning px-3 pb-4 pt-3">
                            <@spring.message 'profile.messages'/> <span class="badge bg-danger pb-2"> ${new_message_number} </span>
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-8">
            <!-- Account details card-->
            <div class="card mb-4">
                <div class="card-header ">
                    <div class="col-8">
                        <@spring.message 'profile.user_info'/>
                    </div>
                </div>
                <!-- Form Group (username)-->
                <div class="card-body">
                    <div>
                        <div class="mb-3">
                            <label class="small mb-1" for="inputUsername"><@spring.message 'profile.user_name'/> </label>
                            <p class="form-control" id="inputUsername" type="text">
                                <#if (user.userName)??> ${user.userName} <#else> <@spring.message 'no.info'/> </#if>
                            </p>
                        </div>
                        <!-- Form Row        -->
                        <div class="mb-3">
                            <label class="small mb-1" for="inputPhone"><@spring.message 'contact.phone_number'/></label>
                            <p class="form-control" id="inputPhone" type="text">
                                <#if (user.phoneNumber)??>${user.phoneNumber}<#else >  <@spring.message 'no.info'/></#if>
                                </p>
                        </div>
                        <!-- Form Row-->
                        <#if user.userType=="applicant">
                            <#assign variable1 = (applicantProfile.firstName)!"<@spring.message 'profile.no.first_name'/>">
                            <#assign variable2 = (applicantProfile.lastName)!"<@spring.message 'profile.no.last_name'/>">
                            <#assign variable3 = (applicantProfile.dateOfBirth)!"<@spring.message 'profile.no.dob'/>">
                            <div class="row gx-3 mb-3">
                                <div class="col-md-6">
                                    <label class="small mb-1" for="inputFirstName"><@spring.message 'profile.first_name'/></label>
                                    <p class="form-control" id="inputFirstName" type="text">${variable1}</p>
                                </div>
                                <div class="col-md-6">
                                    <label class="small mb-1" for="inputLastName"><@spring.message 'profile.last_name'/></label>
                                    <p class="form-control" id="inputLastName" type="text">${variable2}</p>
                                </div>
                            </div>
                            <div class="row gx-3 mb-3">
                                <div class="col-md-6">
                                    <label class="small mb-1" for="inputBirthday"><@spring.message 'resume.dob'/></label>
                                    <p class="form-control" id="inputBirthday">${variable3}</p>
                                </div>
                            </div>
                        <#else>
                            <div class="mb-3">
                                <#assign str = employerProfile.companyName!"<@spring.message 'companies.no_company_name'/>">
                                <label class="small mb-1" for="orgName"><@spring.message 'companies.name'/></label>
                                <p class="form-control" id="orgName" type="text">${str}</p>
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
        <!-- Account resumes/vacancies-->

    </div>
<div class="container">
    <div class="row">
        <#if user.userType=="employer">
            <h3><@spring.message 'profile.employer_vacancies'/></h3>
        <#else>
            <h3><@spring.message 'profile.applicant_resumes'/></h3>
        </#if>
        <#if (myList)??>
            <#list myList.content as item>
                <#if item?index == 0 || item?index % 3 == 0>
                    <div class="row my-3">
                </#if>
                <div class="card col mx-1">
                    <div class="card-body">
                        <h5 class="card-title"><#if (item.title)??> ${item.title}<#else> <@spring.message 'no.title'/></#if></h5>
                        <p class="card-text"><#if (item.dateTime)??>${item.dateTime}<#else ><@spring.message 'no.date'/></#if></p>
                        <p class="card-text"><#if (item.isActive)??>
                                <#if item.isActive == "true"><@spring.message 'instruction.active'/> <#else> <@spring.message 'instruction.not_active'/></#if>
                            </#if></p>
                        <div class="d-flex">
                            <#if user.userType=="employer">
                                <a href="/vacancy/edit/${item.id}" class="btn btn-primary me-3"><@spring.message 'button.edit'/></a>
                                <a href="/vacancy/edit/datefix/${item.id}" class="btn btn-success"><@spring.message 'button.date_update'/></a>
                            <#else>
                                <a href="/resume/edit/${item.id}" class="btn btn-primary me-3"><@spring.message 'button.edit'/></a>
                                <a href="/resume/edit/datefix/${item.id}" class="btn btn-success"><@spring.message 'button.date_update'/></a>
                            </#if>
                        </div>
                        <div class="text-center my-2">
                            <#if user.userType=="employer">
                                <a href="/vacancy/edit/delete/${item.id}" class="btn btn-danger"><@spring.message 'button.delete'/></a>
                            <#else>
                                <a href="/resume/edit/delete/${item.id}" class="btn btn-danger"><@spring.message 'button.delete'/></a>
                            </#if>
                        </div>
                    </div>
                </div>
                <#if item?index % 3 == 2 || item?is_last>
                    </div>
                </#if>
            </#list>
        <#else >
            <@spring.message 'profile.not_created_yet'/>
        </#if>
    </div>

    <!-- Pagination controls -->
    <ul class="pagination">
        <li class="page-item">
            <a class="page-link"
               href="/auth/profile/${user.email}?pageNumber=0"><@spring.message 'pagination.first'/></a>
        </li>
        <li class="page-item">
            <#if (pageNumber > 0)>
                <a class="page-link"
                   href="/auth/profile/${user.email}?pageNumber=${pageNumber - 1}">&laquo;
                    <@spring.message 'pagination.previous'/></a>
            </#if>
        </li>
        <#if (totalPages > 0)>
        <#list 1..totalPages as pageNo>
            <li class="page-item">
                <a class="page-link"
                   href="/auth/profile/${user.email}?pageNumber=${pageNo - 1}">
                    ${pageNo}</a>
            </li>
        </#list>
        <li class="page-item">
            <#if pageNumber < (totalPages-1)>
                <a class="page-link"
                   href="/auth/profile/${user.email}?pageNumber=${pageNumber + 1}"><@spring.message 'pagination.next'/>
                    &raquo;</a>
            </#if>
        </li>
        <li class="page-item">
            <a class="page-link"
               href="/auth/profile/${user.email}?pageNumber=${totalPages-1}"><@spring.message 'pagination.last'/></a>
        </li>
        </#if>
    </ul>
</div>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            let editProfileButton = document.getElementById('edit_profile_button');
            if (editProfileButton) {
                editProfileButton.addEventListener('click', async (e) => {
                    e.preventDefault();

                    try {
                        let user = await readUser();
                        if (user) {
                            window.location.href = baseUrl + '/auth/edit/' + user.username;;
                        }
                    } catch (e) {
                        console.error(e);
                    }
                });
            }
        });

    </script>
    <style>
        body {
            margin-top: 20px;
            background-color: #f2f6fc;
            color: #69707a;
        }

        .img-account-profile {
            height: 10rem;
        }

        .rounded-circle {
            border-radius: 50% !important;
        }

        .card {
            box-shadow: 0 0.15rem 1.75rem 0 rgb(33 40 50 / 15%);
        }

        .card .card-header {
            font-weight: 500;
        }

        .card-header:first-child {
            border-radius: 0.35rem 0.35rem 0 0;
        }

        .card-header {
            padding: 1rem 1.35rem;
            margin-bottom: 0;
            background-color: rgba(33, 40, 50, 0.03);
            border-bottom: 1px solid rgba(33, 40, 50, 0.125);
        }

        .form-control, .dataTable-input {
            display: block;
            width: 100%;
            padding: 0.875rem 1.125rem;
            font-size: 0.875rem;
            font-weight: 400;
            line-height: 1;
            color: #69707a;
            background-color: #fff;
            background-clip: padding-box;
            border: 1px solid #c5ccd6;
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
            border-radius: 0.35rem;
            transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
        }

    </style>
</@main.layout>