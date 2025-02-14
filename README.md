# IT Patagonia backend challenge - Company Management API

## Overview
This API allows managing companies and retrieving information about their adhesion and activity. It includes endpoints for fetching last month's adhered companies, last month's active companies, and creating a new company adhesion.

## Endpoints

### 1. **Get Last Month's Adhered Companies**
- **URL**: `http://localhost:8099/companies/last-adhered` (the default port is set to 8099, feel free to adjust if necessary in the `application.yaml` file)
- **Method**: `GET`
- **Description**: retrieves a list of companies that adhered in the previous month.
- **Response**:
    - `200 OK`: a list of companies that adhered last month.
    - **Response Body**: a list of `CompanyResponseDto` objects.

#### Example Request:

GET `http://localhost:8099/companies/last-adhered`

Response:
```[
  {
    "razonSocial": "My Company S.A.",
    "fechaAdhesion": "2025-01-15",
    "transacciones": [
      {
        "importe": 5000.00,
        "empresaId": "30-12345678-9",
        "cuentaDebito": "12345678",
        "cuentaCredito": "87654321"
      }
    ]
  },
  {
    "razonSocial": "My Other Company S.A.",
    "fechaAdhesion": "2025-01-20",
    "transacciones": []
  }
]
```

### 2. **Get Last Month's Active Companies**
- **URL**: `http://localhost:8099/companies/last-month-active` 
- **Method**: `GET`
- **Description**: retrieves a list of companies that made at least 1 transaction in the last month.
- **Response**:
    - `200 OK`: a list of companies that adhered last month.
    - **Response Body**: a list of `CompanyResponseDto` objects.

#### Example Request:

GET `http://localhost:8099/companies/last-month-active`

Response: `(same as previous GET response)`

### 3. **Create a new company adhesion**
- **URL**: `http://localhost:8099/companies`
- **Method**: `POST`
- **Description**: creates a new company adhesion (without a transaction).
- **Response**:
    - `201 Created`

#### Example Request:

POST `http://localhost:8099/companies`

Request body:

```
{
    "cuit": "30-12335608-9",
    "companyName": "My name S.A", 
    "registrationDate": "2025-01-02"
}
``` 

Notes on creating a new adhesion: 
- All fields are required
- The cuit must comply with the following numeric format `XX-XXXXXXXX-X`
- The registration date must comply with the following numeric format `yyyy-MM-dd`
- Before creating a new adhesion the logic checks if the field `cuit` in the request body has already an existing record in the database, if it does an exception will be thrown and the record will not be inserted


### TESTING LOCALLY

When you run the application an in-memory H2 database is created with some records in both tables (`company` and `transaction`). This is due to the fact that since there are no endpoints to create `transactions` we need some records to test the `GET` endpoints.

You can access the web console for H2 in `http://localhost:8099/h2-console`, and once you are there you can execute the following queries to check the existing records in each table:

- select * from "company";

- select * from "transaction";
