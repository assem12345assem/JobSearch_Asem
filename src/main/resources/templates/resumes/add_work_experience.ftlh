<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="elem-container">
        <div>
            <h3 class="header-top mb-5">Добавить опыт работы: </h3>
        </div>
        <form action="/add_work_experience" class="mb-5" method="post">
            <p>Период работы:</p>
            <div class="form-group row">
                <label for="dateStart" class="col-2">Начало работы</label>
                <input type="date" name="dateStart" class="form-brd-radius col-6 " >
            </div>
            <div class="form-group row">
                <label for="dateEnd" class="col-2">Завершение работы</label>
                <input type="date" name="dateEnd" class="form-brd-radius col-6" >
            </div>

            <div class="form-group row">
                <label for="companyName" class="col-2">Название компании</label>
                <input type="text" name="companyName" class="form-brd-radius col-6" >
            </div>
            <div class="form-group row">
                <label for="position" class="col-2">Должность</label>
                <input type="text" name="position" class="form-brd-radius col-6" >
            </div>
            <div class="form-group row">
                <label for="responsibilities" class="col-2">Обязанности</label>
                <input type="text" name="responsibilities" class="form-brd-radius col-6" >
            </div>


            <button type="submit" class="lined-green-publish mt-3">Добавить</button>

        </form>
    </div>
</@main.layout>