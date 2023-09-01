# Project Title
Simple Payment Project

## Description
Simple Payment Application

## Getting Started
### Dependencies
   * Need to install [Docker](docker.org), docker will generates the Postgres image locally and no need to install postgres in local
   * API testing using Postman if needed

### Run Application
  Some command already docummented in Makefile, following to run the program below:
   * Build the project: install dependencies, migrate db structure, and build java
   ```
   make app_build
   ```
   * Export data from ```./query_data.sql``` to Postgres DB
   
   * Start the project:
   ```
   make app_run
   ```

## APIs Specification
### 1. Get Payments
- API: /payments
- Method: GET
- Full URL: /payments?page=&size=&paymentTypeId=&customerId=
#### 1.1 Request parameters:
- ```page```, or offset in pagination -> Mandatory
- ```size```, size of page -> Mandatory
- ```paymentTypeId```, filter of paymentType ID -> Mandatory
- ```customerId```, filter userId/customerId of payment -> Mandatory
- ```paymentId```, filter payment ID -> Optional
- ```date```, filter transaction date -> Optional
#### 1.2 Request Header:
N/A
#### 1.3 Request Body:
N/A
#### 1.4 Sample Responses
##### 1.4.1 Response 200
Success :  
Requests URL : GET -> /payments?page=0&size=1&customerId=1&paymentTypeId=1  
Response:  
```
{
    "data": [
        [
            1,
            13000,
            1,
            "2023-08-27T17:00:00.000+00:00",
            1
        ]
    ],
    "error": null
}
```

##### 1.4.2 Response 404
Failed Validaton, customerId null :  
API : GET -> /payments?page=0&size=1&customerId=&paymentTypeId=1  
Response:  
```
{
   "timestamp": "2023-09-01T10:09:24.422+00:00",
   "status": 400,
   "error": "Bad Request",
   "path": "/payments"
}

```
##### 1.4.3 Response 500
DB service down:  
API : GET -> /payments?page=0&size=1&customerId=1&paymentTypeId=1  
Response:  
```
{
    "data": null,
    "error": "Internal Server Error"
}
```

### 2. Get Payment by ID
- API: /payment/paymentId/{paymentId}
- Method: GET
- Full URL: /payment/paymentId/{paymentId}
#### 2.1 Request parameters (Key URL):
- ```paymentId```, payment ID -> Mandatory
#### 2.2 Request Header:
N/A
#### 2.3 Request Body:
N/A
#### 2.4 Sample Responses
##### 2.4.1 Response 200
Success :  
Requests URL : GET -> /payment/paymentId/1  
Response:  
```
{
    "data": {
        "id": 1,
        "amount": 13000,
        "paymentTypeId": 1,
        "date": "2023-08-27T17:00:00.000+00:00",
        "customerId": 1
    },
    "error": null
}
```

##### 2.4.2 Response 400
Payment ID not found :  
Requests URL : GET -> /payment/paymentId/1000000  
Response:  
```
{
    "data": null,
    "error": "Payment Not Found!"
}
```

##### 2.4.3 Response 404
Payment ID null :  
Requests URL : GET -> /payment/paymentId/  
Response:  
```
{
    "timestamp": "2023-09-01T10:37:05.336+00:00",
    "status": 404,
    "error": "Not Found",
    "path": "/payment/"
}
```

##### 2.4.4 Response 500
DB service down:  
API : GET -> /payments?page=0&size=1&customerId=1&paymentTypeId=1  
Response:  
```
{
    "data": null,
    "error": "Internal Server Error"
}
```

### 3. Create Payment
- API: /payment
- Method: POST
- Full URL: /payment
#### 3.1 Request parameters:
N/A
#### 3.2 Request Header:
- Content-Type: application/json
#### 3.3 Request Body:
- ```paymentTypeId```: String -> Mandatory
- ```itemId```: String -> Mandatory
- ```customerId```: String -> Mandatory
#### 3.4 Sample Responses
##### 3.4.1 Response 200
Success :  
Request Body:
```
{
    "paymentTypeId": "1",
    "itemId": "1",
    "customerId": "1"
}
```
Response:  
```
{
    "data": {
        "id": 36,
        "amount": 500000,
        "paymentTypeId": 1,
        "date": "2023-09-01T10:42:42.817+00:00",
        "customerId": 1
    },
    "error": null
}
```

##### 3.4.2 Response 500
DB Service down :  
Request Body:
```
{
    "paymentTypeId": "1",
    "itemId": "1",
    "customerId": "1"
}
```
Response:  
```
{
    "data": null,
    "error": "Internal Server Error"
}
```

### 4. Update Payment
- API: /payment
- Method: PUT
- Full URL: /payment
#### 4.1 Request parameters:
N/A
#### 4.2 Request Header:
- Content-Type: application/json
#### 4.3 Request Body:
- ```paymentId```: Integer -> Mandatory
- ```paymentTypeId```: String -> Optional
- ```itemId```: String -> Optional
- ```customerId```: String -> Optional
#### 4.4 Sample Responses
##### 4.4.1 Response 200
Success :  
Request Body:
```
{
    "paymentId": null,
    "paymentTypeId": "1",
    "itemId": "1",
    "customerId": "1"
}
```
Response:  
```
{
    "data": {
        "id": 34,
        "amount": 540000,
        "paymentTypeId": 1,
         "date": "2023-09-01T10:28:31.140+00:00",
        "customerId": 1
    },
    "error": null
}
```

##### 4.4.2 Response 400
Payment ID Not Found :  
Request Body:
```
{
    "paymentId": 100000,
    "paymentTypeId": "34",
    "itemId": "1",
    "customerId": "1"
}
```
Response:  
```
{
    "data": null,
    "error": "Payment Not Found!"
}
```

##### 4.4.3 Response 500
DB Service down :  
Request Body:
```
{
    "paymentTypeId": "1",
    "itemId": "1",
    "customerId": "1"
}
```
Response:  
```
{
    "data": null,
    "error": "Internal Server Error"
}
```

### 5. Delete Payment
- API: /payment/{paymentId}
- Method: DELETE
- Full URL: /payment/{paymentId}
#### 5.1 Request parameters (Key URL):
- ```paymentId```: Integer -> Mandatory
#### 5.2 Request Header:
- Content-Type: application/json
#### 5.3 Request Body (Key URL):
N/A
#### 5.4 Sample Responses
##### 5.4.1 Response 200
Success:  
API : DELETE -> /payment/paymentId/34  
Response:  
```
{
    "data": {
        "paymentId": "34"
    },
    "error": null
}
```

##### 5.4.2 Response 400
Payment ID Not Found :  
API : DELETE -> /payment/paymentId/34  
Response:  
```
{
    "data": null,
    "error": "Payment Not Found!"
}
```

##### 5.4.3 Response 500
DB service down :  
API : DELETE -> /payment/paymentId/34 
Response:  
```
{
    "data": null,
    "error": "Internal Server Error"
}
```

## Environment
This application already run in:
- Windows 11 x64 bits
- Maven version: ```Apache Maven 3.8.1```
- Docker version: ```Docker version 24.0.2```
- JDK 20 with Java version:
```
java 20.0.2 2023-07-18
Java(TM) SE Runtime Environment (build 20.0.2+9-78)
Java HotSpot(TM) 64-Bit Server VM (build 20.0.2+9-78, mixed mode, sharing)
```