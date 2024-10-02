package com.scaler.parking_lot.services;

import com.scaler.parking_lot.exceptions.InvalidParkingLotException;
import com.scaler.parking_lot.models.*;
import com.scaler.parking_lot.respositories.ParkingLotRepository;

import java.util.*;

public class ParkingLotServiceImp implements ParkingLotService{
    private ParkingLotRepository parkingLotRepository;

    public ParkingLotServiceImp(ParkingLotRepository parkingLotRepository){
        this.parkingLotRepository = parkingLotRepository;
    }

    @Override
    public Map<ParkingFloor, Map<String, Integer>> getParkingLotCapacity(long parkingLotId, List<Long> parkingFloors, List<VehicleType> vehicleTypes) throws InvalidParkingLotException, InvalidParkingLotException {
        Optional<ParkingLot> parkingLotOptional = parkingLotRepository.getParkingLotById(parkingLotId);

        if (parkingLotOptional.isEmpty()) {
            throw new InvalidParkingLotException("Parking Lot not Found! ");
        }
        ParkingLot parkingLot = parkingLotOptional.get();
        Map<ParkingFloor, Map<String, Integer>> data = new HashMap<>();

        // parking floorsId is empty and vehicleTypes is also Empty
        if (parkingFloors.isEmpty() && vehicleTypes.isEmpty()) {
            List<ParkingFloor> parkingFloorList = parkingLot.getParkingFloors();
            for (ParkingFloor floor : parkingFloorList) {
                data.put(floor, new HashMap<>());
                for (ParkingSpot spot : floor.getSpots()) {
                    VehicleType supportedVehicleType = spot.getSupportedVehicleType();
                    Map<String, Integer> vehiclesAndTheirCount = data.get(floor);
                    vehiclesAndTheirCount.put(supportedVehicleType.toString(), vehiclesAndTheirCount.getOrDefault(supportedVehicleType.toString(), 0) + 1);
                }
            }

            return data;
        }

        // if parking floorsIDs and vehicleTypes are both present
        else if (!parkingFloors.isEmpty() && !vehicleTypes.isEmpty()) {
            List<ParkingFloor> parkingFloorList = parkingLot.getParkingFloors();
            Set<Long> floorIds = new HashSet<>();
            Set<VehicleType> clientVehicleTypes = new HashSet<>();

            for (VehicleType type : vehicleTypes) {
                clientVehicleTypes.add(type);
            }

            List<ParkingFloor> clientParkingFloorIds = new ArrayList<>();
            for (Long floorId : parkingFloors) {
                floorIds.add(floorId);
            }

            for (ParkingFloor floor : parkingFloorList) {
                if (floorIds.contains(floor.getId())) {
                    clientParkingFloorIds.add(floor);
                }
            }

            for (ParkingFloor floor : clientParkingFloorIds) {
                data.put(floor, new HashMap<>());
                for (ParkingSpot spot : floor.getSpots()) {
                    if (clientVehicleTypes.contains(spot.getSupportedVehicleType())) {
                        Map<String, Integer> vehicleCounts = data.get(floor);
                        vehicleCounts.put(spot.getSupportedVehicleType().toString(), vehicleCounts.getOrDefault(spot.getSupportedVehicleType().toString(), 0) + 1);
                    }
                }
            }

            return data;
        }

        // If the request includes only parking floor ids, the response should contain the capacity of all vehicle types in the given parking floors
        else if (!parkingFloors.isEmpty() && vehicleTypes.isEmpty()) {
            List<ParkingFloor> parkingFloorList = parkingLot.getParkingFloors();
            Set<Long> floorIds = new HashSet<>();

            List<ParkingFloor> clientParkingFloorIds = new ArrayList<>();
            for (Long floorId : parkingFloors) {
                floorIds.add(floorId);
            }

            for (ParkingFloor floor : parkingFloorList) {
                if (floorIds.contains(floor.getId())) {
                    clientParkingFloorIds.add(floor);
                }
            }

            for (ParkingFloor floor : clientParkingFloorIds) {
                data.put(floor, new HashMap<>());
                for (ParkingSpot spot : floor.getSpots()) {
                    Map<String, Integer> vehicleCounts = data.get(floor);
                    vehicleCounts.put(spot.getSupportedVehicleType().toString(), vehicleCounts.getOrDefault(spot.getSupportedVehicleType().toString(), 0) + 1);
                }
            }
            return data;
        }

        // If the request includes only vehicle types, the response should contain the capacity of the given vehicle types in all parking floors.
        else if (parkingFloors.isEmpty() && !vehicleTypes.isEmpty()) {
            List<ParkingFloor> parkingFloorList = parkingLot.getParkingFloors();
            Set<VehicleType> clientVehicleTypes = new HashSet<>();

            for (VehicleType type : vehicleTypes) {
                clientVehicleTypes.add(type);
            }


            for (ParkingFloor floor : parkingFloorList) {
                data.put(floor, new HashMap<>());
                for (ParkingSpot spot : floor.getSpots()) {
                    if (clientVehicleTypes.contains(spot.getSupportedVehicleType())) {
                        Map<String, Integer> vehicleCounts = data.get(floor);
                        vehicleCounts.put(spot.getSupportedVehicleType().toString(), vehicleCounts.getOrDefault(spot.getSupportedVehicleType().toString(), 0) + 1);
                    }
                }
            }

            return data;
        }

        return data;
    }
}
