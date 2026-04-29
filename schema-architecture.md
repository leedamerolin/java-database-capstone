This Spring Boot application uses both MVC and REST controllers to serve different types of users and clients. Thymeleaf templates are used for server-rendered Admin and Doctor dashboards, delivering dynamic HTML pages directly to the browser. REST APIs serve all other modules — including Appointments, PatientDashboard, and PatientRecord — returning JSON responses to API consumers such as mobile apps or frontend clients.

The application interacts with two databases to handle different data needs. MySQL stores structured, relational data for entities such as Patient, Doctor, Appointment, and Admin using Spring Data JPA. MongoDB stores flexible, document-based data such as Prescriptions using Spring Data MongoDB. All controllers route requests through a shared service layer that applies business logic and validation, which in turn delegates data access to the appropriate repositories. MySQL repositories use JPA entities annotated with @Entity, while MongoDB repositories use document models annotated with @Document.

1. User accesses the application — either through a browser via the
   AdminDashboard or DoctorDashboard, or through a REST API client
   such as a mobile app accessing Appointments, PatientDashboard,
   or PatientRecord modules.

2. The incoming request is routed to the appropriate controller based
   on the URL path and HTTP method — Thymeleaf Controllers handle
   browser-based requests, while REST Controllers handle API-based
   requests expecting JSON responses.

3. The controller validates the incoming request and delegates
   the business logic to the Service Layer, which acts as the
   core of the application — applying rules, coordinating workflows,
   and ensuring clean separation from data access logic.

4. The service layer calls the appropriate Repository Layer to
   perform data access operations — MySQL Repositories using
   Spring Data JPA for relational data, or the MongoDB Repository
   using Spring Data MongoDB for document-based data.

5. The repository communicates directly with the underlying database —
   MySQL for structured entities such as Patient, Doctor, Appointment,
   and Admin, and MongoDB for flexible nested data such as Prescriptions.

6. Retrieved data is mapped into Java model classes — JPA entities
   annotated with @Entity for MySQL data, and document objects
   annotated with @Document for MongoDB data — providing a consistent
   object-oriented representation across all application layers.

7. The bound models are used to build the final response — in MVC
   flows, models are passed to Thymeleaf templates and rendered as
   dynamic HTML for the browser; in REST flows, models or DTOs are
   serialized into JSON and returned to the API client, completing
   the request-response cycle.