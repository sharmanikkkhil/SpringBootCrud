# Spring Boot CRUD Application

This is a Spring Boot application for basic CRUD operations on categories and products. It demonstrates how to create, read, update, and delete categories and products using Spring Boot, JPA, and a relational database.

## Features

- **CRUD Operations for Categories**:
  - Create a new category
  - Read all categories (paginated or complete list)
  - Update an existing category
  - Delete a category

- **CRUD Operations for Products**:
  - Create a new product
  - Read all products (paginated or complete list)
  - Update an existing product
  - Delete a product

- **Pagination**:
  - Server-side pagination for categories and products

- **Category-Product Relationship**:
  - Each product is associated with a category
  - Fetching a product includes category details

## Technologies Used

- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Lombok (for reducing boilerplate code)
- H2 Database (for in-memory testing, if needed)

## Getting Started

### Prerequisites

- Java 17 or later
- Maven 3.8.6 or later
- PostgreSQL (or other relational databases if configured)
- IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/sharmanikkhil/SpringBootCrud.git
