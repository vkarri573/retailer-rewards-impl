# Retailer Rewards API
## Introduction

Rewards API allows the following main functionality:
1) Get customer total reward points
2) Get customer monthly reward points
3) Get all customers total reward points
4) Get all customers monthly reward points
5) Create a transaction

## Steps to build and run the unit tests

1) Clone the project
   >git clone https://github.com/vkarri573/retailer-rewards-impl.git

2) Once project is cloned, go to project folder 'retailer-rewards-impl'
   >cd retailer-rewards-impl

3) Run maven test goal to run unit tests
   >mvn clean test

## Steps to run the application

1) Once project is cloned, run maven package goal at project folder 'retailer-rewards-impl'
   >mvn clean package

2) Go to folder 'target'
   >cd target

3) Run below command to start the application
   >java -jar retailer-rewards-impl-0.0.1-SNAPSHOT.jar

## API endpoints

### Get customer total reward points

1) Endpoint URL: http://localhost:8080/rewards/customers/1
2) HTTP method: GET
3) Response code: 200
4) Sample response: 

   {
   "custId": 1,
   "custName": "Venu",
   "totalRewards": 823
   }

### Get customer monthly reward points

1) Endpoint URL: http://localhost:8080/rewards/customers/1/month
2) HTTP method: GET
3) Response code: 200
4) Sample response:

   {
   "custId": 1,
   "custName": "Venu",
   "totalRewards": 823,
   "monthlyRewards": {
   "MARCH": 481,
   "APRIL": 342
   }
   }

### Get all customers total reward points

1) Endpoint URL: http://localhost:8080/rewards/customers
2) HTTP method: GET
3) Response code: 200
4) Sample response:

   [
   {
   "custId": 1,
   "custName": "Venu",
   "totalRewards": 823
   },
   {
   "custId": 2,
   "custName": "Babu",
   "totalRewards": 1428
   }
   ]

### Get all customers monthly reward points

1) Endpoint URL: http://localhost:8080/rewards/customers/month
2) HTTP method: GET
3) Response code: 200
4) Sample response:

   [
   {
   "custId": 1,
   "custName": "Venu",
   "totalRewards": 823,
   "monthlyRewards": {
   "MARCH": 481,
   "APRIL": 342
   }
   },
   {
   "custId": 2,
   "custName": "Babu",
   "totalRewards": 1428,
   "monthlyRewards": {
   "MARCH": 1428,
   "APRIL": 0
   }
   }
   ]

### Create a transaction

1) Endpoint URL: http://localhost:8080/transactions
2) HTTP method: POST
3) Request headers:
      ["Content-Type" : "application/json"]
4) JSON payload:

   {
   "custId": 2,
   "custName": "Babu",
   "amount": 156.00,
   "date": "2022-05-28"
   }

5) Sample response:

   {
   "id": 7,
   "custId": 2,
   "custName": "Babu",
   "amount": 156.0,
   "date": "2022-05-28"
   }









