// ================= IMPORTS =================
import { getAllAppointments } from "./services/appointmentRecordService.js";
import { createPatientRow } from "./components/patientRows.js";


// ================= GLOBAL VARIABLES =================
const tableBody = document.getElementById("patientTableBody");

// today's date (yyyy-mm-dd)
let selectedDate = new Date().toISOString().split("T")[0];

// token for authentication
const token = localStorage.getItem("token");

// patient name filter
let patientName = "null";


// ================= SEARCH BAR =================
const searchBar = document.getElementById("searchBar");
if (searchBar) {
    searchBar.addEventListener("input", () => {
        const value = searchBar.value.trim();

        patientName = value === "" ? "null" : value;

        loadAppointments();
    });
}


// ================= TODAY BUTTON =================
const todayButton = document.getElementById("todayButton");
const datePicker = document.getElementById("datePicker");

if (todayButton) {
    todayButton.addEventListener("click", () => {
        selectedDate = new Date().toISOString().split("T")[0];

        if (datePicker) {
            datePicker.value = selectedDate;
        }

        loadAppointments();
    });
}


// ================= DATE PICKER =================
if (datePicker) {
    datePicker.value = selectedDate;

    datePicker.addEventListener("change", () => {
        selectedDate = datePicker.value;
        loadAppointments();
    });
}


// ================= LOAD APPOINTMENTS =================
async function loadAppointments() {

    try {
        const appointments = await getAllAppointments(
            selectedDate,
            patientName,
            token
        );

        // clear table
        tableBody.innerHTML = "";

        // no data case
        if (!appointments || appointments.length === 0) {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="5" class="noPatientRecord">
                        No Appointments found for today
                    </td>
                </tr>
            `;
            return;
        }

        // render rows
        appointments.forEach(app => {

            const row = createPatientRow(app);

            tableBody.appendChild(row);
        });

    } catch (error) {
        console.error("Error loading appointments:", error);

        tableBody.innerHTML = `
            <tr>
                <td colspan="5" class="noPatientRecord">
                    Error loading appointments
                </td>
            </tr>
        `;
    }
}


// ================= INITIAL LOAD =================
document.addEventListener("DOMContentLoaded", () => {

    // if renderContent exists (safe call)
    if (typeof renderContent === "function") {
        renderContent();
    }

    loadAppointments();
});

