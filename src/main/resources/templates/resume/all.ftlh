<#import '../layout.ftlh' as main>
<@main.layout>
    <div class="container">
        <h1 class="my-3 text-center">Резюме</h1>
        <form class="d-flex m-5" method="post" action="/resume/all/search">
            <input class="form-control me-2" type="search" placeholder="Поиск резюме по названию или ожидаемой зарплате" aria-label="Search" name="search">
            <button class="btn btn-outline-success" type="submit">Поиск</button>
        </form>
        <div class="m-3">
            <h3> Сортировать</h3>
            <div class="d-flex">
                <a href="/resume/all/by_date_reversed" class="btn btn-info " id="toggleLink">по дате</a>


                <form class="row mx-3" action="/resume/all/by_category" action="post">
                    <div class="col-5">выбрать по категории:</div>
                    <select class="form-select border-dark col" aria-label="выбрать категорию"
                            name="category" id="category">
                        <option selected>выбрать категорию"</option>
                        <option value="Technology" class="form-brd-radius col-6">Technology</option>
                        <option value="Healthcare" class="form-brd-radius col-6">Healthcare</option>
                        <option value="Finance" class="form-brd-radius col-6">Finance</option>
                        <option value="Education" class="form-brd-radius col-6">Education</option>
                        <option value="Hospitality" class="form-brd-radius col-6">Hospitality</option>
                        <option value="Marketing" class="form-brd-radius col-6">Marketing</option>
                        <option value="Engineering" class="form-brd-radius col-6">Engineering</option>
                        <option value="Retail" class="form-brd-radius col-6">Retail</option>
                        <option value="Art and Design" class="form-brd-radius col-6">Art and Design</option>
                        <option value="Customer Service" class="form-brd-radius col-6">Customer Service</option>
                        <option value="Sales" class="form-brd-radius col-6">Sales</option>
                        <option value="Manufacturing" class="form-brd-radius col-6">Manufacturing</option>
                        <option value="Media and Entertainment" class="form-brd-radius col-6">Media and Entertainment</option>
                        <option value="Science" class="form-brd-radius col-6">Science</option>
                        <option value="Legal" class="form-brd-radius col-6">Legal</option>
                        <option value="Human Resources" class="form-brd-radius col-6">Human Resources</option>
                        <option value="Transportation" class="form-brd-radius col-6">Transportation</option>
                        <option value="Construction" class="form-brd-radius col-6">Construction</option>
                        <option value="Non-profit" class="form-brd-radius col-6">Non-profit</option>
                        <option value="Agriculture" class="form-brd-radius col-6">Agriculture</option>
                        <option value="Other" class="form-brd-radius col-6">Other</option>

                    </select>
                    <button type="submit" class="btn btn-info col-2 mx-2"> OK</button>
                </form>
            </div>
        </div>
        <div class="row row-cols-4 row-cols-lg-3 g-2 g-lg-3">
            <#if resumes??>
                <#list resumes as item>
                    <div class="col">
                        <div class="p-3 border bg-light">

                            <#if (item.resumeTitle)?has_content>
                                <p>${item.resumeTitle}</p>
                            </#if>

                            <#if (item.expectedSalary)?has_content>
                                <p>${item.expectedSalary}</p>
                            </#if>

                            <#if (item.dateTime)?has_content>
                                <p>${item.dateTime}</p>
                            </#if>

                            <a href="/resume/${item.id}" class="btn btn-success d-flex justify-content-center">Подробно</a>

                        </div>
                    </div>
                </#list>
            <#else>
                Нет данных.
            </#if>
        </div>


    </div>

</@main.layout>