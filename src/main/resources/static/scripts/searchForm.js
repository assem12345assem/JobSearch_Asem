document.getElementById("categoryForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const selectedCategory = document.getElementById("category").value;
    const pageNumber = 0;
    const sort = "id";

    const url = `/vacancy/all/view?pageNumber=0&sort=id&category=${selectedCategory}&date=default&application=default&searchWord=default`;
    window.location.href = url;
})

document.getElementById("dateForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const selectedDate = document.getElementById("date").value;
    const pageNumber = 0;
    const sort = "id";

    const url = `/vacancy/all/view?pageNumber=0&sort=id&category=default&date=${selectedDate}application=default&searchWord=default`;

    window.location.href = url;
})

document.getElementById("applicationsForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const selectedApplications = document.getElementById("applications").value;
    const pageNumber = 0;
    const sort = "id";

    const url = `/vacancy/all/view?pageNumber=0&sort=id&category=default&date=default&application=${selectedApplications}&searchWord=default`;

    window.location.href = url;
})

document.getElementById("searchForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const searchWord = document.getElementById("searchWord").value;
    const pageNumber = 0;
    const sort = "id";

    const url = `/vacancy/all/view?pageNumber=0&sort=id&category=default&date=default&application=default&searchWord=${searchWord}`;

    window.location.href = url;
})