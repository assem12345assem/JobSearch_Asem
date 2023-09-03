<#macro layout>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
              name="viewport">
        <meta content="ie=edge" http-equiv="X-UA-Compatible">
        <title>JobSearch Index page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
        <script
                src="https://kit.fontawesome.com/198548d859.js"
                crossorigin="anonymous"
        ></script>
    </head>

    <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/">Navbar</a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/vacancies">Вакансии</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/resumes">Резюме</a>
                    </li>
                </ul>

                <div class="ml-auto">
                    <#assign known = SPRING_SECURITY_CONTEXT??>
                    <#if known>
                        <#assign
                        name = SPRING_SECURITY_CONTEXT.authentication.principal.getUsername()
                        >
                        <a href="/users/profile/${name}" class="text-light m-2">Hi, ${name}</a>
                        <a href="/logout" class="btn btn-outline-info m-2">Logout</a>
                    <#else>
                        <a href="/users/login" class="btn btn-outline-info m-2">Sign in</a>
                        <a href="/users/register" class="btn btn-outline-info m-2">Register</a>
                    </#if>
                </div>

            </div>
        </div>
    </nav>
    <i class="m-5"></i>
    <div class="container">
        <#nested>
    </div>
    </body>
    </html>
</#macro>