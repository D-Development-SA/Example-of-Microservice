# Example of microservice with Spring Boot
## Technologies and tools
- Intellij idea
-	Maven
-	H2 database
-	Spring boot, spring cloud, spring security, spring gateway
-	Postman
-	Navegador Vivaldi

## How to execute the project
1. Should be placed in the Technical Test project path
2. Open a console in the location
3. Run one of these commands _mvn install_ or _mvn spring-boot:run_
4. Wait for it to finish.

## Description
The purpose of this project is to create a series of microservices that communicate with each other where each one fulfills a single responsibility. User authentication is handled in a microservice that is consulted by the GateWay (the only one that has access to the other microservices). All are registered in NamingService (Eureka) except CloudConfigServer, which has the microservices configurations. The essential idea is to perform a CRUD to the WasteManager microservice that controls WasteManagerAddressService.

## Guide
It explains step by step how to perform the operation of creating a WasteManager. The other queries are similar.

**1-Register**: You must create a user if you have not already done so.

_POST http://localhost:8800/auth/register_

Body:
```
{
  “username” : ”usuario”,
  “password” : ”password”
}
```

**2-Login**

_POST http://localhost:8800/auth/login_

Body:
```
{
  “username” : ”usuario”,
  “password” : ”password”
}
```

**3-Query**: Once you have logged in you can make any query, the authentication token is a cookie that was stored. In this case we will use create a user.

_POST: http://localhost:8800/api/wasteManager/create_

Body:
```
{
  "name": "John Doe",
  "nif": "12345678A",
  "isEnabled": true,
  "version": 41,
  "wasteManagerAddressEntity": {
    "address": "123 Main St",
    "isEnabled": true,
    "version": 1
  },
  "wasteCenterAuthorizationEntities": [{},{},{}]
}
```

4-If you want to see all the stored WasteManager you can do a GET http://localhost:8800/api/wasteManager
