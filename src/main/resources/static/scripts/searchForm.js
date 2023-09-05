document.getElementById("categoryForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const selectedCategory = document.getElementById("category").value;
    const pageNumber = 0;
    const sort = "id";

    const url = `/vacancy/all/view?pageNumber=0&sort=id&category=${selectedCategory}`;

    window.location.href = url;
})