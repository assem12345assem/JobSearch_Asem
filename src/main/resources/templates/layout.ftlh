<#import "/spring.ftl" as spring>
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

        <!-- Font Awesome -->
        <link
                href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
                rel="stylesheet"
        />
        <!-- Google Fonts -->
        <link
                href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
                rel="stylesheet"
        />
        <!-- MDB -->
        <link
                href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.1/mdb.min.css"
                rel="stylesheet"
        />
        <script
                type="text/javascript"
                src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.1/mdb.min.js"
        ></script>
        <script type="text/javascript" src="/static/scripts/main.js"></script>
    </head>

    <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/"><@spring.message 'navbar.logo'/></a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="/"><@spring.message 'navbar.navi.main'/> <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/vacancy/all/view"><@spring.message 'navbar.navi.vacancies'/></a>
                    </li>
                    <li class="nav-item">
                        <#assign known = SPRING_SECURITY_CONTEXT??>
                        <#if known>
                            <#assign
 authority = SPRING_SECURITY_CONTEXT.authentication.getAuthorities()[0].getAuthority()>
                            <#if authority == "ROLE_APPLICANT">
                                <a class="nav-link" href="/vacancy/all/companies"><@spring.message 'navbar.navi.companies'/></a>
                            <#else>
                             <a class="nav-link" href="/resume/all/view"><@spring.message 'navbar.navi.resumes'/></a>
                            </#if>
                        </#if>
                    </li>
                </ul>
                <div class="dropdown me-3">

                    <a class="dropdown-toggle" href="?lang=en" id="dropdownMenuButton1" role="button"
                       data-mdb-toggle="dropdown" aria-expanded="false">
                        <i class="fas fa-globe text-secondary"></i>
                    </a>

                    <ul class="dropdown-menu " aria-labelledby="dropdownMenuButton1">
                        <li>
                            <a class="dropdown-item" href="?lang=en">
                                <i class="flag-united-states flag "></i>English <i
                                    class="fa text-success ms-2"></i></a>
                        </li>
                        <li>
                            <hr class="dropdown-divider"/>
                        </li>
                        <li>
                            <a class="dropdown-item" href="?lang=ru"><i class="flag-russia flag"></i>Русский</a>
                        </li>
                    </ul>
                </div>
                <div class="d-flex align-items-right" >
                    <#assign
                    known = SPRING_SECURITY_CONTEXT??
                    >
                    <#if known>
                        <#assign
                        user = SPRING_SECURITY_CONTEXT.authentication.principal
                        name = user.getUsername()
                        >
                        <a href="/auth/profile/${name}" class="text-light mt-2" id="profile_page"><@spring.message 'navbar.user.hi'/> ${name}</a>
                        <#if _csrf??>
                            <form action="/logout" method="post">
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                       value="${(_csrf.token)!'--no-token--'}"/>
                                <button type="submit" class="btn btn-outline-info m-2" id="logout"><@spring.message 'navbar.user.logout'/></button>
                            </form>
                        <#else>
                            <a href="/logout" class="btn btn-outline-info ms-2" id="logout"><@spring.message 'navbar.user.logout'/></a>
                        </#if>
                    <#else>
                        <a href="/auth/login" class="btn btn-outline-info m-2"><@spring.message 'navbar.user.signin'/></a>
                        <a href="/auth/register" class="btn btn-outline-info m-2"><@spring.message 'navbar.user.signup'/></a>
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
    <script>

            let logout = document.getElementById('logout');
            if (logout) {
                logout.addEventListener('click', () => localStorage.removeItem('user'));
            }

    </script>

</html>
</#macro>