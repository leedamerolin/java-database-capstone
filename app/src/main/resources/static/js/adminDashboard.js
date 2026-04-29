// ================= IMPORTS =================
import { openModal } from "./components/modals.js";
import { getDoctors, filterDoctors, saveDoctor } from "./services/doctorServices.js";
import { createDoctorCard } from "./components/doctorCard.js";


// ================= PAGE LOAD =================
document.addEventListener("DOMContentLoaded", () => {

    // Load all doctors initially
    loadDoctorCards();

    // Add Doctor button (header injected dynamically → use timeout or delegation)
    setTimeout(() => {
        const addBtn = document.getElementById("addDocBtn");
        if (addBtn) {
            addBtn.addEventListener("click", () => {
                openModal("addDoctor");
            });
        }
    }, 500);

    // Search & filters
    const searchBar = document.getElementById("searchBar");
    const filterTime = document.getElementById("filterTime");
    const filterSpecialty = document.getElementById("filterSpecialty");

    if (searchBar) {
        searchBar.addEventListener("input", filterDoctorsOnChange);
    }

    if (filterTime) {
        filterTime.addEventListener("change", filterDoctorsOnChange);
    }

    if (filterSpecialty) {
        filterSpecialty.addEventListener("change", filterDoctorsOnChange);
    }
});


// ================= LOAD ALL DOCTORS =================
async function loadDoctorCards() {
    try {
        const doctors = await getDoctors();
        renderDoctorCards(doctors);
    } catch (error) {
        console.error("Error loading doctors:", error);
    }
}


// ================= RENDER DOCTOR CARDS =================
function renderDoctorCards(doctors) {
    const contentDiv = document.getElementById("content");
    if (!contentDiv) return;

    contentDiv.innerHTML = "";

    if (!doctors || doctors.length === 0) {
        contentDiv.innerHTML = "<p>No doctors found</p>";
        return;
    }

    doctors.forEach(doc => {
        const card = createDoctorCard(doc);
        contentDiv.appendChild(card);
    });
}


// ================= FILTER LOGIC =================
async function filterDoctorsOnChange() {
    try {
        const name = document.getElementById("searchBar").value;
        const time = document.getElementById("filterTime").value;
        const specialty = document.getElementById("filterSpecialty").value;

        const doctors = await filterDoctors(name, time, specialty);

        renderDoctorCards(doctors);

    } catch (error) {
        console.error("Filter error:", error);
    }
}


// ================= ADD DOCTOR =================
window.adminAddDoctor = async function () {

    try {
        const token = localStorage.getItem("token");

        if (!token) {
            alert("Unauthorized. Please login again.");
            return;
        }

        // Collect input values
        const name = document.getElementById("docName").value;
        const specialty = document.getElementById("docSpecialty").value;
        const email = document.getElementById("docEmail").value;
        const password = document.getElementById("docPassword").value;
        const phone = document.getElementById("docPhone").value;

        // Collect availability (checkboxes)
        const timeCheckboxes = document.querySelectorAll("input[name='availableTimes']:checked");
        const availableTimes = Array.from(timeCheckboxes).map(cb => cb.value);

        const doctor = {
            name,
            specialty,
            email,
            password,
            phone,
            availableTimes
        };

        // Call API
        const result = await saveDoctor(doctor, token);

        if (result.success) {
            alert("Doctor added successfully");

            // Close modal (simple way)
            document.getElementById("modal").style.display = "none";

            // Reload doctor list
            loadDoctorCards();

        } else {
            alert(result.message || "Failed to add doctor");
        }

    } catch (error) {
        console.error("Add doctor error:", error);
        alert("Error adding doctor");
    }
};

