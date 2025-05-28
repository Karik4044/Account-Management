# Account-Management

A Java-based desktop application for managing user accounts, built with JavaFX and Maven.

## Features

*   User registration and login.
*   View a list of user accounts.
*   Add, update, and delete user accounts.
*   Search and filter user accounts.
*   Password visibility toggle for input fields and table views.
*   (Add other specific features of your application here)

## Technologies Used

*   Java
*   JavaFX
*   Maven
*   MySQL (or your specific database)

## Prerequisites

*   JDK (Java Development Kit) - Version 11 or higher recommended.
*   Apache Maven
*   MySQL Server (or your chosen database server)

## Setup & Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/Karik4044/Account-Management
    cd Account-Management
    ```

2.  **Database Setup:**
    *   Ensure your MySQL server (or other database) is running.
    *   Create a database for the application (e.g., `account_management_db`).
    *   Import the necessary schema/tables. You might need to provide a `.sql` script for this or describe the table structures.
    *   Update the database connection details in the application. This is typically in a configuration file or directly in the `DatabaseConnection.java` or `DAO` classes.
        *   File: `src/main/java/com/example/quanlytaikhoan/Database/DatabaseConnection.java` (or relevant DAO files)
        *   Modify: `url`, `user`, `password` variables to match your database setup.

3.  **Build the project using Maven:**
    ```bash
    mvn clean install
    ```

## Running the Application

After a successful build, you can run the application using Maven:

```bash
mvn javafx:run
