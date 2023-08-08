<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="elem-container">
        <div>
            <h3 class="header-top mb-5"> Редактирование профиля </h3>
        </div>
        <div>
            <form action="users/profile/edit/${user.id}" class="mb-5" method="post">
                <div class="form-group row">
                    <label for="phoneNumber" class="col-2">Номер телефона</label>
                    <input type="text" name="phoneNumber" class="form-brd-radius col-6">
                </div>
                <div class="form-group row">
                    <label for="userName" class="col-2">Имя пользователя</label>
                    <input type="text" name="userName" class="form-brd-radius col-6">
                </div>
                <div class="form-group row">
                    <label for="password" class="col-2">Изменить пароль</label>
                    <input type="password" name="password" class="form-brd-radius col-6" placeholder="изменить пароль">
                </div>
                <div class="form-group row">
                    <label for="photo" class="col-2">Загрузить фото</label>
                    <input type="file" name="photo" class="form-brd-radius col-6">
                </div>
                <#if user.userType = "employer">
                    <div class="form-group row">
                        <label for="companyName" class="col-2">Наименование компании</label>
                        <input type="text" name="companyName" class="form-brd-radius col-6"
                               placeholder="Наименование компании">
                    </div>
                <#else >
                    <div class="form-group row">
                        <label for="firstName" class="col-2">Имя</label>
                        <input type="text" name="firstName" class="form-brd-radius col-6" placeholder="Имя">
                    </div>
                    <div class="form-group row">
                        <label for="lastName" class="col-2">Фамилия</label>
                        <input type="text" name="lastName" class="form-brd-radius col-6" placeholder="Фамилия">
                    </div>
                    <div class="form-group row">
                        <label for="DOB" class="col-2">Дата рождения</label>
                        <input type="date" name="DOB" class="form-brd-radius col-6">
                    </div>
                </#if>
                <button type="submit" class="lined-green-publish mt-3">Подтвердить</button>
            </form>

        </div>
    </div>

</@main.layout>