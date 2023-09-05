document.getElementById("categoryForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const selectedCategory = document.getElementById("category").value;
    const pageNumber = 0;
    const sort = "id";

    const url = `/vacancy/all/view?pageNumber=0&sort=id&category=${selectedCategory}`;

    window.location.href = url;
})

document.getElementById("dateForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const selectedDate = document.getElementById("date").value;
    const pageNumber = 0;
    const sort = "id";

    const url = `/vacancy/all/view?pageNumber=0&sort=id&category=default&date=${selectedDate}`;

    window.location.href = url;
})

document.getElementById("applicationsForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const selectedApplications = document.getElementById("applications").value;
    const pageNumber = 0;
    const sort = "id";

    const url = `/vacancy/all/view?pageNumber=0&sort=id&category=default&date=default&application=${selectedApplications}`;

    window.location.href = url;
})