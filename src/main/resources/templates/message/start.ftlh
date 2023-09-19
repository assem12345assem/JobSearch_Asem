<#import '../layout.ftlh' as main>
<#import '/spring.ftl' as spring>

<@main.layout>
    <div class="container">
        <div class="main-body">
            <h2 class="text-primary fs-2 text-center"> <@spring.message 'vacancy.apply'/>:</h2>



            <div class="row gutters-sm mt-5">
                <div class="col-md-4 mb-3">
                    <div class="card">
                        <div class="card-body">
                            <div class="d-flex flex-column align-items-center text-center">
                                <#if user.photo?has_content>
                                    <img class="img-account-profile rounded-circle mb-2"
                                         src="/auth/images/${user.email}" alt="photo" width="250" height="auto">
                                <#else>
                                    <img
                                            src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXPgCd93aiNba8lHWJYHQ1C1YwBPwcH_NUmw&usqp=CAU" alt="profile picture">
                                </#if>
                                <div class="mt-3">
                                    <#assign v=vacancy.profile.companyName!"${springMacroRequestContext.getMessage('companies.no_company_name')}">
                                    <h4>${v}</h4>


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

                        </div>
                    </div>
                    <div class="card mb-3">
                        <div class="card-body">
                            <div class="card-title">
<h3><@spring.message 'applications.start'/></h3>
                            </div>
                            <form action="/apply/forjob/${vacancy.id}" method="post">
                                <#if _csrf??>
                                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                           value="${(_csrf.token)!'csrf-token'}"/>
                                </#if>
                                <input type="hidden" name="vacancyId" value="${vacancy.id}">
                                <small><@spring.message 'applications.message_text'/></small>
                                <input class="form-control mb-2" type="text" placeholder="<@spring.message 'applications.message_text'/>"
                                       name="messageText">
                                <p class="mb-3"><@spring.message 'applications.instruction'/></p>


                                <#if (resumes)?? >
                                    <select class="form-select border-dark"
                                            aria-label="Default select example" name="resumeId">
                                        <small><@spring.message 'navbar.navi.resumes'/></small>
                                        <option selected> <@spring.message 'applications.select_resume'/></option>
                                    <#list resumes as resume>

                                            <option value="${resume.id}" class="form-brd-radius col-6">
                                                <#if (resume.resumeTitle)?has_content>
                                                ${resume.resumeTitle} <#else > <@spring.message 'resume.with_no_title'/>
                                                </#if>
                                                </option>
                                    </#list>
                                    </select>


                                </#if>

                                <button type="submit" class="btn btn-primary my-3"> <@spring.message 'button.send'/> </button>
                            </form>
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