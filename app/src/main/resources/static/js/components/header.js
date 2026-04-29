// ================= RENDER HEADER =================
function renderHeader() {

    const headerDiv = document.getElementById("header");
    if (!headerDiv) return;

    // Reset session if homepage
    if (window.location.pathname.endsWith("/")) {
        localStorage.removeItem("userRole");
        localStorage.removeItem("token");
    }

    const role = localStorage.getItem("userRole");
    const token = localStorage.getItem("token");

    // Invalid session handling
    if ((role === "loggedPatient" || role === "admin" || role === "doctor") && !token) {
        localStorage.removeItem("userRole");
        alert("Session expired or invalid login. Please log in again.");
        window.location.href = "/";
        return;
    }

    let headerContent = `
        <div class="header">
            <div class="logo">Clinic System</div>
            <div class="nav">
    `;

    // ================= ROLE BASED HEADER =================

    if (role === "admin") {
        headerContent += `
            <button id="addDocBtn" class="adminBtn">Add Doctor</button>
            <a href="#" id="logoutBtn">Logout</a>
        `;
    }

    else if (role === "doctor") {
        headerContent += `
            <a href="/doctor/dashboard">Home</a>
            <a href="#" id="logoutBtn">Logout</a>
        `;
    }

    else if (role === "patient") {
        headerContent += `
            <a href="#" id="loginBtn">Login</a>
            <a href="#" id="signupBtn">Sign Up</a>
        `;
    }

    else if (role === "loggedPatient") {
        headerContent += `
            <a href="/pages/patientDashboard.html">Home</a>
            <a href="/pages/patientAppointments.html">Appointments</a>
            <a href="#" id="logoutPatientBtn">Logout</a>
        `;
    }

    headerContent += `
            </div>
        </div>
    `;

    // Inject header
    headerDiv.innerHTML = headerContent;

    // Attach listeners
    attachHeaderButtonListeners();
}


// ================= ATTACH LISTENERS =================
function attachHeaderButtonListeners() {

    const addDocBtn = document.getElementById("addDocBtn");
    if (addDocBtn) {
        addDocBtn.addEventListener("click", () => {
            openModal("addDoctor");
        });
    }

    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", logout);
    }

    const logoutPatientBtn = document.getElementById("logoutPatientBtn");
    if (logoutPatientBtn) {
        logoutPatientBtn.addEventListener("click", logoutPatient);
    }

    const loginBtn = document.getElementById("loginBtn");
    if (loginBtn) {
        loginBtn.addEventListener("click", () => openModal("login"));
    }

    const signupBtn = document.getElementById("signupBtn");
    if (signupBtn) {
        signupBtn.addEventListener("click", () => openModal("signup"));
    }
}


// ================= LOGOUT =================
function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("userRole");
    window.location.href = "/";
}


// ================= LOGOUT PATIENT =================
function logoutPatient() {
    localStorage.removeItem("token");
    localStorage.setItem("userRole", "patient");
    window.location.href = "/pages/patientDashboard.html";
}


// ================= AUTO RENDER =================
document.addEventListener("DOMContentLoaded", renderHeader);