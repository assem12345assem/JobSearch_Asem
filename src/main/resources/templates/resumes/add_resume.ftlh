<#import "../../../templates/layout.ftlh" as main>
<@main.layout>
    <div>
        <h3 class="header-top">Создание Резюме: </h3>
    </div>

    <div class="elem-container">
        <form action="/add_resume" class="mb-5" method="post">
            <label for="resumeTitle">Название резюме:</label> <br>
            <input type="text" name="resumeTitle" class="form-brd-radius col-6" placeholder="Введите название">
            <br>
            <label for="category">Категория:</label> <br>


            <select name="category" id="category">
                <option value="" disabled selected hidden>Выберите категорию</option>
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

            <br>
            <label for="salary">Ожидаемый уровень оклада:</label><br>

            <input type="number" name="salary" class="form-brd-radius col-6" placeholder="Введите оклад"><br>

            <p>О себе:</p>
            <div class="d-flex align-items-center ">
                <p><a href="/add_work-experience">Добавить опыт работы</a></p>
                <a href="/add_work-experience">
                    <div class="lined-green p-3 ml-3 text-center">+</div>
                </a>

            </div>
            <div class="d-flex align-items-center mt-3 mb-4">
                <p><a href="/add_education">Добавить образование</a></p>
                <a href="/add_education">
                    <div class="lined-blue p-3 ml-3 text-center">+</div>
                </a>

            </div>


            <p>Ссылки на соц. сети:</p>
            <div class="form-row">
                <input type="text" name="telegram" class="col-sm-4 form-brd-radius text-center">
                <img src="images/telegram.png" alt="telegram" class="img-text">


                <input type="text" name="facebookAccount" class="col-sm-4 form-brd-radius ml-4 text-center">
                <img src="images/facebook.png" alt="facebookAccount" class="img-text">
            </div>
            <div class="form-row mt-2">
                <input type="email" name="email" class="col-sm-4 form-brd-radius text-center"
                       placeholder="examle@mail.com">
                <img src="images/email.png" alt="email" class="img-text">


                <input type="text" name="linkedinAccount" class="col-sm-4 form-brd-radius ml-4" text-center>
                <img src="images/linkedin.png" alt="linkedinAccount" class="img-text">
            </div>
            <div class="form-row mt-2 mb-5">
                <input type="text" name="phoneNumber" class="col-sm-4 form-brd-radius text-center"
                       placeholder="+996 999 555 555">
                <img src="images/phone.png" alt="phoneNumber" class="img-text">


            </div>


            <input type="checkbox" id="isActive" name="isActive" value="isActive">
            <label for="isActive"> Активно</label> <br>
            <button type="submit" class="lined-green-publish mt-3">Опубликовать</button>

        </form>
    </div>
</@main.layout>