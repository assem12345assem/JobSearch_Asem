<#import "../layout.ftlh" as main>
<@main.layout>
    <div>
        <form action="/users/login" class="mb-5" method="post">
            <div class="form-group row">
                <label for="id" class="col-2">Email</label>
                <input type="email" name="id" class="form-brd-radius col-6" placeholder="examle@mail.com" required>

            </div>
            <div class="form-group row">
                <label for="password" class="col-2" >Пароль</label>
                <input type="password" name="password" class="form-brd-radius col-6" placeholder="пароль" required>
            </div>
            <button type="submit" class="lined-green-publish mt-3">подтвердить</button>
        </form>

    </div>


</@main.layout>