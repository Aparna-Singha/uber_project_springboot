package org.example.rideshare.service;

import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.exception.BadRequestException;
import org.example.rideshare.exception.NotFoundException;
import org.example.rideshare.model.Ride;
import org.example.rideshare.model.User;
import org.example.rideshare.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserService userService;

    public RideResponse createRide(CreateRideRequest request, String username) {
        User user = userService.findByUsername(username);
        
        Ride ride = new Ride(user.getId(), request.getPickupLocation(), request.getDropLocation());
        ride = rideRepository.save(ride);
        
        return new RideResponse(ride);
    }

    public List<RideResponse> getUserRides(String username) {
        User user = userService.findByUsername(username);
        List<Ride> rides = rideRepository.findByUserId(user.getId());
        
        return rides.stream()
                .map(RideResponse::new)
                .collect(Collectors.toList());
    }

    public List<RideResponse> getPendingRides() {
        List<Ride> rides = rideRepository.findByStatus("REQUESTED");
        
        return rides.stream()
                .map(RideResponse::new)
                .collect(Collectors.toList());
    }

    public RideResponse acceptRide(String rideId, String driverUsername) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));
        
        if (!ride.getStatus().equals("REQUESTED")) {
            throw new BadRequestException("Ride is not in REQUESTED status");
        }
        
        User driver = userService.findByUsername(driverUsername);
        
        ride.setDriverId(driver.getId());
        ride.setStatus("ACCEPTED");
        ride = rideRepository.save(ride);
        
        return new RideResponse(ride);
    }

    public RideResponse completeRide(String rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));
        
        if (!ride.getStatus().equals("ACCEPTED")) {
            throw new BadRequestException("Ride must be in ACCEPTED status to complete");
        }
        
        ride.setStatus("COMPLETED");
        ride = rideRepository.save(ride);
        
        return new RideResponse(ride);
    }
}
