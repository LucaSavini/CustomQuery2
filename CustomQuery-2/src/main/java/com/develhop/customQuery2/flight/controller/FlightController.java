package com.develhop.customQuery2.flight.controller;

import com.develhop.customQuery2.flight.service.FlightService;
import com.develhop.customQuery2.flight.entities.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.develhop.customQuery2.flight.entities.Status;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/show/list")
    public List<Flight> showList() {
        return flightService.findAll();
    }

    @GetMapping("/generate/list")
    public List<Flight> generateList(@RequestParam(name = "n", defaultValue = "100") int n) {
        return flightService.findAllProvision(n);
    }

    @GetMapping("/show/on-time")
    public List<Flight> showOnTime() {
        return flightService.findOnTimeFlights();
    }

    @GetMapping("/paged")
    public Page<Flight> getPagedFlights(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "10") int size) {
        return flightService.getPagedFlights(page, size);
    }

    @GetMapping("/show/delayedcancelled")
    public List<Flight> getDelayedOrCancelledFlights() {
        return flightService.getDelayedOrCancelledFlights();
    }

    @GetMapping("/show/customstatus")
    public List<Flight> getCustomStatusFlights(@RequestParam(name = "p1") String p1,
                                               @RequestParam(name = "p2") String p2) {
        Status status1 = Status.valueOf(p1); // Converte la stringa in Status
        Status status2 = Status.valueOf(p2); // Converte la stringa in Status
        return flightService.getCustomStatusFlights(status1, status2);
    }
}