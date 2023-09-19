<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>
    <section class="vh-100">
        <div class="container py-5 h-100">
            <div class="row d-flex align-items-center justify-content-center h-100">
                <div class="col-md-8 col-lg-7 col-xl-6">
                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg"
                         class="img-fluid" alt="Phone image">
                </div>
                <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1">
                    <form action="/auth/login" method="post" id="login-form">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>
                        <!-- Email input -->
                            <div class="mb-4">
                                <div id="customErrorMessageEmail" style="color: red;"></div>

                                <div id="emailError" style="display:none;">
                                    <@spring.message 'userDto.email.email'/>
                                </div>
                            <input type="email" name="username" id="form1Example13"
                                   class="form-control form-control-lg" autofocus/>
                            <label class="form-label" for="form1Example13">Email</label>
                        </div>

                        <!-- Password input -->
                        <div class="mb-4">
                            <div id="customErrorMessagePassword" style="color: red;"></div>
                            <div id="passwordError" style="display:none;">
                                <@spring.message 'userDto.password.notBlank'/>
                            </div>
                            <input type="password" name="password" id="form1Example23"
                                   class="form-control form-control-lg"/>
                            <label class="form-label" for="form1Example23"><@spring.message 'profile.password'/></label>
                        </div>
                        <div class="d-flex justify-content-around align-items-center mb-4">
                            <!-- Checkbox -->
                            <div class="form-check">

                                <input class="form-check-input" type="checkbox" value="true" id="form1Example3"
                                       name="remember-me" checked/>
                                <label class="form-check-label" for="form1Example3"> <@spring.message 'profile.remember_me'/> </label>
                            </div>
                            <a href="/auth/forgot_password"><@spring.message 'profile.password_forgot_question'/></a>
                        </div>
                        <button type="submit" class="btn btn-primary btn-lg btn-block"><@spring.message 'profile.login_enter'/></button>
                    </form>
                </div>
            </div>
        </div>

        <script>
            document.getElementById('login-form').addEventListener('submit', onLoginHandler)

            function onLoginHandler(event) {
                let form = event.target
                let userFormData = new FormData(form)
                let user = Object.fromEntries(userFormData)
                saveUser(user)
            }


            const form = document.getElementById('login-form');

            form.addEventListener('submit', function(event) {
                let email = form.elements['username'].value;
                let password = form.elements['password'].value;

                if (!validateEmail(email)) {
                    event.preventDefault();
                    displayCustomErrorMessageEmail();
                }

                if (!validatePassword(password)) {
                    event.preventDefault();
                    displayCustomErrorMessagePassword();
                }

            });



            function validateEmail(email) {
                const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if(email === null) {
                    return false
                }
                return emailPattern.test(email);
            }

            function displayCustomErrorMessageEmail() {
                const errorDiv = document.getElementById('emailError');
                const errorElement = document.getElementById('customErrorMessageEmail');
                errorElement.textContent = errorDiv.innerText;
            }
            function validatePassword(password) {
                if(password.length<3) {
                    return false;
                }
                return true;
            }
            function displayCustomErrorMessagePassword() {
                const errorDiv = document.getElementById('passwordError');
                const errorElement = document.getElementById('customErrorMessagePassword');
                errorElement.textContent = errorDiv.innerText;
            }
        </script>
    </section>
</@main.layout>