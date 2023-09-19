<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>
    <div class="d-flex justify-content-center align-items-center vh-100">

        <div class="container">
            <div class="row">
                <div class="col-md-6 text-center text-primary mt-5">
                    <i class="fa-solid fa-users fa-6x text-info"></i>
                </div>
                <div class="col-md-6 text-center">
                    <h2 class="text-primary"><@spring.message 'error.login.heading'/></h2>
                    <p class="fs-4"><@spring.message 'error.login.message'/></p>
                    <a href="/auth/login" class="btn btn-primary"><@spring.message 'error.login.link'/></a>
                </div>
            </div>
        </div>

    </div>
</@main.layout>