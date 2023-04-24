# DynaTrace-Backend-Task

## Introduction

This project has been created as a task for DynaTrace Gdańsk internship 2023. It is a simple runnable local server created in Java using Spring. It takes data from requests to http://api.nbp.pl/ and displays it plainly in browser after using correct URL or by using SwaggerUI and filling input fields.

## Starting the application

The application can be started in 3 ways:

### Using .jar file

navigate to: `DynatraceTask/`

type given command in terminal: `java -jar dynatrace-backed-task.jar`
or execute file by clicking on it

### Using docker
To create container, image and start docker you need to type a command inside `DynaTrace-Backend-Task/` directory:

`docker compose up --build`

It creates image, container and starts the application on port `8080`

### Using IDE

Open project source code in IDE, then compile and run application.

## Using the API
When the server has started, 4 endpoints are open:

1. provides currency's average exchange rate for given code and date:
```
http://localhost:8080/api/exchanges/{code}/{effectiveDate}
```
2. provides currency's min and max average exchange rate for given code and number of last quotation:
```
http://localhost:8080/api/exchanges/{code}/last/{quotations}
```
3. provides currency's the biggest difference between ask and buy rates for given code and number of last quotation:
```
http://localhost:8080/api/exchanges/{code}/bid-ask/{quotations}
```
4. provides SwaggerUI for a simple front-end
```
http://localhost:8080/swagger-ui/index.html#/
```


## Testing the application
- To query operation 1, open given URL in browser (which should have the value 4.2024 as the returning information):
```
http://localhost:8080/api/exchanges/usd/2023-04-20
```
- To query operation 2, open given URL in browser(:
```
http://localhost:8080/api/exchanges/GBP/last/20
```
expected result is:
```json
{
  "minimum average value": 5.2086,
  "maximum average value": 5.3648
}
```
*values may vary when checking after 2023-04-24 due to different dates being taken in request*


- To query operation 3 open given URL in browser (which should have the value 0.11440039 as the returning information):
```
http://localhost:8080/api/exchanges/GBP/bid-ask/200
```
*value may vary when checking after 2023-04-24 due to different dates being taken in request*


- each of those operations could be executed by using SwaggerUI at address:
```
http://localhost:8080/swagger-ui/index.html#/
```
and by selecting correct request and filling the required inputs

## Additional features

- Implementation of SwaggerUI
- Running application with Docker
- Custom error handling when incorrect data has been given 
