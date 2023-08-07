<#import "../../../templates/layout.ftlh" as main>
<@main.layout>
    <div class="elem-container">
        <div>
            <h3 class="header-top mb-5">Добавить образование: </h3>
        </div>
        <form action="/add_education" class="mb-5" method="post">
            <div class="form-group row">
                <label for="education" class="col-2">Образование</label>
                <input type="text" name="education" class="form-brd-radius col-6" placeholder="высшее/среднее/курсы/др.">
            </div>
            <div class="form-group row">
                <label for="schoolName" class="col-2">Где учился</label>
                <input type="text" name="schoolName" class="form-brd-radius col-6" placeholder="наименование учебного заведения">
            </div>

            <p>Срок обучения:</p>
            <div class="form-group row">
                <label for="startDate" class="col-2">Начало обучения</label>
                <input type="date" name="startDate" class="form-brd-radius col-6 " >
            </div>
            <div class="form-group row">
                <label for="graduationDate" class="col-2">Дата выпуска</label>
                <input type="date" name="graduationDate" class="form-brd-radius col-6">
            </div>
            <button type="submit" class="lined-green-publish mt-3">Добавить</button>

        </form>
    </div>
</@main.layout>