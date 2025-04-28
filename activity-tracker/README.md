# A-track - Backend

## Getting Started
- Cd to */src/main/resources/* and create file named *env.properties* and change this to your values:
  ```
  DATABASE_USERNAME=[your_db_username]
  DATABASE_PASSWORD=[your_db_password]
  SECRET_KEY=[your_secret_key]
  ```
  For a secret key you can create and copy a random key from any site like [this](https://randomkeygen.com)

- Go back to root and run docker file:
  ```
  docker-compose up -d
  ```

- Navigate to backend directory:
  ```
  cd activity-tracker
  ```

- Install dependecies:
  ```
  mvn clean install
  ```

- Run the project:
  ```
  mvn spring-boot:run
  ```

Application will run on port: 8088

## Usage

- To see the swagger documentation go to this address:
  ```
  http://localhost:8088/api/v1/swagger-ui/index.html
  ```
  
- To use Postman in collections authorization section select *Bearer Token* as type and input a token which will be printed in terminal on last line if project is run correctly. Token is of role ADMIN. Alternatively you can go throught authorization process in Postman:
  
1. Run `/auth/register` with valid body
2. Go to `http://localhost:1080/` (MailDev port) and copy validation token and input it in `/auth/activate-account`
3. Run `/auth/authenticate` and use token as mentioned above. This token is of role USER

- To run tests:
  ```
  mvn test
  ```

  
    
