package com.develhop.customQuery2.flight.service;

import com.develhop.customQuery2.flight.entities.Flight;
import com.develhop.customQuery2.flight.entities.Status;
import com.develhop.customQuery2.flight.repository.FlightRepo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FlightService {
    @Autowired
    private FlightRepo flightRepo;

    public List<Flight> findAll() {
        return flightRepo.findAll();
    }

    public List<Flight> findAllProvision(int n) {
        Random random = new Random();
        List<Flight> flights = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            Flight flight = new Flight();
            flight.setDescription("Flight " + i);
            flight.setFromAirport(generateRandomString(random));
            flight.setToAirport(generateRandomString(random));
            flight.setStatus(generateRandomStatus());
            flights.add(flight);
        }
        flightRepo.saveAll(flights);
        return flights;
    }

    private String generateRandomString(Random random) {
        return IntStream.range(0, 5)
                .mapToObj(i -> String.valueOf((char) ('A' + random.nextInt(26))))
                .collect(Collectors.joining());
    }

    private Status generateRandomStatus() {
        return Status.values()[new Random().nextInt(Status.values().length)];
    }

    public List<Flight> findOnTimeFlights() {
        return flightRepo.findByStatus(Status.ONTIME);
    }

    public Page<Flight> getPagedFlights(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return flightRepo.findAll(pageable);
    }

    public List<Flight> getDelayedOrCancelledFlights() {
        List<Status> statuses = Arrays.asList(Status.DELAYED, Status.CANCELLED);
        return flightRepo.findByStatusIn(statuses);
    }
    public List<Flight> getCustomStatusFlights(Status p1, Status p2) {
        List<Status> statuses = Arrays.asList(p1, p2);
        return flightRepo.findByStatusIn(statuses);
    }
}