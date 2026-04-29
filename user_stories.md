# User Story Template

---

## User Story 1

**Title:**  
_As an Admin, I want to manage doctors and patients, so that I can maintain accurate system records._

**Acceptance Criteria:**  
1. Admin can add new doctor and patient details.  
2. Admin can update existing doctor and patient information.  
3. Admin can delete doctor and patient records.  

**Priority:** High  
**Story Points:** 5  

**Notes:**  
- Ensure validation for required fields.  
- Prevent duplicate entries.  

---

## User Story 2

**Title:**  
_As a Doctor, I want to view my dashboard, so that I can check appointments and patient details._

**Acceptance Criteria:**  
1. Doctor can view scheduled appointments.  
2. Doctor can access patient details.  
3. Doctor dashboard loads successfully using Thymeleaf.  

**Priority:** High  
**Story Points:** 3  

**Notes:**  
- Dashboard should be responsive.  

---

## User Story 3

**Title:**  
_As a Patient, I want to book an appointment, so that I can consult a doctor._

**Acceptance Criteria:**  
1. Patient can select doctor and time slot.  
2. Appointment is saved successfully.  
3. Confirmation is returned via REST API.  

**Priority:** High  
**Story Points:** 5  

**Notes:**  
- Prevent booking already occupied slots.  

---

## User Story 4

**Title:**  
_As a Patient, I want to view my medical records, so that I can track my health history._

**Acceptance Criteria:**  
1. Patient can retrieve records via REST API.  
2. Records include prescriptions and past visits.  
3. Data is displayed in JSON format.  

**Priority:** Medium  
**Story Points:** 3  

**Notes:**  
- Use MongoDB for flexible data storage.  

---

## User Story 5

**Title:**  
_As a Doctor, I want to create prescriptions, so that I can provide treatment to patients._

**Acceptance Criteria:**  
1. Doctor can create prescription entries.  
2. Prescription is stored in MongoDB.  
3. Patient can retrieve prescription details.  

**Priority:** High  
**Story Points:** 4  

**Notes:**  
- Ensure proper linking between patient and prescription.  

---

## User Story 6

**Title:**  
_As a System, I want to manage data across MySQL and MongoDB, so that I can handle both structured and unstructured data efficiently._

**Acceptance Criteria:**  
1. MySQL stores structured data (patients, doctors, appointments).  
2. MongoDB stores prescription data.  
3. Service layer correctly routes data to respective repositories.  

**Priority:** Medium  
**Story Points:** 5  

**Notes:**  
- Maintain consistency across databases.  

---
---

## Admin User Story 1

**Title:**  
_As an Admin, I want to log into the portal using my username and password, so that I can securely access and manage the platform._

**Acceptance Criteria:**  
1. Admin can enter valid username and password.  
2. System authenticates credentials successfully.  
3. Admin is redirected to the dashboard upon successful login.  

**Priority:** High  
**Story Points:** 3  

**Notes:**  
- Display error message for invalid credentials.  

---

## Admin User Story 2

**Title:**  
_As an Admin, I want to log out of the portal, so that I can protect system access from unauthorized users._

**Acceptance Criteria:**  
1. Admin can click logout button.  
2. Session is terminated successfully.  
3. Admin is redirected to login page.  

**Priority:** High  
**Story Points:** 2  

**Notes:**  
- Ensure session invalidation after logout.  

---

## Admin User Story 3

**Title:**  
_As an Admin, I want to add doctors to the portal, so that I can maintain updated doctor records._

**Acceptance Criteria:**  
1. Admin can enter doctor details.  
2. Doctor information is saved in database.  
3. Confirmation message is displayed after adding doctor.  

**Priority:** High  
**Story Points:** 4  

**Notes:**  
- Validate required fields before saving.  

---

## Admin User Story 4

**Title:**  
_As an Admin, I want to delete a doctor’s profile from the portal, so that I can remove inactive or incorrect records._

**Acceptance Criteria:**  
1. Admin can select a doctor profile.  
2. Admin can delete the selected doctor.  
3. Doctor record is removed from database.  

**Priority:** Medium  
**Story Points:** 3  

**Notes:**  
- Confirm before deletion to avoid accidental removal.  

---

## Admin User Story 5

**Title:**  
_As an Admin, I want to run a stored procedure in MySQL CLI to get the number of appointments per month, so that I can track usage statistics._

**Acceptance Criteria:**  
1. Stored procedure executes successfully in MySQL CLI.  
2. Monthly appointment data is returned correctly.  
3. Admin can analyze the output for reporting purposes.  

**Priority:** Medium  
**Story Points:** 5  

**Notes:**  
- Ensure database connection is properly configured.  
- Stored procedure should return accurate aggregated data.  

## Patient User Story 1

