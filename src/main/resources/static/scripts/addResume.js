document.addEventListener("DOMContentLoaded", function () {
    const educationList = [];
    const workExperienceList = [];
const form = document.getElementById("resumeForm")
    const confirmEducationButton = document.getElementById("confirmEducation");
    const educationModal = new bootstrap.Modal(document.getElementById("educationModal"));

    confirmEducationButton.addEventListener("click", function () {
        const educationInput = document.getElementById("education").value;
        const schoolNameInput = document.getElementById("schoolName").value;
        const educationStartDateInput = document.getElementById("educationStartDate").value;
        const graduationDateInput = document.getElementById("graduationDate").value;

        const educationObject = {
            education: educationInput,
            schoolName: schoolNameInput,
            educationStartDate: educationStartDateInput,
            graduationDate: graduationDateInput
        };

        educationList.push(educationObject);

        updateEducationDisplay();

        document.getElementById("education").value = "";
        document.getElementById("schoolName").value = "";
        document.getElementById("educationStartDate").value = "";
        document.getElementById("graduationDate").value = "";

        educationModal.hide();
    });

    const confirmWorkExperienceButton = document.getElementById("confirmWorkExperience");
    const workExperienceModal = new bootstrap.Modal(document.getElementById("workExperienceModal"));

    confirmWorkExperienceButton.addEventListener("click", function () {
        const companyNameInput = document.getElementById("companyName").value;
        const positionInput = document.getElementById("position").value;
        const responsibilitiesInput = document.getElementById("responsibilities").value;
        const startDateInput = document.getElementById("startDate").value;
        const endDateInput = document.getElementById("endDate").value;

        const workExperienceObject = {
            companyName: companyNameInput,
            position: positionInput,
            responsibilities: responsibilitiesInput,
            startDate: startDateInput,
            endDate: endDateInput
        };

        workExperienceList.push(workExperienceObject);

        updateWorkExperienceDisplay();

        document.getElementById("companyName").value = "";
        document.getElementById("position").value = "";
        document.getElementById("responsibilities").value = "";
        document.getElementById("startDate").value = "";
        document.getElementById("endDate").value = "";

        workExperienceModal.hide();
    });

    const submitResumeButton = document.getElementById("submitResume");

    submitResumeButton.addEventListener("click", function (event) {
        event.preventDefault();
const userId = getUserIdFromPath()
        const resumeTitle = document.getElementById("resumeTitle").value;
        const category = document.getElementById("category").value;
        const expectedSalary = document.getElementById("expectedSalary").value;

        const contactInfo = {
            telegram: document.getElementById("telegram").value,
            facebook: document.getElementById("facebook").value,
            email: document.getElementById("email").value,
            linkedin: document.getElementById("linkedin").value,
            phoneNumber: document.getElementById("phoneNumber").value
        };

        const isActive = document.getElementById("active");

        const formData = new FormData();
        formData.append("userId", userId);
        formData.append("resumeTitle", resumeTitle);
        formData.append("category", category);
        formData.append("expectedSalary", expectedSalary);
        formData.append("active", isActive.checked);
        formData.append("contactInfo", JSON.stringify(contactInfo));
        formData.append("workExperienceDto", JSON.stringify(workExperienceList))
        formData.append("educationDto", JSON.stringify(educationList))

        const headers = new Headers();
        headers.append("Content-Type", "application/json");

        fetch(`http://localhost:8089/resumes/add/${userId}`, {
            method: "POST",
            headers: headers,
            body: formData
        })
        .then(response => response.json())
        .then(response => {
            console.log(response);
        })
        .catch(error => {
            // Handle errors
            console.error(error);
        });


    });
    function updateEducationDisplay() {
        const displayEducationDiv = document.querySelector(".displayEducation");
        displayEducationDiv.innerHTML = "";

        educationList.forEach(function (educationItem, index) {
            const educationElement = createDisplayElement("Образование", educationItem, index, true);
            displayEducationDiv.appendChild(educationElement);
        });
    }

    function updateWorkExperienceDisplay() {
        const displayWorkExperienceDiv = document.getElementById("displayWorkExperience");
        displayWorkExperienceDiv.innerHTML = "";

        workExperienceList.forEach(function (workExperienceItem, index) {
            const workExperienceElement = createDisplayElement("Опыт работы", workExperienceItem, index, false);
            displayWorkExperienceDiv.appendChild(workExperienceElement);
        });
    }

    function createDisplayElement(title, item, index, isEducation) {
        const element = document.createElement("div");
        element.classList.add("mb-2");

        const deleteButton = document.createElement("button");
        deleteButton.innerText = "Удалить";
        deleteButton.classList.add("btn", "btn-danger", "btn-sm", "ms-2");
        deleteButton.addEventListener("click", function () {
            deleteItem(index, isEducation);
        });

        element.innerHTML = `<strong>${title} #${index + 1}:</strong><br>
            ${Object.entries(item)
                .map(([key, value]) => `${key}: ${value}`)
                .join("<br>")}
        `;

        element.appendChild(deleteButton);
        return element;
    }

    function deleteItem(index, isEducation) {
        if (isEducation) {
            educationList.splice(index, 1);
            updateEducationDisplay();
        } else {
            workExperienceList.splice(index, 1);
            updateWorkExperienceDisplay();
        }
    }
    function getUserIdFromPath() {
        const pathSegments = window.location.pathname.split("/");
        const userId = pathSegments[pathSegments.length - 1];
        return userId;
    }

});
