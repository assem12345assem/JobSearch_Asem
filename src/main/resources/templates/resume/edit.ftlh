<#import "../layout.ftlh" as main>
<#import '/spring.ftl' as spring>

<@main.layout>
    <div class="container">
        <div class="main-body">
            <h2 class="text-primary fs-2 text-center"> <@spring.message 'resume.user_resume'/></h2>
            <form action="/resume/edit/${resume.id}" method="post">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                           value="${(_csrf.token)!'csrf-token'}"/>
                </#if>
                <div class="d-grid gap-2 fixed-button-container">
                    <button class="btn btn-success mt-3 bottom-button" type="submit" id="submitResume"><@spring.message 'button.save'/>
                    </button>
                </div>
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
                                        <h4><#if (resume.profile.firstName)??>${resume.profile.firstName}</#if> <#if (resume.profile.lastName)??>${resume.profile.lastName}</#if></h4>
                                        <p class="text-secondary mb-1"><@spring.message 'resume.dob'/></p>
                                        <p class="text-muted font-size-sm"><#if (resume.profile.dateOfBirth)??>${resume.profile.dateOfBirth}</#if></p>
                                        <a class="btn btn-success" href="/auth/edit/${user.email}"><@spring.message 'profile.edit'/></a>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="card mt-3">
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">

                                    <#if (resume.contact.telegram)??>
                                        <label for="telegram" class="fs-6"><i class="fa-brands fa-telegram fs-3"></i>
                                            Telegram:</label>
                                        <input id="telegram" type="text" name="contact.telegram"
                                               placeholder="${resume.contact.telegram}" class="form-control"
                                               value="${resume.contact.telegram}"
                                        >

                                    <#else>
                                        <label for="telegram" class="fs-6"><i class="fa-brands fa-telegram fs-3"></i>
                                            Telegram:</label>
                                        <input id="telegram" class="form-control" type="text" placeholder="telegram"
                                               name="contact.telegram">
                                    </#if>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">

                                    <#if (resume.contact.email)??>
                                        <label for="email" class="fs-6"><i class="fa-solid fa-envelope fs-3"></i> Email:</label>
                                        <input id="email" class="form-control" type="text"
                                               placeholder="${resume.contact.email}"
                                               name="contact.email" value="${resume.contact.email}">

                                    <#else>
                                        <label for="email" class="fs-6"><i class="fa-solid fa-envelope fs-3"></i> Email:</label>
                                        <input id="email" class="form-control" type="text" placeholder="email"
                                               name="contact.email">
                                    </#if>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                    <#if (resume.contact.facebook)??>
                                        <label for="facebook" class="fs-6"><i class="fa-brands fa-square-facebook fs-3"></i> Facebook:</label>
                                        <input id="facebook" class="form-control" type="text" placeholder="${resume.contact.facebook}" name="contact.facebook" value="${resume.contact.facebook}">
                                    <#else>
                                        <label for="facebook" class="fs-6"><i class="fa-brands fa-square-facebook fs-3"></i> Facebook:</label>
                                        <input id="facebook" class="form-control" type="text" placeholder="facebook" name="contact.facebook">
                                    </#if>
                                </li>

                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">

                                    <#if (resume.contact.linkedIn)??>
                                        <label for="linkedin" class="fs-6"><i class="fa-brands fa-linkedin-in fs-3"></i>
                                            LinkedIn:</label>
                                        <input id="linkedin" class="form-control" type="text"
                                               placeholder="${resume.contact.linkedIn}"
                                               name="contact.linkedIn" value="${resume.contact.linkedIn}">
                                    <#else >
                                        <label for="linkedin" class="fs-6"><i class="fa-brands fa-linkedin-in fs-3"></i>
                                            LinkedIn:</label>
                                        <input id="linkedin" class="form-control" type="text" placeholder="linkedIn"
                                               name="contact.linkedIn">

                                    </#if>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">

                                    <#if (resume.contact.phoneNumber)??>
                                        <label for="phoneNumber" class="fs-6"><i class="fa-solid fa-phone fs-3"></i> <@spring.message 'contact.phone_number'/>:</label>
                                        <input id="phoneNumber" class="form-control" type="text"
                                               placeholder="${resume.contact.phoneNumber}"
                                               name="contact.phoneNumber" value="${resume.contact.phoneNumber}">
                                    <#else >
                                        <label for="phoneNumber" class="fs-6"><i class="fa-solid fa-phone fs-3"></i> <@spring.message 'contact.phone_number'/>:</label>
                                        <input id="phoneNumber" class="form-control" type="text" placeholder="+996 999 555 555"
                                               name="contact.phoneNumber">
                                    </#if>
                                </li>

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
                                        <#if (resume.resumeTitle)??>
                                            <input class="form-control" type="text" placeholder="${resume.resumeTitle}"
                                                   name="resumeTitle" value="${resume.resumeTitle}">
                                        <#else>
                                            <input class="form-control" type="text" placeholder="<@spring.message 'instruction.input.title'/>"
                                                   name="resumeTitle">
                                        </#if>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0"><@spring.message 'category.category'/></h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <#assign var1 = resume.category! "<@spring.message 'category.no.info'/>">
                                        <#if (var1)?has_content>
                                            <@spring.message 'category.select.another'/>
                                            <select class="form-select border-dark" aria-label="Default select example"
                                                    name="category" id="category">
                                                <#assign var1=resume.category! "<@spring.message 'button.sort.by_category'/>">
                                                <option selected>${var1}
                                                <option value="Technology"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Technology'/></option>
                                                <option value="Healthcare"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Healthcare'/></option>
                                                <option value="Finance"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Finance'/></option>
                                                <option value="Education"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Education'/></option>
                                                <option value="Hospitality"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Hospitality'/></option>
                                                <option value="Marketing"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Marketing'/></option>
                                                <option value="Engineering"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Engineering'/></option>
                                                <option value="Retail"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Retail'/></option>
                                                <option value="ArtAndDesign"
                                                        class="form-brd-radius col-6"><@spring.message 'category.ArtAndDesign'/></option>
                                                <option value="CustomerService"
                                                        class="form-brd-radius col-6"><@spring.message 'category.CustomerService'/></option>
                                                <option value="Sales"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Sales'/></option>
                                                <option value="Manufacturing"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Manufacturing'/></option>
                                                <option value="MediaAndEntertainment"
                                                        class="form-brd-radius col-6"><@spring.message 'category.MediaAndEntertainment'/></option>
                                                <option value="Science"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Science'/></option>
                                                <option value="Legal"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Legal'/></option>
                                                <option value="HumanResources"
                                                        class="form-brd-radius col-6"><@spring.message 'category.HumanResources'/></option>
                                                <option value="Transportation"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Transportation'/></option>
                                                <option value="Construction"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Construction'/></option>
                                                <option value="NonProfit"
                                                        class="form-brd-radius col-6"><@spring.message 'category.NonProfit'/></option>
                                                <option value="Agriculture"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Agriculture'/></option>
                                                <option value="Other"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Other'/></option>

                                            </select>
                                        <#else >
                                            <select class="form-select border-dark" aria-label="Default select example"
                                                    name="category" id="category">
                                                <option value="Technology"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Technology'/></option>
                                                <option value="Healthcare"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Healthcare'/></option>
                                                <option value="Finance"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Finance'/></option>
                                                <option value="Education"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Education'/></option>
                                                <option value="Hospitality"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Hospitality'/></option>
                                                <option value="Marketing"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Marketing'/></option>
                                                <option value="Engineering"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Engineering'/></option>
                                                <option value="Retail"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Retail'/></option>
                                                <option value="ArtAndDesign"
                                                        class="form-brd-radius col-6"><@spring.message 'category.ArtAndDesign'/></option>
                                                <option value="CustomerService"
                                                        class="form-brd-radius col-6"><@spring.message 'category.CustomerService'/></option>
                                                <option value="Sales"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Sales'/></option>
                                                <option value="Manufacturing"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Manufacturing'/></option>
                                                <option value="MediaAndEntertainment"
                                                        class="form-brd-radius col-6"><@spring.message 'category.MediaAndEntertainment'/></option>
                                                <option value="Science"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Science'/></option>
                                                <option value="Legal"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Legal'/></option>
                                                <option value="HumanResources"
                                                        class="form-brd-radius col-6"><@spring.message 'category.HumanResources'/></option>
                                                <option value="Transportation"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Transportation'/></option>
                                                <option value="Construction"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Construction'/></option>
                                                <option value="NonProfit"
                                                        class="form-brd-radius col-6"><@spring.message 'category.NonProfit'/></option>
                                                <option value="Agriculture"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Agriculture'/></option>
                                                <option value="Other"
                                                        class="form-brd-radius col-6"><@spring.message 'category.Other'/></option>

                                            </select>
                                        </#if>

                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0"><@spring.message 'resume.expected_salary'/></h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <#if (resume.expectedSalary)?has_content>
                                            <input class="form-control" type="number"
                                                   placeholder="${resume.expectedSalary}"
                                                   name="expectedSalary" value="${resume.expectedSalary}">
                                        <#else >
                                            <input class="form-control" type="number" placeholder="<@spring.message 'instruction.enter.salary'/>"
                                                   name="expectedSalary">
                                        </#if>
                                    </div>
                                </div>
                                <hr>
                                <div class="row mt-1">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0"><@spring.message 'instruction.active'/>:</h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                        <input class="form-check-input fs-2 border-dark" type="checkbox"
                                               id="checkboxId" value="active"
                                               name="isActive" <#if resume.isActive == "true">checked</#if>
                                        />
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
                                        <!-- Button trigger modal -->
                                        <div class="d-flex justify-content-center">
                                            <button type="button" class="btn btn-primary" id="addWorkExperienceButton"
                                                    data-bs-toggle="modal" data-bs-target="#workModal">
                                                +
                                            </button>
                                        </div>

                                        <#if (resume.workList)??>

                                            <#list resume.workList as item>
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <button class="btn btn-info rounded-circle px-3 mb-2">${item_index + 1}</button>
                                                    <a class="btn btn-danger"
                                                       href="/resume/edit/delete_work/${item.id}"><@spring.message 'button.delete'/></a>
                                                </div>


                                                <small><@spring.message 'companies.name'/></small>
                                                <div>
                                                    <#if (item.companyName)?has_content>
                                                        <input class="form-control" type="text"
                                                               placeholder="${item.companyName}"
                                                               name="workList[${item_index}].companyName"
                                                               value="${item.companyName}">
                                                    <#else >
                                                        <input class="form-control" type="text"
                                                               placeholder="<@spring.message 'companies.name'/>"
                                                               name="workList[${item_index}].companyName">
                                                    </#if>


                                                </div>
                                                <small><@spring.message 'resume.position'/></small>
                                                <div>
                                                    <#if (item.position)?has_content>
                                                        <input class="form-control" type="text"
                                                               placeholder="${item.position}"
                                                               name="workList[${item_index}].position"
                                                               value="${item.position}">
                                                    <#else >
                                                        <input class="form-control" type="text" placeholder="<@spring.message 'resume.position'/>"
                                                               name="workList[${item_index}].position">
                                                    </#if>


                                                </div>
                                                <small><@spring.message 'resume.responsibilities'/>:</small>
                                                <div>
                                                    <#if (item.responsibilities)?has_content>
                                                        <input class="form-control" type="text"
                                                               placeholder="${item.responsibilities}"
                                                               name="workList[${item_index}].responsibilities"
                                                               value="${item.responsibilities}">
                                                    <#else >
                                                        <input class="form-control" type="text"
                                                               placeholder="<@spring.message 'resume.responsibilities'/>"
                                                               name="workList[${item_index}].responsibilities">
                                                    </#if>


                                                </div>
                                                <small><@spring.message 'resume.start_work'/>: </small>
                                                <div>
                                                    <#if (item.dateStart)?has_content>

                                                        <input class="form-control" type="date"
                                                               placeholder="<@spring.message 'resume.start_work'/>"
                                                               name="workList[${item_index}].dateStart"
                                                               value="${item.dateStart}"> <br>
                                                    <#else >
                                                        <input class="form-control" type="date"
                                                               placeholder="<@spring.message 'resume.start_work'/>"
                                                               name="workList[${item_index}].dateStart">
                                                    </#if>
                                                </div>

                                                <small><@spring.message 'resume.end_work'/>: </small>
                                                <div>
                                                    <#if (item.dateEnd)?has_content>

                                                        <input class="form-control" type="date"
                                                               placeholder="<@spring.message 'resume.end_work'/>"
                                                               name="workList[${item_index}].dateEnd"
                                                               value="${item.dateEnd}"> <br>
                                                    <#else >
                                                        <input class="form-control" type="date"
                                                               placeholder="<@spring.message 'resume.end_work'/>"
                                                               name="workList[${item_index}].dateEnd">
                                                    </#if>
                                                </div>




                                                <hr>
                                            </#list>
                                        </#if>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 mb-3">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <h6 class="d-flex align-items-center mb-3"><@spring.message 'resume.education'/></h6>
                                        <div class="d-flex justify-content-center">
                                            <button type="button" class="btn btn-primary" id="addEducationButton"
                                                    data-bs-toggle="modal" data-bs-target="#eduModal">
                                                +
                                            </button>
                                        </div>
                                        <#if (resume.eduList)??>
                                        <#list resume.eduList as item>
                                        <div class="d-flex justify-content-between align-items-center">
                                            <button class="btn btn-info rounded-circle px-3 mb-2">${item_index + 1}</button>
                                            <a class="btn btn-danger"
                                               href="/resume/edit/delete_education/${item.id}"><@spring.message 'button.delete'/></a>
                                        </div>

                                        <small><@spring.message 'resume.education'/></small>
                                        <div>
                                            <#if (item.education)?has_content>
                                                <input class="form-control" type="text"
                                                       placeholder="${item.education}"
                                                       name="eduList[${item_index}].education"
                                                       value="${item.education}">
                                            <#else >
                                                <input class="form-control" type="text"
                                                       placeholder="<@spring.message 'resume.education'/>"
                                                       name="eduList[${item_index}].education">
                                            </#if>
                                        </div>
                                        <small><@spring.message 'resume.place_of_study'/>:</small>
                                        <div>

                                            <#if (item.schoolName)?has_content>
                                                <input class="form-control" type="text"
                                                       placeholder="${item.schoolName}"
                                                       name="eduList[${item_index}].schoolName"
                                                       value="${item.schoolName}">
                                            <#else >
                                                <input class="form-control" type="text"
                                                       placeholder="<@spring.message 'resume.school'/>"
                                                       name="eduList[${item_index}].schoolName">
                                            </#if>
                                        </div>
                                        <small><@spring.message 'resume.education_start'/>: </small>
                                        <div>
                                            <#if (item.startDate)?has_content>

                                                <input class="form-control" type="date"
                                                       placeholder="<@spring.message 'resume.education_start'/>"
                                                       name="eduList[${item_index}].startDate"
                                                       value="${item.startDate}"> <br>
                                            <#else >

                                                <input class="form-control" type="date"
                                                       placeholder="<@spring.message 'resume.education_start'/>"
                                                       name="eduList[${item_index}].startDate">
                                            </#if>
                                        </div>
                                        <small><@spring.message 'resume.education_end'/>: </small>
                                        <#if (item.graduationDate)?has_content>


                                            <input class="form-control" type="date"
                                                   placeholder="<@spring.message 'resume.education_end'/>"
                                                   name="eduList[${item_index}].graduationDate"
                                                   value="${item.graduationDate}"> <br>
                                        <#else >


                                            <input class="form-control" type="date"
                                                   placeholder="<@spring.message 'resume.education_end'/>"
                                                   name="eduList[${item_index}].graduationDate">
                                        </#if>
                                    </div>
                                    <hr>
                                    </#list>
                                    </#if>


                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </form>

        </div>

    </div>
</@main.layout>