**Title:**  
_As a Patient, I want to view a list of doctors without logging in, so that I can explore available options before registering._

**Acceptance Criteria:**  
1. Patient can access doctor list without authentication.  
2. Doctor details are displayed correctly.  
3. Page loads successfully without login requirement.  

**Priority:** High  
**Story Points:** 3  

**Notes:**  
- Ensure public access without exposing sensitive data.  

---

## Patient User Story 2

**Title:**  
_As a Patient, I want to sign up using my email and password, so that I can create an account and book appointments._

**Acceptance Criteria:**  
1. Patient can enter email and password.  
2. Account is created successfully.  
3. Confirmation message is displayed after registration.  

**Priority:** High  
**Story Points:** 4  

**Notes:**  
- Validate email format and password strength.  

---

## Patient User Story 3

**Title:**  
_As a Patient, I want to log into the portal, so that I can manage my bookings._

**Acceptance Criteria:**  
1. Patient can enter valid login credentials.  
2. System authenticates successfully.  
3. Patient is redirected to dashboard after login.  

**Priority:** High  
**Story Points:** 3  

**Notes:**  
- Show error message for invalid login.  

---

## Patient User Story 4

**Title:**  
_As a Patient, I want to log out of the portal, so that I can secure my account._

**Acceptance Criteria:**  
1. Patient can click logout option.  
2. Session is terminated successfully.  
3. Patient is redirected to login page.  

**Priority:** High  
**Story Points:** 2  

**Notes:**  
- Ensure session is properly invalidated.  

---

## Patient User Story 5

**Title:**  
_As a Patient, I want to log in and book an hour-long appointment, so that I can consult with a doctor._

**Acceptance Criteria:**  
1. Patient selects doctor and time slot.  
2. Appointment duration is one hour.  
3. Booking is saved successfully.  

**Priority:** High  
**Story Points:** 5  

**Notes:**  
- Prevent overlapping bookings.  

---

## Patient User Story 6

**Title:**  
_As a Patient, I want to view my upcoming appointments, so that I can prepare accordingly._

**Acceptance Criteria:**  
1. Patient can access list of upcoming appointments.  
2. Appointment details are displayed clearly.  
3. Only future appointments are shown.  

**Priority:** Medium  
**Story Points:** 3  

**Notes:**  
- Display date and time clearly for better usability.  

---

## Exercise 4: Doctor User Stories

---

## Doctor User Story 1

**Title:**  
_As a Doctor, I want to log into the portal, so that I can manage my appointments._

**Acceptance Criteria:**  
1. Doctor can enter valid login credentials.  
2. System authenticates successfully.  
3. Doctor is redirected to dashboard after login.  

**Priority:** High  
**Story Points:** 3  

**Notes:**  
- Display error message for invalid login attempts.  

---

## Doctor User Story 2

**Title:**  
_As a Doctor, I want to log out of the portal, so that I can protect my data._

**Acceptance Criteria:**  
1. Doctor can click logout option.  
2. Session is terminated successfully.  
3. Doctor is redirected to login page.  

**Priority:** High  
**Story Points:** 2  

**Notes:**  
- Ensure session invalidation after logout.  

---

## Doctor User Story 3

**Title:**  
_As a Doctor, I want to view my appointment calendar, so that I can stay organized._

**Acceptance Criteria:**  
1. Doctor can view all scheduled appointments.  
2. Appointments are displayed with date and time.  
3. Calendar view is updated in real-time.  

**Priority:** High  
**Story Points:** 4  

**Notes:**  
- Ensure proper sorting of appointments by date.  

---

## Doctor User Story 4

**Title:**  
_As a Doctor, I want to mark my unavailability, so that patients can only book available slots._

**Acceptance Criteria:**  
1. Doctor can select unavailable time slots.  
2. System blocks those slots from booking.  
3. Updated availability is reflected immediately.  

**Priority:** Medium  
**Story Points:** 4  

**Notes:**  
- Prevent booking during unavailable slots.  

---

## Doctor User Story 5

**Title:**  
_As a Doctor, I want to update my profile with specialization and contact information, so that patients have up-to-date information._

**Acceptance Criteria:**  
1. Doctor can edit profile details.  
2. Changes are saved successfully.  
3. Updated information is visible to patients.  

**Priority:** Medium  
**Story Points:** 3  

**Notes:**  
- Validate required fields before saving.  

---

## Doctor User Story 6

**Title:**  
_As a Doctor, I want to view patient details for upcoming appointments, so that I can be prepared._

**Acceptance Criteria:**  
1. Doctor can access patient details for each appointment.  
2. Patient information is displayed correctly.  
3. Only upcoming appointment details are shown.  

**Priority:** High  
**Story Points:** 4  

**Notes:**  
- Ensure patient data privacy and security.  
