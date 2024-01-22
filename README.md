Project Name

StoreXperience Management System

 Overview

The Customer Controller facilitates user registration, customer retrieval, and searching by email. Users can register, explore the list of customers, and find specific customers based on their email addresses.

The Product Controller empowers administrators to oversee products effectively. It supports operations such as retrieving all products, finding products by name, obtaining stores associated with a product, and enabling users to rate products. This functionality enhances the overall product management experience.

The Store Controller is responsible for store-related operations, offering endpoints to list all stores, find a store by ID, and rate a store. This functionality provides a means for users to rate their shopping experiences and contributes to store management.

The Order Controller manages customer orders, allowing users to retrieve all orders, find orders by product name, and find orders by customer name. This functionality ensures efficient order tracking and management

Technologies Used

List the main technologies and frameworks used in your project.

- Spring Boot
- Spring Data JPA

Programimg Language
 	- Java

 Getting Started

Prerequisites

Make sure you have the following tools and configurations installed on your machine:

- Java (JDK)
- Maven
- IDE (IntelliJ, Visual Studio Code, Eclipse,  anything is fine etc.)
- MySQL Workbench

 Installation

1. Clone the repository.
2. Open the project in your preferred IDE.
3. Configure your IDE to use Java and Maven.
4. Connect MySQL Workbench to your local MySQL Server. 
5. Create a schema named "db_prod_data" in MySQL Workbench.

Running the Project

1. Build and run the project using Maven in your IDE.
2. The application will connect to the local MySQL database.
3. Once the project is running, access Swagger at [http://localhost:8094/store/swagger-ui.html](http://localhost:8094/store/swagger-ui.html).

API Endpoints

 Customer Controller

- POST /api/customers/register: Register a new customer.
- GET /api/customers: Get a list of all customers.
- GET /api/customers/by-email: Get a customer by email.

 Product Controller

- GET /api/products: Get a list of all products.
- GET /api/products/{name}: Get products by name containing.
- GET /api/products/{id}/stores: Get stores associated with a product.
- POST /api/products/{id}/rating: Rate a product.

 Store Controller

- GET /api/stores: Get a list of all stores.
- GET /api/stores/{id}: Get a store by ID.
- POST /api/stores/{id}/rating: Rate a store.

 Order Controller

- GET /api/orders: Get a list of all orders.
- GET /api/orders/{name}: Get orders by product name.
- GET /api/orders/customers/{name}: Get orders by customer name.

 Interacting with Swagger

1. Access Swagger UI at [http://localhost:8094/store/swagger-ui.html](http://localhost:8094/store/swagger-ui.html).
2. Explore and interact with the API endpoints using Swagger.
<img width="1461" alt="image" src="https://github.com/MaramKrishnaveni/productStoreUpdatedHcl/assets/45233676/20cabf9b-b78f-411c-b37d-279420041335">

<img width="1451" alt="image" src="https://github.com/MaramKrishnaveni/productStoreUpdatedHcl/assets/45233676/969eba56-5e6b-4422-b5fc-212d4eee9955">




