package com.scaler.parking_lot.controllers;

import com.scaler.parking_lot.dtos.GetParkingLotCapacityRequestDto;
import com.scaler.parking_lot.dtos.GetParkingLotCapacityResponseDto;
import com.scaler.parking_lot.dtos.Response;
import com.scaler.parking_lot.dtos.ResponseStatus;
import com.scaler.parking_lot.exceptions.GetParkingLotRequestValidationException;
import com.scaler.parking_lot.models.ParkingFloor;
import com.scaler.parking_lot.models.VehicleType;
import com.scaler.parking_lot.services.ParkingLotService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    public GetParkingLotCapacityResponseDto getParkingLotCapacity(GetParkingLotCapacityRequestDto request) {
        GetParkingLotCapacityResponseDto response = new GetParkingLotCapacityResponseDto();
        try {
            List<VehicleType> vehicleTypes = new ArrayList<>();
            for (String val : request.getVehicleTypes()) {
                vehicleTypes.add(VehicleType.valueOf(val));
            }

            Map<ParkingFloor, Map<String, Integer>> data = parkingLotService.getParkingLotCapacity(request.getParkingLotId(), request.getParkingFloorIds(), vehicleTypes);
            response.setResponse(new Response(ResponseStatus.SUCCESS, "Succesfully fetched data"));
            response.setCapacityMap(data);
        } catch (Exception e) {
            response.setCapacityMap(null);
            response.setResponse(new Response(ResponseStatus.FAILURE, "Invalid"));
        }

        return response;
    }

}
