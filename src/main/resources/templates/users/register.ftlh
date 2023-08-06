<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="elem-container">
        <div>
            <h3 class="header-top mb-5">	Регистрация пользователя  </h3>
        </div>
        <div>
            <form action="/users/register" class="mb-5" method="post">
                <div class="form-group row">
                    <label for="id" class="col-2">Email</label>
                    <input type="email" name="id" class="form-brd-radius col-6" placeholder="examle@mail.com" required>

                </div>
                <div class="form-group row">
                    <label for="phoneNumber" class="col-2">Номер телефона</label>
                    <input type="text" name="phoneNumber" class="form-brd-radius col-6" >
                </div>
                <div class="form-group row">
                    <label for="userName" class="col-2">Имя пользователя</label>
                    <input type="text" name="userName" class="form-brd-radius col-6" >
                </div>
                <div class="form-group row">
                    <label for="userType" class="col-2">тип учетной записи</label>
                    <select name="userType" id="userType" class="form-brd-radius col-6">
                        <option value="" disabled selected hidden>тип учетной записи</option>
                        <option value="employer" class="form-brd-radius col-6">работодатель</option>
                        <option value="applicant" class="form-brd-radius col-6">соискатель</option>

                    </select>
                </div>
                <div class="form-group row">
                    <label for="password" class="col-2" >Пароль</label>
                    <input type="password" name="password" class="form-brd-radius col-6" placeholder="пароль" required>
                     </div>
                <button type="submit" class="lined-green-publish mt-3">Зарегистрироваться</button>
            </form>

        </div>
    </div>

</@main.layout>