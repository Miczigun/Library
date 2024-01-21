
# Library

The Library Management System is a comprehensive system designed to manage the operations of a library efficiently. It provides a set of features to streamline processes related to books, members, loans, and more. This project is built with Java, Spring Boot, Hibernate and PostgreSQL ensuring a robust and scalable solution for library management.



## Key Features

- **User Authentication**: Secure user authentication and authorization for members, librarians, and administrators.
- **Book Management**: Add, delete, and update books, including information such as ISBN, title, author, and quantity.
- **Member Management**: Manage library members, including registration, profile details, and address updates.
- **Loan System**: Facilitate book checkouts and returns with due date tracking, and penalty management for late returns.
- **Role-based Access Control**: Different access levels for members, librarians, and administrators to control system functionality.
## Installation

1. Clone the repository

```bash
git clone https://github.com/Miczigun/Library.git
```
2. Create database in PostgresSQL and set in application.properties
```
spring.datasource.url=jdbc:postgresql://localhost:5432/Your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```
3. Run app to create tables in database
4. Run sql script in PostgresSQL to add example data

## Running Tests

In progress :)


