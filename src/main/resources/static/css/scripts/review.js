const reviewForm = document.getElementById('review_form')
const baseUrl = 'http://localhost:8089'
const movieId = document.getElementById('movieId').getAttribute('value')
const user = {
    email: 'qwe@qwe.qwe',
    password: 'qwe'
}
function reviewHandler(e) {
    e.preventDefault()
    let form = e.target
    let data = new FormData(form)


    data.append('movieId', movieId)

let headers = new Headers()
    headers.set('AUthorization', 'Basic ' + btoa(username + ":" + password))
    headers.set("Content-Type", "application/json")
    let json = JSON.stringify(Object.fromEntries(data))
    fetch(baseUrl+'/reviews', {
        method:'POST',
        headers: headers,
        body: data
    })
}

let reviewSubmitButton = reviewForm.getElementsByTagName('button')[0]
reviewSubmitButton.addEventListener('submit', reviewHandler)
window.addEventListener('load', async () => {

setInterval(async () => {
    let reviewBlock = document.getElementById('review_block')

    let response = JSON.stringify(await getComments())
    let review = JSON.parse(response)

    for (let reviewElement of review) {
        let element1 = document.createElement('p')

        let element2 = document.createElement('p')

        let element3 = document.createElement('p')
        element1.innerText = reviewElement.reviewer
        element2.innerText = reviewElement.rating
        element3.innerText = reviewElement.comment
        reviewBlock.append(element1)
        reviewBlock.append(element2)
        reviewBlock.append(element3)

    }
// element.innerText =

})
async function getComments() {
return await fetch(
    `${baseUrl}/reviews/${movieId}`, {
        method: 'GET',
        headers: headers
    }
)
}}