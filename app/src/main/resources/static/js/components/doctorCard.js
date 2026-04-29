import { deleteDoctor } from "../services/doctorServices.js";
import { getPatientData } from "../services/patientServices.js";
import { showBookingOverlay } from "./modals.js";

// ================= CREATE DOCTOR CARD =================
export function createDoctorCard(doctor) {

    // Main card
    const card = document.createElement("div");
    card.classList.add("doctor-card");

    // Get role
    const role = localStorage.getItem("userRole");

    // ================= DOCTOR INFO =================
    const infoDiv = document.createElement("div");
    infoDiv.classList.add("doctor-info");

    const name = document.createElement("h3");
    name.textContent = doctor.name;

    const specialization = document.createElement("p");
    specialization.textContent = `Specialty: ${doctor.specialty}`;

    const email = document.createElement("p");
    email.textContent = `Email: ${doctor.email}`;

    const availability = document.createElement("p");
    availability.textContent = `Available: ${
        doctor.availableTimes ? doctor.availableTimes.join(", ") : "N/A"
    }`;

    // Append info
    infoDiv.appendChild(name);
    infoDiv.appendChild(specialization);
    infoDiv.appendChild(email);
    infoDiv.appendChild(availability);

    // ================= ACTION BUTTONS =================
    const actionsDiv = document.createElement("div");
    actionsDiv.classList.add("card-actions");

    // ---------- ADMIN ----------
    if (role === "admin") {

        const removeBtn = document.createElement("button");
        removeBtn.textContent = "Delete";

        removeBtn.addEventListener("click", async () => {

            const confirmDelete = confirm("Are you sure you want to delete this doctor?");
            if (!confirmDelete) return;

            try {
                const token = localStorage.getItem("token");

                await deleteDoctor(doctor.id, token);

                // Remove card from UI
                card.remove();

            } catch (error) {
                alert("Failed to delete doctor");
                console.error(error);
            }
        });

        actionsDiv.appendChild(removeBtn);
    }

    // ---------- PATIENT (NOT LOGGED IN) ----------
    else if (role === "patient") {

        const bookNow = document.createElement("button");
        bookNow.textContent = "Book Now";

        bookNow.addEventListener("click", () => {
            alert("Patient needs to login first.");
        });

        actionsDiv.appendChild(bookNow);
    }

    // ---------- LOGGED PATIENT ----------
    else if (role === "loggedPatient") {

        const bookNow = document.createElement("button");
        bookNow.textContent = "Book Now";

        bookNow.addEventListener("click", async (e) => {

            try {
                const token = localStorage.getItem("token");

                const patientData = await getPatientData(token);

                showBookingOverlay(e, doctor, patientData);

            } catch (error) {
                alert("Error fetching patient data");
                console.error(error);
            }
        });

        actionsDiv.appendChild(bookNow);
    }

    // ================= FINAL ASSEMBLY =================
    card.appendChild(infoDiv);
    card.appendChild(actionsDiv);

    return card;
}