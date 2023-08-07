<#import '../layout.ftlh' as main>
<@main.layout>
    <div>
        <h3 class="header-top">Создание вакансии: </h3>
    </div>

    <div class="elem-container">
        <form action="/add" class="mb-5" method="post">
            <label for="vacancyName">Название вакансии:</label> <br>
            <input type="text" name="vacancyName" class="form-brd-radius col-6" placeholder="Введите название">
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
            <label for="salary">Заработная плата:</label><br>

            <input type="number" name="salary" class="form-brd-radius col-6" placeholder="Введите оклад"><br>


            <div class="mb-3">
                <label for="description">Описание вакансии:</label> <br>
                <textarea type="text" id="description" name="description" class="form-brd-radius col-6"
                    rows="3"></textarea>
            </div>

            <p>Требуемый опыт:</p>
            <div class="form-row">
                <label for="requiredExperienceMin" class="mr-2 ml-5 ">от</label>
                <input type="number" name="requiredExperienceMin" class="col-sm-1 form-brd-radius">

                <label for="requiredExperienceMin" class="mr-2 ml-5">до</label>
                <input type="number" name="requiredExperienceMax" class="col-sm-1 form-brd-radius">
            </div>



            <input type="checkbox" id="isActive" name="isActive" value="isActive">
            <label for="isActive"> Активно</label> <br>
            <button type="submit" class="lined-green-publish">Опубликовать</button>

        </form>
    </div>
</@main.layout>

