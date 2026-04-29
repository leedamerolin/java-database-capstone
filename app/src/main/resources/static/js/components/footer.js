// ================= RENDER FOOTER =================
function renderFooter() {

    const footer = document.getElementById("footer");
    if (!footer) return;

    footer.innerHTML = `
        <footer class="footer">

            <!-- Branding -->
            <div class="footer-brand">
                <h3>Clinic Management System</h3>
                <p>© 2026 Clinic System. All rights reserved.</p>
            </div>

            <!-- Footer Links -->
            <div class="footer-links">

                <!-- Company -->
                <div class="footer-column">
                    <h4>Company</h4>
                    <a href="#">About</a>
                    <a href="#">Careers</a>
                    <a href="#">Press</a>
                </div>

                <!-- Support -->
                <div class="footer-column">
                    <h4>Support</h4>
                    <a href="#">Account</a>
                    <a href="#">Help Center</a>
                    <a href="#">Contact</a>
                </div>

                <!-- Legal -->
                <div class="footer-column">
                    <h4>Legal</h4>
                    <a href="#">Terms</a>
                    <a href="#">Privacy Policy</a>
                    <a href="#">Licensing</a>
                </div>

            </div>

        </footer>
    `;
}

// ================= AUTO LOAD =================
document.addEventListener("DOMContentLoaded", renderFooter);