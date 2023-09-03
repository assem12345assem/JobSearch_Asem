window.addEventListener('load', () => {
    const loginForm = document.getElementById('login-form')
    fetch('http://localhost:8089/users/login', {
        method: 'POST',
        body: data
    })

    loginForm.addEventListener('submit', loginHandler)
})
// get fetch(url)
//post
// fetch(url, {
//     method: 'POST', //PUT, DELETE, PATCH
//     body: data
// })
//
// function loginHandler(e) {
//     e.preventDefault()
//     let form = e.target
//     let data = new FormData(form)
// }