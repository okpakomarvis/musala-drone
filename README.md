# Drones

## Introduction
this a drone webservice delivered via RESTFULL APIs that allow client to communicate with drone webservice. Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access

## Build commands:

- Build:
- Run: ./mvnw spring-boot:run
- Test: ./mvnw test

## Documentation:
***
check the musala.postman_collection.json for all tested APIs and response, the file is in the root folder of this project

### List of APIs
- get all drones
  GET: http://localhost:8080/api/drone
- register a drone
  POST: http://localhost:8080/api/drone/register
- get drone by state
  GET: http://localhost:8080/api/drone/droneByState/
- get Available Drone
  GET: http://localhost:8080/api/drone/availableDrone
- get drone battery capacity
  GET: http://localhost:8080/api/drone/droneCapacity/
- check drone medication
  GET: http://localhost:8080/api/drone/medication/
- register  medication
  POST: http://localhost:8080/api/medication/register
- load drone with medication
  PUT: http://localhost:8080/api/drone/loadDrone