# ProSite Backend

This is the backend for the ProSite application, a personal portfolio and blog platform.

## Setup

### Application Properties

1. Copy the `src/main/resources/application.properties.template` file to `src/main/resources/application.properties`.
2. Edit the `application.properties` file and replace the placeholder values with your actual configuration:

   - Database configuration:
     - `spring.datasource.username`: Your PostgreSQL username
     - `spring.datasource.password`: Your PostgreSQL password

   - JWT configuration:
     - `jwt.secret`: A secure random string for JWT token generation

   - Email configuration:
     - `spring.mail.username`: Your Gmail address
     - `spring.mail.password`: Your Gmail app password

**Important:** The `application.properties` file contains sensitive information and is ignored by Git. Never commit this file to the repository. If you need to update the template, modify the `application.properties.template` file instead.

### Database

Ensure you have PostgreSQL installed and running. Create a database named `IanProSite_DB`.

### Running the Application

1. Install dependencies:
   ```
   ./mvnw clean install
   ```

2. Run the application:
   ```
   ./mvnw spring-boot:run
   ```

The application will start on `http://localhost:8080` by default.

## Features

- User authentication and authorization
- Blog post management
- Project showcase
- Email functionality

## Security

- The `application.properties` file containing sensitive information is ignored by Git.
- JWT is used for secure authentication.
- Passwords are securely hashed before storing in the database.

## Contributing

Please read `CONTRIBUTING.md` for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the `LICENSE.md` file for details.
