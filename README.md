# Display parking lot capacity

## Problem Statement
We want to create a functionality using which can display the capacity of the given parking lot. This functionality will be used by various displays installed across the parking lot to display the capacity of the parking lot.


## Requirements
The functionality should give the overall capacity of the parking lot.
This functionality also supports filters:
- Parking floor ids: The response should contain the capacity of the given parking floors.
- Vehicle types: The response should contain details about given vehicle types.
  If the request includes both parking floor ids and vehicle types, the response should contain the capacity of the given vehicle types in the given parking floors.
  If the request includes only parking floor ids, the response should contain the capacity of all vehicle types in the given parking floors.
  If the request includes only vehicle types, the response should contain the capacity of the given vehicle types in all parking floors.
  If the request does not include any parking floor ids or vehicle types, the response should contain the capacity of all vehicle types in all parking floors.

Request will contain the following things:
- Parking lot id
- List of parking floor ids (optional)
- List of vehicle types (optional)

The response should contain Map<ParkingFloor, Map<String, Integer>>, outer map key is the parking floor,
inner map key is the vehicle type and value is the capacity of the given vehicle type in the given parking floor.

## Instructions
1. Refer the dtos package for input and output objects.
2. Implement the `getParkingLotCapacity` inside the `ParkingLotController` class.
3. Refer the `model` package.
4. Implement the `ParkingLotService` interface and the repositories to achieve the desired result.
5. Refer the `ParkingLotControllerTest` class for the set of test cases that you can run to verify your implementation. Your implementation should pass all the test cases.
6. Do not modify existing methods for interfaces, feel free to add new methods if required.