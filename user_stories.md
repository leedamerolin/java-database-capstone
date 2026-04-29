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