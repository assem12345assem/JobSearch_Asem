<#macro layout>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
              name="viewport">
        <meta content="ie=edge" http-equiv="X-UA-Compatible">
        <title>Http server Index page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="../static.css/style.css">
    </head>

    <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="/">Navbar</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Вакансии</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Резюме</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="users/profile">Мой профиль</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="users/register">Зарегистрироваться</a>
                </li>
            </ul>
            <#--            <a href="/profile" class="btn btn-outline-success my-2 my-sm-0">Profile</a>-->
            <#--            <#if user?has_content>-->
            <#--                <i class="btn btn-outline-success my-2 my-sm-0">${user.email}</i>-->
            <#--            <#else>-->
            <#--                <a href="/login" class="btn btn-outline-success my-2 my-sm-0">LogIn</a>-->
            <#--            </#if>-->
            <#--            <#if user?has_content>-->
            <#--                <i></i>-->
            <#--            <#else>-->
            <#--                <a href="/register" class="btn btn-outline-success my-2 my-sm-0">Sign up</a>-->
            <#--            </#if>-->
            <#--            <a href="/exit" class="btn btn-outline-success my-2 my-sm-0">Exit</a>-->
        </div>
    </nav>
    <i class="m-5"></i>
    <div class="container">
        <#nested>
    </div>
    </body>
    </html>
</#macro>