<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container my-4 border border-secondary rounded">
        <h2 class="text-danger fs-2 text-center">Создание Резюме:</h2>

        <form id="resumeForm" method="post" action="/resumes/add/${userId}">
            <div class="mb-3">
                <label for="resumeTitle" class="form-label">Название резюме:</label>
                <input
                        type="text"
                        class="form-control border-dark"
                        id="resumeTitle"
                        name="resumeTitle"
                        placeholder="Введите название"
                        required
                />
            </div>
            <div class="mb-3">
                <label for="category" class="form-label">Категория:</label>

                <select
                        class="form-select border-dark"
                        aria-label="Default select example" name="category" id="category"
                >
                    <option selected>Выберите категорию
                    <option value="accounting" class="form-brd-radius col-6">accounting</option>
                    <option value="sales" class="form-brd-radius col-6">sales</option>
                    <option value="construction" class="form-brd-radius col-6">construction</option>
                    <option value="management" class="form-brd-radius col-6">management</option>
                    <option value="agriculture" class="form-brd-radius col-6">agriculture</option>
                    <option value="web development" class="form-brd-radius col-6">web development</option>
                    <option value="service" class="form-brd-radius col-6">service</option>
                    <option value="healthcare" class="form-brd-radius col-6">healthcare</option>
                    <option value="education" class="form-brd-radius col-6">education</option>
                    <option value="government" class="form-brd-radius col-6">government</option>
                    <option value="design" class="form-brd-radius col-6">design</option>
                    <option value="media" class="form-brd-radius col-6">media</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="expectedSalary" class="form-label">Ожидаемый уровень оклада:</label>
                <input
                        type="number"
                        class="form-control border-dark"
                        id="expectedSalary"
                        name="expectedSalary" placeholder="Введите оклад"
                />
            </div>
            <div class="mb-3">
                <h4>Образование</h4>
                <div id="educationContainer">
                    <button type="button" id="addEducation" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#educationModal">
                        +
                    </button>
                    <div class="displayEducation">

                    </div>
                </div>
            </div>
            <div class="mb-3">
                <h4>Опыт работы</h4>
                <div id="workExperienceContainer">
                    <button type="button" id="addWorkExperience" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#workExperienceModal">
                        +
                    </button>
                    <div id="displayWorkExperience">

                    </div>
                </div>
            </div>


            <div id="contactInfo">
                <h2>Ссылки на соц. сети:</h2>
                <div class="row g-3 mb-3">
                    <div class="col-md-6">
                        <label for="telegram" class="form-label"><i class="fa-brands fa-telegram fs-3"></i> Telegram:</label>
                        <input type="text" id="telegram" name="telegram" class="form-control border-dark" placeholder="telegram"/>
                    </div>
                    <div class="col-md-6">
                        <label for="facebook" class="form-label"><i class="fa-brands fa-square-facebook fs-3"></i> Facebook:</label>
                        <input type="text" id="facebook" name="facebookAccount" class="form-control border-dark" placeholder="facebook"/>
                    </div>
                </div>
                <div class="row g-3 mb-3">
                    <div class="col-md-6">
                        <label for="email" class="form-label"><i class="fa-solid fa-envelope fs-3"></i> Email:</label>
                        <input type="email" id="email" name="email" class="form-control border-dark" placeholder="examle@mail.com"/>
                    </div>
                    <div class="col-md-6">
                        <label for="linkedin" class="form-label" ><i class="fa-brands fa-linkedin-in fs-3"></i> LinkedIn:</label>
                        <input type="text" id="linkedin" name="linkedinAccount" class="form-control border-dark" placeholder="LinkedIn"/>
                    </div>

                </div>
                <div class="row g-3">
                    <div class="col-md-6">
                        <label for="phoneNumber" class="form-label"><i class="fa-solid fa-phone fs-3"></i> Номер телефона:</label>
                        <input type="tel" id="phoneNumber" name="phoneNumber" class="form-control border-dark" placeholder="+996 999 555 555"/>
                    </div>

                </div>
            </div>

            <div class="form-check my-3">
                <input class="form-check-input" type="checkbox" value="" id="active" name="active" checked>
                <label class="form-check-label" for="active">
                    Активно
                </label>
            </div>
            <div class="d-grid gap-2 my-3">
                <button type="submit" class="btn btn-primary" id="submitResume">Опубликовать</button>
            </div>


        </form>
        <div class="modal fade" id="educationModal" tabindex="-1" aria-labelledby="educationModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="educationModalTitle">Образование</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="education" class="form-label">Образование:</label>
                            <input type="text" id="education" class="form-control border-dark" name="education" required />
                        </div>

                        <div class="mb-3">
                            <label for="schoolName" class="form-label">Где учился:</label>
                            <input type="text" class="form-control border-dark" id="schoolName" name="schoolName" required />

                        </div>
                        <div class="mb-3">
                            <label for="educationStartDate" class="form-label">Начало обучения:</label>
                            <input
                                    type="date"
                                    id="educationStartDate"
                                    name="startDate"
                                    required
                            />

                        </div>

                        <div class="mb-3">
                            <label for="graduationDate" class="form-label">Дата выпуска:</label>
                            <input
                                    type="date"
                                    id="graduationDate"
                                    name="graduationDate"
                                    required
                            />
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                        <button type="button" class="btn btn-primary" id="confirmEducation">Добавить</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="workExperienceModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Добавить опыт работы:</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="companyName" class="form-label">Название компании</label>
                            <input
                                    type="text"
                                    id="companyName" class="form-control border-dark"
                                    name="companyName"
                                    required
                            />
                        </div>

                        <div class="mb-3">
                            <label for="position" class="form-label">Должность</label>
                            <input type="text" id="position" name="position" class="form-control border-dark" required />

                        </div>


                        <div class="mb-3">
                            <label for="responsibilities" class="form-label">Обязанности:</label>
                            <input
                                    type="text"
                                    id="responsibilities"
                                    name="responsibilities"
                                    class="form-control border-dark"
                                    required/>
                        </div>
                        <div class="mb-3">
                            <label for="startDate" class="form-label">Начало работы:</label>
                            <input type="date" id="startDate" name="startDate" required />
                        </div>
                        <div class="mb-3">
                            <label for="endDate" class="form-label">Завершение работы:</label>
                            <input type="date" id="endDate" name="endDate" required />

                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                        <button type="button" class="btn btn-primary" id="confirmWorkExperience">Добавить</button>
                    </div>
                </div>
            </div>
        </div>


    </div>
    <script src="../../static/scripts/addResume.js" defer></script>
</@main.layout>