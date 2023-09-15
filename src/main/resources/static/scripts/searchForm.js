document.getElementById("categoryForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const selectedCategory = document.getElementById("category").value;
    const pageNumber = 0;
    const sort = "id";

    window.location.href = `/vacancy/all/view?pageNumber=0&sort=id&category=${selectedCategory}&date=default&application=default&searchWord=default`;
})

document.getElementById("dateForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const selectedDate = document.getElementById("date").value;
    const pageNumber = 0;
    const sort = "id";

    window.location.href = `/vacancy/all/view?pageNumber=0&sort=id&category=default&date=${selectedDate}application=default&searchWord=default`;
})

document.getElementById("applicationsForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const selectedApplications = document.getElementById("applications").value;
    const pageNumber = 0;
    const sort = "id";
    window.location.href = `/vacancy/all/view?pageNumber=0&sort=id&category=default&date=default&application=${selectedApplications}&searchWord=default`;
})

document.getElementById("searchForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const searchWord = document.getElementById("searchWord").value;
    const pageNumber = 0;
    const sort = "id";
    window.location.href = `/vacancy/all/view?pageNumber=0&sort=id&category=default&date=default&application=default&searchWord=${searchWord}`;
})