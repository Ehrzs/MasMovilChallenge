# MasMovilChallenge

This challenge respond to the following Mas Movil Challenge
> Phone App
> 
> Exercise 1: Create an endpoint to retrieve the phone catalog, and pricing.
> * it returns a collection of phones, and their prices.
> * Each phone should contain a reference to its image, the name, the description,
> and its price.
> 
> Exercise 2: Create endpoints to check and create an order.
> * receives and order that contains the customer information name, surname, and
> email, and the list of phones that the customer wants to buy.
> * Calculate the total prices of the order.
> * Log the final order to the console.
> 
> Bonus Points: The second endpoint use the first endpoint to validate the order.
> 
> Requirements:
> - It should have test.
> - It should be documented in the readme file.
> - It should be a REST API
> - Docker oriented.
> - Microservice approach.
> - Database access from the microservices.
> - Java 10, .NET, NodeJS, Scala
> 
> Questions
> - How would you improve the system?
> - How would you avoid your order API to be overflow?


Is has three separated projects:

## Server
This is the Eureka Server, in which all microservice will be exposed.

## Phone Service
A microservice with the following features:
- Access to database: It will access to the Phone and Order repositories.
- API Exposure: It will expose a typicall API Rest on the Phone and Order entites: GET, POST, PUT. DELETE not added to simplify.

## Edge Service
This is the discovery client that has acces to the Phone Service and will expose the operations requested in the challenge.

## Questions:

### How would you improve the system?
Probably I'll separate the Phone microservice from the Order microservice.
I'll add a new entity/microservice refering to Customers.

The service probably need some security feature, mostly to Order creation, son I'll use Spring Security.

### How would you avoid your order API to be overflow?
With: [Spring Cloud Zuul Ratelimit](https://github.com/marcosbarbero/spring-cloud-zuul-ratelimit)