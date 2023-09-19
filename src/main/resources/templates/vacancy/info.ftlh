<#import '../layout.ftlh' as main>
<#import "/spring.ftl" as spring>

<@main.layout>
    <div class="container">
        <div class="main-body">
            <h2 class="text-primary fs-2 text-center"> <@spring.message 'vacancy.user_vacancy'/></h2>
            <div class="row text-center">
                <#if user.email == viewer.email>
                    <div class="col-sm-12">
                        <a class="btn btn-info mt-3 text-dark px-4" target="__blank" href="/vacancy/edit/${vacancy.id}"><@spring.message 'button.edit'/></a>
                    </div>
                </#if>
            </div>
            <div class="row gutters-sm mt-5">
                <div class="col-md-4 mb-3">
                    <div class="card">
                        <div class="card-body">
                            <div class="d-flex flex-column align-items-center text-center">
                                <#if user.photo?has_content>
                                    <img class="img-account-profile rounded-circle mb-2"
                                         src="/auth/images/${user.email}" alt="photo" width="250"
                                         height="auto">
                                <#else>
                                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXPgCd93aiNba8lHWJYHQ1C1YwBPwcH_NUmw&usqp=CAU"
                                         alt="profile picture">
                                </#if>
                                <div class="mt-3">
                                    <#assign v=vacancy.profile.companyName!"${springMacroRequestContext.getMessage('companies.no_company_name')}">
                                    <h4>${v}</h4>
                                    <#if user.email == viewer.email>
                                        <a class="btn btn-success"
                                           href="/auth/edit/${user.email}"><@spring.message 'profile.edit'/></a>
                                    <#else>
                                        <#if viewer.userType=="applicant">
                                            <a class="btn btn-success"
                                               href="/apply/forjob/${vacancy.id}"><@spring.message 'vacancy.apply.button'/></a>
                                        </#if>
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
                                    <h6 class="mb-0"><@spring.message 'vacancy.name'/></h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <#if (vacancy.vacancyName)??>
                                        <p class="form-control"> ${vacancy.vacancyName}</p>
                                    <#else>
                                        <p class="form-control"> <@spring.message 'no.info'/></p>

                                    </#if>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0"><@spring.message 'category.category'/></h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <p class="form-control">
                                        <#assign var1=vacancy.category! "${springMacroRequestContext.getMessage('category.no.info')}"> ${var1}
                                    </p>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0"><@spring.message 'vacancy.salary'/></h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <#assign var3=vacancy.salary!"${springMacroRequestContext.getMessage('no.info')}">
                                    <p class="form-control">${var3}</p>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0"><@spring.message 'vacancy.description'/></h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <p class="form-control p-1">
                                        <#assign var4=vacancy.description!"${springMacroRequestContext.getMessage('no.info')}"> ${var4}
                                    </p>

                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0"><@spring.message 'vacancy.required.experience'/>:</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <p class="form-control">
                                        <#assign var5=vacancy.requiredExperienceMin!"${springMacroRequestContext.getMessage('no.info')}">
                                        <#assign var6=vacancy.requiredExperienceMax!"${springMacroRequestContext.getMessage('no.info')}">
                                        ${var5} - ${var6} <@spring.message 'vacancy.years'/>
                                    </p>

                                </div>
                            </div>
                            <hr>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>



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

        .gutters-sm>.col,
        .gutters-sm>[class*=col-] {
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