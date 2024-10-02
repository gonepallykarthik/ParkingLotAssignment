package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.Gate;
import com.scaler.parking_lot.models.ParkingFloor;
import com.scaler.parking_lot.models.ParkingLot;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParkingLotRepositoyImp implements ParkingLotRepository{
    private List<ParkingLot> parkingLotList = new ArrayList<>();

    @Override
    public Optional<ParkingLot> getParkingLotByGateId(long gateId) {
        for(ParkingLot lot : parkingLotList) {
            for(Gate gates : lot.getGates()) {
                if (gates.getId() == gateId) {
                    return Optional.of(lot);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<ParkingLot> getParkingLotById(long id) {
        for(ParkingLot lot : parkingLotList) {
            if(lot.getId() == id) {
                return Optional.of(lot);
            }
        }

        return Optional.empty();
    }

    @Override
    public ParkingLot save(ParkingLot parkingLot) {
        parkingLotList.add(parkingLot);
        return parkingLot;
    }
}
