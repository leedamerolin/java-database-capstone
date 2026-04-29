# Schema Design

---

## MySQL Database Design

### Table: patients
- id: INT, Primary Key, Auto Increment
- name: VARCHAR(100), Not Null
- email: VARCHAR(100), Unique, Not Null
- phone: VARCHAR(15), Not Null
- created_at: DATETIME

Notes:
- Email should be unique for each patient.
- Patient data is core and should not be deleted frequently.

---

### Table: doctors
- id: INT, Primary Key, Auto Increment
- name: VARCHAR(100), Not Null
- specialization: VARCHAR(100), Not Null
- email: VARCHAR(100), Unique, Not Null
- phone: VARCHAR(15), Not Null

Notes:
- Specialization helps patients filter doctors.
- Email uniqueness ensures no duplicate doctor records.

---

### Table: appointments
- id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id)
- patient_id: INT, Foreign Key → patients(id)
- appointment_time: DATETIME, Not Null
- status: INT (0 = Scheduled, 1 = Completed, 2 = Cancelled)

Notes:
- A doctor should not have overlapping appointments.
- If a patient is deleted, appointments may be retained for history.

---

### Table: admin
- id: INT, Primary Key, Auto Increment
- username: VARCHAR(50), Unique, Not Null
- password: VARCHAR(255), Not Null

Notes:
- Password should be stored in encrypted format.
- Admin manages doctors and system data.

---

### Table: clinic_locations
- id: INT, Primary Key, Auto Increment
- name: VARCHAR(100), Not Null
- address: VARCHAR(255), Not Null

Notes:
- Used if system supports multiple clinic branches.

---

### Table: payments
- id: INT, Primary Key, Auto Increment
- patient_id: INT, Foreign Key → patients(id)
- amount: DECIMAL(10,2), Not Null
- payment_date: DATETIME
- status: VARCHAR(50)

Notes:
- Used for tracking billing and payment history.

---

## MongoDB Collection Design

### Collection: prescriptions

```json
{
  "_id": "ObjectId('64abc123456')",
  "patientId": 12,
  "doctorId": 5,
  "appointmentId": 101,
  "medications": [
    {
      "name": "Paracetamol",
      "dosage": "500mg",
      "frequency": "Twice a day"
    },
    {
      "name": "Vitamin C",
      "dosage": "250mg",
      "frequency": "Once a day"
    }
  ],
  "doctorNotes": "Take medications after food.",
  "createdAt": "2026-04-29T10:30:00Z",
  "pharmacy": {
    "name": "City Pharmacy",
    "location": "Downtown"
  },
  "tags": ["fever", "general"],
  "refillAllowed": true
}