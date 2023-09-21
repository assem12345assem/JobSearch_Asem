<#import "../layout.ftlh" as main>
<#import '/spring.ftl' as spring>

<@main.layout>

    <div class="container">
        <div class="main-body">
            <h2 class="text-primary fs-2 text-center"> <@spring.message 'resume.user_resume'/></h2>
            <#if user.email == viewer.email>
                    <div class="col-sm-12 text-center">
                        <a class="btn btn-info mt-3 text-dark px-4" target="__blank" href="/resume/edit/${resume.id}"><@spring.message 'button.edit'/></a>
                    </div>
            </#if>

            <!-- /Breadcrumb -->

            <div class="row gutters-sm mt-5">
                <div class="col-md-4 mb-3">
                    <div class="card">
                        <div class="card-body">
                            <div class="d-flex flex-column align-items-center text-center">
                                <#if user.photo?has_content>
                                    <img class="img-account-profile rounded-circle mb-2" src="/auth/images/${user.email}" alt="photo" width="250" height="auto">
                                <#else>
                                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXPgCd93aiNba8lHWJYHQ1C1YwBPwcH_NUmw&usqp=CAU" alt="photo">
                                </#if>                        <div class="mt-3">
                                    <h4><#if (resume.profile.firstName)??>${resume.profile.firstName}</#if>  <#if (resume.profile.lastName)??>${resume.profile.lastName}</#if></h4>
                                    <p class="text-secondary mb-1"><@spring.message 'resume.dob'/>:</p>
                                    <p class="text-muted font-size-sm"><#if (resume.profile.dateOfBirth)??> ${resume.profile.dateOfBirth}</#if></p>
                                    <#if user.email == viewer.email>
                                        <a class="btn btn-success"
                                           href="/auth/edit/${user.email}"><@spring.message 'profile.edit'/></a>

                                    </#if>
                                    <#if viewer.userType == "employer">
                                        <a class="btn btn-success"
                                           href="/apply/offerjob/${resume.id}"><@spring.message 'vacancy.offer'/></a>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card mt-3">
                        <ul class="list-group list-group-flush">
                            <#if resume.contact.telegram??>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                    <h6 class="mb-0"><i class="fa-brands fa-telegram fs-3"></i> Telegram:</h6>
                                    <span class="text-secondary">${resume.contact.telegram}</span>
                                </li>
                            </#if>
                            <#if resume.contact.email??>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                    <h6 class="mb-0"><i class="fa-solid fa-envelope fs-3"></i> Email:</h6>
                                    <span class="text-secondary">${resume.contact.email}</span>
                                </li>
                            </#if>
                            <#if resume.contact.facebook??>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                    <h6 class="mb-0"><i class="fa-brands fa-square-facebook fs-3"></i> Facebook:</h6>
                                    <span class="text-secondary">${resume.contact.facebook}</span>
                                </li>
                            </#if>
                            <#if resume.contact.linkedIn??>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                    <h6 class="mb-0"><i class="fa-brands fa-linkedin-in fs-3"></i> LinkedIn:</h6>
                                    <span class="text-secondary">${resume.contact.linkedIn}</span>
                                </li>
                            </#if>
                            <#if resume.contact.phoneNumber??>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                    <h6 class="mb-0"><i class="fa-solid fa-phone fs-3"></i> <@spring.message 'contact.phone_number'/>:</h6>
                                    <span class="text-secondary">${resume.contact.phoneNumber}</span>
                                </li>
                            </#if>
                        </ul>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="card mb-3">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0"><@spring.message 'resume.title'/></h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <#assign var2 = resume.resumeTitle!"${springMacroRequestContext.getMessage('no.info')}
                                    ">
                                    ${var2}
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0"><@spring.message 'category.category'/></h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <#assign var1 = resume.category! "${springMacroRequestContext.getMessage('category.no.info')}">
                                    ${var1}
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0"><@spring.message 'resume.expected_salary'/></h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <#assign var3 = (resume.expectedSalary)!"${springMacroRequestContext.getMessage('no.info')}">
                                    ${var3}
                                </div>
                            </div>
                            <hr>
                        </div>
                    </div>

                    <div class="row gutters-sm">
                        <div class="col-sm-6 mb-3">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h6 class="d-flex align-items-center mb-3"><@spring.message 'resume.work_experience'/></h6>

                                    <#list resume.workList as item>
                                        <button class="btn btn-info rounded-circle px-3 mb-2">${item_index + 1}</button><br>


                                        <small><@spring.message 'companies.name'/></small>
                                        <div>
                                            ${item.companyName! "${springMacroRequestContext.getMessage('no.info')}
                                            "}
                                        </div>
                                        <small><@spring.message 'resume.position'/></small>
                                        <div>
                                            ${item.position! "${springMacroRequestContext.getMessage('no.info')}"}
                                        </div>
                                        <small><@spring.message 'resume.responsibilities'/>:</small>
                                        <div>
                                            ${item.responsibilities! "${springMacroRequestContext.getMessage('no.info')}"}
                                        </div>
                                        <small><@spring.message 'resume.start_work'/>: </small>
                                        <div>
                                            ${item.dateStart! "${springMacroRequestContext.getMessage('no.info')}"}
                                        </div>
                                        <small><@spring.message 'resume.end_work'/>: </small>
                                        <div>
                                            ${item.dateEnd! "${springMacroRequestContext.getMessage('no.info')}"}
                                        </div>
                                        <hr>
                                    </#list>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 mb-3">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h6 class="d-flex align-items-center mb-3"><@spring.message 'resume.education'/></h6>
                                    <#list resume.eduList as item>
                                        <button class="btn btn-info rounded-circle px-3 mb-2">${item_index + 1}</button><br>

                                        <small><@spring.message 'resume.education'/></small>
                                        <div>
                                            ${item.education! "${springMacroRequestContext.getMessage('no.info')}"}
                                        </div>
                                        <small><@spring.message 'resume.place_of_study'/>:</small>
                                        <div>
                                            ${item.schoolName! "${springMacroRequestContext.getMessage('no.info')}"}
                                        </div>
                                        <small><@spring.message 'resume.education_start'/>: </small>
                                        <div>
                                            ${item.startDate! "${springMacroRequestContext.getMessage('no.info')}"}
                                        </div>
                                        <small><@spring.message 'resume.education_end'/>: </small>
                                        <div>
                                            ${item.graduationDate! "${springMacroRequestContext.getMessage('no.info')}"}
                                        </div>
                                        <hr>
                                    </#list>


                                </div>
                            </div>
                        </div>
                    </div>



                </div>
            </div>

        </div>
    </div>

    <style>
        body{
            margin-top:20px;
            color: #1a202c;
            text-align: left;
            background-color: #e2e8f0;
        }
        .main-body {
            padding: 15px;
        }
        .card {
            box-shadow: 0 1px 3px 0 rgba(0,0,0,.1), 0 1px 2px 0 rgba(0,0,0,.06);
        }
        .card {
            position: relative;
            display: flex;
            flex-direction: column;
            min-width: 0;
            word-wrap: break-word;
            background-color: #fff;
            background-clip: border-box;
            border: 0 solid rgba(0,0,0,.125);
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

        .gutters-sm>.col, .gutters-sm>[class*=col-] {
            padding-right: 8px;
            padding-left: 8px;
        }
        .mb-3, .my-3 {
            margin-bottom: 1rem!important;
        }

        .bg-gray-300 {
            background-color: #e2e8f0;
        }
        .h-100 {
            height: 100%!important;
        }
        .shadow-none {
            box-shadow: none!important;
        }
    </style>


</@main.layout>