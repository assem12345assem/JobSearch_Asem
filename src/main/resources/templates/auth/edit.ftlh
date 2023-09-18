<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>
    <div class="container-xl px-4 mt-4">
        <hr class="mt-0 mb-4">
        <div class="row">
            <div class="col-xl-4">
                <!-- Profile picture card-->
                <div class="card mb-4 mb-xl-0">
                    <div class="card-header"><@spring.message 'profile.profile'/>
                        <#if user.userType=="employer">
                        <@spring.message 'profile.of_Employer'/>
                        <#else>
                        <@spring.message 'profile.of_Applicant'/>
                        </#if>
                    </div>
                    <div class="card-body text-center">
                        <#if user.photo?has_content>
                            <img class="img-account-profile rounded-circle mb-2" src="/auth/images/${user.email}"
                                 alt="photo">
                        <#else>
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXPgCd93aiNba8lHWJYHQ1C1YwBPwcH_NUmw&usqp=CAU"
                                 alt="profile picture">
                        </#if>
                    </div>

                    <!-- Profile picture help block-->
                    <div class="small font-italic text-muted mb-4 text-center"><@spring.message 'profile.pic_size'/></div>
                    <!-- Profile picture upload button-->
                    <div class="my-3 text-center">
                        <form class="text-center my-3" action="/auth/images/upload/${user.email}" method="post"
                              enctype="multipart/form-data">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                       value="${(_csrf.token)!'csrf-token'}"/>
                            </#if>
                            <input type="file" name="file" id="image_input" required><br><br>
                            <button class="btn btn-warning" type="submit"><@spring.message 'button.upload'/></button>
                        </form>
                    </div>

                </div>
            </div>
        </div>

        <div class="col-xl-8">
            <!-- Account details card-->
            <div class="card mb-4">
                <div class="card-header ">
                    <div class="row justify-content-between">
                        <div class="col-8">
                            <@spring.message 'profile.edit_user'/>
                        </div>

                    </div>

                </div>
                <div class="card-body">
                    <form method="post" action="/auth/edit/${user.email}" id="edit_profile">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>
                        <input type="hidden" value="${user.email}" name="email"/>

                        <!-- Form Group (username)-->
                        <div class="mb-3">
                            <label class="small mb-1"
                                   for="inputUsername"><@spring.message 'profile.user_name'/> </label>
                            <input class="form-control" id="inputUsername" name="userName" type="text"
                                   placeholder="${user.userName}">
                        </div>
                        <!-- Form Row        -->
                        <div class="mb-3">
                            <label class="small mb-1" for="inputPhone"><@spring.message 'contact.phone_number'/></label>
                            <input class="form-control" name="phoneNumber" id="inputPhone" type="text"
                                   placeholder="${user.phoneNumber}">
                        </div>
                        <!-- Form Row-->
                        <#if user.userType=="applicant">
                            <#assign variable1 = applicantProfile.firstName!"<@spring.message 'profile.no.first_name'/>">
                            <#assign variable2 = applicantProfile.lastName!"<@spring.message 'profile.no.last_name'/>">
                            <#assign variable3 = applicantProfile.dateOfBirth!"<@spring.message 'profile.no.dob'/>">
                            <div class="row gx-3 mb-3">
                                <div class="col-md-6">
                                    <label class="small mb-1"
                                           for="inputFirstName"><@spring.message 'profile.first_name'/></label>
                                    <input class="form-control" name="firstName" id="inputFirstName"
                                           placeholder="${variable1}">
                                </div>
                                <div class="col-md-6">
                                    <label class="small mb-1"
                                           for="inputLastName"><@spring.message 'profile.last_name'/></label>
                                    <input class="form-control" name="lastName" id="inputLastName" type="text"
                                           placeholder="${variable2}">
                                </div>
                            </div>
                            <div class="row gx-3 mb-3">
                                <div class="col-md-6">
                                    <label class="small mb-1"
                                           for="inputBirthday"><@spring.message 'resume.dob'/></label>
                                    <input class="form-control" name="dateOfBirth" id="inputBirthday" type="date"
                                           name="birthday" value="${variable3}">
                                </div>
                            </div>
                        <#else>
                            <div class="mb-3">
                                <#assign str = employerProfile.companyName!"<@spring.message 'companies.no_company_name'/>">
                                <label class="small mb-1" for="orgName"><@spring.message 'companies.name'/></label>
                                <input class="form-control" name="companyName" id="orgName" type="text"
                                       placeholder="${str}">
                            </div>
                        </#if>

                        <div class="d-grid gap-2">
                            <button class="btn btn-warning" type="submit"><@spring.message 'button.save'/></button>
                        </div>
                    </form>
                </div>
                <div class="mb-5">

                </div>
            </div>
        </div>
    </div>
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