package com.develhop.customQuery2.flight.repository;


import java.util.List;


import com.develhop.customQuery2.flight.entities.Flight;
import com.develhop.customQuery2.flight.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@EnableJpaRepositories
@Repository
public interface FlightRepo extends JpaRepository<Flight, Long> {

    List<Flight> findByStatus(Status status);
    List<Flight> findByStatusIn(List<Status> statuses);

    @Query(value = "SELECT * FROM Flight", nativeQuery = true)
    List<Flight> findFlights();

    @Query(value = "SELECT * FROM Flight", nativeQuery = true)
    Page<Flight> findAllPaged(Pageable pageable);

    @Query(value = "SELECT * FROM Flight WHERE status = :status", nativeQuery = true)
    List<Flight> findOnTimeFlights(@Param("status") Status status);

    @Query(value = "SELECT * FROM Flight WHERE status = :status1 OR status = :status2", nativeQuery = true)
    List<Flight> findDelayedOrCancelledFlights(@Param("status1") Status status1, @Param("status2") Status status2);

    @Query(value = "SELECT * FROM Flight WHERE description LIKE %:desc%", nativeQuery = true)
    List<Flight> findFlightsByDescription(@Param("desc") String description);
}