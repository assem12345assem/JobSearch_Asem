<#import "../layout.ftlh" as main>
<#import '/spring.ftl' as spring>

<@main.layout>
    <section class="vh-100" style="background-color: #eee;">
        <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-lg-12 col-xl-11">
                    <div class="card text-black" style="border-radius: 25px;">
                        <div class="card-body p-md-5">
                            <div class="row justify-content-center">
                                <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                                    <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Регистрация
                                        пользователя</p>

                                    <form class="mx-1 mx-md-4" action="/auth/register" method="post" enctype="multipart/form-data">
                                        <#if _csrf??>
                                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                                   value="${(_csrf.token)!'csrf-token'}"/>
                                        </#if>
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                            <div class=" flex-fill mb-0">
                                                <input type="email" name="email" id="form3Example1c"
                                                       class="form-control" placeholder="examle@mail.com" autofocus required>
                                                <label class="form-label" for="form3Example1c">Email</label>
                                            </div>
                                        </div>

                                        <div class="d-flex flex-row align-items-center mb-4">
<#--                                            <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>-->
                                            <i class="fas fa-phone fa-lg me-3 fa-fw"></i>

                                            <div class=" flex-fill mb-0">
                                                <label class="form-label" for="form3Example2c"
                                                >Номер телефона</label>
                                                <span style="color: red">
                                                <@spring.formInput "userDto.phoneNumber" 'id="form3Example2c" class="form-control" placeholder="Номер телефона"' />
                                                    <@spring.showErrors "<br>" "error"/>
                                                </span>

                                            </div>
                                        </div>

                                        <div class="d-flex flex-row align-items-center mb-4">
<#--                                            <i class="fas fa-lock fa-lg me-3 fa-fw"></i>-->
                                            <i class="fa-solid fa-signature fa-lg me-3 fa-fw"></i>
                                            <div class=" flex-fill mb-0">
                                                <input type="text" name="userName" id="form3Example3c"
                                                       class="form-control"
                                                >
                                                <label class="form-label" for="form3Example3c"
                                                >Имя пользователя</label>
                                            </div>
                                        </div>

                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                                            <div class=" flex-fill mb-0">

                                                <select name="userType" id="form3Example4c" class="form-control"
                                                        required>
                                                    <option value="" disabled selected hidden>выбрать</option>
                                                    <option value="employer" class="form-brd-radius col-6">
                                                        работодатель
                                                    </option>
                                                    <option value="applicant" class="form-brd-radius col-6">соискатель
                                                    </option>

                                                </select>
                                                <label class="form-label" for="form3Example4c">Тип учетной
                                                    записи</label>


                                            </div>
                                        </div>
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <i class="fas fa-lock fa-lg me-3 fa-fw"></i>
                                            <div class=" flex-fill mb-0">
                                                <label class="form-label" for="form3Example5c"
                                                >Пароль</label>
                                                <span style="color: red">
                                                <@spring.formInput "userDto.password" 'id="form3Example5c" class="form-control" placeholder="пароль"' />
                                                    <@spring.showErrors "<br>" "error"/>
                                                </span>

                                            </div>
                                        </div>
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <i class="fa-solid fa-camera-retro fa-lg me-3 fa-fw"></i>
                                            <div class=" flex-fill mb-0">

                                            <label class="form-label" for="file">Фото пользователя
                                                </label> <br>
                                                <input type="file" name="file" id="image_input"><br><br>
                                            </div>
                                        </div>
                                        <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                                            <button type="submit" class="btn btn-primary btn-lg">Зарегистрироваться
                                            </button>
                                        </div>

                                    </form>

                                </div>
                                <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

                                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
                                         class="img-fluid" alt="Sample image">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const userTypeSelect = document.getElementById('form3Example4c');
            const userNameLabel = document.querySelector('label[for="form3Example3c"]');

            userTypeSelect.addEventListener('change', function() {
                if (this.value === 'employer') {
                    userNameLabel.textContent = 'Имя компании';
                } else {
                    userNameLabel.textContent = 'Имя пользователя';
                }
            });
        });
    </script>

</@main.layout>