document.getElementById("categoryForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const selectedCategory = document.getElementById("category").value;
    const pageNumber = 0;
    const sort = "id";

    const url = `/resume/all/view?pageNumber=0&sort=id&category=${selectedCategory}&searchWord=default`;

    window.location.href = url;
})



document.getElementById("searchForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const searchWord = document.getElementById("searchWord").value;
    const pageNumber = 0;
    const sort = "id";

    const url = `/resume/all/view?pageNumber=0&sort=id&category=default&searchWord=${searchWord}`;

    window.location.href = url;
})